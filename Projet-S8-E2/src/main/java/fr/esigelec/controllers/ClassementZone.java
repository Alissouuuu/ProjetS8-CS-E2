package fr.esigelec.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.text.StringEscapeUtils;

import fr.esigelec.dao.ClassementCommuneDAO;
import fr.esigelec.dao.ClassementRegionDAO;
import fr.esigelec.models.ClassementCommune;
import fr.esigelec.models.ClassementRegion;

/**
 * Servlet implementation class Classement
 */
@WebServlet("/ClassementZone")
public class ClassementZone extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassementRegionDAO classementRegionDAO;
	private ClassementCommuneDAO classementCommuneDAO;
	@Resource(name="jdbc/club_sport")
	private DataSource dataSource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassementZone() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String valeurString = null,casActuel=null;
		int nbPages=1395,valeurInt=1,ancienneValeurInt,ecartDroite,ecartGauche,valeurIntMoinsUn,valeurIntMoinsDeux,valeurIntMoinsTrois,valeurIntPlusUn,valeurIntPlusDeux;
		//HashMap<String,String> emplacements = new HashMap<>();
		
		String provenance = (String) request.getParameter("provenance");
		HttpSession session = request.getSession();
		classementCommuneDAO = new ClassementCommuneDAO(dataSource);
		ArrayList<ClassementCommune> classementCommune = null;
		
		if(provenance.equals("classementToutCritere")) {
			String clauseWHERE = (String) session.getAttribute("clauseWHERE");
			String critereAgeGenre = (String) session.getAttribute("critereAgeGenre");
			
			nbPages = classementCommuneDAO.getNombrePagesToutCritere(clauseWHERE, critereAgeGenre) / 25;
		}
		else {
			nbPages = classementCommuneDAO.getNombrePages() / 25;
		}
		System.out.println("Nombre pages"+nbPages);
		
		nbPages+=1;
		
		String action = null;
		String param = null;

		// Récupération des paramètres envoyés par les boutons
		if (request.getParameter("suivant") != null) {
		    action = "suivant";
		    param = request.getParameter("suivant"); // contient la page actuelle
		} else if (request.getParameter("precedent") != null) {
		    action = "precedent";
		    param = request.getParameter("precedent"); // contient la page actuelle
		} else {
		    // Recherche du bouton cliqué parmi les boutons de page
		    String[] boutons = {"a", "b", "c", "d", "e", "f", "g"};
		    for (String bouton : boutons) {
		        if (request.getParameter(bouton) != null && !request.getParameter(bouton).equals("...")) {
		            action = "page";
		            param = request.getParameter(bouton);
		            break;
		        }
		    }
		}

		// Appel à ta fonction
		HashMap<String, String> emplacements = calculerPagination(nbPages,action, param);
		valeurInt = Integer.parseInt(emplacements.get("pageActuelle")); 
		if(provenance.equals("classementToutCritere")) {
			String clauseWHERE = (String) session.getAttribute("clauseWHERE");
			String critereAgeGenre = (String) session.getAttribute("critereAgeGenre");
			
			classementCommune = classementCommuneDAO.getClassementToutCritere(valeurInt,clauseWHERE,critereAgeGenre);
		}
		else {
			classementCommune = classementCommuneDAO.getClassement(valeurInt);
		}
			
		String choix = "commune";
		request.setAttribute("classement", classementCommune);
		request.setAttribute("choix", choix);
		request.setAttribute("emplacements", emplacements);
		int variableRang = (Integer.parseInt(emplacements.get("pageActuelle"))-1)*25;
		request.setAttribute("variableRang", variableRang);
		request.setAttribute("casActuel", casActuel);
		request.setAttribute("provenance", provenance);
		RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/vues/elu/ClassementZone.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String choix = (String) StringEscapeUtils.escapeHtml4(request.getParameter("choix"));
		int nbPages=1;
		HashMap<String,String> emplacements = new HashMap<>();
		if(choix.equals("commune")) {
			classementCommuneDAO = new ClassementCommuneDAO(dataSource);
			ArrayList<ClassementCommune> classementCommune = classementCommuneDAO.getClassement(1);
			request.setAttribute("classement", classementCommune);
			nbPages = classementCommuneDAO.getNombrePages() / 25;
			nbPages +=1;
		}
		else if(choix.equals("region")) {
			classementRegionDAO = new ClassementRegionDAO(dataSource);
			ArrayList<ClassementRegion> classementRegion = classementRegionDAO.getClassement(1);
			request.setAttribute("classement", classementRegion);
		}
		
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
		
		emplacements.put("pageG", String.valueOf(nbPages));
		emplacements.put("pageActuelle", "1");
		
		request.setAttribute("choix", choix);
		request.setAttribute("provenance", "indexElu");
		request.setAttribute("emplacements", emplacements);
		request.setAttribute("variableRang", 0);
		RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/vues/elu/ClassementZone.jsp");
		dispatcher.forward(request, response);
	}
	
	public static HashMap<String, String> calculerPagination(int totalPages,String action, String param) {
	    HashMap<String, String> emplacements = new HashMap<>();
	    int valeurInt = 1;

	    if (param != null && !param.isEmpty()) {
	        int page = Integer.parseInt(param);
	        switch (action) {
	            case "page":
	                valeurInt = page;
	                break;
	            case "suivant":
	                valeurInt = Math.min(page + 1, totalPages);
	                break;
	            case "precedent":
	                valeurInt = Math.max(page - 1, 1);
	                break;
	        }
	    }

	    String valeurString = String.valueOf(valeurInt);
	    int valeurIntMoinsUn = valeurInt - 1;
	    int valeurIntMoinsDeux = valeurInt - 2;
	    int valeurIntMoinsTrois = valeurInt - 3;
	    int valeurIntPlusUn = valeurInt + 1;
	    int valeurIntPlusDeux = valeurInt + 2;
	    int ecartDroite = totalPages - valeurInt;

	    // Boutons "Suivant" et "Précédent"
	    emplacements.put("etatBtnPrecedent", valeurInt == 1 ? "disabled" : "");
	    emplacements.put("etatBtnSuivant", valeurInt == totalPages ? "disabled" : "");

	    // Cas : Début (1 à 5)
	    if (valeurInt >= 1 && valeurInt <= 5) {
	        emplacements.put("etatBtnPageA", valeurInt == 1 ? "active" : "");
	        emplacements.put("etatBtnPageB", valeurInt == 2 ? "active" : "");
	        emplacements.put("etatBtnPageC", valeurInt == 3 ? "active" : "");
	        emplacements.put("etatBtnPageD", valeurInt == 4 ? "active" : "");
	        emplacements.put("etatBtnPageE", valeurInt == 5 ? "active" : "");
	        emplacements.put("etatBtnPageF", "disabled");
	        emplacements.put("etatBtnPageG", "");
	        emplacements.put("pageA", "1");
	        emplacements.put("pageB", "2");
	        emplacements.put("pageC", "3");
	        emplacements.put("pageD", "4");
	        emplacements.put("pageE", "5");
	        emplacements.put("pageF", "...");
	        emplacements.put("pageG", String.valueOf(totalPages));
	    }
	    // Cas : Dernière page
	    else if (valeurInt == totalPages) {
	        emplacements.put("etatBtnPageA", "");
	        emplacements.put("etatBtnPageB", "disabled");
	        emplacements.put("etatBtnPageC", "");
	        emplacements.put("etatBtnPageD", "");
	        emplacements.put("etatBtnPageE", "");
	        emplacements.put("etatBtnPageF", "disabled");
	        emplacements.put("etatBtnPageG", "active");
	        emplacements.put("pageA", "1");
	        emplacements.put("pageB", "...");
	        emplacements.put("pageC", String.valueOf(valeurIntMoinsTrois));
	        emplacements.put("pageD", String.valueOf(valeurIntMoinsDeux));
	        emplacements.put("pageE", String.valueOf(valeurIntMoinsUn));
	        emplacements.put("pageF", "...");
	        emplacements.put("pageG", String.valueOf(totalPages));
	    }
	    // Cas : Avant-dernière (écart droite = 1)
	    else if (ecartDroite == 1) {
	        emplacements.put("etatBtnPageA", "");
	        emplacements.put("etatBtnPageB", "disabled");
	        emplacements.put("etatBtnPageC", "");
	        emplacements.put("etatBtnPageD", "");
	        emplacements.put("etatBtnPageE", "active");
	        emplacements.put("etatBtnPageF", "disabled");
	        emplacements.put("etatBtnPageG", "");
	        emplacements.put("pageA", "1");
	        emplacements.put("pageB", "...");
	        emplacements.put("pageC", String.valueOf(valeurIntMoinsDeux));
	        emplacements.put("pageD", String.valueOf(valeurIntMoinsUn));
	        emplacements.put("pageE", valeurString);
	        emplacements.put("pageF", "...");
	        emplacements.put("pageG", String.valueOf(totalPages));
	    }
	    // Cas : avant-avant-dernière (écart droite = 2)
	    else if (ecartDroite == 2) {
	        emplacements.put("etatBtnPageA", "");
	        emplacements.put("etatBtnPageB", "disabled");
	        emplacements.put("etatBtnPageC", "");
	        emplacements.put("etatBtnPageD", "active");
	        emplacements.put("etatBtnPageE", "");
	        emplacements.put("etatBtnPageF", "disabled");
	        emplacements.put("etatBtnPageG", "");
	        emplacements.put("pageA", "1");
	        emplacements.put("pageB", "...");
	        emplacements.put("pageC", String.valueOf(valeurIntMoinsUn));
	        emplacements.put("pageD", valeurString);
	        emplacements.put("pageE", String.valueOf(valeurIntPlusUn));
	        emplacements.put("pageF", "...");
	        emplacements.put("pageG", String.valueOf(totalPages));
	    }
	    // Cas par défaut (milieu pagination)
	    else {
	        emplacements.put("etatBtnPageA", "");
	        emplacements.put("etatBtnPageB", "disabled");
	        emplacements.put("etatBtnPageC", "active");
	        emplacements.put("etatBtnPageD", "");
	        emplacements.put("etatBtnPageE", "");
	        emplacements.put("etatBtnPageF", "disabled");
	        emplacements.put("etatBtnPageG", "");
	        emplacements.put("pageA", "1");
	        emplacements.put("pageB", "...");
	        emplacements.put("pageC", valeurString);
	        emplacements.put("pageD", String.valueOf(valeurIntPlusUn));
	        emplacements.put("pageE", String.valueOf(valeurIntPlusDeux));
	        emplacements.put("pageF", "...");
	        emplacements.put("pageG", String.valueOf(totalPages));
	    }

	    emplacements.put("pageActuelle", valeurString);
	    return emplacements;
	}


}
