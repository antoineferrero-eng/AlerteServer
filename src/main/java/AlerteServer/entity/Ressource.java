package AlerteServer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ressource")
public class Ressource {

    @Id
    @Column(name = "dk_code")
    private String dkCode;

    @Column(name = "lib_fonction")
    private String libFonction;

    @Column(name = "tel_portable")
    private String telPortable;

    private String email;

    public String getDkCode() {
        return dkCode;
    }

    public void setDkCode(String dkCode) {
        this.dkCode = dkCode;
    }

    public String getLibFonction() {
        return libFonction;
    }

    public void setLibFonction(String libFonction) {
        this.libFonction = libFonction;
    }

    public String getTelPortable() {
        return telPortable;
    }

    public void setTelPortable(String telPortable) {
        this.telPortable = telPortable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}