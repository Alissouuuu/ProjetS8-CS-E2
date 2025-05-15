package fr.esigelec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.esigelec.models.Club;
import fr.esigelec.models.Commune;
import fr.esigelec.models.Federation;

public class ClubDAO {
	
	public static ArrayList<Club> getClubs(int page,String requeteMilieu){
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
		try {
			stmt = DBDAO.getConn().prepareStatement(requete);
			stmt.setInt(1, offset);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id_club");
				nom = rs.getString("lib_club");
				totalLicences = rs.getInt("total");
				totalLicencesFemme = rs.getInt("total_femme");
				totalLicencesHomme = rs.getInt("total_homme");
				commune = CommuneDAO.getCommune(rs.getString("code_commune"));
				federation = FederationDAO.getFederation(rs.getInt("code_federation"));
				clubs.add(new Club(id,federation,commune,nom,totalLicences,totalLicencesFemme,totalLicencesHomme));
			}
		}
		catch(SQLException e) {
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
		return clubs;
	}
}
