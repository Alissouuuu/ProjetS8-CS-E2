package fr.esigelec.models;

public class Club {
	private String federation,commune,region,departement,nom,codeFederation,codeCommune;
	private int id,nombreLicences;
	
	public Club(int id, String federation, String commune,String departement, String region, String nom, String codeFederation, String codeCommune,
			int nombreLicences) {
		this.id = id;
		this.federation = federation;
		this.commune = commune;
		this.departement = departement;
		this.region = region;
		this.nom = nom;
		this.codeFederation = codeFederation;
		this.codeCommune = codeCommune;
		this.nombreLicences = nombreLicences;
	}

	public String getFederation() {
		return federation;
	}

	public String getCommune() {
		return commune;
	}
	
	public String getDepartement() {
		return departement;
	}

	public String getRegion() {
		return region;
	}

	public String getNom() {
		return nom;
	}

	public String getCodeFederation() {
		return codeFederation;
	}

	public String getCodeCommune() {
		return codeCommune;
	}

	public int getId() {
		return id;
	}

	public int getNombreLicences() {
		return nombreLicences;
	}
	
	
	
}
