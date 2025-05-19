package model;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private String sujet;
    private String contenu;
    private String expediteur;
    private String destinataire;
    private LocalDateTime dateEnvoi;

    // Constructeur sans id (pour l'envoi)
    public Message(String sujet, String contenu, String destinataire, String expediteur, LocalDateTime dateEnvoi) {
        this.sujet = sujet;
        this.contenu = contenu;
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.dateEnvoi = dateEnvoi;
    }

    // Constructeur complet (pour lecture BDD)
    public Message(int id, String sujet, String contenu, String destinataire, String expediteur, LocalDateTime dateEnvoi) {
        this.id = id;
        this.sujet = sujet;
        this.contenu = contenu;
        this.destinataire = destinataire;
        this.expediteur = expediteur;
        this.dateEnvoi = dateEnvoi;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getSujet() {
        return sujet;
    }

    public String getContenu() {
        return contenu;
    }

    public String getDestinataire() {
        return destinataire;
    }
    
    public String getExpediteur() { 
    	return expediteur; 
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }
}
