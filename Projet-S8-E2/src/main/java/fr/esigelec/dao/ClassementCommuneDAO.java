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
	public final static String FEMMES_MOINS_14 = "total_femmes_1_14";
	public final static String FEMMES_15_24 = "total_femmes_15_24";
	public final static String FEMMES_25_34 = "total_femmes_25_34";
	public final static String FEMMES_35_49 = "total_femmes_35_49";
	public final static String FEMMES_50_79 = "total_femmes_50_79";
	public final static String FEMMES_80_PLUS = "total_femmes_80_plus";
	
	public final static String HOMMES_MOINS_14 = "total_hommes_1_14";
	public final static String HOMMES_15_24 = "total_hommes_15_24";
	public final static String HOMMES_25_34 = "total_hommes_25_34";
	public final static String HOMMES_35_49 = "total_hommes_35_49";
	public final static String HOMMES_50_79 = "total_hommes_50_79";
	public final static String HOMMES_80_PLUS = "total_hommes_80_plus";
	
	
	public final static String TOTAL_MOINS_14 = "total_1_14";
	public final static String TOTAL_15_24 = "total_15_24";
	public final static String TOTAL_25_34 = "total_25_34";
	public final static String TOTAL_35_49 = "total_35_49";
	public final static String TOTAL_50_79 = "total_50_79";
	public final static String TOTAL_80_PLUS = "total_80_plus";
	
	public ClassementCommuneDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public ArrayList<ClassementCommune> getClassement(int page){
		int offset = (page-1)*25;
		ArrayList<ClassementCommune> classement = new ArrayList<>();
		Commune commune = null;
		int licences,licencesFemmes,licencesHommes;
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		CommuneDAO communeDAO = new CommuneDAO(dataSource);
		String requete = "SELECT commune.code_commune,SUM(total_femme),SUM(total_homme),SUM(total) FROM commune STRAIGHT_JOIN club ON(commune.code_commune=club.code_commune) GROUP BY commune.code_commune ORDER BY SUM(total) DESC LIMIT 25 OFFSET ?";
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(requete);
			stmt.setInt(1, offset);
			rs = stmt.executeQuery();
			while(rs.next()) {
				commune = communeDAO.getCommune(rs.getString("code_commune"));
				licences = rs.getInt("SUM(total)");
				licencesFemmes = rs.getInt("SUM(total_femmes)");
				licencesHommes = rs.getInt("SUM(total_hommes");
				classement.add(new ClassementCommune(commune,licences,licencesFemmes,licencesHommes));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn,stmt,rs);
		}
		return classement;
	}
	
	public ArrayList<ClassementCommune> getClassementToutCritere(int page,String critereGenreAge){
		int offset = (page-1)*25;
		ArrayList<ClassementCommune> classement = new ArrayList<>();
		Commune commune = null;
		int licences,licencesHommes,licencesFemmes;
		int licencesHommes114,licencesHommes1524,licencesHommes2534,licencesHommes3549,licencesHommes5079,licencesHommes8099;
		int licencesFemmes114,licencesFemmes1524,licencesFemmes2534,licencesFemmes3549,licencesFemmes5079,licencesFemmes8099;
		int licencesTotales114,licencesTotales1524,licencesTotales2534,licencesTotales3549,licencesTotales5079,licencesTotales8099;
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		CommuneDAO communeDAO = new CommuneDAO(dataSource);
		String requete = "SELECT"
				+ "    commune.code_commune,"
				+ "    SUM(total_femme) AS total_femmes,"
				+ "    SUM(total_homme) AS total_hommes,"
				+ "    SUM(total) AS total_total,"
				+ "    SUM(tranche_age_F_1_4 + tranche_age_F_5_9 + tranche_age_F_10_14) AS total_femmes_1_14,"
				+ "    SUM(tranche_age_F_15_19 + tranche_age_F_20_24) AS total_femmes_15_24,"
				+ "    SUM(tranche_age_F_25_29 + tranche_age_F_30_34) AS total_femmes_25_34,"
				+ "    SUM(tranche_age_F_35_39 + tranche_age_F_40_44 + tranche_age_F_45_49) AS total_femmes_35_49,"
				+ "    SUM(tranche_age_F_50_54 + tranche_age_F_55_59 + tranche_age_F_60_64 + tranche_age_F_65_69 +"
				+ "        tranche_age_F_70_74 + tranche_age_F_75_79) AS total_femmes_50_79,"
				+ "    SUM(tranche_age_F_80_99) AS total_femmes_80_plus,"
				+ "    SUM(tranche_age_H_1_4 + tranche_age_H_5_9 + tranche_age_H_10_14) AS total_hommes_1_14,"
				+ "    SUM(tranche_age_H_15_19 + tranche_age_H_20_24) AS total_hommes_15_24,"
				+ "    SUM(tranche_age_H_25_29 + tranche_age_H_30_34) AS total_hommes_25_34,"
				+ "    SUM(tranche_age_H_35_39 + tranche_age_H_40_44 + tranche_age_H_45_49) AS total_hommes_35_49,"
				+ "    SUM(tranche_age_H_50_54 + tranche_age_H_55_59 + tranche_age_H_60_64 + tranche_age_H_65_69 +"
				+ "        tranche_age_H_70_74 + tranche_age_H_75_79) AS total_hommes_50_79,"
				+ "    SUM(tranche_age_H_80_99) AS total_hommes_80_plus,"
				+ "    SUM(tranche_age_F_1_4 + tranche_age_F_5_9 + tranche_age_F_10_14 +"
				+ "        tranche_age_H_1_4 + tranche_age_H_5_9 + tranche_age_H_10_14) AS total_1_14,"
				+ "    SUM(tranche_age_F_15_19 + tranche_age_F_20_24 +"
				+ "        tranche_age_H_15_19 + tranche_age_H_20_24) AS total_15_24,"
				+ "    SUM(tranche_age_F_25_29 + tranche_age_F_30_34 +"
				+ "        tranche_age_H_25_29 + tranche_age_H_30_34) AS total_25_34,"
				+ "    SUM(tranche_age_F_35_39 + tranche_age_F_40_44 + tranche_age_F_45_49 +"
				+ "        tranche_age_H_35_39 + tranche_age_H_40_44 + tranche_age_H_45_49) AS total_35_49,"
				+ "    SUM(tranche_age_F_50_54 + tranche_age_F_55_59 + tranche_age_F_60_64 + tranche_age_F_65_69 +"
				+ "        tranche_age_F_70_74 + tranche_age_F_75_79 +"
				+ "        tranche_age_H_50_54 + tranche_age_H_55_59 + tranche_age_H_60_64 + tranche_age_H_65_69 +"
				+ "        tranche_age_H_70_74 + tranche_age_H_75_79) AS total_50_79,"
				+ "    SUM(tranche_age_F_80_99 + tranche_age_H_80_99) AS total_80_plus"
				+ "FROM commune "
				+ "STRAIGHT_JOIN club ON commune.code_commune = club.code_commune "
				+ "GROUP BY commune.code_commune "
				+ "ORDER BY ? DESC LIMIT 25 OFFSET ?";
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(requete);
			stmt.setString(1, critereGenreAge);
			stmt.setInt(2, offset);
			rs = stmt.executeQuery();
			while(rs.next()) {
				commune = communeDAO.getCommune(rs.getString("code_commune"));
				licences = rs.getInt("total_total");
				licencesFemmes = rs.getInt("total_femmes");
				licencesHommes = rs.getInt("total_hommes");
				licencesHommes114 = rs.getInt("total_hommes_1_14");
				licencesHommes1524 = rs.getInt("total_hommes_15_24");
				licencesHommes2534 = rs.getInt("total_hommes_25_34");
				licencesHommes3549 = rs.getInt("total_hommes_35_49");
				licencesHommes5079 = rs.getInt("total_hommes_50_79");
				licencesHommes8099 = rs.getInt("total_hommes_80_plus");
				licencesFemmes114 = rs.getInt("total_femmes_1_14");
				licencesFemmes1524 = rs.getInt("total_femmes_15_24");
				licencesFemmes2534 = rs.getInt("total_femmes_25_34");
				licencesFemmes3549 = rs.getInt("total_femmes_35_49");
				licencesFemmes5079 = rs.getInt("total_femmes_50_79");
				licencesFemmes8099 = rs.getInt("total_femmes_80_plus");
				licencesTotales114 = rs.getInt("total_1_14");
				licencesTotales1524 = rs.getInt("total_15_24");
				licencesTotales2534 = rs.getInt("total_25_34");
				licencesTotales3549 = rs.getInt("total_35_49");
				licencesTotales5079 = rs.getInt("total_50_79");
				licencesTotales8099 = rs.getInt("total_80_plus");
				classement.add(new ClassementCommune(licences,licencesFemmes,licencesHommes,licencesHommes114,
						licencesHommes1524, licencesHommes2534, licencesHommes3549, licencesHommes5079,
						licencesHommes8099, licencesFemmes114, licencesFemmes1524, licencesFemmes2534,
						licencesFemmes3549, licencesFemmes5079, licencesFemmes8099, licencesTotales114,
						licencesTotales1524, licencesTotales2534, licencesTotales3549, licencesTotales5079,
						licencesTotales8099,commune));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn,stmt,rs);
		}
		return classement;
	}
	
	public ArrayList<ClassementCommune> getClassementAll(){
		ArrayList<ClassementCommune> classement = new ArrayList<>();
		Commune commune = null;
		int licences,licencesFemmes,licencesHommes;
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		CommuneDAO communeDAO = new CommuneDAO(dataSource);
		String requete = "SELECT commune.code_commune,SUM(total) FROM commune STRAIGHT_JOIN club ON(commune.code_commune=club.code_commune) GROUP BY commune.code_commune ORDER BY SUM(total) DESC";
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(requete);
			rs = stmt.executeQuery();
			while(rs.next()) {
				commune = communeDAO.getCommune(rs.getString("code_commune"));
				licences = rs.getInt("SUM(total)");
				licencesFemmes = rs.getInt("SUM(total_femmes)");
				licencesHommes = rs.getInt("SUM(total_hommes");
				classement.add(new ClassementCommune(commune,licences,licencesFemmes,licencesHommes));
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
