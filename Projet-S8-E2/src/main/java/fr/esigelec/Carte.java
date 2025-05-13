package fr.esigelec;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import fr.esigelec.dao.*;
import fr.esigelec.model.*;
/**
 * Servlet implementation class Carte
 */
@WebServlet("/Carte")
public class Carte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Carte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// On récupère les valaurs des recherches
		String searchVille = request.getParameter("searchVille");
		String searchDepartement = request.getParameter("searchDepartement");
		String searchRegion = request.getParameter("searchRegion");
		String searchFederation = request.getParameter("searchFederation");
		
		double rayon = 0.0;
		String searchRayon = request.getParameter("rayon");
		
//		System.out.println("Ville : " + searchVille);
//		System.out.println("Département : " + searchDepartement);
//		System.out.println("Région : " + searchRegion);
//		System.out.println("Fédération : " + searchFederation);
		
		// On créer une liste de département pour pouvoir gérer le centrage de la carte (à mettre dans la BDD)
		List<Departement> departements = new ArrayList<>();
		
		departements.add(new Departement("Ain", 46.0833, 5.3333));
		departements.add(new Departement("Aisne", 49.5000, 3.5000));
		departements.add(new Departement("Allier", 46.3333, 3.0000));
		departements.add(new Departement("Alpes-de-Haute-Provence", 44.0833, 6.2333));
		departements.add(new Departement("Hautes-Alpes", 44.6667, 6.3333));
		departements.add(new Departement("Alpes-Maritimes", 43.9333, 7.1833));
		departements.add(new Departement("Ardèche", 44.6667, 4.5000));
		departements.add(new Departement("Ardennes", 49.7500, 4.6667));
		departements.add(new Departement("Ariège", 42.9500, 1.6000));
		departements.add(new Departement("Aube", 48.3333, 4.1667));
		departements.add(new Departement("Aude", 43.1667, 2.5000));
		departements.add(new Departement("Aveyron", 44.4000, 2.6000));
		departements.add(new Departement("Bouches-du-Rhône", 43.5000, 5.5000));
		departements.add(new Departement("Calvados", 49.0000, -0.5000));
		departements.add(new Departement("Cantal", 45.0000, 2.5000));
		departements.add(new Departement("Charente", 45.6667, 0.1667));
		departements.add(new Departement("Charente-Maritime", 45.7500, -0.8000));
		departements.add(new Departement("Cher", 47.0000, 2.5000));
		departements.add(new Departement("Corrèze", 45.2667, 1.8000));
		departements.add(new Departement("Corse-du-Sud", 41.9333, 8.7333));
		departements.add(new Departement("Haute-Corse", 42.5000, 9.0000));
		departements.add(new Departement("Côte-d'Or", 47.5000, 5.0000));
		departements.add(new Departement("Côtes-d'Armor", 48.5000, -2.8333));
		departements.add(new Departement("Creuse", 46.1667, 2.0000));
		departements.add(new Departement("Dordogne", 45.1833, 0.7167));
		departements.add(new Departement("Doubs", 47.2500, 6.3333));
		departements.add(new Departement("Drôme", 44.7500, 5.2500));
		departements.add(new Departement("Eure", 49.0000, 1.2000));
		departements.add(new Departement("Eure-et-Loir", 48.4500, 1.5000));
		departements.add(new Departement("Finistère", 48.3333, -4.0000));
		departements.add(new Departement("Gard", 43.8333, 4.5000));
		departements.add(new Departement("Haute-Garonne", 43.5000, 1.5000));
		departements.add(new Departement("Gers", 43.7000, 0.6000));
		departements.add(new Departement("Gironde", 44.8378, -0.5792));
		departements.add(new Departement("Hérault", 43.6667, 3.5000));
		departements.add(new Departement("Ille-et-Vilaine", 48.0000, -1.5000));
		departements.add(new Departement("Indre", 46.8000, 1.7000));
		departements.add(new Departement("Indre-et-Loire", 47.3333, 0.7000));
		departements.add(new Departement("Isère", 45.3333, 5.5000));
		departements.add(new Departement("Jura", 46.7500, 5.7500));
		departements.add(new Departement("Landes", 43.8333, -0.7500));
		departements.add(new Departement("Loir-et-Cher", 47.5000, 1.3333));
		departements.add(new Departement("Loire", 45.5000, 4.0000));
		departements.add(new Departement("Haute-Loire", 45.0000, 3.7500));
		departements.add(new Departement("Loire-Atlantique", 47.3333, -1.5000));
		departements.add(new Departement("Loiret", 47.8333, 2.0000));
		departements.add(new Departement("Lot", 44.5000, 1.5000));
		departements.add(new Departement("Lot-et-Garonne", 44.2000, 0.6000));
		departements.add(new Departement("Lozère", 44.5000, 3.5000));
		departements.add(new Departement("Maine-et-Loire", 47.5000, -0.5000));
		departements.add(new Departement("Manche", 49.0000, -1.3333));
		departements.add(new Departement("Marne", 49.0000, 4.0000));
		departements.add(new Departement("Haute-Marne", 48.0000, 5.0000));
		departements.add(new Departement("Mayenne", 48.1667, -0.7000));
		departements.add(new Departement("Meurthe-et-Moselle", 48.8333, 6.1667));
		departements.add(new Departement("Meuse", 49.0000, 5.5000));
		departements.add(new Departement("Morbihan", 47.7500, -2.7500));
		departements.add(new Departement("Moselle", 49.0000, 6.5000));
		departements.add(new Departement("Nièvre", 47.0000, 3.5000));
		departements.add(new Departement("Nord", 50.6333, 3.0667));
		departements.add(new Departement("Oise", 49.5000, 2.5000));
		departements.add(new Departement("Orne", 48.7500, 0.0000));
		departements.add(new Departement("Pas-de-Calais", 50.5000, 2.5000));
		departements.add(new Departement("Puy-de-Dôme", 45.7500, 3.0000));
		departements.add(new Departement("Pyrénées-Atlantiques", 43.2500, -0.5000));
		departements.add(new Departement("Hautes-Pyrénées", 43.0000, 0.0000));
		departements.add(new Departement("Pyrénées-Orientales", 42.6667, 2.7500));
		departements.add(new Departement("Bas-Rhin", 48.6667, 7.5000));
		departements.add(new Departement("Haut-Rhin", 47.8333, 7.3333));
		departements.add(new Departement("Rhône", 45.7500, 4.8333));
		departements.add(new Departement("Haute-Saône", 47.6667, 6.2500));
		departements.add(new Departement("Saône-et-Loire", 46.5000, 4.5000));
		departements.add(new Departement("Sarthe", 48.0000, 0.2000));
		departements.add(new Departement("Savoie", 45.5000, 6.3333));
		departements.add(new Departement("Haute-Savoie", 46.0000, 6.5000));
		departements.add(new Departement("Paris", 48.8566, 2.3522));
		departements.add(new Departement("Seine-Maritime", 49.5000, 0.0000));
		departements.add(new Departement("Seine-et-Marne", 48.5000, 2.7500));
		departements.add(new Departement("Yvelines", 48.7500, 1.7500));
		departements.add(new Departement("Deux-Sèvres", 46.5000, -0.3333));
		departements.add(new Departement("Somme", 49.8333, 2.5000));
		departements.add(new Departement("Tarn", 43.8333, 2.1667));
		departements.add(new Departement("Tarn-et-Garonne", 44.0000, 1.2500));
		departements.add(new Departement("Var", 43.5000, 6.0000));
		departements.add(new Departement("Vaucluse", 44.0000, 5.0000));
		departements.add(new Departement("Vendée", 46.5000, -1.0000));
		departements.add(new Departement("Vienne", 46.5833, 0.3333));
		departements.add(new Departement("Haute-Vienne", 45.8333, 1.2500));
		departements.add(new Departement("Vosges", 48.1667, 6.5000));
		departements.add(new Departement("Yonne", 47.8333, 3.5000));
		departements.add(new Departement("Territoire de Belfort", 47.6333, 6.8333));
		departements.add(new Departement("Essonne", 48.5833, 2.2500));
		departements.add(new Departement("Hauts-de-Seine", 48.9000, 2.2500));
		departements.add(new Departement("Seine-Saint-Denis", 48.9333, 2.5000));
		departements.add(new Departement("Val-de-Marne", 48.8000, 2.4500));
		departements.add(new Departement("Val-d'Oise", 49.0500, 2.2000));
		
		// On créer une liste de régions pour pouvoir gérer le centrage de la carte (à mettre dans la BDD)
		List<Region> regions = new ArrayList<>();

		regions.add(new Region("Auvergne-Rhône-Alpes", 45.7640, 4.8357));      // Lyon
		regions.add(new Region("Bourgogne-Franche-Comté", 47.0526, 4.3832));   // Dijon
		regions.add(new Region("Bretagne", 48.2020, -2.9326));                 // Rennes
		regions.add(new Region("Centre-Val de Loire", 47.7516, 1.6750));       // Orléans
		regions.add(new Region("Corse", 42.0396, 9.0129));                     // Ajaccio
		regions.add(new Region("Grand Est", 48.6996, 6.1870));                 // Strasbourg / Nancy
		regions.add(new Region("Hauts-de-France", 50.4800, 2.6400));           // Lille / Amiens
		regions.add(new Region("Île-de-France", 48.8499, 2.6370));             // Paris
		regions.add(new Region("Normandie", 49.1829, -0.3707));                // Caen / Rouen
		regions.add(new Region("Nouvelle-Aquitaine", 45.7084, 0.3970));        // Bordeaux
		regions.add(new Region("Occitanie", 43.6047, 1.4442));                 // Toulouse
		regions.add(new Region("Pays de la Loire", 47.2184, -1.5536));         // Nantes
		regions.add(new Region("Provence-Alpes-Côte d'Azur", 43.9352, 6.0679));// Marseille / Nice

		
		
		// On remplace la valeur des recherche par null si elles contiennent le texte par défaut
		if("-- Par ville --".equals(searchVille)) {
			searchVille = null;
		}
		
		if ("-- Par département --".equals(searchDepartement)) {
			searchDepartement = null;
		}
		
		if ("-- Par région --".equals(searchRegion)) {
			searchRegion = null;
		}
		
		if ("-- Fédération --".equals(searchFederation)) {
			searchFederation = null;
		}
		
		if ("-- Fédération --".equals(searchFederation)) {
			searchFederation = null;
		}
		
		if (searchRayon != null) {
			rayon = Double.parseDouble(searchRayon);
		}
		
		// Déboggage
		request.setAttribute("input", searchVille + searchDepartement + searchRegion + searchFederation + rayon);

