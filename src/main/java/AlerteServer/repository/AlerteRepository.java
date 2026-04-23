package AlerteServer.repository;

import AlerteServer.entity.Alerte;
import AlerteServer.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlerteRepository extends JpaRepository<Alerte, Integer>{
}
