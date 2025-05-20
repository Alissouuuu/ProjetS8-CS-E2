package fr.esigelec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import fr.esigelec.models.ClassementRegion;
import fr.esigelec.models.Region;

public class ClassementRegionDAO {
	private DataSource dataSource;
	public ClassementRegionDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public ArrayList<ClassementRegion> getClassement(int page){
		int offset = (page-1)*25;
		ArrayList<ClassementRegion> classement = new ArrayList<>();
		Region region = null;
		String codeRegion,nomRegion;
		int licences;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String requete = "SELECT region.code_region,region.lib_region,SUM(total) FROM commune STRAIGHT_JOIN departement ON(commune.code_departement=departement.code_departement) STRAIGHT_JOIN region ON(departement.code_region=region.code_region)  STRAIGHT_JOIN club ON(commune.code_commune=club.code_commune)  GROUP BY region.code_region ORDER BY SUM(total) DESC LIMIT 25 OFFSET ?";
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(requete);
			stmt.setInt(1, offset);
			rs = stmt.executeQuery();
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
		
		finally {
			close(conn,stmt,rs);
		}
		return classement;
	}
	
	public ArrayList<ClassementRegion> getClassementAll(){
		ArrayList<ClassementRegion> classement = new ArrayList<>();
		Region region = null;
		String codeRegion,nomRegion;
		int licences;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String requete = "SELECT region.code_region,region.lib_region,SUM(total) FROM commune STRAIGHT_JOIN departement ON(commune.code_departement=departement.code_departement) STRAIGHT_JOIN region ON(departement.code_region=region.code_region)  STRAIGHT_JOIN club ON(commune.code_commune=club.code_commune)  GROUP BY region.code_region ORDER BY SUM(total) DESC";
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(requete);
			rs = stmt.executeQuery();
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
