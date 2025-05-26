package modelCarte;


public class RegionCarte {
    private String nom;
    private double lat;
    private double lon;

    public RegionCarte(String nom, double lat, double lon) {
        this.nom = nom;
        this.lat = lat;
        this.lon= lon;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}


}

