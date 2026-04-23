package AlerteServer.repository;

import AlerteServer.entity.Departement;
import AlerteServer.entity.Vigilance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VigilanceRepository extends JpaRepository<Vigilance, Integer>{
}
