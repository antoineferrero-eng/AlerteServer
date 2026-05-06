package AlerteServer.repository;

import AlerteServer.entity.Alerte;
import AlerteServer.entity.Bulletin;
import AlerteServer.entity.Departement;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlerteRepository extends JpaRepository<Alerte, Integer>{
    @Transactional
    void deleteByBulletin(Bulletin bulletin);
}
