package dao;

import java.sql.*;
import java.util.*;

import util.*;

public class FederationDAO {

	public List<String> getListFederation() throws SQLException, ClassNotFoundException {
	    List<String> federations = new ArrayList<>();
	    //System.out.println(">> Entrée dans getListFederation()");
	    
	    String sql = "SELECT DISTINCT lib_federation FROM federation ORDER BY lib_federation";

	    try (Connection conn = DBConnexion.getConnection();
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
}
