package fr.esigelec.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import fr.esigelec.dao.ClassementCommuneDAO;
import fr.esigelec.dao.ClassementRegionDAO;
import fr.esigelec.models.ClassementCommune;
import fr.esigelec.models.ClassementRegion;

/**
 * Servlet implementation class ClassementFiltres
 */
@WebServlet("/ClassementFiltres")
public class ClassementFiltres extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/club_sport")
	DataSource dataSource;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassementFiltres() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vues/elu/classementFiltres.jsp");
		dispatcher.forward(request, response);
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String choix = request.getParameter("choix");
		if(choix != null && !choix.isBlank()) {
			HttpSession session = request.getSession();
			String codeCommune = request.getParameter("commune");
			String codeDepartement = request.getParameter("departement");
			String codeRegion = request.getParameter("region");
			String codeFederation = request.getParameter("federation");
			String age = request.getParameter("age");
			String genre = request.getParameter("genre");
			ArrayList<String> elementsWHERE = new ArrayList<>();
			HashMap<String,String> emplacements = new HashMap<>();
			String critereFederation = null,critereZoneGeo = null,critereAgeGenre=null,clauseWHERE="";
			switch(choix) {
			case "commune":
				
				if(codeFederation != null && !codeFederation.isBlank() && !codeFederation.equals("all")) {
					//recherche par fédération
					critereFederation = "code_federation = "+codeFederation;
					elementsWHERE.add(critereFederation);
				}
				if(codeDepartement != null && !codeDepartement.isBlank() && !codeDepartement.equals("all")) {
					critereZoneGeo = "commune.code_departement = "+codeDepartement;
					elementsWHERE.add(critereZoneGeo);
				}
				else if(codeRegion != null && !codeRegion.isBlank() && !codeRegion.equals("all")) {
					critereZoneGeo = "departement.code_region = "+codeRegion;
					elementsWHERE.add(critereZoneGeo);
				}
				System.out.println("code fed : "+codeFederation);
				
				if(genre != null) {
					switch(genre) {
					case "homme":
						if(age != null) {
							
							switch(age) {
							case "moins15":
								critereAgeGenre = ClassementCommuneDAO.HOMMES_MOINS_15;
								break;
							case "15_24":
								critereAgeGenre = ClassementCommuneDAO.HOMMES_15_24;
								break;
							case "35_49":
								critereAgeGenre = ClassementCommuneDAO.HOMMES_35_49;
								break;
							case "50_79":
								critereAgeGenre = ClassementCommuneDAO.HOMMES_50_79;
								break;
							case "80plus":
								critereAgeGenre = ClassementCommuneDAO.HOMMES_80_PLUS;
								break;
							default:
								critereAgeGenre = ClassementCommuneDAO.HOMMES_TOTAL;
							}
						}
						break;
					case "femme":
						switch(age) {
						case "moins15":
							critereAgeGenre = ClassementCommuneDAO.FEMMES_MOINS_15;
							break;
						case "15_24":
							critereAgeGenre = ClassementCommuneDAO.FEMMES_15_24;
							break;
						case "35_49":
							critereAgeGenre = ClassementCommuneDAO.FEMMES_35_49;
							break;
						case "50_79":
							critereAgeGenre = ClassementCommuneDAO.FEMMES_50_79;
							break;
						case "80plus":
							critereAgeGenre = ClassementCommuneDAO.FEMMES_80_PLUS;
							break;
						default:
							critereAgeGenre = ClassementCommuneDAO.FEMMES_TOTAL;
						}
						break;
					default :
						switch(age) {
						case "moins15":
							critereAgeGenre = ClassementCommuneDAO.TOTAL_MOINS_15;
							break;
						case "15_24":
							critereAgeGenre = ClassementCommuneDAO.TOTAL_15_24;
							break;
						case "35_49":
							critereAgeGenre = ClassementCommuneDAO.TOTAL_35_49;
							break;
						case "50_79":
							critereAgeGenre = ClassementCommuneDAO.TOTAL_50_79;
							break;
						case "80plus":
							critereAgeGenre = ClassementCommuneDAO.TOTAL_80_PLUS;
							break;
						default:
							critereAgeGenre = ClassementCommuneDAO.TOTAL;
						}
					}
				}
				else {
					critereAgeGenre = ClassementCommuneDAO.TOTAL;
				}
				
				if(elementsWHERE.size() == 0) {
					clauseWHERE = "";
				}
				else {
					clauseWHERE = " WHERE ";
					for(int i =0;i<elementsWHERE.size();i++) {
						clauseWHERE += elementsWHERE.get(i) + " ";
						if(i < elementsWHERE.size()-1) {
							clauseWHERE += " AND ";
						}
					}
				}
				
				ClassementCommuneDAO classementCommuneDAO = new ClassementCommuneDAO(dataSource);
				ArrayList<ClassementCommune> classementCommunes = classementCommuneDAO.getClassementToutCritere(1, clauseWHERE, critereAgeGenre);
				
				
				
				emplacements.put("etatBtnPrecedent", "disabled");
				emplacements.put("etatBtnPageA", "active");
				emplacements.put("etatBtnPageB", "");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "2");
				emplacements.put("pageC", "3");
				emplacements.put("pageD", "4");
				emplacements.put("pageE", "5");
				emplacements.put("pageF", "...");
				int nbPages = classementCommuneDAO.getNombrePagesToutCritere(clauseWHERE, critereAgeGenre) / 25;
				nbPages +=1;
				emplacements.put("pageG", String.valueOf(nbPages));
				
				
				request.setAttribute("classement", classementCommunes);
				request.setAttribute("choix", "commune");
				/*request.setAttribute("clauseWHERE", clauseWHERE);
				request.setAttribute("critereAgeGenre", critereAgeGenre);*/
				
				session.setAttribute("clauseWHERE", clauseWHERE);
				session.setAttribute("critereAgeGenre", critereAgeGenre);
				
				
				break;
			case "departement":
				break;
			case "region":
				if(codeFederation != null && !codeFederation.isBlank() && !codeFederation.equals("all")) {
					//recherche par fédération
					critereFederation = "code_federation = "+codeFederation;
					elementsWHERE.add(critereFederation);
				}
				if(genre != null) {
					switch(genre) {
					case "homme":
						if(age != null) {
							
							switch(age) {
							case "moins15":
								critereAgeGenre = ClassementRegionDAO.HOMMES_MOINS_15;
								break;
							case "15_24":
								critereAgeGenre = ClassementRegionDAO.HOMMES_15_24;
								break;
							case "35_49":
								critereAgeGenre = ClassementRegionDAO.HOMMES_35_49;
								break;
							case "50_79":
								critereAgeGenre = ClassementRegionDAO.HOMMES_50_79;
								break;
							case "80plus":
								critereAgeGenre = ClassementRegionDAO.HOMMES_80_PLUS;
								break;
							default:
								critereAgeGenre = ClassementRegionDAO.HOMMES_TOTAL;
							}
						}
						break;
					case "femme":
						switch(age) {
						case "moins15":
							critereAgeGenre = ClassementRegionDAO.FEMMES_MOINS_15;
							break;
						case "15_24":
							critereAgeGenre = ClassementRegionDAO.FEMMES_15_24;
							break;
						case "35_49":
							critereAgeGenre = ClassementRegionDAO.FEMMES_35_49;
							break;
						case "50_79":
							critereAgeGenre = ClassementRegionDAO.FEMMES_50_79;
							break;
						case "80plus":
							critereAgeGenre = ClassementRegionDAO.FEMMES_80_PLUS;
							break;
						default:
							critereAgeGenre = ClassementRegionDAO.FEMMES_TOTAL;
						}
						break;
					default :
						switch(age) {
						case "moins15":
							critereAgeGenre = ClassementRegionDAO.TOTAL_MOINS_15;
							break;
						case "15_24":
							critereAgeGenre = ClassementRegionDAO.TOTAL_15_24;
							break;
						case "35_49":
							critereAgeGenre = ClassementRegionDAO.TOTAL_35_49;
							break;
						case "50_79":
							critereAgeGenre = ClassementRegionDAO.TOTAL_50_79;
							break;
						case "80plus":
							critereAgeGenre = ClassementRegionDAO.TOTAL_80_PLUS;
							break;
						default:
							critereAgeGenre = ClassementRegionDAO.TOTAL;
						}
					}
				}
				else {
					critereAgeGenre = ClassementRegionDAO.TOTAL;
				}
				
				if(elementsWHERE.size() == 0) {
					clauseWHERE = "";
				}
				else {
					clauseWHERE = " WHERE ";
					for(int i =0;i<elementsWHERE.size();i++) {
						clauseWHERE += elementsWHERE.get(i) + " ";
						if(i < elementsWHERE.size()-1) {
							clauseWHERE += " AND ";
						}
					}
				}
				
				ClassementRegionDAO classementRegionDAO = new ClassementRegionDAO(dataSource);
				ArrayList<ClassementRegion> classementRegions = classementRegionDAO.getClassementToutCritere(1, clauseWHERE, critereAgeGenre);
			
				
				
				request.setAttribute("classement", classementRegions);
				request.setAttribute("choix", "region");
				break;
			default:
				
			}
			emplacements.put("pageActuelle", "1");
			
			request.setAttribute("commune", codeCommune);
			request.setAttribute("departement", codeDepartement);
			request.setAttribute("region", codeRegion);
			request.setAttribute("age", age);
			request.setAttribute("genre", genre);
			
			request.setAttribute("variableRang", 0);
			request.setAttribute("emplacements", emplacements);
			request.setAttribute("provenance", "classementToutCritere");
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vues/elu/ClassementZone.jsp");
		dispatcher.forward(request, response);
	}

}
