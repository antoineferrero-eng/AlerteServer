package AlerteServer.service;

import AlerteServer.dto.OtDTO;
import AlerteServer.entity.Ot;
import AlerteServer.exception.IdNotFoundException;
import AlerteServer.repository.OtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtService {

    @Autowired
    private OtRepository otRepository;

    public List<OtDTO> getAll() {
        return otRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public OtDTO getById(String id) {
        return otRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new IdNotFoundException("Ot not found: " + id));
    }

    private OtDTO mapToDTO(Ot ot) {
        String ressourceId = (ot.getRessource() != null) ? ot.getRessource().getDkCode() : null;
        String emplacementId = (ot.getEmplacement() != null) ? ot.getEmplacement().getDkCode() : null;
        return new OtDTO(ot.getNumeroOt(), ot.getCrDebutIntervention(), ressourceId, emplacementId);
    }
}