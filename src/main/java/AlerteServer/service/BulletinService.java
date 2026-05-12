package AlerteServer.service;

import AlerteServer.dto.AlerteDTO;
import AlerteServer.dto.BulletinDTO;
import AlerteServer.dto.DailyMeteoDTO;
import AlerteServer.dto.DepartementDTO;
import AlerteServer.entity.Bulletin;
import AlerteServer.entity.Daily_meteo;
import AlerteServer.exception.IdNotFoundException;
import AlerteServer.repository.BulletinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BulletinService {

    private static final Logger log = LoggerFactory.getLogger(BulletinService.class);

    @Autowired
    private BulletinRepository bulletinRepository;

    public List<BulletinDTO> getAll() {
        return bulletinRepository.findAllWithDetails()
                .stream()
                .map(this::mapToDetailDTO)
                .toList();
    }

    public BulletinDTO getById(Long id) {
        return bulletinRepository.findByIdWithDetails(id)
                .map(this::mapToDetailDTO)
                .orElseThrow(() -> new IdNotFoundException("Bulletin not found: " + id));
    }

    public List<BulletinDTO> getByDep(String dep) {
        return bulletinRepository.findByDepWithDetails(dep)
                .stream()
                .map(this::mapToDetailDTO)
                .toList();
    }

    public List<BulletinDTO> getByDate(String dateStr) {
        return bulletinRepository.findByDateWithDetails(LocalDate.parse(dateStr))
                .stream()
                .map(this::mapToDetailDTO)
                .toList();
    }

    private BulletinDTO mapToDetailDTO(Bulletin bulletin) {
        DepartementDTO depDTO = null;
        if (bulletin.getDepartement() != null) {
            depDTO = new DepartementDTO(
                    bulletin.getDepartement().getNum(),
                    bulletin.getDepartement().getLat(),
                    bulletin.getDepartement().getLongitude()
            );
        }

        Set<AlerteDTO> alertesDTO = bulletin.getAlertes().stream()
                .map(a -> new AlerteDTO(a.getId(), a.getType(), a.getLevel(), bulletin.getId()))
                .collect(Collectors.toSet());

        Set<DailyMeteoDTO> meteosDTO = bulletin.getDailyMeteos().stream()
                .map(this::mapToMeteoDTO)
                .collect(Collectors.toSet());

        return new BulletinDTO(
                bulletin.getId(),
                bulletin.getDate(),
                depDTO,
                alertesDTO,
                meteosDTO
        );
    }

    private DailyMeteoDTO mapToMeteoDTO(Daily_meteo m) {
        return new DailyMeteoDTO(
                m.getId(),
                m.getDate(),
                m.getWeatherCode(),
                m.getTempMax(),
                m.getTempMin(),
                m.getApparentTempMax(),
                m.getApparentTempMin(),
                m.getSunrise(),
                m.getSunset(),
                m.getDaylightDuration(),
                m.getSunshineDuration(),
                m.getUvIndexMax(),
                m.getUvIndexClearSkyMax(),
                m.getRainSum(),
                m.getShowersSum(),
                m.getSnowfallSum(),
                m.getPrecipitationSum(),
                m.getPrecipitationHours(),
                m.getPrecipitationProbabilityMax(),
                m.getWindSpeedMax(),
                m.getWindGustsMax(),
                m.getWindDirectionDominant(),
                m.getShortwaveRadiationSum(),
                m.getEvapotranspiration()
        );
    }

    public List<BulletinDTO> getByDepAndDate(String dep, String dateStr) {
        return bulletinRepository.findByDepAndDateWithDetails(dep, LocalDate.parse(dateStr))
                .stream()
                .map(this::mapToDetailDTO)
                .toList();
    }

    public void purgeOldData() {
        LocalDate limitDate = LocalDate.now().minusMonths(1);
        log.info("Purge des bulletins antérieurs au : {}", limitDate);
        try {
            bulletinRepository.deleteOldBulletins(limitDate);
            log.info("Purge effectuée avec succès.");
        } catch (Exception e) {
            log.error("Erreur lors de la purge des données", e);
        }
    }
}