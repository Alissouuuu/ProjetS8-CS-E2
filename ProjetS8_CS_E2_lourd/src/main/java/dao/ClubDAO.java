
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

    private static final String URL = "jdbc:mysql://localhost:3306/club_sport";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public List<Club> findAllWithDetails() {
        List<Club> clubs = new ArrayList<>();

        String sql = 
        	    "SELECT c.id_club, c.lib_club, r.lib_region AS lib_region, lv.lib_commune, " +
        	    "f.lib_federation, d.lib_departement, r.lib_region " +
        	    "FROM Club c " +
        	    "JOIN Commune com ON c.code_commune = com.code_commune " +
        	    "JOIN libelle_ville_commune cl ON com.code_commune = cl.code_commune " +
        	    "JOIN libelle_ville lv ON cl.id_libelle = lv.id_libelle " +
        	    "JOIN Federation f ON c.code_federation = f.code_federation " +
        	    "JOIN Departement d ON com.code_departement = d.code_departement " +
        	    "JOIN Region r ON d.code_region = r.code_region";


        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_club");
                String nom = rs.getString("lib_club");
                
                String commune = rs.getString("lib_commune");
                String departement = rs.getString("lib_departement");
                String federation = rs.getString("lib_federation");
                String region = rs.getString("lib_region");
                clubs.add(new Club(id, nom, commune, departement,region,  federation));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clubs;
    }
    
    
    private double[] getCenterCoordinates(Connection conn, String commune, String departement, String region) throws SQLException {
        StringBuilder sql = new StringBuilder(
        		"SELECT com.latitude, com.longitude " +
        		        "FROM Commune com " +
        		        "JOIN libelle_ville_commune cl ON com.code_commune = cl.code_commune " +
        		        "JOIN libelle_ville lv ON cl.id_libelle = lv.id_libelle " +
        		        "JOIN Departement d ON com.code_departement = d.code_departement " +
        		        "JOIN Region r ON d.code_region = r.code_region " +
        		        "WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        if (commune != null && !commune.isEmpty()) {
            sql.append(" AND lv.lib_commune LIKE ?");
            params.add("%" + commune + "%");
        }

        if (departement != null && !departement.equals("Toutes")) {
            sql.append(" AND d.lib_departement = ?");
            params.add(departement);
        }

        if (region != null && !region.equals("Toutes")) {
            sql.append(" AND r.lib_region = ?");
            params.add(region);
        }

        sql.append(" LIMIT 1");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new double[]{rs.getDouble("latitude"), rs.getDouble("longitude")};
            }
        }

        return null; 
    }

    

    public List<Club> findByFilters(String federation, String commune, String region, String departement, int rayonKm) {
        List<Club> clubs = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            boolean useDistance = rayonKm > 0 && (
                    (commune != null && !commune.isEmpty()) ||
                    (departement != null && !departement.equals("Toutes")) ||
                    (region != null && !region.equals("Toutes"))
            );

            StringBuilder sql = new StringBuilder();
            List<Object> params = new ArrayList<>();
            double[] centerCoords = null;

            // SQL base selon useDistance
            if (useDistance) {
                sql.append("SELECT c.id_club, c.lib_club, r.lib_region AS lib_region, lv.lib_commune, ")
                   .append("d.lib_departement, f.lib_federation, com.latitude, com.longitude, ")
                   .append("(6371 * acos(cos(radians(?)) * cos(radians(com.latitude)) * ")
                   .append("cos(radians(com.longitude) - radians(?)) + sin(radians(?)) * sin(radians(com.latitude)))) AS distance ")
                   .append("FROM Club c ")
                   .append("JOIN Commune com ON c.code_commune = com.code_commune ")
                   .append("JOIN libelle_ville_commune cl ON com.code_commune = cl.code_commune ")
                   .append("JOIN libelle_ville lv ON cl.id_libelle = lv.id_libelle ")
                   .append("JOIN Federation f ON c.code_federation = f.code_federation ")
                   .append("JOIN Departement d ON com.code_departement = d.code_departement ")
                   .append("JOIN Region r ON d.code_region = r.code_region ")
                   .append("WHERE 1=1 ");

                // Obtenir les coordonnées
                centerCoords = getCenterCoordinates(conn, commune, departement, region);
                if (centerCoords == null) return clubs;

                // Coords en premiers paramètres
                params.add(centerCoords[0]);
                params.add(centerCoords[1]);
                params.add(centerCoords[0]);

            } else {
                sql.append("SELECT c.id_club, c.lib_club, r.lib_region AS lib_region, lv.lib_commune, ")
                   .append("d.lib_departement, f.lib_federation ")
                   .append("FROM Club c ")
                   .append("JOIN Commune com ON c.code_commune = com.code_commune ")
                   .append("JOIN libelle_ville_commune cl ON com.code_commune = cl.code_commune ")
                   .append("JOIN libelle_ville lv ON cl.id_libelle = lv.id_libelle ")
                   .append("JOIN Federation f ON c.code_federation = f.code_federation ")
                   .append("JOIN Departement d ON com.code_departement = d.code_departement ")
                   .append("JOIN Region r ON d.code_region = r.code_region ")
                   .append("WHERE 1=1 ");
            }

            // Filtres
            if (federation != null && !federation.equals("Toutes")) {
                sql.append("AND f.lib_federation = ? ");
                params.add(federation);
            }

            if (!useDistance && commune != null && !commune.isEmpty()) {
                sql.append("AND lv.lib_commune LIKE ? ");
                params.add("%" + commune + "%");
            }

            if (region != null && !region.equals("Toutes")) {
                sql.append("AND r.lib_region = ? ");
                params.add(region);
            }

            if (departement != null && !departement.equals("Toutes")) {
                sql.append("AND d.lib_departement = ? ");
                params.add(departement);
            }

            if (useDistance) {
                sql.append("HAVING distance <= ? ");
                params.add(rayonKm);
            }

            // Préparation
            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            int index = 1;
            for (Object param : params) {
                stmt.setObject(index++, param);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clubs.add(new Club(
                    rs.getInt("id_club"),
                    rs.getString("lib_club"),
                    rs.getString("lib_commune"),
                    rs.getString("lib_departement"),
                    rs.getString("lib_region"),
                    rs.getString("lib_federation")
                ));
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
