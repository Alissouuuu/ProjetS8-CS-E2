/**
 * La classe evenement est conçue pour gérer les événements organisés par un club
 * @author imane
 * @version 1.0
 */
package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evenement {
/** le nombre maximal des participants dans un evenement*/
private int nbrMaxParticipants;
/** le lieu de  l'evenement*/
private String lieuEvenement;
/** l'heure de l'organisation de  l'evenement*/
private LocalTime heureEvenement;
/** la date de l'organisation de  l'evenement*/
private LocalDate dateEvenement;
/** le nom de  l'evenement*/
private String nomEvenement;
/** la description de  l'evenement*/
private String descriptionEvenement;
/**
 * getter du nombre maximal des participants dans un evenement
 * @return le nombre maximal des participants dans un evenement
 */
public int getNbrMaxParticipants() {
	return nbrMaxParticipants;
}
/**
 * setter du nombre maximal des participants dans un evenement
 * @param nbrMaxParticipants
 */
public void setNbrMaxParticipants(int nbrMaxParticipants) {
	this.nbrMaxParticipants = nbrMaxParticipants;
}
/**
 * getter du lieu de  l'evenement
 * @return le lieu de  l'evenement
 */
public String getLieuEvenement() {
	return lieuEvenement;
}
/**
 * setter du lieu de  l'evenement
 * @param lieuEvenement
 */
public void setLieuEvenement(String lieuEvenement) {
	this.lieuEvenement = lieuEvenement;
}
/**
 * getter de l'heure de  l'evenement
 * @return l'heure de  l'evenement
 */
public LocalTime getHeureEvenement() {
	return heureEvenement;
}
/**
 * setter de l'heure de  l'evenement
 * @param heureEvenement
 */
public void setHeureEvenement(LocalTime heureEvenement) {
	this.heureEvenement = heureEvenement;
}
/**
 * getter de la date de  l'evenement
 * @return la date de  l'evenement
 */
public LocalDate getDateEvenement() {
	return dateEvenement;
}
/**
 * setter de la date de  l'evenement
 * @param ddateEvenement
 */
public void setDateEvenement(LocalDate ddateEvenement) {
	this.dateEvenement = ddateEvenement;
}
/**
 * getter du nom de  l'evenement
 * @return
 */
public String getNomEvenement() {
	return nomEvenement;
}
/**
 * setter du nom de  l'evenement
 * @param nomEvenement
 */
public void setNomEvenement(String nomEvenement) {
	this.nomEvenement = nomEvenement;
}
/**
 * getter de la description de  l'evenement
 * @return la description de  l'evenement
 */
public String getDescriptionEvenement() {
	return descriptionEvenement;
}
/**
 * setter de la description de  l'evenement
 * @param descriptionEvenement
 */
public void setDescriptionEvenement(String descriptionEvenement) {
	this.descriptionEvenement = descriptionEvenement;
}
}
