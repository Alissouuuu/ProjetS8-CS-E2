package fr.esigelec.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

import java.util.*;

import javax.sql.DataSource;

import java.io.*;
import fr.esigelec.dao.*;
import fr.esigelec.models.Commune;
import fr.esigelec.models.Departement;
import fr.esigelec.models.Federation;
import fr.esigelec.models.Region;
import fr.esigelec.modelCarte.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class IndexVisiteurAPIServlet
 */
@WebServlet("/IndexVisiteurAPIServlet")
public class IndexVisiteurAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/club_sport")
	private DataSource dataSource;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexVisiteurAPIServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Passage des listes de Communes/Régions/Département/Fédération pour les dropdow en json
		// On récupère les valaurs des recherches

		String searchVille = request.getParameter("searchVille");
		String searchDepartement = request.getParameter("searchDepartement");
		String searchRegion = request.getParameter("searchRegion");
		String searchFederation = request.getParameter("searchFederation");
		String searchRayon = request.getParameter("rayon");
		
		//System.out.println(searchVille + searchDepartement + searchRegion + searchFederation + searchRayon);
		
		String zoneGeo = "";
		double rayon = 0.0;
		double lat = 46;
		double lon = 2;
		
		List<ClubCarte> clubs = new ArrayList<>();
	    List<String> communesList = new ArrayList<>();
	    List<String> regionsList = new ArrayList<>();
	    List<String> departementsList = new ArrayList<>();
	    List<String> federationsList = new ArrayList<>();
	    
	    boolean useGeoLoc = "true".equals(request.getParameter("useGeoLoc"));
	    
	    if (useGeoLoc) {
	        lat = Double.parseDouble(request.getParameter("lat"));
	        lon = Double.parseDouble(request.getParameter("lon"));
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
		
		// On créer une liste de départements pour pouvoir gérer le centrage de la carte
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
		            double latList = Double.parseDouble(parts[1]);
		            double lonList = Double.parseDouble(parts[2]);
		            departements.add(new DepartementCarte(name, latList, lonList));
		        }
		    }

		} catch (IOException ex) {
		    ex.printStackTrace();
		}
		
		// On créer une liste de régions pour pouvoir gérer le centrage de la carte
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
		            double latList = Double.parseDouble(parts[1]);
		            double lonList = Double.parseDouble(parts[2]);
		            regions.add(new RegionCarte(name, latList, lonList));
		        }
		    }

		} catch (IOException ex) {
		    ex.printStackTrace();
		}
		
		
	    try {
	    	
	    	// Appel des DAO
			//Libelle_villeDAO libelle_villeDAO = new Libelle_villeDAO();
			RegionDAO regionDAO = new RegionDAO(dataSource);
			DepartementDAO departementDAO = new DepartementDAO(dataSource);
			FederationDAO federationDAO = new FederationDAO(dataSource);
			CommuneDAO communeDAO = new CommuneDAO(dataSource);
			
			// Récupérations des listes
		    //List<String> communesList = libelle_villeDAO.getListCommune();
		    //List<String> regionsList = regionDAO.getListRegion();
		    //List<String> departementsList = departementDAO.getListDepartement();
		    //List<String> federationsList = federationDAO.getListFederation();
		    
		    ArrayList<Commune> communesListObjects = communeDAO.getCommunes();
		    ArrayList<Region> regionsListObjects = regionDAO.getRegions();
		    ArrayList<Departement> departementsListObjects = departementDAO.getDepartements();
		    ArrayList<Federation> federationsListObjects = federationDAO.getFederations();
		    
		    for(Commune c : communesListObjects) {
		    	communesList.add(c.getNom());
		    }
		    for(Region r : regionsListObjects) {
		    	regionsList.add(r.getNom());
		    }
		    for(Departement d : departementsListObjects) {
		    	departementsList.add(d.getNom());
		    }
		    for(Federation f : federationsListObjects) {
		    	federationsList.add(f.getNom());
		    }
		    
//		    for (String commune : communes) {
//		    	System.out.println(commune);
//		    }
//		    for (String departement : departementsList) {
//		    	System.out.println(departement);
//		    }
//		    for (String region : regions) {
//		    	System.out.println(region);
//		    }
//		    for (String federation : federations) {
//		    	System.out.println(federation);
//		    }
		    
	
	    } catch (Exception e) {
	        e.printStackTrace(); // Pour voir dans la console du serveur
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
	    }
	    
		
	    // Affichage de tous les clubs de France par fédération
		if(searchVille == null && searchDepartement == null && searchRegion == null 
				&& searchFederation != null && rayon == 0) {
			zoneGeo = "France";
			try {

				if (searchFederation != null && !searchFederation.trim().isEmpty() ) {
					ClubCarteDAO dao = new ClubCarteDAO(dataSource);
					// System.out.println(">> Appel à searchClubByFederation en cours");
					clubs = dao.searchClubByFederation(searchFederation);
					
//					for(ClubCarte club : clubs) {
//						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//						+" / "+ club.getLat() +" / "+ club.getLon());
//					}
				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
		}
		
		// Affichage de tous les clubs d'une ville par fédération
		if( searchDepartement == null && searchRegion == null 
				&& searchVille != null && rayon == 0) { // On vérifie qu'aucun département ni aucune région
																// n'est sélectionné
			zoneGeo = "Ville";
			try {

				if (searchFederation != null && !searchFederation.trim().isEmpty() 
						&& searchVille != null && !searchVille.trim().isEmpty()) {
					ClubCarteDAO dao = new ClubCarteDAO(dataSource);
					List<ClubCarte> clubsDAO = new ArrayList<>();
					// System.out.println(">> Appel à searchClubByVille en cours");
					clubsDAO = dao.searchClubByVille(searchVille);
					
					lat = clubsDAO.get(0).getLat();
					lon = clubsDAO.get(0).getLon();
					
					for(ClubCarte club : clubsDAO) {
						if (club.getFederation().equals(searchFederation.trim())) {
							clubs.add(club);
						}
					}
					
//					for(Club club : clubsFiltred) {
//						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//						+" / "+ club.getLat() +" / "+ club.getLon());
//					}

				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
		}
		
		// Affichage de tous les clubs d'une ville
		if( searchDepartement == null && searchRegion == null && searchFederation == null 
				&& searchVille != null && rayon == 0) { 
																// On vérifie qu'aucun département ni aucune région
																// n'est sélectionné
			zoneGeo = "Ville";
			try {

				if (searchVille != null && !searchVille.trim().isEmpty()) {
					ClubCarteDAO dao = new ClubCarteDAO(dataSource);
					// System.out.println(">> Appel à searchClubByVille en cours");
					clubs = dao.searchClubByVille(searchVille);
					
					lat = clubs.get(0).getLat();
					lon = clubs.get(0).getLon();
					
					
//					for(Club club : clubsFiltred) {
//						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//						+" / "+ club.getLat() +" / "+ club.getLon());
//					}
				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}
		
		if(rayon != 0) {
			
			// Affichage de tous les clubs d'une ville par fédération avec rayon
			if( searchDepartement == null && searchRegion == null 
					&& searchVille != null && useGeoLoc == false) { // On vérifie qu'aucun département ni aucune région
																	// n'est sélectionné
				zoneGeo = "Ville";
				try {

					if (searchFederation != null && !searchFederation.trim().isEmpty() 
							&& searchVille != null && !searchVille.trim().isEmpty()) {
						ClubCarteDAO dao = new ClubCarteDAO(dataSource);
						List<ClubCarte> clubsDAO = new ArrayList<>();
						// System.out.println(">> Appel à searchClubByVille en cours");
						clubsDAO = dao.searchClubByRayon(searchVille, rayon);
						
						lat = clubsDAO.get(0).getLat();
						lon = clubsDAO.get(0).getLon();
						
						for(ClubCarte club : clubsDAO) {
							if (club.getFederation().equals(searchFederation.trim())) {
								clubs.add(club);
							}
						}
						
//						for(Club club : clubsFiltred) {
//							System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//							+" / "+ club.getLat() +" / "+ club.getLon());
//						}
						


					}

				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
					System.out.println("Erreur lors de la récupèration des données");
				}
				
			}
			
			// Affichage de tous les clubs d'une ville avec rayon
			if( searchDepartement == null && searchRegion == null && searchFederation == null 
					&& searchVille != null && useGeoLoc == false) { // On vérifie qu'aucun département ni aucune région
																	// n'est sélectionné
				zoneGeo = "Ville";
				try {

					if (searchVille != null && !searchVille.trim().isEmpty()) {
						ClubCarteDAO dao = new ClubCarteDAO(dataSource);
						// System.out.println(">> Appel à searchClubByVille en cours");
						clubs = dao.searchClubByRayon(searchVille, rayon);
						
						lat = clubs.get(0).getLat();
						lon = clubs.get(0).getLon();
						
						
//						for(Club club : clubsFiltred) {
//							System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//							+" / "+ club.getLat() +" / "+ club.getLon());
//						}
					}

				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
					System.out.println("Erreur lors de la récupèration des données");
				}
				
			}
			
			
			// Autour de sois
			if( searchDepartement == null && searchRegion == null && searchFederation == null && useGeoLoc == true) { // On vérifie qu'aucun département ni aucune région
							// n'est sélectionné
			zoneGeo = "GeoLoc";
			try {
				ClubCarteDAO dao = new ClubCarteDAO(dataSource);
					// System.out.println(">> Appel à searchClubByVille en cours");
					clubs = dao.searchClubByRayonUseGeoLoc(lat,lon, rayon);
		
					//for(Club club : clubsFiltred) {
					//System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
					//+" / "+ club.getLat() +" / "+ club.getLon());
					//}

				
				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
					System.out.println("Erreur lors de la récupèration des données");
				}
			
			}
			
			// Autour de sois par fédération avec rayon
			if( searchDepartement == null && searchRegion == null && useGeoLoc == true) { // On vérifie qu'aucun département ni aucune région
																	// n'est sélectionné
				zoneGeo = "GeoLoc";
				try {

					if (searchFederation != null && !searchFederation.trim().isEmpty() 
							&& searchVille != null && !searchVille.trim().isEmpty()) {
						ClubCarteDAO dao = new ClubCarteDAO(dataSource);
						List<ClubCarte> clubsDAO = new ArrayList<>();
						// System.out.println(">> Appel à searchClubByVille en cours");
						clubsDAO = dao.searchClubByRayon(searchVille, rayon);
						
						lat = clubsDAO.get(0).getLat();
						lon = clubsDAO.get(0).getLon();
						
						for(ClubCarte club : clubsDAO) {
							if (club.getFederation().equals(searchFederation.trim())) {
								clubs.add(club);
							}
						}
						
//						for(Club club : clubsFiltred) {
//							System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//							+" / "+ club.getLat() +" / "+ club.getLon());
//						}
						


					}

				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
					System.out.println("Erreur lors de la récupèration des données");
				}
				
			}
		}
		
		// Affichage de tous les clubs d'un département par fédération
		if( searchVille == null && searchRegion == null && searchDepartement != null) { // On vérifie qu'aucune ville ni aucune région
																// n'est sélectionné	
			zoneGeo = "Departement";
			try {

				if (searchFederation != null && !searchFederation.trim().isEmpty() 
						&& searchDepartement != null && !searchDepartement.trim().isEmpty()) {
					ClubCarteDAO dao = new ClubCarteDAO(dataSource);
					
					List<ClubCarte> clubsDAO = new ArrayList<>();
					// System.out.println(">> Appel à searchClubByVille en cours");
					clubsDAO = dao.searchClubByDepartement(searchDepartement);
					
					// On récupère la latitude et la longitude du département depuis la liste
					for (DepartementCarte d : departements) {
						if(d.getNom().equals(searchDepartement)) {
							 lat = d.getLat();
							 lon = d.getLon();
						}
					}

					
					for(ClubCarte club : clubsDAO) {
						if (club.getFederation().equals(searchFederation.trim())) {
							clubs.add(club);
						}
					}
					
//					for(Club club : clubsFiltred) {
//						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//						+" / "+ club.getLat() +" / "+ club.getLon());
//					}
					
				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}	
		
		
		// Affichage de tous les clubs d'un département
		if( searchVille == null && searchRegion == null && searchFederation == null && searchDepartement != null) { // On vérifie qu'aucune ville ni aucune région
																						// n'est sélectionné
			zoneGeo = "Departement";
			try {

				if (searchDepartement != null && !searchDepartement.trim().isEmpty()) {
					ClubCarteDAO dao = new ClubCarteDAO(dataSource);
					// System.out.println(">> Appel à searchClubByVille en cours");
					clubs = dao.searchClubByDepartement(searchDepartement);
					
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

				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}		
		
		
		// Affichage de tous les clubs d'une région par fédération
		if( searchVille == null && searchDepartement == null && searchRegion != null ) { // On vérifie qu'aucun département ni aucune région
																// n'est sélectionné
			zoneGeo = "Region";
			try {

				if (searchFederation != null && !searchFederation.trim().isEmpty() 
						&& searchRegion != null && !searchRegion.trim().isEmpty()) {
					ClubCarteDAO dao = new ClubCarteDAO(dataSource);
					
					List<ClubCarte> clubsDAO = new ArrayList<>();
					// System.out.println(">> Appel à searchClubByREgion en cours");
					clubsDAO = dao.searchClubByRegion(searchRegion);
					
					// On récupère la latitude et la longitude du département depuis la liste
					for (RegionCarte r : regions) {
						if(r.getNom().equals(searchRegion)) {
							 lat = r.getLat();
							 lon = r.getLon();
						}
					}

					
					for(ClubCarte club : clubsDAO) {
						if (club.getFederation().equals(searchFederation.trim())) {
							clubs.add(club);
						}
					}
					
//					for(Club club : clubsFiltred) {
//						System.out.println(club.getLibelle_club() +" / "+  club.getCommune()
//						+" / "+ club.getLat() +" / "+ club.getLon());
//					}

				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}			
		
		// Affichage de tous les clubs d'une région 
		
		if( searchVille == null && searchDepartement == null && searchFederation == null && searchRegion != null) { // On vérifie qu'aucun département ni aucune région
																// n'est sélectionné
			zoneGeo = "Region";
			try {

				if (searchRegion != null && !searchRegion.trim().isEmpty()) {
					ClubCarteDAO dao = new ClubCarteDAO(dataSource);
//					 System.out.println(">> Appel à searchClubByREgion en cours");
					clubs = dao.searchClubByRegion(searchRegion);
					
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

				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Erreur lors de la récupèration des données");
			}
			
		}
		
		
		// Création de l’objet JSON global
		Gson gson = new Gson();
		JsonObject responseObject = new JsonObject();

		// Ajoute la liste dans l’objet
		responseObject.addProperty("zoneGeo", zoneGeo);
		responseObject.addProperty("lat", lat);
		responseObject.addProperty("lon", lon);
		responseObject.add("listeClubs", gson.toJsonTree(clubs));
		responseObject.add("listeCommune", gson.toJsonTree(communesList));
		responseObject.add("listeDepartement", gson.toJsonTree(departementsList));
		responseObject.add("listeRegion", gson.toJsonTree(regionsList));
		responseObject.add("listeFederation", gson.toJsonTree(federationsList));
	
		// Préparation de la réponse
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		out.print(gson.toJson(responseObject));
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
