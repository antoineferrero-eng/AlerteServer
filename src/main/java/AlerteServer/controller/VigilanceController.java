package AlerteServer.controller;

import AlerteServer.entity.Alertes;
import AlerteServer.entity.Vigilance;
import AlerteServer.service.AlertesService;
import AlerteServer.service.VigilanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/vigilance")
public class VigilanceController {

    @Autowired
    private VigilanceService vigilanceService;

    @GetMapping
    public List<Vigilance> getAll() {
        return vigilanceService.getAll();
    }

    @GetMapping("/{id}")
    public Vigilance getById(@PathVariable int id) {
        return vigilanceService.getById(id);
    }

}
