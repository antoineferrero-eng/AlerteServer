package AlerteServer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppConfig {

    private List<String> activeLevels;
    private List<String> activeTypes;
    private String mailCron;
    private String updateCron;

    public AppConfig(
            @Value("${alerte.niveaux.actifs:2,3,4}") List<String> activeLevels,
            @Value("${alerte.types.actifs:1,2,3,4,5,6,7,8,9}") List<String> activeTypes,
            @Value("${alerte.mail.cron:0 0 8 * * *}") String mailCron,
            @Value("${alerte.update.cron:0 0 6 * * *}") String updateCron
    ) {
        this.activeLevels = activeLevels.stream().map(String::trim).collect(Collectors.toCollection(ArrayList::new));
        this.activeTypes = activeTypes.stream().map(String::trim).collect(Collectors.toCollection(ArrayList::new));
        this.mailCron = mailCron.trim();
        this.updateCron = updateCron.trim();
    }

    public List<String> getActiveLevels() {
        return activeLevels;
    }

    public void setActiveLevels(List<String> activeLevels) {
        this.activeLevels = activeLevels;
    }

    public List<String> getActiveTypes() {
        return activeTypes;
    }

    public void setActiveTypes(List<String> activeTypes) {
        this.activeTypes = activeTypes;
    }

    public String getMailCron() {
        return mailCron;
    }

    public void setMailCron(String mailCron) {
        if (mailCron != null && !mailCron.contains("NaN")) {
            this.mailCron = mailCron.trim();
        }
    }

    public String getUpdateCron() {
        return updateCron;
    }

    public void setUpdateCron(String updateCron) {
        if (updateCron != null && !updateCron.contains("NaN")) {
            this.updateCron = updateCron.trim();
        }
    }
}
