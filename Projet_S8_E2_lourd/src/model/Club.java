package model;

public class Club {
    private int id;
    private String nom;
    private String commune;
    private String departement;
    private String federation;
    private int codeCommune;

    public Club(int id, String nom, int codeCommune,String commune, String departement, String federation) {
        this.id = id;
        this.nom = nom;
        this.commune = commune;
        this.federation = federation;
        this.codeCommune=codeCommune;
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
    
    public int getCodeCommune() {
    	return codeCommune;
    }
    
    public void setCodeCommune(int codeCommune) {
    	this.codeCommune=codeCommune;
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
                ", codeCommune='" + codeCommune + '\'' +
                ", commune='" + commune + '\'' +
                //", departement='" + departement + '\'' +
                ", federation='" + federation + '\'' +
                '}';
 
	}

	
}
