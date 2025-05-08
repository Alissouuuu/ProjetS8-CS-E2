package fr.esigelec.dao;

import java.sql.*;
import java.util.*;

import fr.esigelec.model.*;
import fr.esigelec.CalculRayon;
import fr.esigelec.*;

public class DAOClub {
	
	public List<String> getListCommune() throws SQLException, ClassNotFoundException {
	    List<String> villes = new ArrayList<>();
	    //System.out.println(">> Entrée dans getListCommune()");
	    
	    String sql = "SELECT DISTINCT libelle_commune FROM commune ORDER BY libelle_commune";

	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	            villes.add(rs.getString("libelle_commune"));
	        }
	        
	    }catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
//	    for (String ville : villes) {
//	    	System.out.println(ville);
//	    }
	    return villes;
	}
	
	public List<String> getListDepartement() throws SQLException, ClassNotFoundException {
	    List<String> departements = new ArrayList<>();
	    //System.out.println(">> Entrée dans getListDepartement()");
	    
	    String sql = "SELECT DISTINCT libelle_departement FROM departement ORDER BY libelle_departement";

	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	        	departements.add(rs.getString("libelle_departement"));
	        }
	        
	    }catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
//	    for (String departement : departements) {
//	    	System.out.println(departement);
//	    }
	    return departements;
	}
	
	public List<String> getListRegion() throws SQLException, ClassNotFoundException {
	    List<String> regions = new ArrayList<>();
	    //System.out.println(">> Entrée dans getListRegion()");
	    
	    String sql = "SELECT DISTINCT libelle_region FROM region ORDER BY libelle_region";

	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	        	regions.add(rs.getString("libelle_region"));
	        }
	        
	    }catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
//	    for (String region : regions) {
//	    	System.out.println(region);
//	    }
	    return regions;
	}
	
	public List<String> getListFederation() throws SQLException, ClassNotFoundException {
	    List<String> federations = new ArrayList<>();
	    //System.out.println(">> Entrée dans getListFederation()");
	    
	    String sql = "SELECT DISTINCT libelle_federation FROM federation ORDER BY libelle_federation";

	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	        	federations.add(rs.getString("libelle_federation"));
	        }
	        
	    }catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
