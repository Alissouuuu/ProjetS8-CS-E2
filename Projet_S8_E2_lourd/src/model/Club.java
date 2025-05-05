package model;

public class Club {
    private int id;
    private String nom;
    private String commune;
    private String departement;
    private String federation;
 
    private String region;


    public Club(int id, String nom, String commune, String departement, String region, String federation) {
        this.id = id;
        this.nom = nom;
        this.commune = commune;
        this.federation = federation;
        this.region=region;
        this.departement=departement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Object getDepartement() {
		return departement;
	}
    
    public String getRegion() {
        return region;
    }

    
    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getFederation() {
        return federation;
    }

    public void setFederation(String federation) {
        this.federation = federation;
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                
                ", commune='" + commune + '\'' +
                ", departement='" + departement + '\'' +
                ", region='" + region + '\'' +
                ", federation='" + federation + '\'' +
                '}';
 
	}

	
}
