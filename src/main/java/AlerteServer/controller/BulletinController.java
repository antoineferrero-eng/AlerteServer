package AlerteServer.controller;

import AlerteServer.dto.BulletinDTO;
import AlerteServer.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bulletins")
public class BulletinController {

    @Autowired
    private BulletinService bulletinService;

    @GetMapping
    public List<BulletinDTO> getBulletins(
            @RequestParam(required = false) String dep,
            @RequestParam(required = false) String date) {

        if (dep != null && date != null) {
            return bulletinService.getByDepAndDate(dep, date);
        }
        if (dep != null) {
            return bulletinService.getByDep(dep);
        }
        if (date != null) {
            return bulletinService.getByDate(date);
        }

        return bulletinService.getAll();
    }

    @GetMapping("/{id}")
    public BulletinDTO getById(@PathVariable Long id) {
        return bulletinService.getById(id);
    }
}