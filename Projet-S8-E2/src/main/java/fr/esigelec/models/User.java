/**
  La classe User est conçue pour gérer les différents types d'utilisateurs du site web :
 * visiteurs non inscrits, élus, et membres du monde sportif.
 * 
 * Elle gère aussi un justificatif d'identité en fichier attaché.
 * 
 * @author imane
 * @version 1.0
 */
package fr.esigelec.models;

public class User {
	/** l'id de l'utilisateur */
	private int idUser;
	/** le nom de l'utilisateur */
	private String nom;
	/** le prenom de l'utilisateur */
	private String prenom;
	/** l'email de l'utilisateur */
	private String email;
	/**
	 * le role de l'utilisateur 0 == élu 1== membre sportif
	 */
	private int role;
	/** fonction des elus ou membres sporitfs */
	private String fonction;
	/**
	 * statut de la demande d'inscription 0=refusée 1=acceptée 2=en attente
	 */
	private int statutDemande;
	private String mdp;
	/** mot de passe du compte */
	// fichier justificatif de l'identité de uplodé par l'utilisateur
	/** nom du fichier */
	private String nomFichier;
	/**
	 * Type MIME du fichier identifiant le type de contenu d’un fichier MIME ==
	 * Multipurpose Internet Mail Extensions nous permet de savoir comment traiter
	 * un fichier selon l'extension du fichier et éviter l'upload d'un fichier
	 * d'extension .exe
	 * 
	 */
	private String typeFichier;
	/** taille du fichier en octets */
	private long tailleFichier;
	/**
	 * le contenu binaire du fichier justificatif
	 */
	private byte[] justificatifDonnees;
	/** constructeurs */
	public User() {
    }
	
	public User(int idUser, String nom, String prenom, String email, int role, String fonction, int statutDemande,
			String mdp, String nomFichier, String typeFichier, long tailleFichier, byte[] justificatifDonnees) {
		this.idUser = idUser;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.role = role;
		this.fonction = fonction;
		this.statutDemande = statutDemande;
		this.mdp = mdp;
		this.nomFichier = nomFichier;
		this.typeFichier = typeFichier;
		this.tailleFichier = tailleFichier;
		this.justificatifDonnees = justificatifDonnees;
	}
	 // === getters et setters ===
	
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public int getStatutDemande() {
        return statutDemande;
    }

    public void setStatutDemande(int statutDemande) {
        this.statutDemande = statutDemande;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(String typeFichier) {
        this.typeFichier = typeFichier;
    }

    public long getTailleFichier() {
        return tailleFichier;
    }

    public void setTailleFichier(long tailleFichier) {
        this.tailleFichier = tailleFichier;
    }

    public byte[] getJustificatifDonnees() {
        return justificatifDonnees;
    }

    public void setJustificatifDonnees(byte[] justificatifDonnees) {
        this.justificatifDonnees = justificatifDonnees;
    }
}
