package fr.esigelec;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;

import fr.esigelec.models.Club;

/**
 * Servlet implementation class Recherche
 */
@WebServlet("/Recherche")
public class Recherche extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Recherche() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		ArrayList<Club> clubsTout = new ArrayList<>();
		//à suivre...  il faut faire des listes pour tous les cas de filtres (ville,departement,region,federation) afin de s'en servir dans un switch ou des if
		String zone = (String) StringEscapeUtils.escapeHtml4(request.getParameter("zone"));
		String nomZone = (String) StringEscapeUtils.escapeHtml4(request.getParameter("nom-zone"));
		String federation = (String) StringEscapeUtils.escapeHtml4(request.getParameter("federation"));
		
		if(federation == null || federation.isEmpty() || federation.isBlank())
			federation = "football";
		if(nomZone == null || nomZone.isEmpty() || nomZone.isBlank())
			zone = "tout";
		
		final String nomZoneFinal = nomZone;
		final String federationFinal = federation;
		
		Club club1 = new Club(1,"football","Rouen","Seine-Maritime","Normandie","FC Rouen","001","76000",50);
		Club club2 = new Club(1,"basketball","Rouen","Seine-Maritime","Normandie","BasketClub Rouen","002","76000",40);
		Club club3 = new Club(1,"football","Caen","Calvados","Normandie","FC Caen","001","14118",50);
		Club club4 = new Club(1,"basketball","Caen","Calvados","Normandie","Basketball Caen","002","14118",60);
		Club club5 = new Club(1,"tennis","Paris","Paris","Île-de-France","Tennis Paris","003","75000",100);
		Club club6 = new Club(1,"football","Evry-Courcouronnes","Essonne","Île-de-France","Evry FC","001","91000",50);
		Club club7 = new Club(1,"natation","Le Havre","Seine-Maritime","Normandie","Les Bains des Docks","004","76600",1000);
		Club club8 = new Club(1,"muaythai","Lyon","Rhône","Rhône-Alpes","Muay Lyon","005","69000",35);
		
		
		clubsTout.add(club1);
		clubsTout.add(club2);
		clubsTout.add(club3);
		clubsTout.add(club4);
		clubsTout.add(club5);
		clubsTout.add(club6);
		clubsTout.add(club7);
		clubsTout.add(club8);
		
		switch(zone) {
		case "tout":
			
			break;
		case "commune":
			clubsTout.removeIf(club -> !club.getCommune().equals(nomZoneFinal));
			break;
		case "departement":
			clubsTout.removeIf(club -> !club.getDepartement().equals(nomZoneFinal));
			break;
		case "region":
			clubsTout.removeIf(club -> !club.getRegion().equals(nomZoneFinal));
			break;
		default:
			break;
		}
		
		clubsTout.removeIf(club -> !club.getFederation().equals(federationFinal));
		
		/*HttpSession session = request.getSession();
		session.setAttribute("clubs", clubsTout);*/
		request.setAttribute("zone",zone);
		request.setAttribute("nom-zone",nomZoneFinal);
		request.setAttribute("federation",federationFinal);
		request.setAttribute("clubs",clubsTout);
		RequestDispatcher dispatcher = request.getRequestDispatcher("./Index.jsp");
		dispatcher.forward(request, response);
		//response.sendRedirect("./Index.jsp");
		
		
		
		

		
	}

}
