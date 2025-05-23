package fr.esigelec.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    
    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Passage des listes de Communes/Régions/Département/Fédération pour les dropdow en json
		
		
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
		    
		    ArrayList<Commune> communesList = communeDAO.getCommunes();
		    ArrayList<Region> regionsList = regionDAO.getRegions();
		    ArrayList<Departement> departementsList = departementDAO.getDepartements();
		    ArrayList<Federation> federationsList = federationDAO.getFederations();
		    
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
		    
		    // Passage en Json
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");

		    PrintWriter out = response.getWriter();
		    out.print("{");

		    out.print("\"listeCommune\":[");
		    for (int i = 0; i < communesList.size(); i++) {
		        String nom = communesList.get(i).getNom();
		        out.printf("\"%s\"", escapeJson(nom));
		        if (i < communesList.size() - 1) out.print(",");
		    }
		    out.print("],");

		    out.print("\"listeDepartement\":[");
		    for (int i = 0; i < departementsList.size(); i++) {
		        String nom = departementsList.get(i).getNom();
		        out.printf("\"%s\"", escapeJson(nom));
		        if (i < departementsList.size() - 1) out.print(",");
		    }
		    out.print("],"); 

		    out.print("\"listeRegion\":[");
		    for (int i = 0; i < regionsList.size(); i++) {
		        String nom = regionsList.get(i).getNom().trim();
		        out.printf("\"%s\"", escapeJson(nom));
		        if (i < regionsList.size() - 1) out.print(",");
		    }
		    out.print("],");

		    out.print("\"listeFederation\":[");
		    for (int i = 0; i < federationsList.size(); i++) {
		        String nom = federationsList.get(i).getNom();
		        out.printf("\"%s\"", escapeJson(nom));
		        if (i < federationsList.size() - 1) out.print(",");
		    }
		    out.print("]"); 

		    out.print("}");
		    out.flush();

	
	    } catch (Exception e) {
	        e.printStackTrace(); // Pour voir dans la console du serveur
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
