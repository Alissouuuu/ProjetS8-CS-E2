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

public class AddDepartement {
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
    private static final Map<String, String> departementCodes = new HashMap<>();
    
    // Définir la correspondance entre les codes des départements et les codes des régions
    private static final Map<String, String> departementToRegion = new HashMap<>();
    
    // Caractère de séparation des colonnes dans le fichier CSV
    final static String separateur = ";";
    
    // Nom de la table pour les départements
    final static String nomTableDepartement = "departement"; 

    static {
        // Remplir la map avec les codes des départements et leurs libellés
        departementCodes.put("01", "Ain");
        departementCodes.put("02", "Aisne");
        departementCodes.put("03", "Allier");
        departementCodes.put("04", "Alpes-de-Haute-Provence");
        departementCodes.put("05", "Hautes-Alpes");
        departementCodes.put("06", "Alpes-Maritimes");
        departementCodes.put("07", "Ardèche");
        departementCodes.put("08", "Ardennes");
        departementCodes.put("09", "Ariège");
        departementCodes.put("10", "Aube");
        departementCodes.put("11", "Aude");
        departementCodes.put("12", "Aveyron");
        departementCodes.put("13", "Bouches-du-Rhône");
        departementCodes.put("14", "Calvados");
        departementCodes.put("15", "Cantal");
        departementCodes.put("16", "Charente");
        departementCodes.put("17", "Charente-Maritime");
        departementCodes.put("18", "Cher");
        departementCodes.put("19", "Corrèze");
        departementCodes.put("2A", "Corse-du-Sud");
        departementCodes.put("2B", "Haute-Corse");
        departementCodes.put("21", "Côte-d'Or");
        departementCodes.put("22", "Côtes-d'Armor");
        departementCodes.put("23", "Creuse");
        departementCodes.put("24", "Dordogne");
        departementCodes.put("25", "Doubs");
        departementCodes.put("26", "Drôme");
        departementCodes.put("27", "Eure");
        departementCodes.put("28", "Eure-et-Loir");
        departementCodes.put("29", "Finistère");
        departementCodes.put("30", "Gard");
        departementCodes.put("31", "Haute-Garonne");
        departementCodes.put("32", "Gers");
        departementCodes.put("33", "Gironde");
        departementCodes.put("34", "Hérault");
        departementCodes.put("35", "Ille-et-Vilaine");
        departementCodes.put("36", "Indre");
        departementCodes.put("37", "Indre-et-Loire");
        departementCodes.put("38", "Isère");
        departementCodes.put("39", "Jura");
        departementCodes.put("40", "Landes");
        departementCodes.put("41", "Loir-et-Cher");
        departementCodes.put("42", "Loire");
        departementCodes.put("43", "Haute-Loire");
        departementCodes.put("44", "Loire-Atlantique");
        departementCodes.put("45", "Loiret");
        departementCodes.put("46", "Lot");
        departementCodes.put("47", "Lot-et-Garonne");
        departementCodes.put("48", "Lozère");
        departementCodes.put("49", "Maine-et-Loire");
        departementCodes.put("50", "Manche");
        departementCodes.put("51", "Marne");
        departementCodes.put("52", "Haute-Marne");
        departementCodes.put("53", "Mayenne");
        departementCodes.put("54", "Meurthe-et-Moselle");
        departementCodes.put("55", "Meuse");
        departementCodes.put("56", "Morbihan");
        departementCodes.put("57", "Moselle");
        departementCodes.put("58", "Nièvre");
        departementCodes.put("59", "Nord");
        departementCodes.put("60", "Oise");
        departementCodes.put("61", "Orne");
        departementCodes.put("62", "Pas-de-Calais");
        departementCodes.put("63", "Puy-de-Dôme");
        departementCodes.put("64", "Pyrénées-Atlantiques");
        departementCodes.put("65", "Hautes-Pyrénées");
        departementCodes.put("66", "Pyrénées-Orientales");
        departementCodes.put("67", "Bas-Rhin");
        departementCodes.put("68", "Haut-Rhin");
        departementCodes.put("69", "Rhône");
        departementCodes.put("70", "Haute-Saône");
        departementCodes.put("71", "Saône-et-Loire");
        departementCodes.put("72", "Sarthe");
        departementCodes.put("73", "Savoie");
        departementCodes.put("74", "Haute-Savoie");
        departementCodes.put("75", "Paris");
        departementCodes.put("76", "Seine-Maritime");
        departementCodes.put("77", "Seine-et-Marne");
        departementCodes.put("78", "Yvelines");
        departementCodes.put("79", "Deux-Sèvres");
        departementCodes.put("80", "Somme");
        departementCodes.put("81", "Tarn");
        departementCodes.put("82", "Tarn-et-Garonne");
        departementCodes.put("83", "Var");
        departementCodes.put("84", "Vaucluse");
        departementCodes.put("85", "Vendée");
        departementCodes.put("86", "Vienne");
        departementCodes.put("87", "Haute-Vienne");
        departementCodes.put("88", "Vosges");
        departementCodes.put("89", "Yonne");
        departementCodes.put("90", "Territoire de Belfort");
        departementCodes.put("91", "Essonne");
        departementCodes.put("92", "Hauts-de-Seine");
        departementCodes.put("93", "Seine-Saint-Denis");
        departementCodes.put("94", "Val-de-Marne");
        departementCodes.put("95", "Val-d'Oise");
        departementCodes.put("971", "Guadeloupe");
        departementCodes.put("972", "Martinique");
        departementCodes.put("973", "Guyane");
        departementCodes.put("974", "La Réunion");
        departementCodes.put("975", "Saint-Pierre-et-Miquelon");
        departementCodes.put("976", "Mayotte");
        departementCodes.put("977", "Saint-Barthélemy");
        departementCodes.put("978", "Saint-Martin");
        departementCodes.put("980", "Monaco"); // pas un département français, mais parfois utilisé
        departementCodes.put("986", "Wallis-et-Futuna");
        departementCodes.put("987", "Polynésie française");
        departementCodes.put("988", "Nouvelle-Calédonie");
        
     // Régions métropolitaines
        departementToRegion.put("01", "84");  // Auvergne-Rhône-Alpes
        departementToRegion.put("02", "32");  // Hauts-de-France
        departementToRegion.put("03", "84");
        departementToRegion.put("04", "93");  // Provence-Alpes-Côte d’Azur
        departementToRegion.put("05", "93");
        departementToRegion.put("06", "93");
        departementToRegion.put("07", "84");
        departementToRegion.put("08", "44");  // Grand Est
        departementToRegion.put("09", "76");  // Occitanie
        departementToRegion.put("10", "44");
        departementToRegion.put("11", "76");
        departementToRegion.put("12", "76");
        departementToRegion.put("13", "93");
        departementToRegion.put("14", "28");  // Normandie
        departementToRegion.put("15", "84");
        departementToRegion.put("16", "75");  // Nouvelle-Aquitaine
        departementToRegion.put("17", "75");
        departementToRegion.put("18", "24");  // Centre-Val de Loire
        departementToRegion.put("19", "75");
        departementToRegion.put("2A", "94");  // Corse
        departementToRegion.put("2B", "94");
        departementToRegion.put("21", "27");  // Bourgogne-Franche-Comté
        departementToRegion.put("22", "53");  // Bretagne
        departementToRegion.put("23", "75");
        departementToRegion.put("24", "75");
        departementToRegion.put("25", "27");
        departementToRegion.put("26", "84");
        departementToRegion.put("27", "28");
        departementToRegion.put("28", "24");
        departementToRegion.put("29", "53");
        departementToRegion.put("30", "76");
        departementToRegion.put("31", "76");
        departementToRegion.put("32", "76");
        departementToRegion.put("33", "75");
        departementToRegion.put("34", "76");
        departementToRegion.put("35", "53");
        departementToRegion.put("36", "24");
        departementToRegion.put("37", "24");
        departementToRegion.put("38", "84");
        departementToRegion.put("39", "27");
        departementToRegion.put("40", "75");
        departementToRegion.put("41", "24");
        departementToRegion.put("42", "84");
        departementToRegion.put("43", "84");
        departementToRegion.put("44", "52");  // Pays de la Loire
        departementToRegion.put("45", "24");
        departementToRegion.put("46", "76");
        departementToRegion.put("47", "75");
        departementToRegion.put("48", "76");
        departementToRegion.put("49", "52");
        departementToRegion.put("50", "28");
        departementToRegion.put("51", "44");
        departementToRegion.put("52", "44");
        departementToRegion.put("53", "52");
        departementToRegion.put("54", "44");
        departementToRegion.put("55", "44");
        departementToRegion.put("56", "53");
        departementToRegion.put("57", "44");
        departementToRegion.put("58", "27");
        departementToRegion.put("59", "32");
        departementToRegion.put("60", "32");
        departementToRegion.put("61", "28");
        departementToRegion.put("62", "32");
        departementToRegion.put("63", "84");
        departementToRegion.put("64", "75");
        departementToRegion.put("65", "76");
        departementToRegion.put("66", "76");
        departementToRegion.put("67", "44");
        departementToRegion.put("68", "44");
        departementToRegion.put("69", "84");
        departementToRegion.put("70", "27");
        departementToRegion.put("71", "27");
        departementToRegion.put("72", "52");
        departementToRegion.put("73", "84");
        departementToRegion.put("74", "84");
        departementToRegion.put("75", "11");  // Île-de-France
        departementToRegion.put("76", "28");
        departementToRegion.put("77", "11");
        departementToRegion.put("78", "11");
        departementToRegion.put("79", "75");
        departementToRegion.put("80", "32");
        departementToRegion.put("81", "76");
        departementToRegion.put("82", "76");
        departementToRegion.put("83", "93");
        departementToRegion.put("84", "93");
        departementToRegion.put("85", "52");
        departementToRegion.put("86", "75");
        departementToRegion.put("87", "75");
        departementToRegion.put("88", "44");
        departementToRegion.put("89", "27");
        departementToRegion.put("90", "27");
        departementToRegion.put("91", "11");
        departementToRegion.put("92", "11");
        departementToRegion.put("93", "11");
        departementToRegion.put("94", "11");
        departementToRegion.put("95", "11");

        // Régions d’outre-mer
        departementToRegion.put("971", "01"); // Guadeloupe
        departementToRegion.put("972", "02"); // Martinique
        departementToRegion.put("973", "03"); // Guyane
        departementToRegion.put("974", "04"); // La Réunion
        departementToRegion.put("976", "06"); // Mayotte

        // Collectivités d’outre-mer et assimilées (codes région fictifs pour usage interne)
        departementToRegion.put("975", "99"); // Saint-Pierre-et-Miquelon
        departementToRegion.put("977", "99"); // Saint-Barthélemy
        departementToRegion.put("978", "99"); // Saint-Martin
        departementToRegion.put("986", "99"); // Wallis-et-Futuna
        departementToRegion.put("987", "99"); // Polynésie française
        departementToRegion.put("988", "99"); // Nouvelle-Calédonie
        departementToRegion.put("980", "99"); // Monaco (si utilisé)

    }

