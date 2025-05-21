package model;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String fonction;
    private int role;
    private byte[] pieceJustificative;
    
    public Utilisateur() {
    	
    }

    public Utilisateur(int id, String nom, String prenom, String email, int role, String fonction) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.fonction=fonction;
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    public String getEmail() { return email; }
    
    public int getRole() { return role; }
    
    public String getNom() { return nom; }
    
    public int getId() { return id; }
    
    public String getPrenom() { return prenom; }
    
    public String getFonction() { return fonction; }
    
    public byte[] getPieceJustificative() { return pieceJustificative; }

    
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setFonction(String fonction) { this.fonction = fonction; }
    public void setRole(int role) { this.role = role; }
    public void setPieceJustificative(byte[] pieceJustificative) { this.pieceJustificative = pieceJustificative; }
}
