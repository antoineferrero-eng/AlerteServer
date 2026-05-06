package AlerteServer.controller;

import AlerteServer.dto.AlerteDTO;
import AlerteServer.service.AlerteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alertes")
public class AlerteController {

    @Autowired
    private AlerteService alerteService;

    @GetMapping
    public List<AlerteDTO> getAll() {
        return alerteService.getAll();
    }

    @GetMapping("/{id}")
    public AlerteDTO getById(@PathVariable int id) {
        return alerteService.getById(id);
    }
}