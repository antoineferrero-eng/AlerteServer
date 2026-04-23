package AlerteServer.controller;

import AlerteServer.entity.Alerte;
import AlerteServer.entity.Alertes;
import AlerteServer.service.AlerteService;
import AlerteServer.service.AlertesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/alertes")
public class AlertesController {

    @Autowired
    private AlertesService alertesService;

    @GetMapping
    public List<Alertes> getAll() {
        return alertesService.getAll();
    }

    @GetMapping("/{id}")
    public Alertes getById(@PathVariable Date id) {
        return alertesService.getById(id);
    }

}
