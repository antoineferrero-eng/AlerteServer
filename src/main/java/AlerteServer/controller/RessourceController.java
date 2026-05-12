package AlerteServer.controller;

import AlerteServer.dto.ContactAlerteDTO;
import AlerteServer.dto.RessourceDTO;
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
        return ressourceService.getAll();
    }

    @GetMapping("/{id}")
    public RessourceDTO getById(@PathVariable String id) {
        return ressourceService.getById(id);
    }

    @GetMapping("/concerned")
    public List<ContactAlerteDTO> getContacts(
            @RequestParam("date") String date,
            @RequestParam("dept") String deptNum) {
        return ressourceService.getContactsByAlerte(date, deptNum);
    }
}