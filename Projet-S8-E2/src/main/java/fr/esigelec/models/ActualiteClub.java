/**
 * la classe club pour gérer  les actulités des clubs de chaque fédération 
 * @author imane
 * @version 1.0
 */
package fr.esigelec.models;

import java.time.LocalDate;

public class ActualiteClub {
	/** le titre de l'actualité */
	private String titreActu;
	/** la description de l'actualité */
	private String descriptionActu;
	/** l'objet du conseil */
	private String objetConseil;
	/** la description du conseil */
	private String descriptionConseil;
	/** le nom de la competition */
	private String nomCompetition;
	/** le lieu de la competition */
	private String lieuCompetition;
	/** le score de la competition */
	private int scoreCompetition;
	/** la date de la competition */
	private LocalDate dateCompetition;
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
	/**
	 * getter de l'objet du conseil partagé
	 * @return l'objet du conseil partagé
	 */
	public String getObjetConseil() {
		return this.objetConseil;
	}
	/**
	 * setter de l'objet du conseil partagé
	 * @param objetConseil
	 */
	public void setObjetConseil(String objetConseil) {
		this.objetConseil = objetConseil;
	}
	/**
	 * getter description du conseil partagé
	 * @return description du conseil 
	 */
	public String getDescriptionConseil() {
		return this.descriptionConseil;
	}
	/**
	 * setter description du conseil partagé
	 * @param descriptionConseil
	 */
	public void setDescriptionConseil(String descriptionConseil) {
		this.descriptionConseil = descriptionConseil;
	}
	/**
	 * getter du nom de la competition
	 * @return le  nom de la competition
	 */
	public String getNomCompetition() {
		return this.nomCompetition;
	}
	/**
	 * setter du nom de la competition
	 * @param nomCompetition
	 */
	public void setNomCompetition(String nomCompetition) {
		this.nomCompetition = nomCompetition;
	}
	/**
	 * getter du lieu de la competition du club
	 * @return le lieu de la competition du club
	 */
	public String getLieuCompetition() {
		return this.lieuCompetition;
	}
	/**
	 * setter du lieu de la competition du club
	 * @param lieuCompetition
	 */
	public void setLieuCompetition(String lieuCompetition) {
		this.lieuCompetition = lieuCompetition;
	}
	/**
	 * getter du score final de la competition 
	 * @return le score final d'une competition
	 */
	public int getScoreCompetition() {
		return this.scoreCompetition;
	}
	/**
	 * setter du score final de la competition 
	 * @param scoreCompetition
	 */
	public void setScoreCompetition(int scoreCompetition) {
		this.scoreCompetition = scoreCompetition;
	}
	/**
	 * getter de la date de la competition
	 * @return la date de la competition
	 */
	public LocalDate getDateCompetition() {
		return this.dateCompetition;
	}
	/**
	 * setter de la date de la competition
	 * @param dateCompetition
	 */
	public void setDateCompetition(LocalDate dateCompetition) {
		this.dateCompetition = dateCompetition;
	}
	

}
