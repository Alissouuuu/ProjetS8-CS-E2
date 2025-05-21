package contolleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import java.util.*;
import java.io.*;
import dao.*;
import modelCarte.*;
/**
 * Servlet implementation class Carte
 */
@WebServlet("/IndexVisiteurServlet")
public class IndexVisiteurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexVisiteurServlet() {
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
//		System.out.println("Région : " + searchRegionCarte);
//		System.out.println("Fédération : " + searchFederation);
		
		// On créer une liste de département pour pouvoir gérer le centrage de la carte (à mettre dans la BDD)
		List<DepartementCarte> departements = new ArrayList<>();
		
		Properties prd = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("departements.properties")) {
		    if (input == null) {
		        System.out.println("Fichier departements.properties introuvable !");
		        return;
		    }
		    prd.load(input);

		    for (String key : prd.stringPropertyNames()) {
		        String[] parts = prd.getProperty(key).split(";");
		        if (parts.length == 3) {
		            String name = parts[0];
		            double lat = Double.parseDouble(parts[1]);
		            double lon = Double.parseDouble(parts[2]);
		            departements.add(new DepartementCarte(name, lat, lon));
		        }
		    }

		} catch (IOException ex) {
		    ex.printStackTrace();
		}
		
		// On créer une liste de régions pour pouvoir gérer le centrage de la carte (à mettre dans la BDD)
		List<RegionCarte> regions = new ArrayList<>();

		Properties prr = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("regions.properties")) {
		    if (input == null) {
		        System.out.println("Fichier regions.properties introuvable !");
		        return;
		    }
		    prr.load(input);

		    for (String key : prr.stringPropertyNames()) {
		        String[] parts = prr.getProperty(key).split(";");
		        if (parts.length == 3) {
		            String name = parts[0];
		            double lat = Double.parseDouble(parts[1]);
		            double lon = Double.parseDouble(parts[2]);
		            regions.add(new RegionCarte(name, lat, lon));
		        }
		    }

		} catch (IOException ex) {
		    ex.printStackTrace();
		}


		
		
		// On remplace la valeur des recherche par null si elles contiennent le texte par défaut
		if("-- Sélectionner une commune --".equals(searchVille)) {
			searchVille = null;
		}
		
		if ("-- Sélectionner un département --".equals(searchDepartement)) {
			searchDepartement = null;
		}
		
		if ("-- Sélectionner une région --".equals(searchRegion)) {
			searchRegion = null;
		}
		
		if ("-- Sélectionner une fédération --".equals(searchFederation)) {
			searchFederation = null;
		}

		if (searchRayon != null) {
			rayon = Double.parseDouble(searchRayon);
		}
		
		// Déboggage
		request.setAttribute("input", searchVille + searchDepartement + searchRegion + searchFederation + rayon);

//-----------------------------------------------------------------------------------------------------------
		


