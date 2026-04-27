package AlerteServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "daily_meteo")
public class Daily_meteo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_bulletin")
    @JsonIgnore
    private Bulletin bulletin;

    @Column(columnDefinition = "TEXT")
    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bulletin getBulletin() {
        return bulletin;
    }

    public void setBulletin(Bulletin bulletin) {
        this.bulletin = bulletin;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}