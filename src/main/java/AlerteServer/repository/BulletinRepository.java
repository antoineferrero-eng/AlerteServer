package AlerteServer.repository;

import AlerteServer.entity.Bulletin;
import AlerteServer.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface BulletinRepository extends JpaRepository<Bulletin, Integer>{

    @Query("SELECT b FROM Bulletin b WHERE b.departement = :departement AND b.date = :date")
    Optional<Bulletin> findByDepartementAndDate(@Param("departement") Departement departement, @Param("date") LocalDate date);

    @Query(value = "SELECT CAST(jsonb_agg(jsonb_build_object(" +
            "'id', b.id, " +
            "'alertes', (SELECT jsonb_agg(jsonb_build_object('type', a.type, 'level', a.level)) FROM alerte a WHERE a.id_bulletin = b.id), " +
            "'dailyMeteos', (SELECT jsonb_agg(jsonb_build_object('id', dm.id, 'data', dm.data)) FROM daily_meteo dm WHERE dm.id_bulletin = b.id), " +
            "'departement', (SELECT jsonb_build_object(" +
            "'num', d.num, 'lat', d.lat, 'long', d.long, " +
            "'sites', (SELECT jsonb_agg(jsonb_build_object(" +
            "'dk-code-emplacement', s.dk_code, " +
            "'ordresDeTravail', (SELECT jsonb_agg(jsonb_build_object(" +
            "'numeroOt', odt.numero_ot, " +
            "'debut', odt.cr_debut_intervention, " +
            "'ressource', (SELECT jsonb_build_object('email', r.email, 'dk-code-ressource', r.dk_code) FROM ressource r WHERE r.dk_code = odt.dk_code_ressource)" +
            ")) FROM ordre_de_travail odt WHERE odt.dk_code_emplacement = s.dk_code)" +
            ")) FROM site s WHERE s.departement = d.num AND EXISTS (SELECT 1 FROM ordre_de_travail o WHERE o.dk_code_emplacement = s.dk_code))" +
            ") FROM departement d WHERE d.num = b.id_departement)" +
            ")) AS TEXT) FROM bulletin b", nativeQuery = true)
    String findAllBulletinsJson();

    @Query(value = "SELECT CAST(jsonb_build_object(" +
            "'id', b.id, " +
            "'alertes', (SELECT jsonb_agg(jsonb_build_object('type', a.type, 'level', a.level)) FROM alerte a WHERE a.id_bulletin = b.id), " +
            "'dailyMeteos', (SELECT jsonb_agg(jsonb_build_object('id', dm.id, 'data', dm.data)) FROM daily_meteo dm WHERE dm.id_bulletin = b.id), " +
            "'departement', (SELECT jsonb_build_object(" +
            "'num', d.num, 'lat', d.lat, 'long', d.long, " +
            "'sites', (SELECT jsonb_agg(jsonb_build_object(" +
            "'dk-code-emplacement', s.dk_code, " +
            "'ordresDeTravail', (SELECT jsonb_agg(jsonb_build_object(" +
            "'numeroOt', odt.numero_ot, " +
            "'debut', odt.cr_debut_intervention, " +
            "'ressource', (SELECT jsonb_build_object('email', r.email, 'dk-code-ressource', r.dk_code) FROM ressource r WHERE r.dk_code = odt.dk_code_ressource)" +
            ")) FROM ordre_de_travail odt WHERE odt.dk_code_emplacement = s.dk_code)" +
            ")) FROM site s WHERE s.departement = d.num AND EXISTS (SELECT 1 FROM ordre_de_travail o WHERE o.dk_code_emplacement = s.dk_code))" +
            ") FROM departement d WHERE d.num = b.id_departement)" +
            ") AS TEXT) FROM bulletin b WHERE b.id = :id", nativeQuery = true)
    String findBulletinByIdJson(@Param("id") Long id);

    @Query(value = "SELECT CAST(jsonb_agg(jsonb_build_object(" +
            "'id', b.id, " +
            "'alertes', (SELECT jsonb_agg(jsonb_build_object('type', a.type, 'level', a.level)) FROM alerte a WHERE a.id_bulletin = b.id), " +
            "'dailyMeteos', (SELECT jsonb_agg(jsonb_build_object('id', dm.id, 'data', dm.data)) FROM daily_meteo dm WHERE dm.id_bulletin = b.id), " +
            "'departement', (SELECT jsonb_build_object(" +
            "'num', d.num, 'lat', d.lat, 'long', d.long, " +
            "'sites', (SELECT jsonb_agg(jsonb_build_object(" +
            "'dk-code-emplacement', s.dk_code, " +
            "'ordresDeTravail', (SELECT jsonb_agg(jsonb_build_object(" +
            "'numeroOt', odt.numero_ot, " +
            "'debut', odt.cr_debut_intervention, " +
            "'ressource', (SELECT jsonb_build_object('email', r.email, 'dk-code-ressource', r.dk_code) FROM ressource r WHERE r.dk_code = odt.dk_code_ressource)" +
            ")) FROM ordre_de_travail odt WHERE odt.dk_code_emplacement = s.dk_code)" +
            ")) FROM site s WHERE s.departement = d.num AND EXISTS (SELECT 1 FROM ordre_de_travail o WHERE o.dk_code_emplacement = s.dk_code))" +
            ") FROM departement d WHERE d.num = b.id_departement)" +
            ")) AS TEXT) FROM bulletin b WHERE b.id_departement = :dep", nativeQuery = true)
    String findBulletinByDepJson(@Param("dep") String dep);
}