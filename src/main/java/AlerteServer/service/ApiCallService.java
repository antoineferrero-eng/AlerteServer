package AlerteServer.service;

import AlerteServer.entity.DailyMeteo;
import AlerteServer.repository.DailyMeteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ApiCallService {

    @Autowired
    private DailyMeteoRepository dailyMeteoRepository;

    @Autowired
    private WebClient webClient;

    @Scheduled(fixedRate = 3600000)
    public void getDailyMeteo() {
        LocalDateTime today = LocalDateTime.now();
        if (dailyMeteoRepository.existsById(today)) {
        }
        dailyMeteoRepository.deleteAll();
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", 52.52)
                        .queryParam("longitude", 13.41)
                        .queryParam("daily", "weather_code,apparent_temperature_min,apparent_temperature_max,temperature_2m_max,temperature_2m_min,sunset,sunrise,daylight_duration,sunshine_duration,uv_index_max,uv_index_clear_sky_max,rain_sum,showers_sum,snowfall_sum,precipitation_sum,precipitation_hours,precipitation_probability_max,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant,shortwave_radiation_sum,et0_fao_evapotranspiration")
                        .queryParam("timezone", "Europe/London")
                        .queryParam("past_days", 0)
                        .queryParam("forecast_days", 2)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(jsonResponse -> {
                    DailyMeteo dailyMeteo = new DailyMeteo();
                    dailyMeteo.setTime(LocalDateTime.now());
                    dailyMeteo.setData(jsonResponse);
                    dailyMeteoRepository.save(dailyMeteo);
                }, error -> {
                    System.err.println("Erreur lors de l'appel API : " + error.getMessage());
                });
    }
}
