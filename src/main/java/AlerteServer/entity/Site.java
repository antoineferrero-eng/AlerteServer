package AlerteServer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @Column(name = "dk_code")
    private String dkCode;

    @ManyToOne
    @JoinColumn(name = "departement")
    private Departement departement;

    @ManyToOne
    @JoinColumn(name = "dk_code_parent")
    private Site parent;

    public String getDkCode() {
        return dkCode;
    }

    public void setDkCode(String dkCode) {
        this.dkCode = dkCode;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Site getParent() {
        return parent;
    }

    public void setParent(Site parent) {
        this.parent = parent;
    }
}