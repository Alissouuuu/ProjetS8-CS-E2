/**
 * La classe Commune est conçue pour gérer les  communes
 * @author imane
 * @version 1.0
 */
package model;

import java.util.ArrayList;

public class Commune {
	/** le code commune */
	private String codeCommune;
	/** le qpv d'une commune */
	private String qpv;
	/**
	 * la latitude de la commune
	 */
	private double latitude;
	/**
	 * la longitude de la commune
	 */
	private double longitude;
	/** les codes postaux d'une commune */
	private ArrayList<CodePostal> codesPostaux;
    private ArrayList<LibelleCommune> libelles;


	/** methodes de la classe commune */
	public Commune(String code, String qpv, double latitude, double longitude)
	{
		this.codeCommune=code;
		this.qpv=qpv;
		this.latitude=latitude;
		this.longitude=longitude;
		this.codesPostaux=new ArrayList<>();
		this.libelles=new ArrayList<>();

		
	}
	public Commune() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * getter du code de la commune
	 * 
	 * @return le code commune
	 */
	public String getCodeCommune() {
		return codeCommune;
	}

	/**
	 * setter du code de la commune
	 * 
	 * @param codeCommune
	 */
	public void setCodeCommune(String codeCommune) {
		this.codeCommune = codeCommune;
	}

	/**
	 * getter du qpv de la commune
	 * 
	 * @return le qpv de la commune
	 */
	public String getQpv() {
		return qpv;
	}

	/**
	 * setter du qpv de la commune
	 * 
	 * @param qpv
	 */
	public void setQpv(String qpv) {
		this.qpv = qpv;
	}

	/**
	 * getter de la latitude de la commune
	 * 
	 * @return la latitude de la commune
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * setter de la latitude de la commune
	 * 
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * getter de la longitude de la commune
	 * 
	 * @return la longitude de la commune
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * setter de la longitude de la commune
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public ArrayList<CodePostal> getCodesPostaux()
	{
		return this.codesPostaux;
	}
	public ArrayList<LibelleCommune> getLibellesCommune()
	{
		return this.libelles;
	}
	public void setLibelles(ArrayList<LibelleCommune> libelles) {
	    this.libelles = libelles;
	}

}
