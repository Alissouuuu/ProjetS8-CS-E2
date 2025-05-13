package fr.esigelec.dao;

import java.sql.*;
import java.util.*;

import fr.esigelec.model.*;
import fr.esigelec.*;

public class DAOClub {
	
//------------------------------------------------------------------------------------------------
	
	public List<String> getListCommune() throws SQLException, ClassNotFoundException {
	    List<String> villes = new ArrayList<>();
	    //System.out.println(">> Entrée dans getListCommune()");
	    
	    // DISTINCT pour éliminer les doublons
	    String sql = "SELECT DISTINCT lib_commune FROM libelle_ville ORDER BY lib_commune";

	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	            villes.add(rs.getString("lib_commune"));
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
	    
	    String sql = "SELECT DISTINCT lib_departement FROM departement ORDER BY lib_departement";

	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	        	departements.add(rs.getString("lib_departement"));
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
	    
	    String sql = "SELECT DISTINCT lib_region FROM region ORDER BY lib_region";

	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	        	regions.add(rs.getString("lib_region"));
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
	    
	    String sql = "SELECT DISTINCT lib_federation FROM federation ORDER BY lib_federation";

	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	        	federations.add(rs.getString("lib_federation"));
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
	
//------------------------------------------------------------------------------------------------
	
	
	public List<ClubDAO> searchClubByVille(String nomVille) throws SQLException, ClassNotFoundException {
		List<ClubDAO> clubs = new ArrayList<>();
		//System.out.println(">> Entrée dans searchClubByVille()");

		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.code_federation = federation.code_federation "
				+ "INNER JOIN commune ON club.code_commune = commune.code_commune "
				+ "INNER JOIN libelle_ville_commune ON club.code_commune = libelle_ville_commune.code_commune "
				+ "INNER JOIN libelle_ville ON libelle_ville_commune.id_libelle = libelle_ville.id_libelle "
				+ "WHERE LOWER(lib_commune) LIKE ?";
		
	      try (Connection conn = DBConnection.getConnection();
	      PreparedStatement stmt = conn.prepareStatement(requete)) {

	    	  stmt.setString(1, nomVille.toLowerCase());
   	  
    	  try (ResultSet rs = stmt.executeQuery()) {
    		  while (rs.next()) {
    		        ClubDAO club = new ClubDAO(rs.getInt("id_club"), rs.getString("lib_club"), rs.getString("lib_commune"), 
    		        		rs.getString("lib_federation"),rs.getInt("total_homme"), rs.getInt("total_femme"),
                  		  rs.getDouble("latitude"),rs.getDouble("longitude"));
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

		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.code_federation = federation.code_federation "
				+ "INNER JOIN commune ON club.code_commune = commune.code_commune "
				+ "INNER JOIN libelle_ville_commune ON club.code_commune = libelle_ville_commune.code_commune "
				+ "INNER JOIN libelle_ville ON libelle_ville_commune.id_libelle = libelle_ville.id_libelle "
				+ "INNER JOIN departement ON commune.code_departement = departement.code_departement "
				+ "WHERE LOWER(lib_departement) LIKE ?";
		
	      try (Connection conn = DBConnection.getConnection();
	      PreparedStatement stmt = conn.prepareStatement(requete)) {

	    	  stmt.setString(1, nomDepartement.toLowerCase());
   	  
    	  try (ResultSet rs = stmt.executeQuery()) {
    		  while (rs.next()) {
    		        ClubDAO club = new ClubDAO(rs.getInt("id_club"), rs.getString("lib_club"), rs.getString("lib_commune"), 
    		        		rs.getString("lib_federation"),rs.getInt("total_homme"), rs.getInt("total_femme"),
                    		  rs.getDouble("latitude"),rs.getDouble("longitude"));
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

		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.code_federation = federation.code_federation "
				+ "INNER JOIN commune ON club.code_commune = commune.code_commune "
				+ "INNER JOIN libelle_ville_commune ON club.code_commune = libelle_ville_commune.code_commune "
				+ "INNER JOIN libelle_ville ON libelle_ville_commune.id_libelle = libelle_ville.id_libelle "
				+ "INNER JOIN departement ON commune.code_departement = departement.code_departement "
				+ "INNER JOIN region ON departement.code_region = region.code_region "
				+ "WHERE LOWER(lib_region) LIKE ?";
		
	      try (Connection conn = DBConnection.getConnection();
	      PreparedStatement stmt = conn.prepareStatement(requete)) {

	    	  stmt.setString(1, nomRegion.toLowerCase());
   	  
    	  try (ResultSet rs = stmt.executeQuery()) {
    		  while (rs.next()) {
  		        ClubDAO club = new ClubDAO(rs.getInt("id_club"), rs.getString("lib_club"), rs.getString("lib_commune"), 
		        		rs.getString("lib_federation"),rs.getInt("total_homme"), rs.getInt("total_femme"),
              		  rs.getDouble("latitude"),rs.getDouble("longitude"));

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

		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.code_federation = federation.code_federation "
				+ "INNER JOIN commune ON club.code_commune = commune.code_commune "
				+ "INNER JOIN libelle_ville_commune ON club.code_commune = libelle_ville_commune.code_commune "
				+ "INNER JOIN libelle_ville ON libelle_ville_commune.id_libelle = libelle_ville.id_libelle "
				+ "WHERE LOWER(lib_federation) LIKE ?";
		
	      try (Connection conn = DBConnection.getConnection();
	      PreparedStatement stmt = conn.prepareStatement(requete)) {

	    	  stmt.setString(1, "%" + nomFederation.toLowerCase() + "%");
   	  
    	  try (ResultSet rs = stmt.executeQuery()) {
    		  while (rs.next()) {
    		        ClubDAO club = new ClubDAO(rs.getInt("id_club"), rs.getString("lib_club"), rs.getString("lib_commune"), 
    		        		rs.getString("lib_federation"),rs.getInt("total_homme"), rs.getInt("total_femme"),
    	              		  rs.getDouble("latitude"),rs.getDouble("longitude"));
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
	    double latCentre = 0;
	    double lonCentre = 0;
	    System.out.println(">> Entrée dans searchByRayon()");
	    

	    
	    String sql = """
	            SELECT *, (
	            6371 * ACOS(
	                COS(RADIANS(?)) * COS(RADIANS(latitude)) *
	                COS(RADIANS(longitude) - RADIANS(?)) +
	                SIN(RADIANS(?)) * SIN(RADIANS(latitude))
	            )
	        ) AS distance
	        FROM club INNER JOIN federation ON club.code_federation = federation.code_federation 
			INNER JOIN commune ON club.code_commune = commune.code_commune 
			INNER JOIN libelle_ville_commune ON club.code_commune = libelle_ville_commune.code_commune 
			INNER JOIN libelle_ville ON libelle_ville_commune.id_libelle = libelle_ville.id_libelle 
	        WHERE latitude BETWEEN ? AND ?
	          AND longitude BETWEEN ? AND ?
	        HAVING distance <= ?
	        """;
	    
	    // Formule de Haversine 
	    
	    String sql2 = """ 
	    		
	    		SELECT latitude,longitude FROM commune 
	    		INNER JOIN libelle_ville_commune ON commune.code_commune = libelle_ville_commune.code_commune 
	    		INNER JOIN libelle_ville ON libelle_ville_commune.id_libelle = libelle_ville.id_libelle
				WHERE LOWER(lib_commune) LIKE ?;
	    		""";

	    try (Connection conn = DBConnection.getConnection();
		  	      PreparedStatement stmt = conn.prepareStatement(sql2)) {

		  	    	  stmt.setString(1, nomVille.toLowerCase());
		     	  
		      	  try (ResultSet rs = stmt.executeQuery()) {
		      		  while (rs.next()) {
		      			  latCentre = rs.getDouble("latitude");
		      			  lonCentre = rs.getDouble("longitude");
		      		    }     
		  	      }
		      	 
		  	} catch (SQLException e) {
		          System.err.println("Erreur SQL : " + e.getMessage());
		          e.printStackTrace();
		      }
	    
	    
	    // Conversion approximative des degrés pour le bounding box
	    double rayonLat = rayon / 111.0;
	    double rayonLon = rayon / (111.0 * Math.cos(Math.toRadians(latCentre)));

	    double latMin = latCentre - rayonLat;
	    double latMax = latCentre + rayonLat;
	    double lonMin = lonCentre - rayonLon;
	    double lonMax = lonCentre + rayonLon;

	    
	    try (Connection conn = DBConnection.getConnection();
	  	      PreparedStatement stmt = conn.prepareStatement(sql)) {

	    	
	    	
		    	stmt.setDouble(1, latCentre); // COS(RADIANS(?))
		        stmt.setDouble(2, lonCentre); // COS(RADIANS(long - ?))
		        stmt.setDouble(3, latCentre); // SIN(RADIANS(?))
	
		        stmt.setDouble(4, latMin);
		        stmt.setDouble(5, latMax);
		        stmt.setDouble(6, lonMin);
		        stmt.setDouble(7, lonMax);
	
		        stmt.setDouble(8, rayon);
	     	  
	      	  try (ResultSet rs = stmt.executeQuery()) {
	      		  while (rs.next()) {
	    		        ClubDAO club = new ClubDAO(rs.getInt("id_club"), rs.getString("lib_club"), rs.getString("lib_commune"), 
	    		        		rs.getString("lib_federation"),rs.getInt("total_homme"), rs.getInt("total_femme"),
	    	              		  rs.getDouble("latitude"),rs.getDouble("longitude"));
	    		        clubs.add(club);
	      		    }     
	  	      }
	  	} catch (SQLException e) {
	          System.err.println("Erreur SQL : " + e.getMessage());
	          e.printStackTrace();
	      }
	    
	    
//	    for (String ville : villes) {
//	    	System.out.println(ville);
//	    }
	    return clubs;
		}
}
