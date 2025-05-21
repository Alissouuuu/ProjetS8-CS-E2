package model;

import java.time.LocalDateTime;

public class LogConnexion {

    private int id;
    private String adresseIp;
    private boolean tentativeEchouee;
    private LocalDateTime dateConnexion;
    private Utilisateur utilisateur;

    public LogConnexion(int id, String adresseIp, boolean tentativeEchouee, LocalDateTime dateConnexion, Utilisateur utilisateur) {
        this.id = id;
        this.adresseIp = adresseIp;
        this.tentativeEchouee = tentativeEchouee;
        this.dateConnexion = dateConnexion;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public String getAdresseIp() {
        return adresseIp;
    }

    public boolean isTentativeEchouee() {
        return tentativeEchouee;
    }

    public LocalDateTime getDateConnexion() {
        return dateConnexion;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public String getStatut() {
        return tentativeEchouee ? "Échec" : "Succès";
    }
}
