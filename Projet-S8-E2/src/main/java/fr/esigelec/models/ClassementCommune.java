package fr.esigelec.models;

public class ClassementCommune implements IClassement{
	private int licences,licencesFemmes,licencesHommes;
	private int licencesHommes114,licencesHommes1524,licencesHommes2534,licencesHommes3549,licencesHommes5079,licencesHommes8099;
	private int licencesFemmes114,licencesFemmes1524,licencesFemmes2534,licencesFemmes3549,licencesFemmes5079,licencesFemmes8099;
	private int licencesTotales114,licencesTotales1524,licencesTotales2534,licencesTotales3549,licencesTotales5079,licencesTotales8099;
	private Commune commune;
	
	public ClassementCommune(Commune com,int lic,int licF,int licH) {
		this.licences = lic;
		this.commune = com;
		this.licencesFemmes = licF;
		this.licencesHommes = licH;
	}
	

	public ClassementCommune(int licences, int licencesFemmes, int licencesHommes, int licencesHommes114,
			int licencesHommes1524, int licencesHommes2534, int licencesHommes3549, int licencesHommes5079,
			int licencesHommes8099, int licencesFemmes114, int licencesFemmes1524, int licencesFemmes2534,
			int licencesFemmes3549, int licencesFemmes5079, int licencesFemmes8099, int licencesTotales114,
			int licencesTotales1524, int licencesTotales2534, int licencesTotales3549, int licencesTotales5079,
			int licencesTotales8099, Commune commune) {
		this.licences = licences;
		this.licencesFemmes = licencesFemmes;
		this.licencesHommes = licencesHommes;
		this.licencesHommes114 = licencesHommes114;
		this.licencesHommes1524 = licencesHommes1524;
		this.licencesHommes2534 = licencesHommes2534;
		this.licencesHommes3549 = licencesHommes3549;
		this.licencesHommes5079 = licencesHommes5079;
		this.licencesHommes8099 = licencesHommes8099;
		this.licencesFemmes114 = licencesFemmes114;
		this.licencesFemmes1524 = licencesFemmes1524;
		this.licencesFemmes2534 = licencesFemmes2534;
		this.licencesFemmes3549 = licencesFemmes3549;
		this.licencesFemmes5079 = licencesFemmes5079;
		this.licencesFemmes8099 = licencesFemmes8099;
		this.licencesTotales114 = licencesTotales114;
		this.licencesTotales1524 = licencesTotales1524;
		this.licencesTotales2534 = licencesTotales2534;
		this.licencesTotales3549 = licencesTotales3549;
		this.licencesTotales5079 = licencesTotales5079;
		this.licencesTotales8099 = licencesTotales8099;
		this.commune = commune;
	}


	public int getLicencesHommes114() {
		return licencesHommes114;
	}


	public int getLicencesHommes1524() {
		return licencesHommes1524;
	}


	public int getLicencesHommes2534() {
		return licencesHommes2534;
	}


	public int getLicencesHommes3549() {
		return licencesHommes3549;
	}


	public int getLicencesHommes5079() {
		return licencesHommes5079;
	}


	public int getLicencesHommes8099() {
		return licencesHommes8099;
	}


	public int getLicencesFemmes114() {
		return licencesFemmes114;
	}


	public int getLicencesFemmes1524() {
		return licencesFemmes1524;
	}


	public int getLicencesFemmes2534() {
		return licencesFemmes2534;
	}


	public int getLicencesFemmes3549() {
		return licencesFemmes3549;
	}


	public int getLicencesFemmes5079() {
		return licencesFemmes5079;
	}


	public int getLicencesFemmes8099() {
		return licencesFemmes8099;
	}


	public int getLicencesTotales114() {
		return licencesTotales114;
	}


	public int getLicencesTotales1524() {
		return licencesTotales1524;
	}


	public int getLicencesTotales2534() {
		return licencesTotales2534;
	}


	public int getLicencesTotales3549() {
		return licencesTotales3549;
	}


	public int getLicencesTotales5079() {
		return licencesTotales5079;
	}


	public int getLicencesTotales8099() {
		return licencesTotales8099;
	}


	public int getLicences() {
		return licences;
	}
	
	public int getLicencesFemmes() {
		return licencesFemmes;
	}
	
	public int getLicencesHommes() {
		return licencesHommes;
	}

	public Commune getCommune() {
		return commune;
	}
	
	
}
