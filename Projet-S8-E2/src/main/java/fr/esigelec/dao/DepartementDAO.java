package fr.esigelec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.esigelec.models.Departement;
import fr.esigelec.models.Region;

public class DepartementDAO {
	public static Departement getDepartement(String codeDepartement) {
		Region region = null;
		Departement departement = null;
		PreparedStatement stmt = null;
		try {
			stmt = DBDAO.getConn().prepareStatement("SELECT lib_departement,code_region FROM departement WHERE code_departement=?");
			stmt.setString(1, codeDepartement);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				region = RegionDAO.getRegion(rs.getString("code_region"));
				departement = new Departement(codeDepartement,rs.getString("lib_departement"),region);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(stmt !=null)
					stmt.close();
			}
			catch(SQLException ignore) {
				System.out.println("Erreur lors de la fermeture de requête");
			}
		}
		return departement;
	}
}
