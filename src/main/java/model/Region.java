/**
 * la classe region pour gerer les régions françaises
 * @author imane
 * @version 1.0
 */
package model;

import java.util.ArrayList;

public class Region {
	/** le code de chaque region */
	private int codeRegion;
	/** le libelle de chaque region */
	private String libelleRegion;
	/** liste des departements d'une région */
	private ArrayList<Departement> listeDepartements;

	/**
	 * constructeur de la classe region
	 * 
	 * @param code    code de la région
	 * @param libelle le nom de la région
	 avec set*/
	public Region(int code, String libelle) {
		this.codeRegion = code;
		this.libelleRegion = libelle;
		this.listeDepartements = new ArrayList<>();
	}

	public Region() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * getter du code de la region
	 * 
	 * @return le code de la region
	 */
	public int getCodeRegion() {
		return codeRegion;
	}

	/**
	 * setter du code de la region
	 * 
	 * @param le code de la region
	 */
	public void setCodeRegion(int codeRegion) {
		this.codeRegion = codeRegion;
	}

	/**
	 * getter du nom de la region
	 * 
	 * @return le nom de la region
	 */
	public String getLibelleRegion() {
		return libelleRegion;
	}

	/**
	 * setter du nom de la region
	 * 
	 * @param libelleRegion
	 */
	public void setLibelleRegion(String libelleRegion) {
		this.libelleRegion = libelleRegion;
	}

	/**
	 * getter de la liste des departements de la région
	 * @return
	 */
	public ArrayList<Departement> getListeDepartements() {
		return listeDepartements;
	}

}
