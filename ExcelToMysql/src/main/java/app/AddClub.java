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
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;

public class AddClub {
    
    final static String separateur = ";";
    
    // Nom de la table pour les communes
    final static String nomTableClub = "club"; // Nom de la table des clubs
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
        String nomFichierClub = pr.getProperty("nomFichierLicences"); // Fichier CSV contenant les communes

        Connection con = null;
        PreparedStatement stmt = null;
        String ligne = null;
        String tab[];
        int retour, lignesAjt = 0, paliers = 1000;
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
            HashSet<String> communes = new HashSet<>();
            HashMap<String, Integer> compteurClubs = new HashMap<>();
            chargerCommunes(con,communes);
            // Création du flux de lecture en mode caractères
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nomFichierClub), "UTF-8"));
            // Nombre de lignes insérées dans la BDD
            ligne = br.readLine();
            String requeteCommune = "INSERT INTO " + nomTableClub + " (lib_club,code_commune,code_federation,tranche_age_F_1_4,tranche_age_F_5_9,tranche_age_F_10_14,"
            		+ "tranche_age_F_15_19,tranche_age_F_20_24,tranche_age_F_25_29,tranche_age_F_30_34,tranche_age_F_35_39,tranche_age_F_40_44,"
            		+ "tranche_age_F_45_49,tranche_age_F_50_54,tranche_age_F_55_59,tranche_age_F_60_64,tranche_age_F_65_69,tranche_age_F_70_74,"
            		+ "tranche_age_F_75_79,tranche_age_F_80_99,tranche_age_F_NR,tranche_age_H_1_4,tranche_age_H_5_9,tranche_age_H_10_14,"
            		+ "tranche_age_H_15_19,tranche_age_H_20_24,tranche_age_H_25_29,tranche_age_H_30_34,tranche_age_H_35_39,tranche_age_H_40_44,"
            		+ "tranche_age_H_45_49,tranche_age_H_50_54,tranche_age_H_55_59,tranche_age_H_60_64,tranche_age_H_65_69,tranche_age_H_70_74,"
            		+ "tranche_age_H_75_79,tranche_age_H_80_99,tranche_age_H_NR,total_femme,total_homme,total) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            stmt = con.prepareStatement(requeteCommune);  // Préparer la requête d'insertion dans la table `commune`
            boolean mauvaiseLigne;
            String code_commune = null,lib_club=null,titre_federation,nom_commune=null,key=null;
            int code_federation,nombreClubFC,compteur;
            // Traiter chaque ligne du fichier CSV
            do {
                try {
                    ligne = br.readLine();
                    if (ligne != null) {
                        tab = ligne.split(separateur); // Séparer la ligne en fonction du séparateur défini (ici, ";")
                        mauvaiseLigne = false;
                        	
                     	code_commune = enleverGuillemets(tab[0]).trim();
                     	
                     	titre_federation= enleverGuillemets(tab[8]).trim();
                     	code_federation = Integer.parseInt(enleverGuillemets(tab[7]).trim());
                     	nom_commune = enleverGuillemets(tab[1]).trim();
                     	
                     	
                     	if(code_commune.length() == 4)
                        	code_commune = "0"+code_commune;
                     	
                        if(!communes.contains(code_commune)) {
                        	System.out.println("commune non en bdd !");
                        }
                        else {
                        	/*nombreClubFC = nombreLibelleClub(con,code_commune,code_federation);
                        	if(nombreClubFC == 0)
                        		lib_club = "Club de "+titre_federation+" de "+nom_commune;
                        	else
                        		lib_club = (nombreClubFC+1)+"e club de "+titre_federation+" de "+nom_commune;*/
                        	
                        	key = code_commune + "-" + code_federation;
                        	compteur = compteurClubs.getOrDefault(key, 0);

                        	// Génère le nom du club
                        	if (compteur == 0)
                        	    lib_club = "Club de " + titre_federation + " de " + nom_commune;
                        	else
                        	    lib_club = (compteur + 1) + "e club de " + titre_federation + " de " + nom_commune;

               
                        	stmt.setString(1,lib_club);
                            stmt.setString(2, code_commune);  // on inscrit le code_commune dans la requête
                            cleanInt(stmt,tab,7,3);
                            cleanInt(stmt,tab,9,4);
                            cleanInt(stmt,tab,10,5);
                            cleanInt(stmt,tab,11,6);
                            cleanInt(stmt,tab,12,7);
                            cleanInt(stmt,tab,13,8);
                            cleanInt(stmt,tab,14,9);
                            cleanInt(stmt,tab,15,10);
                            cleanInt(stmt,tab,16,11);
                            cleanInt(stmt,tab,17,12);
                            cleanInt(stmt,tab,18,13);
                            cleanInt(stmt,tab,19,14);
                            cleanInt(stmt,tab,20,15);
                            cleanInt(stmt,tab,21,16);
                            cleanInt(stmt,tab,22,17);
                            cleanInt(stmt,tab,23,18);
                            cleanInt(stmt,tab,24,19);
                            cleanInt(stmt,tab,25,20);
                            cleanInt(stmt,tab,26,21);
                            cleanInt(stmt,tab,27,22);
                            cleanInt(stmt,tab,28,23);
                            cleanInt(stmt,tab,29,24);
                            cleanInt(stmt,tab,30,25);
                            cleanInt(stmt,tab,31,26);
                            cleanInt(stmt,tab,32,27);
                            cleanInt(stmt,tab,33,28);
                            cleanInt(stmt,tab,34,29);
                            cleanInt(stmt,tab,35,30);
                            cleanInt(stmt,tab,36,31);
                            cleanInt(stmt,tab,37,32);
                            cleanInt(stmt,tab,38,33);
                            cleanInt(stmt,tab,39,34);
                            cleanInt(stmt,tab,40,35);
                            cleanInt(stmt,tab,41,36);
                            cleanInt(stmt,tab,42,37);
                            cleanInt(stmt,tab,43,38);
                            cleanInt(stmt,tab,44,39);
                            stmt.setInt(40, calculerTotalTab(tab,9,26)); //total femmes
                            stmt.setInt(41, calculerTotalTab(tab,27,44)); //total hommes
                            cleanInt(stmt,tab,45,42); //total hommes et femmes
                            
                            stmt.executeUpdate();  // Exécuter la requête d'insertion
                            compteurClubs.put(key, compteur + 1);// Mets à jour le compteur
                            lignesAjt++;

                            // Afficher le nombre de lignes insérées tous les 1000 enregistrements
                            if (lignesAjt == paliers) {
                                System.out.println(lignesAjt + " lignes ajoutées "+lib_club);
                                paliers += 1000;
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
    
    public static int nombreLibelleClub(Connection conn, String code_commune,int code_federation) throws SQLException{
    	String sql = "SELECT COUNT(id_club) FROM "+nomTableClub+" WHERE code_commune = ? AND code_federation = ?";
    	try(PreparedStatement stmt = conn.prepareStatement(sql)){
    		stmt.setString(1,code_commune);
    		stmt.setInt(2,code_federation);
    		try(ResultSet rs = stmt.executeQuery()){
    			if(rs.next())
    				return rs.getInt(1);
    			else
    				return 0;
    		}
    	}
    }
    
    public static void cleanInt(PreparedStatement stmt, String[] tab, int tab_index, int index) throws SQLException {
		String tab_value;
		try {
			tab_value = enleverGuillemets(tab[tab_index]);
			if (tab_value == null || tab_value.trim().isEmpty()) {
				stmt.setInt(index, 0);
			} else {
				int value = Integer.parseInt(tab_value.trim());
				stmt.setInt(index, value);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			stmt.setInt(index, 0);
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
    
    public static int calculerTotalTab(String[] tab,int indexDebut,int indexFin) {
    	int valeur,total=0;
    	for(int i=indexDebut;i<=indexFin;i++) {
    		valeur = Integer.parseInt(enleverGuillemets(tab[i]).trim());
    		total+=valeur;
    	}
    	return total;
    }
    
    public static void chargerCommunes(Connection conn,HashSet communes) throws SQLException {
    	String sql = "SELECT code_commune FROM commune";
    	try (PreparedStatement stmt = conn.prepareStatement(sql);
    	     ResultSet rs = stmt.executeQuery()) {
    	    while (rs.next()) {
    	        communes.add(rs.getString("code_commune"));
    	    }
    	}
    }
}

