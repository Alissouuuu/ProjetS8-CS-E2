package fr.esigelec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import fr.esigelec.modelCarte.ClubCarte;



public class ClubCarteDAO {
	private DataSource dataSource;
	
	public ClubCarteDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public List<ClubCarte> searchClubByVille(String nomVille) throws SQLException, ClassNotFoundException {
		List<ClubCarte> clubs = new ArrayList<>();
		//System.out.println(">> Entrée dans searchClubByVille()");

		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.code_federation = federation.code_federation "
				+ "INNER JOIN commune ON club.code_commune = commune.code_commune "
				+ "INNER JOIN libelle_ville_commune ON club.code_commune = libelle_ville_commune.code_commune "
				+ "INNER JOIN libelle_ville ON libelle_ville_commune.id_libelle = libelle_ville.id_libelle "
				+ "WHERE LOWER(lib_commune) LIKE ?";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
	      try {
	    	  conn = dataSource.getConnection();
		      stmt = conn.prepareStatement(requete);
	    	  stmt.setString(1, nomVille.toLowerCase());
   	  
    		  rs = stmt.executeQuery();
    		  while (rs.next()) {
    		        ClubCarte club = new ClubCarte(rs.getInt("id_club"), rs.getString("lib_club"), rs.getString("lib_commune"), 
    		        		rs.getString("lib_federation"),rs.getInt("total_homme"), rs.getInt("total_femme"),
                  		  rs.getDouble("latitude"),rs.getDouble("longitude"));
    		        clubs.add(club);
    		    }
    		  
    		    if (clubs.isEmpty()) {
    		        System.out.println("Aucun club trouvé pour le mot-clé : " + nomVille);
    		    }
    		    
			} catch (SQLException e) {
		        System.err.println("Erreur SQL : " + e.getMessage());
		        e.printStackTrace();
		    }
			//System.out.println(club.getCommune() + club.getLat() + club.getLon() );
	      close(conn,stmt,rs);
			return clubs;
		}
	
	
	public List<ClubCarte> searchClubByDepartement(String nomDepartement) throws SQLException, ClassNotFoundException {
		List<ClubCarte> clubs = new ArrayList<>();
		//System.out.println(">> Entrée dans searchClubByDepartement()");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.code_federation = federation.code_federation "
				+ "INNER JOIN commune ON club.code_commune = commune.code_commune "
				+ "INNER JOIN libelle_ville_commune ON club.code_commune = libelle_ville_commune.code_commune "
				+ "INNER JOIN libelle_ville ON libelle_ville_commune.id_libelle = libelle_ville.id_libelle "
				+ "INNER JOIN departement ON commune.code_departement = departement.code_departement "
				+ "WHERE LOWER(lib_departement) LIKE ?";
		
	      try {
	    	  conn = dataSource.getConnection();
		      stmt = conn.prepareStatement(requete);
	    	  stmt.setString(1, nomDepartement.toLowerCase());
   	  
    		  rs = stmt.executeQuery();
    		  while (rs.next()) {
    		        ClubCarte club = new ClubCarte(rs.getInt("id_club"), rs.getString("lib_club"), rs.getString("lib_commune"), 
    		        		rs.getString("lib_federation"),rs.getInt("total_homme"), rs.getInt("total_femme"),
                    		  rs.getDouble("latitude"),rs.getDouble("longitude"));
    		        clubs.add(club);
    		    }
    		  
    		    if (clubs.isEmpty()) {
    		        System.out.println("Aucun club trouvé pour le mot-clé : " + nomDepartement);
    		    } 
			} catch (SQLException e) {
		        System.err.println("Erreur SQL : " + e.getMessage());
		        e.printStackTrace();
		    }
			//System.out.println(club.getCommune() + club.getLat() + club.getLon() );
	      close(conn,stmt,rs);
			return clubs;
		}
	
