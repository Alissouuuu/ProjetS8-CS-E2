/**
 * la classe club pour gérer  les actulités des clubs de chaque fédération 
 * @author imane
 * @version 1.0
 */
package model;

import java.time.LocalDate;

public class ActualiteClub {
	/** le titre de l'actualité */
	private String titreActu;
	/** la description de l'actualité */
	private String descriptionActu;
	private int idUser;
	private int idClub;
	private int idActu;
	
	/**
	 * getter du titre de l'actualité du club
	 * @return le titre de l'actualité à afficher
	 */
	public String getTitreActu() {
		return this.titreActu;
	}
	/***
	 * setter du titre de l'actualité du club
	 * @param titreActu
	 */
	public void setTitreActu(String titreActu) {
		this.titreActu = titreActu;
	}
	/**
	 * getter de la description de l'actualité du club
	 * @return la description de l'actualité à afficher
	 */
	public String getDescriptionActu() {
		return this.descriptionActu;
	}
	/**
	 * setter de la description de l'actualité du club
	 * @param descriptionActu
	 */
	public void setDescriptionActu(String descriptionActu) {
		this.descriptionActu = descriptionActu;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getIdClub() {
		return idClub;
	}
	public void setIdClub(int idClub) {
		this.idClub = idClub;
	}
	public void setDatePublication(LocalDate localDate) {
		
	}
	public int getIdActu() {
		return idActu;
	}
	public void setIdActu(int idActu) {
		this.idActu = idActu;
	}

	

}
