package AlerteServer.service;

import AlerteServer.entity.DailyMeteo;
import AlerteServer.exception.IdNotFoundException;
import AlerteServer.repository.DailyMeteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
