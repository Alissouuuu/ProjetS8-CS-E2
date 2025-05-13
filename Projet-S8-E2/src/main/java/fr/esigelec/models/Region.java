package fr.esigelec.models;

public class Region {
	private String codeRegion,nom;
	
	public Region(String codeRegion,String nom) {
		this.codeRegion = codeRegion;
		this.nom = nom;
	}
	
	public String getCodeRegion() {
		return codeRegion;
	}
	public String getNom() {
		return nom;
	}
	
	
}
