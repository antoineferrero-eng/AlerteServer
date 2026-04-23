package AlerteServer.controller;

import AlerteServer.entity.Ot;
import AlerteServer.entity.Site;
import AlerteServer.service.OtService;
import AlerteServer.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/ot")
public class OtController {

    @Autowired
    private OtService otService;

    @GetMapping
    public List<Ot> getAll() {
        return otService.getAll();
    }

    @GetMapping("/{id}")
    public Ot getById(@PathVariable String id) {
        return otService.getById(id);
    }

}
