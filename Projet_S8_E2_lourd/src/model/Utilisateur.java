package model;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private int role;

    public Utilisateur(int id, String nom, String prenom, String email, int role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    public String getEmail() { return email; }
    public int getRole() { return role; }
}
