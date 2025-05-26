package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evenement {

    // Champs liés à la base de données
    private int id;             // id_evenement
    private String type;
    private int nbrMax;
    private LocalDate date;
    private LocalTime heure;
    private String lieu;
    private String description;
    private int idUser;         // utilisateur responsable
    private Integer idClub;     // club associé (nullable)
    private String email;

    // Champs pour affichage uniquement
    private String nomResponsable;
    private String nomClub;

    // --- Constructeurs ---

    // Constructeur complet pour insertion / récupération de BDD
    public Evenement(int id, String type, int nbrMax, LocalDate date, LocalTime heure,
                     String lieu, String description, int idUser, Integer idClub) {
        this.id = id;
        this.type = type;
        this.nbrMax = nbrMax;
        this.date = date;
        this.heure = heure;
        this.lieu = lieu;
        this.description = description;
        this.idUser = idUser;
        this.idClub = idClub;
    }

    // Constructeur pour affichage dans JTable
    public Evenement(String type, LocalDate date, LocalTime heure, String lieu,
                     int nbrMax, String nomResponsable, String nomClub) {
        this.type = type;
        this.date = date;
        this.heure = heure;
        this.lieu = lieu;
        this.nbrMax = nbrMax;
        this.nomResponsable = nomResponsable;
        this.nomClub = nomClub;
    }
    
    
    // Constructeur pour affichage dans JTable
    public Evenement(String type, LocalDate date, LocalTime heure, String lieu,
                     int nbrMax, String nomResponsable, String nomClub, String email) {
        this.type = type;
        this.date = date;
        this.heure = heure;
        this.lieu = lieu;
        this.nbrMax = nbrMax;
        this.nomResponsable = nomResponsable;
        this.nomClub = nomClub;
        this.email=email;
    }
    
    public Evenement(int id, String type, LocalDate date, LocalTime heure, String lieu,
            int nbrMax, String description, String nomResponsable,
            String nomClub, String email) {
			this.id = id;
			this.type = type;
			this.date = date;
			this.heure = heure;
			this.lieu = lieu;
			this.nbrMax = nbrMax;
			this.description = description;
			this.nomResponsable = nomResponsable;
			this.nomClub = nomClub;
			this.email = email;
	}


    // --- Getters & Setters ---

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getNbrMax() {
        return nbrMax;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public String getLieu() {
        return lieu;
    }

    public String getDescription() {
        return description;
    }

    public int getIdUser() {
        return idUser;
    }

    public Integer getIdClub() {
        return idClub;
    }

    public String getNomResponsable() {
        return nomResponsable;
    }

    public String getNomClub() {
        return nomClub;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNbrMax(int nbrMax) {
        this.nbrMax = nbrMax;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdClub(Integer idClub) {
        this.idClub = idClub;
    }

    public void setNomResponsable(String nomResponsable) {
        this.nomResponsable = nomResponsable;
    }

    public void setNomClub(String nomClub) {
        this.nomClub = nomClub;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
