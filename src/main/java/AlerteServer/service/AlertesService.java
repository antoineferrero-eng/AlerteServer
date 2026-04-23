package AlerteServer.service;

import AlerteServer.entity.Alerte;
import AlerteServer.entity.Alertes;
import AlerteServer.exeption.IdNotFoundException;
import AlerteServer.repository.AlerteRepository;
import AlerteServer.repository.AlertesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AlertesService {

    @Autowired
    private AlertesRepository alertesRepository;

    public List<Alertes> getAll() {
        return alertesRepository.findAll();
    }

    public Alertes getById(Date id) {
        return alertesRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Dep not found: " + id));
    }
}
