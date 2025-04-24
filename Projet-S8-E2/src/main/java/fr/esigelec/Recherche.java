package fr.esigelec;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

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
		ArrayList<Club> clubs1 = new ArrayList<>();
		clubs1.add(new Club(1,"football","Rouen","Seine-Maritime","Normandie","FC Rouen","001","76000",50));
		clubs1.add(new Club(1,"basketball","Rouen","Seine-Maritime","Normandie","BasketClub Rouen","002","76000",40));
		clubs1.add(new Club(1,"football","Caen","Calvados","Normandie","FC Caen","001","14118",50));
		clubs1.add(new Club(1,"basketball","Caen","Calvados","Normandie","Basketball Caen","002","14118",60));
		clubs1.add(new Club(1,"football","Rouen","Seine-Maritime","Normandie","FC Rouen","001","76000",50));
		clubs1.add(new Club(1,"football","Rouen","Seine-Maritime","Normandie","FC Rouen","001","76000",50));
		clubs1.add(new Club(1,"football","Rouen","Seine-Maritime","Normandie","FC Rouen","001","76000",50));
		clubs1.add(new Club(1,"football","Rouen","Seine-Maritime","Normandie","FC Rouen","001","76000",50));

		
	}

}
