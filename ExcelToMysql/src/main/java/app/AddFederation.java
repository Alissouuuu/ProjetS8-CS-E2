package app;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class AddFederation {
    final static String separateur = ";";
    final static String nomTableFederation = "federation"; // Nom de la table pour les fédérations

    public static void main(String[] args) {
        Properties pr = new Properties();
        try {
            pr.load(AddFederation.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String host = pr.getProperty("host");
        String nomBase = pr.getProperty("nomBase");
        String login = pr.getProperty("login");
        String motDePasse = pr.getProperty("motDePasse");
        String nomFichierFederation = pr.getProperty("nomFichierFederation");

        if (nomFichierFederation == null || nomFichierFederation.isEmpty()) {
            System.out.println("Le chemin du fichier de fédération est incorrect ou manquant.");
            return; // Arrêter l'exécution si le fichier est introuvable
        }
        

        Connection con = null; // objet connexion
        PreparedStatement stmt = null; // prepareStatement
        String ligne = null; // ligne lue dans le fichier
        String tab[]; // tableau issu du split
        int retour, lignesAjt = 0, paliers = 50,lignesNonAjt=0, paliersNonAjt=1000; // nb de lignes insérées et non insérées
        Scanner sc = new Scanner(System.in);
        String reponse;

        // Chargement du pilote MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e2) {
            System.err.println("Pilote MySQL non trouvé : com.mysql.cj.jdbc.Driver");
            System.exit(-1);
        }

        try {
            // Connexion avec choix de l'encodage
            con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + nomBase + "?characterEncoding=UTF-8", login, motDePasse);
            System.out.println("Connexion réussie !");
            // activation du mode transactionnel
            con.setAutoCommit(false);
            // Création du flux de lecture en mode caractères
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nomFichierFederation), "UTF-8"));
            // nombre de lignes insérées dans le BDD
            ligne = br.readLine();
            String requeteFederation = "insert into " + nomTableFederation + " (code_federation, lib_federation) values (?, ?)";
            stmt = con.prepareStatement(requeteFederation);
            do {

                try {
                    ligne = br.readLine();
                    if (ligne != null) {
                        tab = ligne.split(separateur); // Découper la ligne en fonction du séparateur
                            	
                                // Vérifier que la fédération n'existe pas déjà
                        if(!federationExiste(con,enleverGuillemets(tab[7]))) {
                                cleanInt(stmt,tab,7,1);
                                cleanString(stmt,tab,8,2);
                                retour = stmt.executeUpdate(); // Exécuter la requête d'insertion
                                System.out.println("insertion réussie !");
                                lignesAjt++;

                                if (lignesAjt == paliers) {
                                    System.out.println(lignesAjt + " lignes ajoutées");
                                    paliers += 50;
                                }

                                // Comptabiliser les lignes ajoutées
                                //compteurLignesAjoutees = compteurLignesAjoutees + retour;

                                if (retour != 1) {
                                    System.out.println("**********************************ERREUR ... sur les requêtes");
                                    throw new Exception();
                                }
                        }
                        else {
                        	lignesNonAjt++;
                        	if(lignesNonAjt == paliersNonAjt) {
                        		System.out.println("Pas d'insertion"+lignesNonAjt+", federation déjà existante !");
                        		paliersNonAjt+=1000;
                        	}
                        	
                        }
                    }

                } catch (SQLIntegrityConstraintViolationException doublon) {
                    System.out.println("Doublon ignoré ...");
                    doublon.printStackTrace();
                    continue; // Passer à la ligne suivante sans exécuter d'autres opérations
                }

            } while (ligne != null);

            // On demande si l'utilisateur souhaite valider la transaction complète
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
                if (con != null) {
                    con.rollback();
                }

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

    // Vérifier si une fédération existe déjà dans la base de données
    public static boolean federationExiste(Connection conn, String codeFederation) throws SQLException {
        String sql = "SELECT 1 FROM federation WHERE code_federation = ?"; // Vérifie si la fédération existe déjà
        int codeFederationInt = Integer.parseInt(codeFederation);
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codeFederationInt); // Paramétrer le code_federation dans la requête
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retourne true si la fédération existe déjà, sinon false
            }
        }
    }

    public static void cleanDouble(PreparedStatement stmt,String [] tab,int tab_index, int index) throws SQLException {
		String tab_value;
		try {
			tab_value = enleverGuillemets(tab[tab_index]);
			if(tab_value == null || tab_value.trim().isEmpty()) {
				stmt.setNull(index, Types.DOUBLE);
			}
			else {
				double value = Double.parseDouble(tab_value.trim());
				stmt.setDouble(index, value);
			}
			
		}
		catch(ArrayIndexOutOfBoundsException e) {
			stmt.setNull(index,Types.DOUBLE);
			
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
	public static void cleanInt(PreparedStatement stmt, String[] tab, int tab_index, int index) throws SQLException {
		String tab_value;
		try {
			tab_value = enleverGuillemets(tab[tab_index]);
			if (tab_value == null || tab_value.trim().isEmpty()) {
				stmt.setNull(index, Types.INTEGER);
			} else {
				int value = Integer.parseInt(tab_value.trim());
				stmt.setInt(index, value);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			stmt.setNull(index, Types.INTEGER);
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
