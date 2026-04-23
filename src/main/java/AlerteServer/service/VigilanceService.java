package AlerteServer.service;

import AlerteServer.entity.Alertes;
import AlerteServer.entity.Vigilance;
import AlerteServer.exeption.IdNotFoundException;
import AlerteServer.repository.AlertesRepository;
import AlerteServer.repository.VigilanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VigilanceService {

    @Autowired
    private VigilanceRepository vigilanceRepository;

    public List<Vigilance> getAll() {
        return vigilanceRepository.findAll();
    }

    public Vigilance getById(int id) {
        return vigilanceRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Dep not found: " + id));
    }
}
