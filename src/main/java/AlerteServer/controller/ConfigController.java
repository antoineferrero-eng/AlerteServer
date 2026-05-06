package AlerteServer.controller;

import AlerteServer.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/alert-levels")
    public List<String> getActiveLevels() {
        return emailService.getActiveLevels();
    }

    @PostMapping("/alert-levels")
    public void setActiveLevels(@RequestBody List<String> levels) {
        emailService.setActiveLevels(levels);
    }
}