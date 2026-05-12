package AlerteServer.service;

import AlerteServer.dto.DepartementDTO;
import AlerteServer.entity.Departement;
import AlerteServer.exception.IdNotFoundException;
import AlerteServer.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    public List<DepartementDTO> getAll() {
        return departementRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public DepartementDTO getById(String id) {
        return departementRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new IdNotFoundException("Departement not found: " + id));
    }

    private DepartementDTO mapToDTO(Departement departement) {
        return new DepartementDTO(
                departement.getNum(),
                departement.getLat(),
                departement.getLongitude()
        );
    }
}