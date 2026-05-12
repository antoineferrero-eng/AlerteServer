package AlerteServer.service;

import AlerteServer.config.AppConfig;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.support.CronExpression;
import java.util.concurrent.ScheduledFuture;

@Service
public class SchedulingService {

    private static final Logger log = LoggerFactory.getLogger(SchedulingService.class);

    private final TaskScheduler taskScheduler;
    private final AppConfig appConfig;
    private final ApiCallService apiCallService;

    public SchedulingService(TaskScheduler taskScheduler, AppConfig appConfig, ApiCallService apiCallService) {
        this.taskScheduler = taskScheduler;
        this.appConfig = appConfig;
        this.apiCallService = apiCallService;
    }

    private volatile ScheduledFuture<?> updateFuture;
    private volatile ScheduledFuture<?> mailFuture;

    @PostConstruct
    public void init() {
        scheduleDataUpdate();
        scheduleMail();
    }

    public void rescheduleDataUpdate() {
        if (updateFuture != null) {
            updateFuture.cancel(false);
        }
        scheduleDataUpdate();
        log.info("Mise à jour des données re-planifiée avec le cron : {}", appConfig.getUpdateCron());
    }

    public void rescheduleMail() {
        if (mailFuture != null) {
            mailFuture.cancel(false);
        }
        scheduleMail();
        log.info("Envoi des mails re-planifié avec le cron : {}", appConfig.getMailCron());
    }

    private void scheduleDataUpdate() {
        String cron = appConfig.getUpdateCron();
        if (!CronExpression.isValidExpression(cron)) {
            log.error("Expression cron invalide pour la mise à jour des données : {}. Utilisation d'une valeur par défaut.", cron);
            cron = "0 0 6 * * *";
        }
        updateFuture = taskScheduler.schedule(
                () -> {
                    log.info("Déclenchement planifié : import des données météo");
                    apiCallService.runDailyImport();
                },
                new CronTrigger(cron)
        );
    }

    private void scheduleMail() {
        String cron = appConfig.getMailCron();
        if (!CronExpression.isValidExpression(cron)) {
            log.error("Expression cron invalide pour l'envoi des mails : {}. Utilisation d'une valeur par défaut.", cron);
            cron = "0 0 8 * * *";
        }
        mailFuture = taskScheduler.schedule(
                () -> {
                    log.info("Déclenchement planifié : envoi des mails d'alerte");
                    apiCallService.sendAlertEmailsToAllDepartments();
                },
                new CronTrigger(cron)
        );
    }
}
