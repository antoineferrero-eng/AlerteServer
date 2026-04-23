package AlerteServer.repository;

import AlerteServer.entity.Alertes;
import AlerteServer.entity.DailyMeteo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface DailyMeteoRepository extends JpaRepository<DailyMeteo, Date>{
}
