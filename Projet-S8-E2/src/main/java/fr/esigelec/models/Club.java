package fr.esigelec.models;

public class Club {
	private String nom;
	private Commune commune;
	private Federation federation;
	private int id,totalLicences,totalLicencesFemme,totalLicencesHomme,trancheAgeF14,trancheAgeF59,trancheAgeF1014,
    		trancheAgeF1519,trancheAgeF2024,trancheAgeF2529,trancheAgeF3034,trancheAgeF3539,trancheAgeF4044,
    		trancheAgeF4549,trancheAgeF5054,trancheAgeF5559,trancheAgeF6064,trancheAgeF6569,trancheAgeF7074,
    		trancheAgeF7579,trancheAgeF8099,trancheAgeFNR,trancheAgeH14,trancheAgeH59,trancheAgeH1014,
    		trancheAgeH1519,trancheAgeH2024,trancheAgeH2529,trancheAgeH3034,trancheAgeH3539,trancheAgeH4044,
    		trancheAgeH4549,trancheAgeH5054,trancheAgeH5559,trancheAgeH6064,trancheAgeH6569,trancheAgeH7074,
    		trancheAgeH7579,trancheAgeH8099,trancheAgeHNR;
	
	public Club(int id,Federation federation,Commune commune,String nom,int totalLicences,int totalLicencesFemme,int totalLicencesHomme) {
		this.id = id;
		this.federation = federation;
		this.commune = commune;
		this.nom = nom;
		this.totalLicences = totalLicences;
		this.totalLicencesFemme = totalLicencesFemme;
		this.totalLicencesHomme = totalLicencesHomme;
	}
	
	
	public Club(String nom, Commune commune, Federation federation, int id, int totalLicences, int totalLicencesFemme,
			int totalLicencesHomme, int trancheAgeF14, int trancheAgeF59, int trancheAgeF1014, int trancheAgeF1519,
			int trancheAgeF2024, int trancheAgeF2529, int trancheAgeF3034, int trancheAgeF3539, int trancheAgeF4044,
			int trancheAgeF4549, int trancheAgeF5054, int trancheAgeF5559, int trancheAgeF6064, int trancheAgeF6569,
			int trancheAgeF7074, int trancheAgeF7579, int trancheAgeF8099, int trancheAgeFNR, int trancheAgeH14,
			int trancheAgeH59, int trancheAgeH1014, int trancheAgeH1519, int trancheAgeH2024, int trancheAgeH2529,
			int trancheAgeH3034, int trancheAgeH3539, int trancheAgeH4044, int trancheAgeH4549, int trancheAgeH5054,
			int trancheAgeH5559, int trancheAgeH6064, int trancheAgeH6569, int trancheAgeH7074, int trancheAgeH7579,
			int trancheAgeH8099, int trancheAgeHNR) {
		super();
		this.nom = nom;
		this.commune = commune;
		this.federation = federation;
		this.id = id;
		this.totalLicences = totalLicences;
		this.totalLicencesFemme = totalLicencesFemme;
		this.totalLicencesHomme = totalLicencesHomme;
		this.trancheAgeF14 = trancheAgeF14;
		this.trancheAgeF59 = trancheAgeF59;
		this.trancheAgeF1014 = trancheAgeF1014;
		this.trancheAgeF1519 = trancheAgeF1519;
		this.trancheAgeF2024 = trancheAgeF2024;
		this.trancheAgeF2529 = trancheAgeF2529;
		this.trancheAgeF3034 = trancheAgeF3034;
		this.trancheAgeF3539 = trancheAgeF3539;
		this.trancheAgeF4044 = trancheAgeF4044;
		this.trancheAgeF4549 = trancheAgeF4549;
		this.trancheAgeF5054 = trancheAgeF5054;
		this.trancheAgeF5559 = trancheAgeF5559;
		this.trancheAgeF6064 = trancheAgeF6064;
		this.trancheAgeF6569 = trancheAgeF6569;
		this.trancheAgeF7074 = trancheAgeF7074;
		this.trancheAgeF7579 = trancheAgeF7579;
		this.trancheAgeF8099 = trancheAgeF8099;
		this.trancheAgeFNR = trancheAgeFNR;
		this.trancheAgeH14 = trancheAgeH14;
		this.trancheAgeH59 = trancheAgeH59;
		this.trancheAgeH1014 = trancheAgeH1014;
		this.trancheAgeH1519 = trancheAgeH1519;
		this.trancheAgeH2024 = trancheAgeH2024;
		this.trancheAgeH2529 = trancheAgeH2529;
		this.trancheAgeH3034 = trancheAgeH3034;
		this.trancheAgeH3539 = trancheAgeH3539;
		this.trancheAgeH4044 = trancheAgeH4044;
		this.trancheAgeH4549 = trancheAgeH4549;
		this.trancheAgeH5054 = trancheAgeH5054;
		this.trancheAgeH5559 = trancheAgeH5559;
		this.trancheAgeH6064 = trancheAgeH6064;
		this.trancheAgeH6569 = trancheAgeH6569;
		this.trancheAgeH7074 = trancheAgeH7074;
		this.trancheAgeH7579 = trancheAgeH7579;
		this.trancheAgeH8099 = trancheAgeH8099;
		this.trancheAgeHNR = trancheAgeHNR;
	}



