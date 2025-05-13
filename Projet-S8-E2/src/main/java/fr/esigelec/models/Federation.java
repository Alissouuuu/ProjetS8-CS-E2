package fr.esigelec.models;

public class Federation {
	private int codeFederation;
	private String nom;

	public Federation(int codeFederation, String nom) {
		super();
		this.codeFederation = codeFederation;
		this.nom = nom;
	}

	public int getCodeFederation() {
		return codeFederation;
	}

	public String getNom() {
		return nom;
	}
	
}
