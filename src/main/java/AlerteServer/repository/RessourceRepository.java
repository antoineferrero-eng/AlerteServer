package AlerteServer.repository;

import AlerteServer.entity.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RessourceRepository extends JpaRepository<Ressource, String> {

    @Query(value = "SELECT DISTINCT r.email as email, r.tel_portable as telephone, a.type as type, a.level as niveau " +
            "FROM ressource r " +
            "JOIN ordre_de_travail odt ON r.dk_code = odt.dk_code_ressource " +
            "JOIN site s ON odt.dk_code_emplacement = s.dk_code " +
            "JOIN bulletin b ON s.departement = b.id_departement " +
            "JOIN alerte a ON a.id_bulletin = b.id " +
            "WHERE b.date = CAST(CAST(:date AS TEXT) AS DATE) " +
            "AND b.id_departement = CAST(:deptNum AS TEXT)", nativeQuery = true)
    List<Map<String, String>> findContactsByAlerte(@Param("date") LocalDate date,
                                                   @Param("deptNum") String deptNum);
}