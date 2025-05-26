package modelCarte;

public class FederationCarte {

	private int code_federation;
	private String libelle;

	public FederationCarte(int code_federation, String libelle) {

		this.code_federation = code_federation;
		this.libelle = libelle;
	}

	public int getCode_federation() {
		return code_federation;
	}

	public void setCode_federation(int code_federation) {
		this.code_federation = code_federation;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
	
}