//------------------------------------------------------------------------------------------------------

		
		// Affichage de tous les clubs de France par fédération
		
		if(searchVille == null && searchDepartement == null && searchRegion == null && rayon == 0) {
			
			try {

				if (searchFederation != null && !searchFederation.trim().isEmpty() ) {
					ClubDAO dao = new ClubDAO();
					System.out.println(">> Appel à searchClubByFederation en cours");
					List<ClubCarte> clubs = dao.searchClubByFederation(searchFederation);
					
					for(ClubCarte club : clubs) {
						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
						+" / "+ club.getLat() +" / "+ club.getLon());
					}
					
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
					ClubDAO dao = new ClubDAO();
					List<ClubCarte> clubsFiltred = new ArrayList<>();
					// System.out.println(">> Appel à searchClubByVille en cours");
					List<ClubCarte> clubs = dao.searchClubByVille(searchVille);
					
					double lat = clubs.get(0).getLat();
					double lon = clubs.get(0).getLon();
					
					for(ClubCarte club : clubs) {
						if (club.getFederation().equals(searchFederation.trim())) {
							clubsFiltred.add(club);
						}
					}
					
//					for(Club club : clubsFiltred) {
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
					ClubDAO dao = new ClubDAO();
					// System.out.println(">> Appel à searchClubByVille en cours");
					List<ClubCarte> clubs = dao.searchClubByVille(searchVille);
					
					double lat = clubs.get(0).getLat();
					double lon = clubs.get(0).getLon();
					
					
//					for(Club club : clubsFiltred) {
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
						ClubDAO dao = new ClubDAO();
						List<ClubCarte> clubsFiltred = new ArrayList<>();
						// System.out.println(">> Appel à searchClubByVille en cours");
						List<ClubCarte> clubs = dao.searchClubByRayon(searchVille, rayon);
						
						double lat = clubs.get(0).getLat();
						double lon = clubs.get(0).getLon();
						
						for(ClubCarte club : clubs) {
							if (club.getFederation().equals(searchFederation.trim())) {
								clubsFiltred.add(club);
							}
						}
						
//						for(Club club : clubsFiltred) {
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
						ClubDAO dao = new ClubDAO();
						// System.out.println(">> Appel à searchClubByVille en cours");
						List<ClubCarte> clubs = dao.searchClubByRayon(searchVille, rayon);
						
						double lat = clubs.get(0).getLat();
						double lon = clubs.get(0).getLon();
						
						
//						for(Club club : clubsFiltred) {
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
					ClubDAO dao = new ClubDAO();
					double lat = 0;
					double lon = 0;
					
					List<ClubCarte> clubsFiltred = new ArrayList<>();
					// System.out.println(">> Appel à searchClubByVille en cours");
					List<ClubCarte> clubs = dao.searchClubByDepartement(searchDepartement);
					
					// On récupère la latitude et la longitude du département depuis la liste
					for (DepartementCarte d : departements) {
						if(d.getNom().equals(searchDepartement)) {
							 lat = d.getLat();
							 lon = d.getLon();
						}
					}

					
					for(ClubCarte club : clubs) {
						if (club.getFederation().equals(searchFederation.trim())) {
							clubsFiltred.add(club);
						}
					}
					
//					for(Club club : clubsFiltred) {
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
					ClubDAO dao = new ClubDAO();
					double lat = 0;
					double lon = 0;
					
					// System.out.println(">> Appel à searchClubByVille en cours");
					List<ClubCarte> clubs = dao.searchClubByDepartement(searchDepartement);
					
					// On récupère la latitude et la longitude du département depuis la liste
					for (DepartementCarte d : departements) {
						if(d.getNom().equals(searchDepartement)) {
							 lat = d.getLat();
							 lon = d.getLon();
						}
					}
					
//					for(Club club : clubsFiltred) {
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
					ClubDAO dao = new ClubDAO();
					double lat = 0;
					double lon = 0;
					
					List<ClubCarte> clubsFiltred = new ArrayList<>();
					// System.out.println(">> Appel à searchClubByREgion en cours");
					List<ClubCarte> clubs = dao.searchClubByRegion(searchRegion);
					
					// On récupère la latitude et la longitude du département depuis la liste
					for (RegionCarte r : regions) {
						if(r.getNom().equals(searchRegion)) {
							 lat = r.getLat();
							 lon = r.getLon();
						}
					}

					
					for(ClubCarte club : clubs) {
						if (club.getFederation().equals(searchFederation.trim())) {
							clubsFiltred.add(club);
						}
					}
					
//					for(Club club : clubsFiltred) {
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
					ClubDAO dao = new ClubDAO();
					double lat = 0;
					double lon = 0;
					
//					 System.out.println(">> Appel à searchClubByREgion en cours");
					List<ClubCarte> clubs = dao.searchClubByRegion(searchRegion);
					
					// On récupère la latitude et la longitude du département depuis la liste
					for (RegionCarte r : regions) {
						if(r.getNom().equals(searchRegion)) {
							 lat = r.getLat();
							 lon = r.getLon();
						}
					}
					
//					for(Club club : clubs) {
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
//				ClubDAO dao = new ClubDAO();
//				// System.out.println(">> Appel à searchClubByVille en cours");
//				Club club = dao.searchClubByVille(searchVille);
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
        request.getRequestDispatcher("/WEB-INF/vues/visiteur/indexVisiteur.jsp").forward(request, response);
        
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    String latStr = request.getParameter("lat");
//	    String lonStr = request.getParameter("lon");
//	    String rayon = request.getParameter("rayon");
//	    double rayonKm = Double.parseDouble(rayon);
//
//	    if (latStr != null && lonStr != null) {
//	        try {
//	        	System.out.println("Dans le try");
//	            double lat = Double.parseDouble(latStr);
//	            double lon = Double.parseDouble(lonStr);
//
//	            ClubDAO clubDAO = new ClubDAO();
//	            List<Club> clubFiltred = clubDAO.searchClubByRayonUseGeoLoc(lat,lon,rayonKm);
//
//	            // Construction de la réponse JSON
//	            response.setContentType("application/json");
//	            response.setCharacterEncoding("UTF-8");
//
//	            PrintWriter out = response.getWriter();
//	            out.print("\"dansLeRayon\":["); // Nom des datas qu'on veut passer 
//	            for (int i = 0; i < clubFiltred.size(); i++) {
//	                Club club = clubFiltred.get(i);
//	                out.print("{");
//	                out.printf("\"nom\":\"%s\",", escapeJson(club.getLibelle_club()));
//	                out.printf("\"lat\":%f,", club.getLat());
//	                out.printf("\"lon\":%f", club.getLon());
//	                out.print("}"); 
//	                if (i < clubFiltred.size() - 1) {
//	                    out.print(",");
//	                }
//	            }
//	            out.print("]");
//	            out.flush();
//
//	        } catch (NumberFormatException | SQLException |ClassNotFoundException e) {
//	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Latitude ou longitude invalide");
//	        }
//	    } else {
//	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Latitude ou longitude manquante");
//	    }
	    
	  //---------------------------------------------------------------------------------------------------------

	    
	    }	    
	
}
