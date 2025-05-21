/**
 * La classe LibelleCommune est conçue pour gérer les  noms des communes
 * @author imane
 * @version 1.0
 */
package model;

public class LibelleCommune {
	/** id du libelle d'une commune*/
	private int idLibelle;
	/** libelle d'une commune*/
	private String libelleCommune;
	/**
	 * getter   de l'id du libelle d'une commune
	 * @return id du libelle d'une commune
	 */
	public int getIdLibelle() {
		return idLibelle;
	}
	/**
	 * setter   de l'id du libelle d'une commune
	 * @param idLibelle
	 */
	public void setIdLibelle(int idLibelle) {
		this.idLibelle = idLibelle;
	}
	/**
	 * getter du libelle de la commune
	 * @return le libelle de la commune
	 */
	public String getLibelleCommune() {
		return libelleCommune;
	}
	/**
	 * setter du libelle de la commune
	 * @param libelleCommune
	 */
	public void setLibelleCommune(String libelleCommune) {
		this.libelleCommune = libelleCommune;
	}

}
