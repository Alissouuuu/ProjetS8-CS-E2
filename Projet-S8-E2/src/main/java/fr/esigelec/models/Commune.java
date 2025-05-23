package fr.esigelec.models;

public class Commune implements ZoneGeo{
	private String codeCommune,codePostal,nom,qpv;
	private double longitude,latitude;
	private Departement departement;
	
	public Commune(String codeCommune, String codePostal, String nom, String qpv, double longitude, double latitude,
			Departement departement) {
		super();
		this.codeCommune = codeCommune;
		this.codePostal = codePostal;
		this.nom = nom;
		this.qpv = qpv;
		this.longitude = longitude;
		this.latitude = latitude;
		this.departement = departement;
	}

	public String getCodeCommune() {
		return codeCommune;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public String getNom() {
		return nom;
	}

	public String getQpv() {
		return qpv;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public Departement getDepartement() {
		return departement;
	}
	
	
}
