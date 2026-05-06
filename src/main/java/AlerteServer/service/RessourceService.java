package AlerteServer.service;

import AlerteServer.entity.Ressource;
import AlerteServer.exception.IdNotFoundException;
import AlerteServer.repository.RessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> getContactsByAlerte(String date,  String deptNum) {
        LocalDate parsedDate = LocalDate.parse(date);
        return ressourceRepository.findContactsByAlerte(parsedDate, deptNum);
    }
}
