package AlerteServer.repository;

import AlerteServer.entity.Alertes;
import AlerteServer.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface AlertesRepository extends JpaRepository<Alertes, Date>{
}
