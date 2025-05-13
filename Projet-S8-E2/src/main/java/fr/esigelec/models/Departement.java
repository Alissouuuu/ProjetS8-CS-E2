package fr.esigelec.models;

public class Departement {
	private String codeDepartement,nom;
	private Region region;
	
	public Departement(String codeDepartement, String nom,Region region) {
		this.codeDepartement = codeDepartement;
		this.nom = nom;
		this.region = region;
	}

	public String getCodeDepartement() {
		return codeDepartement;
	}

	public String getNom() {
		return nom;
	}

	public Region getRegion() {
		return region;
	}
	
}
