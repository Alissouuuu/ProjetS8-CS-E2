package app;
/**
 * Fichier permettant d'importer un fichier CSV dans une table MySQL
 * Ce Script utilise le mode transactionnel afin de respecter l'approche ACID (Atomicité, Cohérence, Isolation, Durabilité) 
 * qui permet d'assurer l'intégrité des données au sein d'une base de données. 
 * @author Serais Sébastien
 */
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class AddLibelleVille {
    
    final static String separateur = ";";
    
    // Nom de la table pour les communes
    final static String nomTableLibelleVille = "libelle_ville"; // Nom de la table des communes
    final static String nomTableLVC = "libelle_ville_commune";
    final static String nomTableCommune = "commune";

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
        String nomFichierLibelleVille = pr.getProperty("nomFichierLicences"); // Fichier CSV contenant les communes

        Connection con = null;
        PreparedStatement stmt = null,stmtLVC=null;
        int compteurLignesAjoutees = 0;
        String ligne = null;
        String tab[];
        int retour, lignesAjt = 0, paliers = 1000,lignesAjtLVC=0,paliersLVC=1000;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nomFichierLibelleVille), "UTF-8"));
            // Nombre de lignes insérées dans la BDD
            ligne = br.readLine();
            String requeteCommune = "INSERT INTO " + nomTableLibelleVille + " (lib_commune) VALUES(?)";
            String requeteLVC = "INSERT INTO "+nomTableLVC+" (id_libelle,code_commune) VALUES(?,?)";
            stmt = con.prepareStatement(requeteCommune,Statement.RETURN_GENERATED_KEYS);  // Préparer la requête d'insertion dans la table `commune`
            stmtLVC = con.prepareStatement(requeteLVC);
            String libelleVille=null,code_commune=null;
            int idGenere;
            HashMap<String,Integer> libelleIdMap = new HashMap<>();

            // Traiter chaque ligne du fichier CSV
            do {
                try {
                    ligne = br.readLine();
                    if (ligne != null) {
                        tab = ligne.split(separateur); // Séparer la ligne en fonction du séparateur défini (ici, ";")
                        
                        libelleVille = tab[1].trim(); // Extraire le nom de la commune (2e colonne)
                        code_commune = tab[0].trim();
                        
                        libelleVille = enleverGuillemets(libelleVille);
                        code_commune = enleverGuillemets(code_commune);
                        // Vérifier si le code_commune existe dans la table commune
                            if (!libelleIdMap.containsKey(libelleVille)) {
                                // Insérer la commune dans la base de données
                                stmt.setString(1, libelleVille);  // on inscrit le code postal dans la requête
                                stmt.executeUpdate();  // Exécuter la requête d'insertion
                                idGenere = getIdRequete(stmt);
                                libelleIdMap.put(libelleVille, idGenere);
                                //System.out.println("id généré dans la map : "+libelleIdMap.get(libelleVille));
                                lignesAjt++;
                                
                                // Afficher le nombre de lignes insérées tous les 5 enregistrements
                                if (lignesAjt == paliers) {
                                    System.out.println(lignesAjt + " lignes ajoutées");
                                    paliers += 1000;
                                }

                                // Comptabiliser le nombre de lignes ajoutées

                            } else {
                                //System.out.println("Code postal déjà existant ou commune non en bdd : ");
                            }
                            /*if(!communeExiste(con,code_commune))
                            	System.out.println("commune existe pas ! "+code_commune);*/
                            if(libelleIdMap.containsKey(libelleVille) && communeExiste(con,code_commune) &&!lVCExiste(con,libelleIdMap.get(libelleVille),code_commune)) {
                            	//System.out.println("ajout lvc !");
                            	stmtLVC.setInt(1,libelleIdMap.get(libelleVille));
                            	stmtLVC.setString(2, code_commune);
                            	stmtLVC.executeUpdate();
                            	lignesAjtLVC++;
                            	
                            	if (lignesAjtLVC == paliersLVC) {
                                    System.out.println(lignesAjtLVC + " lignes LVC ajoutées");
                                    paliersLVC += 1000;
                                }

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

    // Vérifie si le lib commune existe déjà dans la base de données
    public static boolean libelleVilleExiste(Connection conn, String libelleVille) throws SQLException {
        String sql = "SELECT 1 FROM " + nomTableLibelleVille + " WHERE lib_commune = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, libelleVille);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retourne true si le libelle existe déjà
            }
        }
    }
    
    public static boolean lVCExiste(Connection conn, int id,String code_commune) throws SQLException {
        String sql = "SELECT 1 FROM " + nomTableLVC + " WHERE id_libelle = ? AND code_commune = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, id);
            stmt.setString(2, code_commune);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retourne true si la paire existe déjà
            }
        }
    }
    
    
    public static int getIdRequete(PreparedStatement stmt) throws SQLException {
    	ResultSet rs = stmt.getGeneratedKeys();
    	int idGenere = 0;
    	if(rs.next()) {
    		idGenere = rs.getInt(1);
    	}
    	//si idGenere vaut encore 0, alors pas d'id généré
    	return idGenere;
    }
    

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

