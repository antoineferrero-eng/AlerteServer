package AlerteServer.controller;

import AlerteServer.dto.OtDTO;
import AlerteServer.service.OtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ordre-de-travails")
public class OtController {

    @Autowired
    private OtService otService;

    @GetMapping
    public List<OtDTO> getAll() {
        return otService.getAll();
    }

    @GetMapping("/{id}")
    public OtDTO getById(@PathVariable String id) {
        return otService.getById(id);
    }
}