	public List<ClubCarte> searchClubByRegion(String nomRegion) throws SQLException, ClassNotFoundException {
		List<ClubCarte> clubs = new ArrayList<>();
		//System.out.println(">> Entrée dans searchClubByRegion()");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.code_federation = federation.code_federation "
				+ "INNER JOIN commune ON club.code_commune = commune.code_commune "
				+ "INNER JOIN libelle_ville_commune ON club.code_commune = libelle_ville_commune.code_commune "
				+ "INNER JOIN libelle_ville ON libelle_ville_commune.id_libelle = libelle_ville.id_libelle "
				+ "INNER JOIN departement ON commune.code_departement = departement.code_departement "
				+ "INNER JOIN region ON departement.code_region = region.code_region "
				+ "WHERE LOWER(lib_region) LIKE ?";
		
	      try {
	    	  conn = dataSource.getConnection();
		      stmt = conn.prepareStatement(requete);
	    	  stmt.setString(1, nomRegion.toLowerCase());
   	  
    
    		  rs = stmt.executeQuery();
    		  while (rs.next()) {
  		        ClubCarte club = new ClubCarte(rs.getInt("id_club"), rs.getString("lib_club"), rs.getString("lib_commune"), 
		        		rs.getString("lib_federation"),rs.getInt("total_homme"), rs.getInt("total_femme"),
              		  rs.getDouble("latitude"),rs.getDouble("longitude"));

    		        clubs.add(club);
    		    }
    		  
    		    if (clubs.isEmpty()) {
    		        System.out.println("Aucun club trouvé pour le mot-clé : " + nomRegion);
    		    } 
	      
			} catch (SQLException e) {
		        System.err.println("Erreur SQL : " + e.getMessage());
		        e.printStackTrace();
		    }
			//System.out.println(club.getCommune() + club.getLat() + club.getLon() );
	      close(conn,stmt,rs);
			return clubs;
		}
	
	
	public List<ClubCarte> searchClubByFederation(String nomFederation) throws SQLException, ClassNotFoundException {
		List<ClubCarte> clubs = new ArrayList<>();
		// System.out.println(">> Entrée dans searchClubByFederation()");

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String requete = "SELECT * FROM club INNER JOIN federation ON club.code_federation = federation.code_federation "
				+ "INNER JOIN commune ON club.code_commune = commune.code_commune "
				+ "INNER JOIN libelle_ville_commune ON club.code_commune = libelle_ville_commune.code_commune "
				+ "INNER JOIN libelle_ville ON libelle_ville_commune.id_libelle = libelle_ville.id_libelle "
				+ "WHERE LOWER(lib_federation) LIKE ?";
		
	      try {
	    	  conn = dataSource.getConnection();
		      stmt = conn.prepareStatement(requete);
	    	  stmt.setString(1, "%" + nomFederation.toLowerCase() + "%");
   	  
    	  
    		  rs = stmt.executeQuery();
    		  while (rs.next()) {
    		        ClubCarte club = new ClubCarte(rs.getInt("id_club"), rs.getString("lib_club"), rs.getString("lib_commune"), 
    		        		rs.getString("lib_federation"),rs.getInt("total_homme"), rs.getInt("total_femme"),
    	              		  rs.getDouble("latitude"),rs.getDouble("longitude"));
    		        clubs.add(club);
    		    }
    		  
    		    if (clubs.isEmpty()) {
    		        System.out.println("Aucun club trouvé pour le mot-clé : " + nomFederation);
    		    } 
	      
	} catch (SQLException e) {
        System.err.println("Erreur SQL : " + e.getMessage());
        e.printStackTrace();
    }
//	      for(ClubCarte club : clubs) {
//	    	  System.out.println(club.getCommune() + club.getLat() + club.getLon() );  
//	      }
	      close(conn,stmt,rs);
	return clubs;
}
	
	
	public List<ClubCarte> searchClubByRayon(String nomVille, double rayon) throws SQLException, ClassNotFoundException {
	    List<ClubCarte> clubs = new ArrayList<>();
	    double latCentre = 0;
	    double lonCentre = 0;
	    //System.out.println(">> Entrée dans searchByRayon()");
	    Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

	    
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

	    try {
	    	conn = dataSource.getConnection();
	  	     stmt = conn.prepareStatement(sql2);
		  	    	  stmt.setString(1, nomVille.toLowerCase());
		     	
		      		rs = stmt.executeQuery();
		      		  while (rs.next()) {
		      			  latCentre = rs.getDouble("latitude");
		      			  lonCentre = rs.getDouble("longitude");
		      		    }     
		  	  
		      	 
		  	} catch (SQLException e) {
		          System.err.println("Erreur SQL : " + e.getMessage());
		          e.printStackTrace();
		      }
	    close(conn,stmt,rs);
	    
	    // Conversion approximative des degrés pour le bounding box
	    double rayonLat = rayon / 111.0;
	    double rayonLon = rayon / (111.0 * Math.cos(Math.toRadians(latCentre)));

	    double latMin = latCentre - rayonLat;
	    double latMax = latCentre + rayonLat;
	    double lonMin = lonCentre - rayonLon;
	    double lonMax = lonCentre + rayonLon;

	    
	    try {
	    	conn = dataSource.getConnection();
	  	    stmt = conn.prepareStatement(sql);
	    	
	    	
		    	stmt.setDouble(1, latCentre); // COS(RADIANS(?))
		        stmt.setDouble(2, lonCentre); // COS(RADIANS(long - ?))
		        stmt.setDouble(3, latCentre); // SIN(RADIANS(?))
	
		        stmt.setDouble(4, latMin);
		        stmt.setDouble(5, latMax);
		        stmt.setDouble(6, lonMin);
		        stmt.setDouble(7, lonMax);
	
		        stmt.setDouble(8, rayon);
	     	  
	      
	      		rs = stmt.executeQuery();
	      		  while (rs.next()) {
	    		        ClubCarte club = new ClubCarte(rs.getInt("id_club"), rs.getString("lib_club"), rs.getString("lib_commune"), 
	    		        		rs.getString("lib_federation"),rs.getInt("total_homme"), rs.getInt("total_femme"),
	    	              		  rs.getDouble("latitude"),rs.getDouble("longitude"));
	    		        clubs.add(club);
	      		    }     
	  	      
	  	} catch (SQLException e) {
	          System.err.println("Erreur SQL : " + e.getMessage());
	          e.printStackTrace();
	      }
	    
	    
//	    for (String ville : villes) {
//	    	System.out.println(ville);
//	    }
	    close(conn,stmt,rs);
	    return clubs;
		}
	
