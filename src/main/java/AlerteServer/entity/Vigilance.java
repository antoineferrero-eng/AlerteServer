package AlerteServer.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vigilance")
public class Vigilance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "iddepartement")
    private Departement departement;

    @ManyToOne
    @JoinColumn(name = "meteo_j0")
    private DailyMeteo meteoJ0;

    @ManyToOne
    @JoinColumn(name = "meteo_j1")
    private DailyMeteo meteoJ1;

    @ManyToOne
    @JoinColumn(name = "idalertes")
    private Alertes alertes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public DailyMeteo getMeteoJ0() {
        return meteoJ0;
    }

    public void setMeteoJ0(DailyMeteo meteoJ0) {
        this.meteoJ0 = meteoJ0;
    }

    public DailyMeteo getMeteoJ1() {
        return meteoJ1;
    }

    public void setMeteoJ1(DailyMeteo meteoJ1) {
        this.meteoJ1 = meteoJ1;
    }

    public Alertes getAlertes() {
        return alertes;
    }

    public void setAlertes(Alertes alertes) {
        this.alertes = alertes;
    }
}