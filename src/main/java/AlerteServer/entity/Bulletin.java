package AlerteServer.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bulletin")
public class Bulletin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "id_departement")
    private Departement departement;

    @OneToMany(mappedBy = "bulletin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alerte> alertes;

    @OneToMany(mappedBy = "bulletin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Daily_meteo> dailyMeteos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Departement getDepartement() { return departement; }
    public void setDepartement(Departement departement) { this.departement = departement; }
    public List<Alerte> getAlertes() { return alertes; }
    public void setAlertes(List<Alerte> alertes) { this.alertes = alertes; }
    public List<Daily_meteo> getDailyMeteos() { return dailyMeteos; }
    public void setDailyMeteos(List<Daily_meteo> dailyMeteos) { this.dailyMeteos = dailyMeteos; }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}