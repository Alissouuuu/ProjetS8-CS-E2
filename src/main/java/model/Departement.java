/**
 * la classe departement pour gerer les departements francais
 * @author imane
 * @version 1.0
 */
package model;

import java.util.ArrayList;

public class Departement {
	/** le code de chaque departement */
	private String codeDepartement;
	/** le libelle de chaque departement */
	private String libelleDepartement;
	/** liste des communes d'un departement */
	private ArrayList<Commune> listeCommunes;

	public Departement(String code, String libelle) {
		this.codeDepartement = code;
		this.libelleDepartement = libelle;
		this.listeCommunes = new ArrayList<>();
	}

	public Departement() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * getter du code du departement
	 * 
	 * @return le code du departement
	 */
	public String getCodeDepartement() {
		return codeDepartement;
	}

	/**
	 * setter du code du departement
	 * 
	 * @param codeDepartement
	 */
	public void setCodeDepartement(String codeDepartement) {
		this.codeDepartement = codeDepartement;
	}

	/**
	 * getter du libelle du departement
	 * 
	 * @return le nom du departement
	 */
	public String getLibelleDepartement() {
		return libelleDepartement;
	}

	/**
	 * setter du libelle du departement
	 * 
	 * @param libelleDepartement
	 */
	public void setLibelleDepartement(String libelleDepartement) {
		this.libelleDepartement = libelleDepartement;
	}
	/**
	 * getter de la liste des communes du departement
	 * @return
	 */
	public ArrayList<Commune> getListeCommunes() {
		return listeCommunes;
	}
}
