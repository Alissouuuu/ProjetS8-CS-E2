/**
 * la classe LogConnexion pour gérer les connexions des utilisateurs 
 * @author imane
 * @version 1.0
 */
package model;

public class LogConnexion {
	/** l'id de chaque connexion */
	private int idConnexion;
	/** l'adresse ip de la connexion courrante*/
	private String adresseIp;
	/**  le nombre de tentatives de connexions échouées*/
	private int tentativesConnexionEchouee;
	/***
	 * constructeur de la classe LogConnexion
	 * @param id l'id de chaque connexio
	 * @param adresseIp l'adresse ip de la connexion
	 * @param connEchouee le nombre de tentatives de connexions échouées
	 */
	public LogConnexion(int id, String adresseIp, int connEchouee)
	{
		this.adresseIp=adresseIp;
		this.idConnexion=id;
		this.tentativesConnexionEchouee=connEchouee;
	}
	/**
	 * getter de l'id de la connexion
	 * @return l'id de la connexion
	 */
	public int getIdConnexion() {
		return idConnexion;
	}
	/**
	 * setter de l'id de la connexion
	 * @param idConnexion id de la connexion
	 */
	public void setIdConnexion(int idConnexion) {
		this.idConnexion = idConnexion;
	}
	/**
	 * getter l'adresse ip de la connexion courrante
	 * @return l'adresse ip de la connexion courrante
	 */
	public String getAdresseIp() {
		return adresseIp;
	}
	/**
	 * setter de l'adresse ip de la connexion courrante
	 * @param adresseIp l'adresse ip de la connexion courrante
	 */
	public void setAdresseIp(String adresseIp) {
		this.adresseIp = adresseIp;
	}
	/**
	 * getter du nombre de tentatives de connexions échouées
	 * @return le nombre de tentatives de connexions échouées
	 */
	public int getTentativesConnexionEchouee() {
		return tentativesConnexionEchouee;
	}
	/**
	 * setter du  nombre de tentatives de connexions échouées
	 * @param tentativesConnexionEchouee le nombre de tentatives de connexions échouées
	 */
	public void setTentativesConnexionEchouee(int tentativesConnexionEchouee) {
		this.tentativesConnexionEchouee = tentativesConnexionEchouee;
	}
	

}
