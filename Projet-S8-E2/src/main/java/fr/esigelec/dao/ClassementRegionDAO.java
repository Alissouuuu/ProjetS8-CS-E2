package fr.esigelec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.esigelec.models.ClassementRegion;
import fr.esigelec.models.Region;

public class ClassementRegionDAO {
	public ClassementRegionDAO() {}
	
	public static ArrayList<ClassementRegion> getClassement(int page){
		int offset = (page-1)*25;
		ArrayList<ClassementRegion> classement = new ArrayList<>();
		Region region = null;
		String codeRegion,nomRegion;
		int licences;
		String requete = "SELECT region.code_region,region.lib_region,SUM(total) FROM commune STRAIGHT_JOIN departement ON(commune.code_departement=departement.code_departement) STRAIGHT_JOIN region ON(departement.code_region=region.code_region)  STRAIGHT_JOIN club ON(commune.code_commune=club.code_commune)  GROUP BY region.code_region ORDER BY SUM(total) DESC LIMIT 25 OFFSET ?";
		try {
			PreparedStatement stmt = DBDAO.getConn().prepareStatement(requete);
			stmt.setInt(1, offset);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				codeRegion = rs.getString("code_region");
				nomRegion = rs.getString("lib_region");
				region = new Region(codeRegion,nomRegion);
				licences = rs.getInt("SUM(total)");
				classement.add(new ClassementRegion(region,licences));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return classement;
	}
}