//-----------------------------------------------------------------------------------------------------------
		
		// On récupère les listes des différentes zones géographiques et des fédérations
		

		// On récupère la liste des communes présentes dans la BDD et on la passe dans la JSP
		List<String> villes = new ArrayList<>();
		try {
		    villes = new DAOClub().getListCommune();
		} catch (SQLException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
		
//	    for (String ville : villes) {
//	    	System.out.println(ville);
//	    }
	    
		request.setAttribute("villes", villes);
		
		// On récupère la liste des departements présents dans la BDD et on la passe dans la JSP
		List<String> departementsBDD = new ArrayList<>();
		try {
			departementsBDD = new DAOClub().getListDepartement();
		} catch (SQLException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
		
//	    for (String departement : departements) {
//	    	System.out.println(departement);
//	    }
	    
		request.setAttribute("departements", departementsBDD);
		
		// On récupère la liste des regions présentes dans la BDD et on la passe dans la JSP
		List<String> regionsBDD = new ArrayList<>();
		try {
			regionsBDD = new DAOClub().getListRegion();
		} catch (SQLException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
		
//	    for (String region : regions) {
//	    	System.out.println(region);
//	    }
	    
		request.setAttribute("regions", regionsBDD);
		
		// On récupère la liste des fédérations présentes dans la BDD et on la passe dans la JSP
		List<String> federations = new ArrayList<>();
		try {
			federations = new DAOClub().getListFederation();
		} catch (SQLException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
		
//	    for (String federation : federations) {
//	    	System.out.println(federation);
//	    }
	    
		request.setAttribute("federations", federations);

//------------------------------------------------------------------------------------------------------

		
		// Affichage de tous les clubs de France par fédération
		
		if(searchVille == null && searchDepartement == null && searchRegion == null && rayon == 0) {
			
			try {

				if (searchFederation != null && !searchFederation.trim().isEmpty() ) {
					DAOClub dao = new DAOClub();
					// System.out.println(">> Appel à searchClubByFederation en cours");
					List<ClubDAO> clubs = dao.searchClubByFederation(searchFederation);
					
//					for(ClubDAO club : clubs) {
//						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//						+" / "+ club.getLat() +" / "+ club.getLon());
//					}
					
					request.setAttribute("clubs", clubs);
					request.setAttribute("zoneGeo", "France");

				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}
		
		// Affichage de tous les clubs d'une ville par fédération
		
		if( searchDepartement == null && searchRegion == null && rayon == 0) { // On vérifie qu'aucun département ni aucune région
																// n'est sélectionné
			
			try {

				if (searchFederation != null && !searchFederation.trim().isEmpty() 
						&& searchVille != null && !searchVille.trim().isEmpty()) {
					DAOClub dao = new DAOClub();
					List<ClubDAO> clubsFiltred = new ArrayList<>();
					// System.out.println(">> Appel à searchClubByVille en cours");
					List<ClubDAO> clubs = dao.searchClubByVille(searchVille);
					
					double lat = clubs.get(0).getLat();
					double lon = clubs.get(0).getLon();
					
					for(ClubDAO club : clubs) {
						if (club.getFederation().equals(searchFederation.trim())) {
							clubsFiltred.add(club);
						}
					}
					
//					for(ClubDAO club : clubsFiltred) {
//						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//						+" / "+ club.getLat() +" / "+ club.getLon());
//					}
					
					request.setAttribute("clubs", clubsFiltred);
					request.setAttribute("zoneGeo", "Ville");
					
					// On passe à la jsp la latitude et la longitude de la ville 
					request.setAttribute("lat", lat);
					request.setAttribute("lon", lon);

				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}


		// Affichage de tous les clubs d'une ville 
		
		if( searchDepartement == null && searchRegion == null && searchFederation == null && rayon == 0) { 
																// On vérifie qu'aucun département ni aucune région
																// n'est sélectionné
			
			try {

				if (searchVille != null && !searchVille.trim().isEmpty()) {
					DAOClub dao = new DAOClub();
					// System.out.println(">> Appel à searchClubByVille en cours");
					List<ClubDAO> clubs = dao.searchClubByVille(searchVille);
					
					double lat = clubs.get(0).getLat();
					double lon = clubs.get(0).getLon();
					
					
//					for(ClubDAO club : clubsFiltred) {
//						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//						+" / "+ club.getLat() +" / "+ club.getLon());
//					}
					
					request.setAttribute("clubs", clubs);
					request.setAttribute("zoneGeo", "Ville");
					
					// On passe à la jsp la latitude et la longitude de la ville 
					request.setAttribute("lat", lat);
					request.setAttribute("lon", lon);

				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}		
		
		
		if(rayon != 0) {
			// Affichage de tous les clubs d'une ville par fédération avec rayon
			
			if( searchDepartement == null && searchRegion == null) { // On vérifie qu'aucun département ni aucune région
																	// n'est sélectionné
				
				try {

					if (searchFederation != null && !searchFederation.trim().isEmpty() 
							&& searchVille != null && !searchVille.trim().isEmpty()) {
						DAOClub dao = new DAOClub();
						List<ClubDAO> clubsFiltred = new ArrayList<>();
						// System.out.println(">> Appel à searchClubByVille en cours");
						List<ClubDAO> clubs = dao.searchClubByRayon(searchVille, rayon);
						
						double lat = clubs.get(0).getLat();
						double lon = clubs.get(0).getLon();
						
						for(ClubDAO club : clubs) {
							if (club.getFederation().equals(searchFederation.trim())) {
								clubsFiltred.add(club);
							}
						}
						
//						for(ClubDAO club : clubsFiltred) {
//							System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//							+" / "+ club.getLat() +" / "+ club.getLon());
//						}
						
						request.setAttribute("clubs", clubsFiltred);
						request.setAttribute("zoneGeo", "Ville");
						
						// On passe à la jsp la latitude et la longitude de la ville 
						request.setAttribute("lat", lat);
						request.setAttribute("lon", lon);

					}

				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
					System.out.println("Erreur lors de la récupèration des données");
				}
				
			}


			// Affichage de tous les clubs d'une ville avec rayon
			
			if( searchDepartement == null && searchRegion == null && searchFederation == null) { // On vérifie qu'aucun département ni aucune région
																	// n'est sélectionné
				
				try {

					if (searchVille != null && !searchVille.trim().isEmpty()) {
						DAOClub dao = new DAOClub();
						// System.out.println(">> Appel à searchClubByVille en cours");
						List<ClubDAO> clubs = dao.searchClubByRayon(searchVille, rayon);
						
						double lat = clubs.get(0).getLat();
						double lon = clubs.get(0).getLon();
						
						
//						for(ClubDAO club : clubsFiltred) {
//							System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//							+" / "+ club.getLat() +" / "+ club.getLon());
//						}
						
						request.setAttribute("clubs", clubs);
						request.setAttribute("zoneGeo", "Ville");
						
						// On passe à la jsp la latitude et la longitude de la ville 
						request.setAttribute("lat", lat);
						request.setAttribute("lon", lon);

					}

				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
					System.out.println("Erreur lors de la récupèration des données");
				}
				
			}

		}
		
		
		// Affichage de tous les clubs d'un département par fédération
		
		if( searchVille == null && searchRegion == null) { // On vérifie qu'aucune ville ni aucune région
																// n'est sélectionné
			
			try {

				if (searchFederation != null && !searchFederation.trim().isEmpty() 
						&& searchDepartement != null && !searchDepartement.trim().isEmpty()) {
					DAOClub dao = new DAOClub();
					double lat = 0;
					double lon = 0;
					
					List<ClubDAO> clubsFiltred = new ArrayList<>();
					// System.out.println(">> Appel à searchClubByVille en cours");
					List<ClubDAO> clubs = dao.searchClubByDepartement(searchDepartement);
					
					// On récupère la latitude et la longitude du département depuis la liste
					for (Departement d : departements) {
						if(d.getNom().equals(searchDepartement)) {
							 lat = d.getLat();
							 lon = d.getLon();
						}
					}

					
					for(ClubDAO club : clubs) {
						if (club.getFederation().equals(searchFederation.trim())) {
							clubsFiltred.add(club);
						}
					}
					
//					for(ClubDAO club : clubsFiltred) {
//						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//						+" / "+ club.getLat() +" / "+ club.getLon());
//					}
					
					request.setAttribute("clubs", clubsFiltred);
					request.setAttribute("zoneGeo", "Departement");
					
					// On passe à la jsp la latitude et la longitude de la ville 
					request.setAttribute("lat", lat);
					request.setAttribute("lon", lon);

				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}	
		
		
		// Affichage de tous les clubs d'un département
		
		if( searchVille == null && searchRegion == null && searchFederation == null) { // On vérifie qu'aucune ville ni aucune région
																						// n'est sélectionné
			
			try {

				if (searchDepartement != null && !searchDepartement.trim().isEmpty()) {
					DAOClub dao = new DAOClub();
					double lat = 0;
					double lon = 0;
					
					// System.out.println(">> Appel à searchClubByVille en cours");
					List<ClubDAO> clubs = dao.searchClubByDepartement(searchDepartement);
					
					// On récupère la latitude et la longitude du département depuis la liste
					for (Departement d : departements) {
						if(d.getNom().equals(searchDepartement)) {
							 lat = d.getLat();
							 lon = d.getLon();
						}
					}
					
//					for(ClubDAO club : clubsFiltred) {
//						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//						+" / "+ club.getLat() +" / "+ club.getLon());
//					}
					
					request.setAttribute("clubs", clubs);
					request.setAttribute("zoneGeo", "Departement");
					
					// On passe à la jsp la latitude et la longitude de la ville 
					request.setAttribute("lat", lat);
					request.setAttribute("lon", lon);

				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}		
		
		
		// Affichage de tous les clubs d'une région par fédération
		
		if( searchVille == null && searchDepartement == null) { // On vérifie qu'aucun département ni aucune région
																// n'est sélectionné
			
			try {

				if (searchFederation != null && !searchFederation.trim().isEmpty() 
						&& searchRegion != null && !searchRegion.trim().isEmpty()) {
					DAOClub dao = new DAOClub();
					double lat = 0;
					double lon = 0;
					
					List<ClubDAO> clubsFiltred = new ArrayList<>();
					// System.out.println(">> Appel à searchClubByREgion en cours");
					List<ClubDAO> clubs = dao.searchClubByRegion(searchRegion);
					
					// On récupère la latitude et la longitude du département depuis la liste
					for (Region r : regions) {
						if(r.getNom().equals(searchRegion)) {
							 lat = r.getLat();
							 lon = r.getLon();
						}
					}

					
					for(ClubDAO club : clubs) {
						if (club.getFederation().equals(searchFederation.trim())) {
							clubsFiltred.add(club);
						}
					}
					
//					for(ClubDAO club : clubsFiltred) {
//						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//						+" / "+ club.getLat() +" / "+ club.getLon());
//					}
					
					request.setAttribute("clubs", clubsFiltred);
					request.setAttribute("zoneGeo", "Region");
					
					// On passe à la jsp la latitude et la longitude de la ville 
					request.setAttribute("lat", lat);
					request.setAttribute("lon", lon);

				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}			
		
		// Affichage de tous les clubs d'une région 
		
		if( searchVille == null && searchDepartement == null && searchFederation == null) { // On vérifie qu'aucun département ni aucune région
																// n'est sélectionné
			
			try {

				if (searchRegion != null && !searchRegion.trim().isEmpty()) {
					DAOClub dao = new DAOClub();
					double lat = 0;
					double lon = 0;
					
//					 System.out.println(">> Appel à searchClubByREgion en cours");
					List<ClubDAO> clubs = dao.searchClubByRegion(searchRegion);
					
					// On récupère la latitude et la longitude du département depuis la liste
					for (Region r : regions) {
						if(r.getNom().equals(searchRegion)) {
							 lat = r.getLat();
							 lon = r.getLon();
						}
					}
					
//					for(ClubDAO club : clubs) {
//						System.out.println("Ok");
//					}
					
					request.setAttribute("clubs", clubs);
					request.setAttribute("zoneGeo", "Region");
					
					// On passe à la jsp la latitude et la longitude de la ville 
					request.setAttribute("lat", lat);
					request.setAttribute("lon", lon);

				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}
		
		
		
//		// Recherche d'un club par ville 
//		try {
//
//			// System.out.println(">> Condition searchQuery != null && !empty : " + searchQuery);
//
//			if (searchVille != null && !searchVille.trim().isEmpty() ) {
//				DAOClub dao = new DAOClub();
//				// System.out.println(">> Appel à searchClubByVille en cours");
//				ClubDAO club = dao.searchClubByVille(searchVille);
//				
//				if(club != null) {
//		        request.setAttribute("commune", club.getCommune());
//		        request.setAttribute("lat", club.getLat());
//		        request.setAttribute("lon", club.getLon());
//		        // System.out.println("Données club récupérées : " + club.getLat() + ", " + club.getLon());
//				}
//
//			}
//
//		} catch (SQLException | ClassNotFoundException e) {
//			e.printStackTrace();
//			request.setAttribute("error", "Erreur lors de la récupération des données.");
//		}
		

		
//		System.out.println("Attributs envoyés à la JSP : " + 
//			    request.getAttribute("lat") + ", " + 
//			    request.getAttribute("lon") + ", " + 
//			    request.getAttribute("commune"));
		
        // Forward vers la JSP
        request.getRequestDispatcher("/WEB-INF/Carte.jsp").forward(request, response);
	}

}
