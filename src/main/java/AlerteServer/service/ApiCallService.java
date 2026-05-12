package AlerteServer.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiCallService {

    private static final Logger log = LoggerFactory.getLogger(ApiCallService.class);

    private final EmailService emailService;
    private final BulletinService bulletinService;
    private final MeteoFranceService meteoFranceService;
    private final OpenMeteoService openMeteoService;

    public ApiCallService(EmailService emailService,
            BulletinService bulletinService,
            MeteoFranceService meteoFranceService,
            OpenMeteoService openMeteoService) {
        this.emailService = emailService;
        this.bulletinService = bulletinService;
        this.meteoFranceService = meteoFranceService;
        this.openMeteoService = openMeteoService;
    }

    @Transactional
    public void runDailyImport() {
        log.info("Lancement de runDailyImport");
        try {
            bulletinService.purgeOldData();

            JsonNode vigilanceData = meteoFranceService.fetchVigilanceData();
            if (vigilanceData != null) {
                meteoFranceService.processAndSaveVigilanceData(vigilanceData);
                openMeteoService.fetchAndSaveAllDailyMeteo();
                log.info("Importation complète terminée avec succès");
            }
        } catch (Exception e) {
            log.error("Erreur dans runDailyImport", e);
        }
    }

    public void sendAlertEmailsToAllDepartments() {
        emailService.sendAlertEmailsToAllDepartments();
    }
}