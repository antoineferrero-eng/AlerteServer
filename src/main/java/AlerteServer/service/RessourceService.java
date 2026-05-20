package AlerteServer.service;

import AlerteServer.dto.ContactAlerteDTO;
import AlerteServer.entity.Ressource;
import AlerteServer.exception.IdNotFoundException;
import AlerteServer.repository.RessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RessourceService {

    @Autowired
    private RessourceRepository ressourceRepository;

    public List<Ressource> getAll() {
        return ressourceRepository.findAll();
    }

    public Ressource getById(String id) {
        return ressourceRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Ressource not found: " + id));
    }

    public List<ContactAlerteDTO> getContactsByAlerte(String date, String deptNum) {
        return ressourceRepository.findContactsByAlerte(LocalDate.parse(date), deptNum);
    }
}