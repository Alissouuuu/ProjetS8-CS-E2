package fr.esigelec.model;

public class ClubDAO {
	
	private int id_club;
	private String libelle_club;
	private String commune;
	private String federation;
	private double lat;
	private double lon;
	
	
	public ClubDAO(int id_club, String libelle_club, String commune, String federation, double lat, double lon) {
		super();
		this.id_club = id_club;
		this.libelle_club = libelle_club;
		this.commune = commune;
		this.federation = federation;
		this.lat = lat;
		this.lon = lon;
	}
	
	public int getId_club() {
		return id_club;
	}
	public void setId_club(int id_club) {
		this.id_club = id_club;
	}
	public String getLibelle_club() {
		return libelle_club;
	}
	public void setLibelle_club(String libelle_club) {
		this.libelle_club = libelle_club;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
	}
	public String getFederation() {
		return federation;
	}
	public void setFederation(String federation) {
		this.federation = federation;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	
}
