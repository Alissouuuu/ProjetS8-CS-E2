/**
 * @author imane
 * @version 1.1
 * DAO des régions
 */
package dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Region;
import util.DBConnexion;

import java.sql.*;
public class RegionDAO {
	
	public List<Region> getListeRegion() throws SQLException, ClassNotFoundException {
	    List<Region> regions = new ArrayList<>();
	    
	    
	    String sql = "SELECT * FROM region ORDER BY lib_region";

	    try (Connection conn = DBConnexion.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	        	Region region = new Region();
                region.setCodeRegion(rs.getInt("code_region"));
                region.setLibelleRegion(rs.getString("lib_region"));
                regions.add(region);
	        }
	        
	    }catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    }

	    return regions;
	}
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
