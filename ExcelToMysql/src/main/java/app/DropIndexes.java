package app;
import java.sql.*;

public class DropIndexes {

    // Méthode pour supprimer les index autres que PRIMARY et FK
    public static void supprimerIndexes(String tableName, Connection conn, Statement stmt) {
        ResultSet rs = null;

        try {
            // Requête pour obtenir les index
            String query = "SELECT index_name " +
                           "FROM information_schema.statistics " +
                           "WHERE table_schema = DATABASE() AND table_name = ? " +
                           "AND index_name <> 'PRIMARY' " +
                           "AND index_name NOT LIKE 'FK_%'"; // On suppose que les clés étrangères commencent par 'FK_'

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, tableName);

            rs = pstmt.executeQuery();

            // Générer et exécuter les commandes DROP INDEX pour chaque index trouvé
            while (rs.next()) {
                String indexName = rs.getString("index_name");
                System.out.println("Suppression de l'index : " + indexName);

                // Générer la commande SQL pour supprimer l'index
                String dropIndexSQL = "ALTER TABLE " + tableName + " DROP INDEX " + indexName;
                stmt.executeUpdate(dropIndexSQL);
                System.out.println("Index supprimé : " + indexName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
