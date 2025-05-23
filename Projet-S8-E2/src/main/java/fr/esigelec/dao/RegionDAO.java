package fr.esigelec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import fr.esigelec.models.Region;

public class RegionDAO {
	private DataSource dataSource;
	
	public RegionDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Region getRegion(String codeRegion) {
		
		Region region = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT lib_region FROM region WHERE code_region=?");
			stmt.setString(1, codeRegion);
			rs = stmt.executeQuery();
			if(rs.next())
				region = new Region(codeRegion,rs.getString("lib_region"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn,stmt,rs);
		}
		return region;
	}
	
	public ArrayList<Region> getRegions(){
		ArrayList<Region> regions = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT code_region,lib_region FROM region");
			rs = stmt.executeQuery();
			if(rs.next())
				regions.add(new Region(rs.getString("code_region"),rs.getString("lib_region")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn,stmt,rs);
		}
		return regions;
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
