package fr.esigelec.modelCarte;

public class DepartementCarte {
    private String nom;
    private double lat;
    private double lon;

    public DepartementCarte(String nom, double lat, double lon) {
        this.nom = nom;
        this.lat = lat;
        this.lon = lon;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

}


