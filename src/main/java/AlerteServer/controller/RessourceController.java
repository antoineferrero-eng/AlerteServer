package AlerteServer.controller;

import AlerteServer.entity.Ressource;
import AlerteServer.entity.Site;
import AlerteServer.service.RessourceService;
import AlerteServer.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/ressource")
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

}
