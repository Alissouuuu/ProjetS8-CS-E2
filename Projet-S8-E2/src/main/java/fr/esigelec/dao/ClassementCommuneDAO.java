package fr.esigelec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import fr.esigelec.models.ClassementCommune;
import fr.esigelec.models.Commune;

public class ClassementCommuneDAO {
	private DataSource dataSource;
	
	public ClassementCommuneDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public ArrayList<ClassementCommune> getClassement(int page){
		int offset = (page-1)*25;
		ArrayList<ClassementCommune> classement = new ArrayList<>();
		Commune commune = null;
		int licences;
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		CommuneDAO communeDAO = new CommuneDAO(dataSource);
		String requete = "SELECT commune.code_commune,SUM(total) FROM commune STRAIGHT_JOIN club ON(commune.code_commune=club.code_commune) GROUP BY commune.code_commune ORDER BY SUM(total) DESC LIMIT 25 OFFSET ?";
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(requete);
			stmt.setInt(1, offset);
			rs = stmt.executeQuery();
			while(rs.next()) {
				commune = communeDAO.getCommune(rs.getString("code_commune"));
				licences = rs.getInt("SUM(total)");
				classement.add(new ClassementCommune(commune,licences));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn,stmt,rs);
		}
		return classement;
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
