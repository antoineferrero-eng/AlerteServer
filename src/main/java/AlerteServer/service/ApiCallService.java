package AlerteServer.service;

import AlerteServer.entity.Alerte;
import AlerteServer.entity.Bulletin;
import AlerteServer.entity.Daily_meteo;
import AlerteServer.entity.Departement;
import AlerteServer.repository.AlerteRepository;
import AlerteServer.repository.BulletinRepository;
import AlerteServer.repository.Daily_meteoRepository;
import AlerteServer.repository.DepartementRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ApiCallService {

    private static final Logger log = LoggerFactory.getLogger(ApiCallService.class);

    private final BulletinRepository bulletinRepository;
    private final AlerteRepository alerteRepository;
    private final DepartementRepository departementRepository;
    private final Daily_meteoRepository dailyMeteoRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ApiCallService(BulletinRepository bulletinRepository,
                          AlerteRepository alerteRepository,
                          DepartementRepository departementRepository,
                          Daily_meteoRepository dailyMeteoRepository,
                          WebClient.Builder webClientBuilder,
                          ObjectMapper objectMapper) {
        this.bulletinRepository = bulletinRepository;
        this.alerteRepository = alerteRepository;
        this.departementRepository = departementRepository;
        this.dailyMeteoRepository = dailyMeteoRepository;
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    //@Scheduled(fixedRate = 3600000)
    public void runDailyImport() {
        log.info("Lancement de runDailyImport");
        try {
            JsonNode data = fetchVigilanceData();
            if (data != null) {
                processAndSaveVigilanceData(data);
                fetchAndSaveAllDailyMeteo();
                log.info("Importation complete terminee avec succes");
            }
        } catch (Exception e) {
            log.error("Erreur dans runDailyImport", e);
        }
    }

    private JsonNode fetchVigilanceData() {
        String username = "bjEoVZLQFh0NXoftNKNKdSK4Zcoa";
        String password = "GGYTw2sjqC5twSV5Kph3TNwAHpka";
        String auth = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");

        JsonNode tokenResponse = webClient.post()
                .uri("https://portail-api.meteofrance.fr/oauth2/token")
                .header("Authorization", "Basic " + auth)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (tokenResponse == null || !tokenResponse.has("access_token")) return null;
        String token = tokenResponse.get("access_token").asText();

        return webClient.get()
                .uri("https://portail-api.meteofrance.fr/public/DPVigilance/v1/cartevigilance/encours")
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    private void processAndSaveVigilanceData(JsonNode json) {
        JsonNode periods = json.path("product").path("periods");
        Set<Long> clearedBulletins = new HashSet<>();

        if (periods.isMissingNode() || !periods.isArray()) return;

        for (JsonNode period : periods) {
            String echeance = period.path("echeance").asText();
            LocalDate targetDate = echeance.equals("J1") ? LocalDate.now().plusDays(1) : (echeance.equals("J") ? LocalDate.now() : null);

            if (targetDate == null) continue;

            JsonNode domainIds = period.path("timelaps").path("domain_ids");
            if (domainIds.isMissingNode() || !domainIds.isArray()) continue;

            for (JsonNode domainNode : domainIds) {
                String deptNum = domainNode.path("domain_id").asText();
                if (deptNum.equals("FRA")) continue;

                List<String> targetNums = new ArrayList<>();
                if (deptNum.equals("2A") || deptNum.equals("2B")) {
                    targetNums.add("20");
                } else if (deptNum.equals("99")) {
                    targetNums.add("MON");
                    targetNums.add("AND");
                } else {
                    targetNums.add(deptNum);
                }

                for (String finalDeptNum : targetNums) {
                    Departement dept = departementRepository.findById(finalDeptNum).orElseGet(() -> {
                        Departement d = new Departement();
                        d.setNum(finalDeptNum);
                        return departementRepository.save(d);
                    });

                    Bulletin bulletin = bulletinRepository.findByDepartementAndDate(dept, targetDate)
                            .orElseGet(() -> {
                                Bulletin b = new Bulletin();
                                b.setDepartement(dept);
                                b.setDate(targetDate);
                                return bulletinRepository.save(b);
                            });

                    if (!clearedBulletins.contains(bulletin.getId())) {
                        alerteRepository.deleteByBulletin(bulletin);
                        clearedBulletins.add(bulletin.getId());
                    }

                    JsonNode phenomenons = domainNode.path("phenomenon_items");
                    if (phenomenons.isArray()) {
                        for (JsonNode phenomNode : phenomenons) {
                            Alerte alerte = new Alerte();
                            alerte.setType(phenomNode.path("phenomenon_id").asInt());
                            alerte.setLevel(phenomNode.path("phenomenon_max_color_id").asInt());
                            alerte.setBulletin(bulletin);
                            alerteRepository.save(alerte);
                        }
                    }
                }
            }
        }
    }

    public void fetchAndSaveAllDailyMeteo() {
        List<Departement> depts = departementRepository.findAll().stream()
                .filter(d -> d.getLat() != null && d.getLongitude() != null)
                .collect(Collectors.toList());

        for (int i = 0; i < depts.size(); i += 50) {
            List<Departement> batch = depts.subList(i, Math.min(i + 50, depts.size()));
            String lats = batch.stream().map(d -> d.getLat().toString().replace(",", ".")).collect(Collectors.joining(","));
            String longs = batch.stream().map(d -> d.getLongitude().toString().replace(",", ".")).collect(Collectors.joining(","));

            String uri = "https://api.open-meteo.com/v1/forecast?latitude=" + lats + "&longitude=" + longs +
                    "&daily=temperature_2m_max,weather_code,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,daylight_duration,sunshine_duration,uv_index_max,uv_index_clear_sky_max,rain_sum,showers_sum,snowfall_sum,precipitation_sum,precipitation_hours,precipitation_probability_max,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant,shortwave_radiation_sum,et0_fao_evapotranspiration&past_days=0&forecast_days=2";

            JsonNode response = webClient.get().uri(uri).retrieve().bodyToMono(JsonNode.class).block();

            if (response != null) {
                if (response.isArray()) {
                    for (int j = 0; j < response.size(); j++) {
                        saveDailyMeteoForIndex(batch.get(j), response.get(j), 0, LocalDate.now());
                        saveDailyMeteoForIndex(batch.get(j), response.get(j), 1, LocalDate.now().plusDays(1));
                    }
                } else {
                    saveDailyMeteoForIndex(batch.get(0), response, 0, LocalDate.now());
                    saveDailyMeteoForIndex(batch.get(0), response, 1, LocalDate.now().plusDays(1));
                }
            }
        }
    }

    private void saveDailyMeteoForIndex(Departement dept, JsonNode fullNode, int index, LocalDate date) {
        List<String> targetCodes = "99".equals(dept.getNum()) ? List.of("99A", "99B") : List.of(dept.getNum());

        for (String code : targetCodes) {
            departementRepository.findById(code).ifPresent(targetDept -> {
                bulletinRepository.findByDepartementAndDate(targetDept, date).ifPresent(bulletin -> {
                    dailyMeteoRepository.deleteByBulletin(bulletin);

                    ObjectNode singleDayNode = objectMapper.createObjectNode();
                    fullNode.fields().forEachRemaining(entry -> {
                        if (!entry.getKey().equals("daily")) {
                            singleDayNode.set(entry.getKey(), entry.getValue());
                        }
                    });

                    ObjectNode dailySingleNode = objectMapper.createObjectNode();
                    JsonNode dailyAll = fullNode.path("daily");
                    dailyAll.fields().forEachRemaining(entry -> {
                        if (entry.getValue().isArray() && entry.getValue().size() > index) {
                            dailySingleNode.set(entry.getKey(), entry.getValue().get(index));
                        }
                    });
                    singleDayNode.set("daily", dailySingleNode);

                    Daily_meteo dm = new Daily_meteo();
                    dm.setBulletin(bulletin);
                    dm.setData(singleDayNode.toString());
                    dailyMeteoRepository.save(dm);
                });
            });
        }
    }
}