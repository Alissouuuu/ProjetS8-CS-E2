package dao;

import model.Club;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClubDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/SportiZone";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public List<Club> findAllWithDetails() {
        List<Club> clubs = new ArrayList<>();

        String sql = "SELECT c.id_club, c.lib_club, c.code_commune, com.lib_commune, f.lib_federation, d.lib_departement, r.lib_region " +
                "FROM Club c " +
                "JOIN Commune com ON c.code_commune = com.code_commune " +
                "JOIN Federation f ON c.code_federation = f.code_federation " +
                "JOIN Departement d ON com.code_departement = d.code_departement " +
                "JOIN Region r ON d.code_region = r.code_region";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_club");
                String nom = rs.getString("lib_club");
                int codeCommune = rs.getInt("code_commune");
                String commune = rs.getString("lib_commune");
                String departement = rs.getString("lib_departement");
                String federation = rs.getString("lib_federation");
               // String region = rs.getString("lib_region");
                clubs.add(new Club(id, nom, codeCommune, commune, departement, federation));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clubs;
    }

    public List<Club> findByFilters(String federation, String commune, String region, String departement, int rayonKm) {
        List<Club> clubs = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT c.id_club, c.lib_club, c.code_commune, com.lib_commune, f.lib_federation, d.lib_departement, r.lib_region " +
                "FROM Club c " +
                "JOIN Commune com ON c.code_commune = com.code_commune " +
                "JOIN Federation f ON c.code_federation = f.code_federation " +
                "JOIN Departement d ON com.code_departement = d.code_departement " +
                "JOIN Region r ON d.code_region = r.code_region WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (federation != null && !federation.equals("Toutes")) {
            sql.append(" AND f.lib_federation = ?");
            params.add(federation);
        }
        if (commune != null && !commune.isEmpty()) {
            sql.append(" AND com.lib_commune LIKE ?");
            params.add("%" + commune + "%");
        }
        if (region != null && !region.equals("Toutes")) {
            sql.append(" AND r.lib_region = ?");
            params.add(region);
        }
        if (departement != null && !departement.equals("Toutes")) {
            sql.append(" AND d.lib_departement = ?");
            params.add(departement);
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_club");
                    String nom = rs.getString("lib_club");
                    int codeCom = rs.getInt("code_commune");
                    String com = rs.getString("lib_commune");
                    String dep = rs.getString("lib_departement");
                    //String reg = rs.getString("lib_region");
                    String fed = rs.getString("lib_federation");
                    clubs.add(new Club(id, nom, codeCom, com, dep, fed));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clubs;
    }

    public List<String> findAllFederationNames() {
        List<String> federations = new ArrayList<>();
        String sql = "SELECT lib_federation FROM Federation ORDER BY lib_federation";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                federations.add(rs.getString("lib_federation"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return federations;
    }

    public List<String> findAllRegionNames() {
        List<String> regions = new ArrayList<>();
        String sql = "SELECT lib_region FROM Region ORDER BY lib_region";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                regions.add(rs.getString("lib_region"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return regions;
    }

    public List<String> findAllDepartementNames() {
        List<String> departements = new ArrayList<>();
        String sql = "SELECT lib_departement FROM Departement ORDER BY lib_departement";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                departements.add(rs.getString("lib_departement"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departements;
    }

}
