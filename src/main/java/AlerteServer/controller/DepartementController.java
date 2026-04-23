package AlerteServer.controller;

import AlerteServer.entity.Departement;
import AlerteServer.service.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/departement")
public class DepartementController {

    @Autowired
    private DepartementService departementService;

    @GetMapping
    public List<Departement> getAll() {
        return departementService.getAll();
    }

    @GetMapping("/{id}")
    public Departement getById(@PathVariable String id) {
        return departementService.getById(id);
    }

}