    public static void main(String[] args) {
        // Charger les propriétés de connexion à la base de données depuis un fichier db.properties
        Properties pr = new Properties();
        try {
            pr.load(AddDepartement.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String host = pr.getProperty("host");
        String nomBase = pr.getProperty("nomBase");
        String login = pr.getProperty("login");
        String motDePasse = pr.getProperty("motDePasse");
        String nomFichierDepartement = pr.getProperty("nomFichierDepartement"); // Fichier CSV contenant les départements

        Connection con = null;
        PreparedStatement stmt = null;
        int compteurLignesAjoutees = 0;
        String ligne = null;
        String tab[];
        int retour, lignesAjt = 0, paliers = 5, lignesNonAjt=0,paliersNonAjt=1000;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nomFichierDepartement), "UTF-8"));
            // Nombre de lignes insérées dans la BDD
            ligne = br.readLine();
            String requeteDepartement = "INSERT INTO " + nomTableDepartement + " (code_departement, lib_departement, code_region) VALUES (?, ?, ?)";
            stmt = con.prepareStatement(requeteDepartement);  // Préparer la requête d'insertion dans la table `departement`

            // Traiter chaque ligne du fichier CSV
            do {
                try {
                    ligne = br.readLine();
                    if (ligne != null) {
                        tab = ligne.split(separateur); // Séparer la ligne en fonction du séparateur défini (ici, ";")
                            String code_departement = tab[4].trim(); // Extraire le code du département (deuxième colonne)
                            code_departement = enleverGuillemets(code_departement);
                            
                            // Formater le code de département à deux chiffres s'il est à un seul chiffre
                            if (code_departement.length() == 1) {
                                code_departement = "0" + code_departement; // Ajouter un zéro devant le code si nécessaire
                            }

                            String code_region = departementToRegion.get(code_departement); // Récupérer le code de la région

                            // Vérifier si le code_region existe dans la table 'region'
                            if (!departementCodes.containsKey(code_departement) || code_region == null || !codeRegionExiste(con, code_region) || codeDepartementExiste(con,code_departement)) {
                                //System.out.println("Le code département n'est soit pas bon, soit n'a pas de région associée dans la bdd");
                                lignesNonAjt++;
                            	if(lignesNonAjt == paliersNonAjt) {
                            		System.out.println("Ligne non ajoutée : "+lignesNonAjt);
                            		paliersNonAjt+=1000;
                            	}
                                
                            }
                            else{
                                String libelle_departement = departementCodes.get(code_departement); // Récupérer le libellé du département
                                // Insérer le code_departement, libelle_departement et code_region dans la base de données
                                stmt.setString(1, code_departement);  // Définir le code_departement
                                stmt.setString(2, libelle_departement);  // Définir le libelle_departement
                                stmt.setString(3, code_region);  // Définir le code_region
                                stmt.executeUpdate();  // Exécuter la requête d'insertion
                                lignesAjt++;

                                // Afficher le nombre de lignes insérées tous les 1000 enregistrements
                                if (lignesAjt == paliers) {
                                    System.out.println(lignesAjt + " lignes ajoutées");
                                    paliers += 1000;
                                }

                           
                            }
                    }

                } catch (SQLIntegrityConstraintViolationException doublon) {
                    // Gérer les doublons dans les données (si le code du département existe déjà)
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

    // Vérifie si le code_region existe dans la table 'region'
    public static boolean codeRegionExiste(Connection conn, String code_region) throws SQLException {
        String sql = "SELECT 1 FROM region WHERE code_region = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code_region);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retourne true si le code_region existe dans la table region
            }
        }
    }
    
    // Vérifie si le code_region existe dans la table 'region'
    public static boolean codeDepartementExiste(Connection conn, String code_departement) throws SQLException {
        String sql = "SELECT 1 FROM departement WHERE code_departement = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code_departement);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retourne true si le code_region existe dans la table region
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
