package AlerteServer.service;

import AlerteServer.dto.AlerteDTO;
import AlerteServer.entity.Alerte;
import AlerteServer.exception.IdNotFoundException;
import AlerteServer.repository.AlerteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlerteService {

    @Autowired
    private AlerteRepository alerteRepository;

    public List<AlerteDTO> getAll() {
        return alerteRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public AlerteDTO getById(int id) {
        return alerteRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new IdNotFoundException("Alerte not found: " + id));
    }

    private AlerteDTO mapToDTO(Alerte alerte) {
        Long bulletinId = (alerte.getBulletin() != null) ? alerte.getBulletin().getId() : null;
        return new AlerteDTO(
                alerte.getId(),
                alerte.getType(),
                alerte.getLevel(),
                bulletinId
        );
    }
}