	public List<ClubCarte> searchClubByRayonUseGeoLoc(double latCentre,double lonCentre, double rayon) throws SQLException, ClassNotFoundException {
	    List<ClubCarte> clubs = new ArrayList<>();
	    System.out.println(">> Entrée dans searchByRayonUseGeoLoc()");
	    Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

	    
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
	     
	    // Conversion approximative des degrés pour le bounding box
	    double rayonLat = rayon / 111.0;
	    double rayonLon = rayon / (111.0 * Math.cos(Math.toRadians(latCentre)));

	    double latMin = latCentre - rayonLat;
	    double latMax = latCentre + rayonLat;
	    double lonMin = lonCentre - rayonLon;
	    double lonMax = lonCentre + rayonLon;

	    
	    try {
	    	conn = dataSource.getConnection();
	  	    stmt = conn.prepareStatement(sql);
	    	
	    	
		    	stmt.setDouble(1, latCentre); // COS(RADIANS(?))
		        stmt.setDouble(2, lonCentre); // COS(RADIANS(long - ?))
		        stmt.setDouble(3, latCentre); // SIN(RADIANS(?))
	
		        stmt.setDouble(4, latMin);
		        stmt.setDouble(5, latMax);
		        stmt.setDouble(6, lonMin);
		        stmt.setDouble(7, lonMax);
	
		        stmt.setDouble(8, rayon);
	     	  
	      	 
	      		rs = stmt.executeQuery();
	      		  while (rs.next()) {
	    		        ClubCarte club = new ClubCarte(rs.getInt("id_club"), rs.getString("lib_club"), rs.getString("lib_commune"), 
	    		        		rs.getString("lib_federation"),rs.getInt("total_homme"), rs.getInt("total_femme"),
	    	              		  rs.getDouble("latitude"),rs.getDouble("longitude"));
	    		        clubs.add(club);
	      		    }     
	  	     
	  	} catch (SQLException e) {
	          System.err.println("Erreur SQL : " + e.getMessage());
	          e.printStackTrace();
	      }
	    
	    
//	    for (String ville : villes) {
//	    	System.out.println(ville);
//	    }
	    close(conn,stmt,rs);
	    return clubs;
		}
	
	private void close(Connection con,Statement stmt, ResultSet rs) {
		try {
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(con != null)
				con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
