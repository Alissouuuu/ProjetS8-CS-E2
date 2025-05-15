package fr.esigelec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.esigelec.models.Region;

public class RegionDAO {
	public static Region getRegion(String codeRegion) {
		
		Region region = null;
		PreparedStatement stmt = null;
		try {
			stmt = DBDAO.getConn().prepareStatement("SELECT lib_region FROM region WHERE code_region=?");
			stmt.setString(1, codeRegion);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				region = new Region(codeRegion,rs.getString("lib_region"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(stmt !=null)
					stmt.close();
			}
			catch(SQLException ignore) {
				System.err.println("Erreur lors de la fermeture de requête");
			}
		}
		return region;
	}
}
