package AlerteServer.controller;

import AlerteServer.dto.ContactAlerteDTO;
import AlerteServer.dto.RessourceDTO;
import AlerteServer.entity.Ressource;
import AlerteServer.service.RessourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ressources")
public class RessourceController {
    
    private final RessourceService ressourceService;

    public RessourceController(RessourceService ressourceService) {
        this.ressourceService = ressourceService;
    }

    @GetMapping
    public List<RessourceDTO> getAll() {
        return ressourceService.getAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public RessourceDTO getById(@PathVariable String id) {
        return mapToDTO(ressourceService.getById(id));
    }

    @GetMapping("/concerned")
    public List<ContactAlerteDTO> getContacts(
            @RequestParam("date") String date,
            @RequestParam("dept") String deptNum) {
        return ressourceService.getContactsByAlerte(date, deptNum);
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