//	    for (String region : regions) {
//	    	System.out.println(region);
//	    }
	    return federations;
	}
	
	
	public List<ClubDAO> searchClubByVille(String nomVille) throws SQLException, ClassNotFoundException {
		List<ClubDAO> clubs = new ArrayList<>();
		//System.out.println(">> Entrée dans searchClubByVille()");

		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.id_federation = federation.id_federation "
				+ "INNER JOIN commune ON club.id_commune = commune.id_commune "
				+ "WHERE LOWER(libelle_commune) LIKE ?";
		
	      try (Connection conn = DBConnection.getConnection();
	      PreparedStatement stmt = conn.prepareStatement(requete)) {

	    	  stmt.setString(1, "%" + nomVille.toLowerCase() + "%");
   	  
    	  try (ResultSet rs = stmt.executeQuery()) {
    		  while (rs.next()) {
    		        ClubDAO club = new ClubDAO(rs.getInt("id_club"), rs.getString("libelle"), rs.getString("libelle_commune"), rs.getString("libelle_federation"),
                  		  rs.getDouble("lat"),rs.getDouble("lon"));
    		        clubs.add(club);
    		    }
    		  
    		    if (clubs.isEmpty()) {
    		        System.out.println("Aucun club trouvé pour le mot-clé : " + nomVille);
    		    } 
	      }
			} catch (SQLException e) {
		        System.err.println("Erreur SQL : " + e.getMessage());
		        e.printStackTrace();
		    }
			//System.out.println(club.getCommune() + club.getLat() + club.getLon() );
			return clubs;
		}
	
	
	public List<ClubDAO> searchClubByDepartement(String nomDepartement) throws SQLException, ClassNotFoundException {
		List<ClubDAO> clubs = new ArrayList<>();
		//System.out.println(">> Entrée dans searchClubByDepartement()");

		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.id_federation = federation.id_federation "
				+ "INNER JOIN commune ON club.id_commune = commune.id_commune "
				+ "INNER JOIN departement ON commune.id_departement = departement.id_departement "
				+ "WHERE LOWER(libelle_departement) LIKE ?";
		
	      try (Connection conn = DBConnection.getConnection();
	      PreparedStatement stmt = conn.prepareStatement(requete)) {

	    	  stmt.setString(1, "%" + nomDepartement.toLowerCase() + "%");
   	  
    	  try (ResultSet rs = stmt.executeQuery()) {
    		  while (rs.next()) {
    		        ClubDAO club = new ClubDAO(rs.getInt("id_club"), rs.getString("libelle"), rs.getString("libelle_commune"), rs.getString("libelle_federation"),
                  		  rs.getDouble("lat"),rs.getDouble("lon"));
    		        clubs.add(club);
    		    }
    		  
    		    if (clubs.isEmpty()) {
    		        System.out.println("Aucun club trouvé pour le mot-clé : " + nomDepartement);
    		    } 
	      }
			} catch (SQLException e) {
		        System.err.println("Erreur SQL : " + e.getMessage());
		        e.printStackTrace();
		    }
			//System.out.println(club.getCommune() + club.getLat() + club.getLon() );
			return clubs;
		}
	
	public List<ClubDAO> searchClubByRegion(String nomRegion) throws SQLException, ClassNotFoundException {
		List<ClubDAO> clubs = new ArrayList<>();
		//System.out.println(">> Entrée dans searchClubByRegion()");

		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.id_federation = federation.id_federation "
				+ "INNER JOIN commune ON club.id_commune = commune.id_commune "
				+ "INNER JOIN region ON club.id_region = region.id_region "
				+ "WHERE LOWER(libelle_region) LIKE ?";
		
	      try (Connection conn = DBConnection.getConnection();
	      PreparedStatement stmt = conn.prepareStatement(requete)) {

	    	  stmt.setString(1, "%" + nomRegion.toLowerCase() + "%");
   	  
    	  try (ResultSet rs = stmt.executeQuery()) {
    		  while (rs.next()) {
  		        ClubDAO club = new ClubDAO(rs.getInt("id_club"), rs.getString("libelle"), rs.getString("libelle_commune"), rs.getString("libelle_federation"),
                		  rs.getDouble("lat"),rs.getDouble("lon"));

    		        clubs.add(club);
    		    }
    		  
    		    if (clubs.isEmpty()) {
    		        System.out.println("Aucun club trouvé pour le mot-clé : " + nomRegion);
    		    } 
	      }
			} catch (SQLException e) {
		        System.err.println("Erreur SQL : " + e.getMessage());
		        e.printStackTrace();
		    }
			//System.out.println(club.getCommune() + club.getLat() + club.getLon() );
			return clubs;
		}
	
	
	public List<ClubDAO> searchClubByFederation(String nomFederation) throws SQLException, ClassNotFoundException {
		List<ClubDAO> clubs = new ArrayList<>();
		//System.out.println(">> Entrée dans searchClubByFederation()");

		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.id_federation = federation.id_federation "
				+ "INNER JOIN commune ON club.id_commune = commune.id_commune "
				+ "WHERE LOWER(libelle_federation) LIKE ?";
		
	      try (Connection conn = DBConnection.getConnection();
	      PreparedStatement stmt = conn.prepareStatement(requete)) {

	    	  stmt.setString(1, "%" + nomFederation.toLowerCase() + "%");
   	  
    	  try (ResultSet rs = stmt.executeQuery()) {
    		  while (rs.next()) {
    		        ClubDAO club = new ClubDAO(rs.getInt("id_club"), rs.getString("libelle"), rs.getString("libelle_commune"), rs.getString("libelle_federation"),
                  		  rs.getDouble("lat"),rs.getDouble("lon"));
    		        clubs.add(club);
    		    }
    		  
    		    if (clubs.isEmpty()) {
    		        System.out.println("Aucun club trouvé pour le mot-clé : " + nomFederation);
    		    } 
	      }
	} catch (SQLException e) {
        System.err.println("Erreur SQL : " + e.getMessage());
        e.printStackTrace();
    }
	//System.out.println(club.getCommune() + club.getLat() + club.getLon() );
	return clubs;
}
	
	
	public List<ClubDAO> searchClubByRayon(String nomVille, double rayon) throws SQLException, ClassNotFoundException {
	    List<ClubDAO> clubs = new ArrayList<>();
	    List<ClubDAO> clubsFiltred = new ArrayList<>();
	    double lat = 0;
	    double lon = 0;
	    //System.out.println(">> Entrée dans getListCommune()");
	    
	    String sql = "SELECT * FROM club INNER JOIN federation ON club.id_federation = federation.id_federation "
				+ "INNER JOIN commune ON club.id_commune = commune.id_commune "
				+ "INNER JOIN departement ON club.id_departement = departement.id_departement "
				+ "INNER JOIN region ON club.id_region = region.id_region ";
	    
	    String sql2 = "SELECT lat,lon FROM commune ON club.id_commune = commune.id_commune "
				+ "WHERE LOWER(libelle_commune) LIKE ? ";

	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	        	ClubDAO club = new ClubDAO(rs.getInt("id_club"), rs.getString("libelle"), rs.getString("libelle_commune"), rs.getString("libelle_federation"),
                		  rs.getDouble("lat"),rs.getDouble("lon"));
  		        clubs.add(club);
  			    conn.close();
	        }
	        
	    }catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }
	    

	    
	    try (Connection conn = DBConnection.getConnection();
	  	      PreparedStatement stmt = conn.prepareStatement(sql2)) {

	  	    	  stmt.setString(1, "%" + nomVille.toLowerCase() + "%");
	     	  
	      	  try (ResultSet rs = stmt.executeQuery()) {
	      		  while (rs.next()) {
	      			  lat = rs.getDouble("lat");
	      			  lon = rs.getDouble("lon");
	      		    }     
	  	      }
	  	} catch (SQLException e) {
	          System.err.println("Erreur SQL : " + e.getMessage());
	          e.printStackTrace();
	      }
	    
	    
	    for(ClubDAO club : clubs) {
	    	CalculRayon calcul = new CalculRayon();
	    	double calculedRayon = calcul.calculRayon(lat, lon, club.getLat(), club.getLon());
	    	if(calculedRayon <= rayon) {
	    		clubsFiltred.add(club);
	    	}
	    }
	    
//	    for (String ville : villes) {
//	    	System.out.println(ville);
//	    }
	    return clubsFiltred;
		}
}
