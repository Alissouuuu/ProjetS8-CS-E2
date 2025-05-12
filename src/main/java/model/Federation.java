/**
 * La classe fédération est conçue pour gerer les fédérations de sport 
 * @author imane
 * @version 1.0
 */
package model;

import java.util.ArrayList;

public class Federation {
	/** le code de chaque federation */
	private int codeFederation;
	/**
	 * le nom de la fédération sportive
	 */
	private String libelleFedeation;
	/** liste des clubs appartenant à la federation */
	private ArrayList<Club> listeClubs;

	/**
	 * getter du code de la federation
	 * 
	 * @return le code de la federation
	 */
	public int getCodeFederation() {
		return codeFederation;
	}

	/**
	 * setter du code de la federation
	 * 
	 * @param codeFederation
	 */
	public void setCodeFederation(int codeFederation) {
		this.codeFederation = codeFederation;
	}

	/**
	 * getter du nom de la federation
	 * 
	 * @return le nom de la federation
	 */
	public String getLibelleFedeation() {
		return libelleFedeation;
	}

	/**
	 * setter du nom de la federation
	 * 
	 * @param libelleFedeation
	 */
	public void setLibelleFedeation(String libelleFedeation) {
		this.libelleFedeation = libelleFedeation;
	}

	/**
	 * Constructeur de la classe Fedeation
	 * 
	 * @param code    code la federation
	 * @param libelle nom de la federation
	 */
	public Federation(int code, String libelle) {
		this.codeFederation = code;
		this.libelleFedeation = libelle;
		this.listeClubs = new ArrayList<>();

	}

	/**
	 * getter de la liste des clubs
	 * 
	 * @return
	 */
	public ArrayList<Club> getListeClubs() {
		return listeClubs;
	}

}
