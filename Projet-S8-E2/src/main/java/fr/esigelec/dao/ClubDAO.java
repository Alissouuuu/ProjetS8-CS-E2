package fr.esigelec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import fr.esigelec.models.Club;
import fr.esigelec.models.Commune;
import fr.esigelec.models.Federation;

public class ClubDAO {
	private DataSource dataSource;
	
	public ClubDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public ArrayList<Club> getClubs(int page,String requeteMilieu){
		//SELECT id_club,lib_club,total,total_femme,total_homme,club.code_commune,club.code_federation FROM club INNER JOIN code_postal ON club.code_commune=code_postal.code_commune INNER JOIN federation ON (club.code_federation=federation.code_federation) 
		
		String requeteDebut = "SELECT id_club,lib_club,total,total_femme,total_homme,club.code_commune,club.code_federation FROM club NATURAL JOIN code_postal NATURAL JOIN federation ";
		String requeteFin = " LIMIT 25 OFFSET ?";
		if(requeteMilieu == null)
			requeteMilieu = "";
		String requete = requeteDebut+requeteMilieu+requeteFin;
		int offset = (page-1)*25;
		ArrayList<Club> clubs = new ArrayList<>();
		int id,totalLicences,totalLicencesFemme,totalLicencesHomme;
		String nom;
		Commune commune = null;
		Federation federation = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		CommuneDAO communeDAO = new CommuneDAO(dataSource);
		FederationDAO federationDAO = new FederationDAO(dataSource);
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(requete);
			stmt.setInt(1, offset);
			rs = stmt.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id_club");
				nom = rs.getString("lib_club");
				totalLicences = rs.getInt("total");
				totalLicencesFemme = rs.getInt("total_femme");
				totalLicencesHomme = rs.getInt("total_homme");
				commune = communeDAO.getCommune(rs.getString("code_commune"));
				federation = federationDAO.getFederation(rs.getInt("code_federation"));
				clubs.add(new Club(id,federation,commune,nom,totalLicences,totalLicencesFemme,totalLicencesHomme));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn,stmt,rs);
		}
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
