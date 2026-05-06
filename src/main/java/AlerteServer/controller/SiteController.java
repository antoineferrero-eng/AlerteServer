package AlerteServer.controller;

import AlerteServer.entity.Site;
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
    public List<Site> getAll() {
        return siteService.getAll();
    }

    @GetMapping("/{id}")
    public Site getById(@PathVariable String id) {
        return siteService.getById(id);
    }

}
