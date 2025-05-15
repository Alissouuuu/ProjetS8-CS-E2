package fr.esigelec.models;

public class ClassementRegion implements IClassement{
	private int licences;
	private Region region;
	
	public ClassementRegion(Region reg,int lic) {
		this.licences = lic;
		this.region = reg;
	}

	public int getLicences() {
		return licences;
	}

	public Region getRegion() {
		return region;
	}
	
	
}
