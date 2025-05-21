package model;

import java.time.LocalDateTime;

public class LogAdmin {

    private int id_log;
    private Utilisateur id_admin;           
    private String type_action;
    private String type_entite;
    private int id_entite;
    private String ancienne_valeur;
    private String nouvelle_valeur;
    private String adresse_IP;
    private LocalDateTime dateHeureAction;
    private boolean reussite;

    public LogAdmin(int id_log, Utilisateur id_admin, String type_action, String type_entite, int id_entite,
                    String ancienne_valeur, String nouvelle_valeur, String adresse_IP,
                    LocalDateTime dateHeureAction, boolean reussite) {
        this.id_log = id_log;
        this.id_admin = id_admin;
        this.type_action = type_action;
        this.type_entite = type_entite;
        this.id_entite = id_entite;
        this.ancienne_valeur = ancienne_valeur;
        this.nouvelle_valeur = nouvelle_valeur;
        this.adresse_IP = adresse_IP;
        this.dateHeureAction = dateHeureAction;
        this.reussite = reussite;
    }

    public int getId_log() {
        return id_log;
    }

    public Utilisateur getId_admin() {
        return id_admin;
    }

    public String getType_action() {
        return type_action;
    }

    public String getType_entite() {
        return type_entite;
    }

    public int getId_entite() {
        return id_entite;
    }

    public String getAncienne_valeur() {
        return ancienne_valeur;
    }

    public String getNouvelle_valeur() {
        return nouvelle_valeur;
    }

    public String getAdresse_IP() {
        return adresse_IP;
    }

    public LocalDateTime getDateHeureAction() {
        return dateHeureAction;
    }

    public boolean isReussite() {
        return reussite;
    }

    public String getStatut() {
        return reussite ? "Succès" : "Échec";
    }
}
