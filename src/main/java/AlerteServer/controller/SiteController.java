package AlerteServer.controller;

import AlerteServer.dto.SiteDTO;
import AlerteServer.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sites")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @GetMapping
    public List<SiteDTO> getAll() {
        return siteService.getAll();
    }

    @GetMapping("/{id}")
    public SiteDTO getById(@PathVariable String id) {
        return siteService.getById(id);
    }
}