/**
 * @author imane
 * @version 1.1
 * DAO des clubs
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Club;
import util.DBConnexion;

public class ClubDAO {

	public List<Club> rechercherParRegion(int codeRegion) throws SQLException {

		String sql = "SELECT c.lib_club,c.total_femme,c.total_homme " 
				+ "FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN departement d ON co.code_departement = d.code_departement "
				+ "JOIN region r ON d.code_region = r.code_region " 
				+ "WHERE r.code_region = ? ";
		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codeRegion);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicenciesFemme(rs.getInt("total_femme"));
				club.setTotalLicenciesHomme(rs.getInt("total_homme"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParDepartement(String codeDepartement) throws SQLException {

		String sql = "SELECT c.lib_club,c.total_femme,c.total_homme " 
				+ "FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN departement d ON co.code_departement = d.code_departement "
				+ "WHERE d.code_departement = ? ";
		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, codeDepartement);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicenciesFemme(rs.getInt("total_femme"));
				club.setTotalLicenciesHomme(rs.getInt("total_homme"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParCodePostal(String codePostal) throws SQLException {

		String sql = "SELECT c.lib_club,c.total_femme,c.total_homme " + "FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN code_postal cp ON co.code_commune = cp.code_commune " + "WHERE cp.code_postal = ?";
		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, codePostal);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicenciesFemme(rs.getInt("total_femme"));
				club.setTotalLicenciesHomme(rs.getInt("total_homme"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParRegionEtGenre(int codeRegion, String genre) throws SQLException {
		String colonne = genre.equals("H") ? "total_homme" : "total_femme";
		String sql = "SELECT c.lib_club, c." + colonne + " AS total FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN departement d ON co.code_departement = d.code_departement "
				+ "JOIN region r ON d.code_region = r.code_region " + "WHERE r.code_region = ? AND c." + colonne
				+ " > 0 ORDER BY c." + colonne + " DESC";

		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codeRegion);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicencies(rs.getInt("total"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParRegionEtAge(int codeRegion, String trancheAge) throws SQLException {
		String colF = "tranche_age_F_" + trancheAge;
		String colH = "tranche_age_H_" + trancheAge;
		String sql = "SELECT c.lib_club, (c." + colF + " + c." + colH + ") AS total FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN departement d ON co.code_departement = d.code_departement "
				+ "JOIN region r ON d.code_region = r.code_region " + "WHERE r.code_region = ? AND (c." + colF + " + c."
				+ colH + ") > 0 ORDER BY total DESC";

		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codeRegion);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicencies(rs.getInt("total"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParRegionEtGenreEtAge(int codeRegion, String genre, String trancheAge)
			throws SQLException {
		String colonne = "tranche_age_" + genre + "_" + trancheAge;
		String sql = "SELECT c.lib_club, c." + colonne + " AS total FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN departement d ON co.code_departement = d.code_departement "
				+ "JOIN region r ON d.code_region = r.code_region " + "WHERE r.code_region = ? AND c." + colonne
				+ " > 0 ORDER BY total DESC";

		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codeRegion);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicencies(rs.getInt("total"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParCodeEtGenre(String codePostal, String genre) throws SQLException {
		String colonne = genre.equals("H") ? "total_homme" : "total_femme";
		String sql = "SELECT c.lib_club, c." + colonne + " AS total FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN code_postal cp ON cp.code_commune = co.code_commune " + "WHERE cp.code_postal = ? AND c."
				+ colonne + " > 0 ORDER BY c." + colonne + " DESC";

		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, codePostal);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicencies(rs.getInt("total"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParCodeEtAge(String codePostal, String trancheAge) throws SQLException {
		String colF = "tranche_age_F_" + trancheAge;
		String colH = "tranche_age_H_" + trancheAge;
		String sql = "SELECT c.lib_club, (c." + colF + " + c." + colH + ") AS total FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN code_postal cp ON cp.code_commune = co.code_commune " + "WHERE cp.code_postal = ? AND (c."
				+ colF + " + c." + colH + ") > 0 ORDER BY total DESC";

		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, codePostal);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicencies(rs.getInt("total"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParCodeEtGenreEtAge(String codePostal, String genre, String trancheAge)
			throws SQLException {
		String colonne = "tranche_age_" + genre + "_" + trancheAge; 
		String sql = "SELECT c.lib_club, c." + colonne + " AS total FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN code_postal cp ON cp.code_commune = co.code_commune " + "WHERE cp.code_postal = ? AND c."
				+ colonne + " > 0 ORDER BY c." + colonne + " DESC";

		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, codePostal);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicencies(rs.getInt("total"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParRegionEtFederation(int codeRegion, String federationParam) throws SQLException {
		String sql = "SELECT c.lib_club,c.total_femme,c.total_homme " + "FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN departement d ON co.code_departement = d.code_departement "
				+ "JOIN region r ON d.code_region = r.code_region "
				+ "JOIN federation f on c.code_federation = f.code_federation "
				+ "WHERE r.code_region = ? AND f.code_federation = ? ";
		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codeRegion);
			stmt.setString(2, federationParam);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicenciesFemme(rs.getInt("total_femme"));
				club.setTotalLicenciesHomme(rs.getInt("total_homme"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParCodeEtFederation(String codePostal, String federationParam) throws SQLException {
		String sql = "SELECT c.lib_club,c.total_femme,c.total_homme " + "FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN code_postal cp ON co.code_commune = cp.code_commune "
				+ "JOIN federation f on c.code_federation = f.code_federation "
				+ "WHERE cp.code_postal = ? AND f.code_federation = ? ";
		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, codePostal);
			stmt.setString(2, federationParam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicenciesFemme(rs.getInt("total_femme"));
				club.setTotalLicenciesHomme(rs.getInt("total_homme"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParDepartementEtFederation(String departementParam, String federationParam)
			throws SQLException {

		String sql = "SELECT c.lib_club,c.total_femme,c.total_homme " + "FROM club c "
				+ "JOIN commune co ON c.code_commune = co.code_commune "
				+ "JOIN departement d ON co.code_departement = d.code_departement "
				+"JOIN federation f on c.code_federation = f.code_federation " + 
				"WHERE d.code_departement = ? AND f.code_federation = ? ";
		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, departementParam);
			stmt.setString(2, federationParam);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicenciesFemme(rs.getInt("total_femme"));
				club.setTotalLicenciesHomme(rs.getInt("total_homme"));
				clubs.add(club);
			}
		}
		return clubs;
	}

	public List<Club> rechercherParFederation(String federationParam) 	throws SQLException {

		String sql = "SELECT c.lib_club,c.total_femme,c.total_homme " + "FROM club c "
				+"JOIN federation f on c.code_federation = f.code_federation " + 
				" WHERE f.code_federation = ? ";
		List<Club> clubs = new ArrayList<>();
		try (Connection conn = DBConnexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, federationParam);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Club club = new Club();
				club.setLibelleClub(rs.getString("lib_club"));
				club.setTotalLicenciesFemme(rs.getInt("total_femme"));
				club.setTotalLicenciesHomme(rs.getInt("total_homme"));
				clubs.add(club);
			}
		}
		return clubs;
	}

}