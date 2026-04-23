package AlerteServer.service;

import AlerteServer.entity.DailyMeteo;
import AlerteServer.entity.Vigilance;
import AlerteServer.exeption.IdNotFoundException;
import AlerteServer.repository.DailyMeteoRepository;
import AlerteServer.repository.VigilanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class DailyMeteoService {

    @Autowired
    private DailyMeteoRepository dailyMeteoRepository;

    public List<DailyMeteo> getAll() {
        return dailyMeteoRepository.findAll();
    }

    public DailyMeteo getById(Date id) {
        return dailyMeteoRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Dep not found: " + id));
    }


}
