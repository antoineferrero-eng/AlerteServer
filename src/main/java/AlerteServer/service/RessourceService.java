package AlerteServer.service;

import AlerteServer.dto.ContactAlerteDTO;
import AlerteServer.dto.RessourceDTO;
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

    public List<RessourceDTO> getAll() {
        return ressourceRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public RessourceDTO getById(String id) {
        return ressourceRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new IdNotFoundException("Ressource not found: " + id));
    }

    public List<ContactAlerteDTO> getContactsByAlerte(String date, String deptNum) {
        return ressourceRepository.findContactsByAlerte(LocalDate.parse(date), deptNum);
    }

    private RessourceDTO mapToDTO(Ressource res) {
        return new RessourceDTO(
                res.getDkCode(),
                res.getLibFonction(),
                res.getTelPortable(),
                res.getEmail()
        );
    }
}