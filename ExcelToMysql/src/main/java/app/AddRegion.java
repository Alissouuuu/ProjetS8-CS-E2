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
import java.util.HashMap;
import java.util.Map;

public class AddRegion {
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
    private static final Map<String, String> regionCodes = new HashMap<>();
    final static String separateur = ";";
    final static String nomTableRegion = "region"; 

    static {
        regionCodes.put("Guadeloupe", "01");
        regionCodes.put("Martinique", "02");
        regionCodes.put("Guyane", "03");
        regionCodes.put("La Réunion", "04");
        regionCodes.put("Mayotte", "06");
        regionCodes.put("Île-de-France", "11");
        regionCodes.put("Centre-Val de Loire", "24");
        regionCodes.put("Bourgogne-Franche-Comté", "27");
        regionCodes.put("Normandie", "28");
        regionCodes.put("Hauts-de-France", "32");
        regionCodes.put("Grand Est", "44");
        regionCodes.put("Pays de la Loire", "52");
        regionCodes.put("Bretagne", "53");
        regionCodes.put("Nouvelle-Aquitaine", "75");
        regionCodes.put("Occitanie", "76");
        regionCodes.put("Auvergne-Rhône-Alpes", "84");
        regionCodes.put("Provence-Alpes-Côte d'Azur", "93");
        regionCodes.put("Corse", "94");
        regionCodes.put("COM","99");
    }

    public static void main(String[] args) {
        Properties pr = new Properties();
        try {
            pr.load(AddRegion.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String host = pr.getProperty("host");
        String nomBase = pr.getProperty("nomBase");
        String login = pr.getProperty("login");
        String motDePasse = pr.getProperty("motDePasse");
        String nomFichierRegion = pr.getProperty("nomFichierRegion");

        File fichier = new File(nomFichierRegion);
        if (!fichier.exists() || fichier.isDirectory()) {
            System.out.println("Le fichier spécifié est introuvable ou c'est un répertoire : " + nomFichierRegion);
            return;  // Arrêter l'exécution si le fichier est introuvable ou c'est un répertoire
        }

        Connection con = null;
        PreparedStatement stmt = null;
        int compteurLignesAjoutees = 0;
        String ligne = null;
        String tab[];
        int retour, lignesAjt = 0, paliers = 5, paliersNonAjt=1000,lignesNonAjt=0;
        Scanner sc = new Scanner(System.in);
        String reponse;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e2) {
            System.err.println("Pilote MySQL non trouvé : com.mysql.cj.jdbc.Driver");
            System.exit(-1);
        }

        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + nomBase + "?characterEncoding=UTF-8", login, motDePasse);
            con.setAutoCommit(false);

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nomFichierRegion), "UTF-8"));
            ligne = br.readLine();
            String requeteRegion = "INSERT INTO " + nomTableRegion + " (code_region, lib_region) VALUES (?, ?)";
            stmt = con.prepareStatement(requeteRegion);

            do {
                try {
                    ligne = br.readLine();
                    if (ligne != null) {
                        //System.out.println("Ligne lue : " + ligne);  // Afficher la ligne lue pour le débogage
                        tab = ligne.split(separateur);
                        
                            String libelle_region = tab[5].trim();
                            libelle_region = enleverGuillemets(libelle_region);
                            
                            if (regionCodes.containsKey(libelle_region)) {
                                String code_region = regionCodes.get(libelle_region);

                                if (!regionExiste(con, code_region)) {
                                	stmt.setString(1, code_region);
                                	cleanString(stmt,tab,5,2);
                                    stmt.executeUpdate();
                                    lignesAjt++;

                                    if (lignesAjt == paliers) {
                                        System.out.println(lignesAjt + " lignes ajoutées");
                                        paliers += 5;
                                    }

                                    compteurLignesAjoutees++;
                                } else {
                                	lignesNonAjt++;
                                	if(lignesNonAjt == paliersNonAjt) {
                                		System.out.println("Région "+lignesNonAjt+" déjà existante dans la base de données : " + libelle_region);
                                		paliersNonAjt+=1000;
                                	}
                                    
                                }
                            } else {
                                System.out.println("Libellé de région non trouvé dans la map : " + libelle_region);
                            }
                    }

                } catch (SQLIntegrityConstraintViolationException doublon) {
                    System.out.println("Doublon ignoré pour la ligne : " + ligne);
                    doublon.printStackTrace();
                }

            } while (ligne != null);

            System.out.println("VALIDER les requêtes ? (O/N)");

            reponse = sc.nextLine();
            if ("O".equalsIgnoreCase(reponse)) {
                System.out.println("COMMIT");
                con.commit();
                System.out.println("Lignes ajoutées : " + lignesAjt);
            } else {
                System.out.println("ROLLBACK");
                con.rollback();
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println("ROLLBACK");
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean regionExiste(Connection conn, String code_region) throws SQLException {
        String sql = "SELECT 1 FROM " + nomTableRegion + " WHERE code_region = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code_region);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
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
