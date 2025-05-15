package fr.esigelec.models;

public class ClassementCommune implements IClassement{
	private int licences;
	private Commune commune;
	
	public ClassementCommune(Commune com,int lic) {
		this.licences = lic;
		this.commune = com;
	}

	public int getLicences() {
		return licences;
	}

	public Commune getCommune() {
		return commune;
	}
	
	
}
