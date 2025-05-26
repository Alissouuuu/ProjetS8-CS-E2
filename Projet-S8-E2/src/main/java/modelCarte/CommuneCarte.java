package modelCarte;

public class CommuneCarte {
	private int code_commune;
	private String libelle;
	private int code_qpv;
	
	public CommuneCarte(int code_commune, String libelle, int code_qpv) {
		
		this.code_commune = code_commune;
		this.libelle = libelle;
		this.code_qpv = code_qpv;
	}

	public int getCode_commune() {
		return code_commune;
	}

	public void setCode_commune(int code_commune) {
		this.code_commune = code_commune;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getCode_qpv() {
		return code_qpv;
	}

	public void setCode_qpv(int code_qpv) {
		this.code_qpv = code_qpv;
	}
	
}
