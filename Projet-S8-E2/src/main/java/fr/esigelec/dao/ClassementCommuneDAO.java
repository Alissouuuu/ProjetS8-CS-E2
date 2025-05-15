package fr.esigelec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.esigelec.models.ClassementCommune;
import fr.esigelec.models.Commune;

public class ClassementCommuneDAO {
	public ClassementCommuneDAO() {}
	
	public static ArrayList<ClassementCommune> getClassement(int page){
		int offset = (page-1)*25;
		ArrayList<ClassementCommune> classement = new ArrayList<>();
		Commune commune = null;
		int licences;
		String requete = "SELECT commune.code_commune,SUM(total) FROM commune STRAIGHT_JOIN club ON(commune.code_commune=club.code_commune) GROUP BY commune.code_commune ORDER BY SUM(total) DESC LIMIT 25 OFFSET ?";
		try {
			PreparedStatement stmt = DBDAO.getConn().prepareStatement(requete);
			stmt.setInt(1, offset);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				commune = CommuneDAO.getCommune(rs.getString("code_commune"));
				licences = rs.getInt("SUM(total)");
				classement.add(new ClassementCommune(commune,licences));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return classement;
	}
}