	public String getNom() {
		return nom;
	}

	public Commune getCommune() {
		return commune;
	}

	public Federation getFederation() {
		return federation;
	}

	public int getId() {
		return id;
	}

	public int getTotalLicences() {
		return totalLicences;
	}

	public int getTotalLicencesFemme() {
		return totalLicencesFemme;
	}

	public int getTotalLicencesHomme() {
		return totalLicencesHomme;
	}

	public int getTrancheAgeF14() {
		return trancheAgeF14;
	}

	public int getTrancheAgeF59() {
		return trancheAgeF59;
	}

	public int getTrancheAgeF1014() {
		return trancheAgeF1014;
	}

	public int getTrancheAgeF1519() {
		return trancheAgeF1519;
	}

	public int getTrancheAgeF2024() {
		return trancheAgeF2024;
	}

	public int getTrancheAgeF2529() {
		return trancheAgeF2529;
	}

	public int getTrancheAgeF3034() {
		return trancheAgeF3034;
	}

	public int getTrancheAgeF3539() {
		return trancheAgeF3539;
	}

	public int getTrancheAgeF4044() {
		return trancheAgeF4044;
	}

	public int getTrancheAgeF4549() {
		return trancheAgeF4549;
	}

	public int getTrancheAgeF5054() {
		return trancheAgeF5054;
	}

	public int getTrancheAgeF5559() {
		return trancheAgeF5559;
	}

	public int getTrancheAgeF6064() {
		return trancheAgeF6064;
	}

	public int getTrancheAgeF6569() {
		return trancheAgeF6569;
	}

	public int getTrancheAgeF7074() {
		return trancheAgeF7074;
	}

	public int getTrancheAgeF7579() {
		return trancheAgeF7579;
	}

	public int getTrancheAgeF8099() {
		return trancheAgeF8099;
	}

	public int getTrancheAgeFNR() {
		return trancheAgeFNR;
	}

	public int getTrancheAgeH14() {
		return trancheAgeH14;
	}

	public int getTrancheAgeH59() {
		return trancheAgeH59;
	}

	public int getTrancheAgeH1014() {
		return trancheAgeH1014;
	}

	public int getTrancheAgeH1519() {
		return trancheAgeH1519;
	}

	public int getTrancheAgeH2024() {
		return trancheAgeH2024;
	}

	public int getTrancheAgeH2529() {
		return trancheAgeH2529;
	}

	public int getTrancheAgeH3034() {
		return trancheAgeH3034;
	}

	public int getTrancheAgeH3539() {
		return trancheAgeH3539;
	}

	public int getTrancheAgeH4044() {
		return trancheAgeH4044;
	}

	public int getTrancheAgeH4549() {
		return trancheAgeH4549;
	}

	public int getTrancheAgeH5054() {
		return trancheAgeH5054;
	}

	public int getTrancheAgeH5559() {
		return trancheAgeH5559;
	}

	public int getTrancheAgeH6064() {
		return trancheAgeH6064;
	}

	public int getTrancheAgeH6569() {
		return trancheAgeH6569;
	}

	public int getTrancheAgeH7074() {
		return trancheAgeH7074;
	}

	public int getTrancheAgeH7579() {
		return trancheAgeH7579;
	}

	public int getTrancheAgeH8099() {
		return trancheAgeH8099;
	}

	public int getTrancheAgeHNR() {
		return trancheAgeHNR;
	}

	
	
}
