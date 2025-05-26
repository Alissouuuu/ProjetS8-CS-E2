package model;

import java.util.HashMap;


import java.sql.Connection;
import java.sql.SQLException;

import util.DBConnexion;

public class Test {

	public static void main(String[] args) {
		HashMap<String,String> tranche_age = new HashMap<>();
		tranche_age.put("Homme", "0-4");
		tranche_age.put("Femme", "0-4");
		tranche_age.put("Homme", "10-14");
	
		System.out.println(tranche_age);
		ActualiteClub actu = new ActualiteClub();
		System.out.println(actu.getDescriptionActu());
		actu.setDescriptionActu("saalam");
		System.out.println(actu.getDescriptionActu());
		 try {
	            Connection conn = DBConnexion.getConnection();
	            if (conn != null && !conn.isClosed()) {
	                System.out.println("Connexion reussie à la bdd.");
	            } else {
	                System.out.println(" Connexion echouée.");
	            }
	        } catch (SQLException e) {
	            System.out.println("Erreur SQL : " + e.getMessage());
	        } catch (Exception e) {
	            System.out.println("Erreur generale : " + e.getMessage());
	        }
	}
	

}
