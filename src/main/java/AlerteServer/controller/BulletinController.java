package AlerteServer.controller;

import AlerteServer.entity.Bulletin;
import AlerteServer.service.BulletinService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/bulletins")
public class BulletinController {

    @Autowired
    private BulletinService bulletinService;

    @GetMapping
    public JsonNode getAll() throws Exception {
        return bulletinService.getAll();
    }

    @GetMapping("/{id}")
    public JsonNode getById(@PathVariable Long id) throws Exception {
        return bulletinService.getById(id);
    }

    @GetMapping("/departement/{dep}")
    public JsonNode getByDep(@PathVariable String dep) throws Exception {
        return bulletinService.getByDep(dep);
    }
}