package app;
/**
 * Fichier permettant d'importer un fichier CSV dans une table MySQL
 * Ce Script utilise le mode transactionnel afin de respecter l'approche ACID (Atomicité, Cohérence, Isolation, Durabilité) 
 * qui permet d'assurer l'intégrité des données au sein d'une base de données. 
 * @author Serais Sébastien
 */
import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class AddCommuneQPV {
	// nom de la machine hôte qui héberge le SGBD Mysql
    // final static String host = "localhost";
    // nom de la BDD sur le serveur Mysql
    // final static String nomBase = "club_sport";
    // login de la BDD
    // final static String login = "root";
    // mot de passe
    // final static String motDePasse = "root";
    // chemin fichier csv à importer
    // final static String nomFichierDepartement = "/Users/achabilaguide/Documents/Fichier club_de_sport";
    // caractère de séparation des colonnes
    // Définir la correspondance entre les codes des départements et leurs libellés
    
    final static String separateur = ";";
    
    // Nom de la table pour les communes
    final static String nomTableCommune = "commune"; // Nom de la table des communes

    public static void main(String[] args) {
        // Charger les propriétés de connexion à la base de données depuis un fichier db.properties
        Properties pr = new Properties();
        try {
            pr.load(AddCommune.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String host = pr.getProperty("host");
        String nomBase = pr.getProperty("nomBase");
        String login = pr.getProperty("login");
        String motDePasse = pr.getProperty("motDePasse");
        String nomFichierCommuneQPV = pr.getProperty("nomFichierCommuneQPV"); // Fichier CSV contenant les communes

        Connection con = null;
        PreparedStatement stmt = null;
        int compteurLignesAjoutees = 0;
        String ligne = null;
        String tab[];
        int retour, lignesAjt = 0, paliers = 5;
        Scanner sc = new Scanner(System.in);
        String reponse;

        // Chargement du pilote MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e2) {
            System.err.println("Pilote MySQL non trouvé : com.mysql.cj.jdbc.Driver");
            System.exit(-1);  // Si le pilote MySQL n'est pas trouvé, arrêter le programme
        }

        try {
            // Connexion à la base de données avec les paramètres de connexion
            con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + nomBase + "?characterEncoding=UTF-8", login, motDePasse);

            // Activation du mode transactionnel
            con.setAutoCommit(false);

            // Création du flux de lecture en mode caractères
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nomFichierCommuneQPV), "UTF-8"));
            // Nombre de lignes insérées dans la BDD
            ligne = br.readLine();
            String requeteCommune = "UPDATE " + nomTableCommune + " SET qpv=? WHERE code_commune=?";
            stmt = con.prepareStatement(requeteCommune);  // Préparer la requête d'insertion dans la table `commune`
            String code_commune = null,qpv=null;

            // Traiter chaque ligne du fichier CSV
            do {
                try {
                    ligne = br.readLine();
                    if (ligne != null) {
                        tab = ligne.split(separateur); // Séparer la ligne en fonction du séparateur défini (ici, ";")
                        
                        code_commune = tab[0].trim(); // Extraire le code de la commune (première colonne)
                        code_commune = enleverGuillemets(code_commune);
                      
                        // Vérifier si le code_commune existe dans la table commune
                            if (communeExiste(con, code_commune)) {
                                // Insérer la commune dans la base de données
                                cleanString(stmt, tab,2,1);  // on inscrit le qpv dans la requête
                                stmt.setString(2, code_commune);
                                stmt.executeUpdate();  // Exécuter la requête d'insertion
                                lignesAjt++;

                                // Afficher le nombre de lignes insérées tous les 1000 enregistrements
                                if (lignesAjt == paliers) {
                                    System.out.println(lignesAjt + " lignes ajoutées");
                                    paliers += 5;
                                }

                                // Comptabiliser le nombre de lignes ajoutées

                            } else {
                                System.out.println("Commune non existante dans la base de données : " + code_commune);
                            }
                    }

                } catch (SQLIntegrityConstraintViolationException doublon) {
                    // Gérer les doublons dans les données (si le code de la commune existe déjà)
                    System.out.println("Doublon ignoré ...");
                    doublon.printStackTrace();
                }

            } while (ligne != null);  // Continuer à lire le fichier tant qu'il y a des lignes

            // Demander à l'utilisateur s'il souhaite valider la transaction complète
            System.out.println("VALIDER les requêtes ? (O/N)");

            reponse = sc.nextLine();
            if ("O".equalsIgnoreCase(reponse)) {
                System.out.println("COMMIT");
                con.commit();  // Valider toutes les modifications
                System.out.println("Lignes ajoutées : " + lignesAjt);
            } else {
                System.out.println("ROLLBACK");
                con.rollback();  // Annuler toutes les modifications
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println("ROLLBACK");
                con.rollback();  // Annuler les modifications en cas d'erreur

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        } finally {
            // Fermer le PreparedStatement et la connexion après utilisation
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void cleanString(PreparedStatement stmt,String [] tab,int tab_index, int index) throws SQLException {
		String tab_value;
		try {
			tab_value = enleverGuillemets(tab[tab_index]);
			if(tab_value == null || tab_value.trim().isEmpty()) {
				stmt.setNull(index, Types.VARCHAR);
			}
			else {
				String value = EmojiFilter.filterEmoji(tab_value.trim());
				stmt.setString(index, value);
			}
			
		}
		catch(ArrayIndexOutOfBoundsException e) {
			stmt.setNull(index,Types.VARCHAR);
			
		}
	}

    // Vérifie si la commune existe déjà dans la base de données
    public static boolean communeExiste(Connection conn, String code_commune) throws SQLException {
        String sql = "SELECT 1 FROM " + nomTableCommune + " WHERE code_commune = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code_commune);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retourne true si la commune existe déjà
            }
        }
    }
    public static String enleverGuillemets(String input) {
        if (input == null || input.length() < 2) {
            return input;
        }

        char firstChar = input.charAt(0);
        char lastChar = input.charAt(input.length() - 1);

        if ((firstChar == '"' && lastChar == '"') || (firstChar == '\'' && lastChar == '\'')) {
            return input.substring(1, input.length() - 1);
        }

        return input;
    }
}

