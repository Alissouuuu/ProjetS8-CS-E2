package dao;

import java.sql.*;
import java.util.*;

import util.*;

public class RegionDAO {

	public List<String> getListRegion() throws SQLException, ClassNotFoundException {
	    List<String> regions = new ArrayList<>();
	    //System.out.println(">> Entrée dans getListRegion()");
	    
	    String sql = "SELECT DISTINCT lib_region FROM region ORDER BY lib_region";

	    try (Connection conn = DBConnexion.getConnection();
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
}
