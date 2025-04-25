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
		ArrayList<Club> clubsTout = new ArrayList<>();
		ArrayList<Club> clubsRouen = new ArrayList<>();
		ArrayList<Club> clubsHavre = new ArrayList<>();
		ArrayList<Club> clubsCaen = new ArrayList<>();
		ArrayList<Club> clubsParis = new ArrayList<>();
		ArrayList<Club> clubsEvry = new ArrayList<>();
		ArrayList<Club> clubsLyon = new ArrayList<>();
		ArrayList<Club> clubsSeine = new ArrayList<>();
		ArrayList<Club> clubsCalvados = new ArrayList<>();
		ArrayList<Club> clubsRhone = new ArrayList<>();
		ArrayList<Club> clubsNormandie = new ArrayList<>();
		ArrayList<Club> clubsRhoneAlpes = new ArrayList<>();
		ArrayList<Club> clubsIDF = new ArrayList<>();
		//à suivre...  il faut faire des listes pour tous les cas de filtres (ville,departement,region,federation) afin de s'en servir dans un switch ou des if
		
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
		
		

		
	}

}
