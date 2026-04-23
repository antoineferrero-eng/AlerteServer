package AlerteServer.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerte")
public class Alerte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer type;

    private Integer level;

    @ManyToOne
    @JoinColumn(name = "id_alertes")
    private Alertes alertes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Alertes getAlertes() {
        return alertes;
    }

    public void setAlertes(Alertes alertes) {
        this.alertes = alertes;
    }
}