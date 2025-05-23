package dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Region;
import util.DBConnexion;

import java.sql.*;
public class RegionDAO {
	
	public List<Region> getListRegion() throws SQLException, ClassNotFoundException {
	    List<Region> regions = new ArrayList<>();
	    
	    //System.out.println(">> Entrée dans getListRegion()");
	    
	    String sql = "SELECT DISTINCT * FROM region ORDER BY lib_region";

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
    //  Recuperer une région par son ID
	 public Region getRegionById(int codeRegion) throws SQLException {
	        String sql = "SELECT code_region, lib_region FROM region WHERE code_region = ?";
	        Region region = null;

	        try (Connection connection = DBConnexion.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(sql)) {

	            stmt.setInt(1, codeRegion);

	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    region = new Region();
	                    region.setCodeRegion(rs.getInt("code_region"));
	                    region.setLibelleRegion(rs.getString("lib_region"));
	                }
	            }
	        }

	        return region;
	    }
	

}
