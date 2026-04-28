package AlerteServer.controller;

import AlerteServer.entity.Ressource;
import AlerteServer.entity.Site;
import AlerteServer.service.RessourceService;
import AlerteServer.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/ressources")
public class RessourceController {

    @Autowired
    private RessourceService ressourceService;

    @GetMapping
    public List<Ressource> getAll() {
        return ressourceService.getAll();
    }

    @GetMapping("/{id}")
    public Ressource getById(@PathVariable String id) {
        return ressourceService.getById(id);
    }

    @GetMapping("/concerne")
    public List<Map<String, Object>> getContacts(
            @RequestParam("date") String date,
            @RequestParam("dept") String deptNum) {
        return ressourceService.getContactsByAlerte(date, deptNum);
    }

}
