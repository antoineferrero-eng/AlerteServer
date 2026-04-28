package AlerteServer.controller;

import AlerteServer.entity.DailyMeteo;
import AlerteServer.service.DailyMeteoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/dailymeteo")
public class DailyMeteoController {

    @Autowired
    private DailyMeteoService dailyMeteoService;

    @GetMapping
    public List<DailyMeteo> getAll() {
        return dailyMeteoService.getAll();
    }

    @GetMapping("/{id}")
    public DailyMeteo getById(@PathVariable Date id) {
        return dailyMeteoService.getById(id);
    }

}
