package fr.esigelec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import fr.esigelec.models.Departement;
import fr.esigelec.models.Region;

public class DepartementDAO {
	private DataSource dataSource;
	
	public DepartementDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Departement getDepartement(String codeDepartement) {
		Region region = null;
		RegionDAO regionDAO = new RegionDAO(dataSource);
		Departement departement = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT lib_departement,code_region FROM departement WHERE code_departement=?");
			stmt.setString(1, codeDepartement);
			rs = stmt.executeQuery();
			if(rs.next())
				region = regionDAO.getRegion(rs.getString("code_region"));
				departement = new Departement(codeDepartement,rs.getString("lib_departement"),region);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn,stmt,rs);
		}
		return departement;
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
