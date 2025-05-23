package fr.esigelec.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import javax.sql.DataSource;

import fr.esigelec.dao.DepartementDAO;
import fr.esigelec.dao.FederationDAO;
import fr.esigelec.dao.RegionDAO;
import fr.esigelec.models.Departement;
import fr.esigelec.models.Federation;
import fr.esigelec.models.Region;

/**
 * Servlet implementation class Accueil
 */
@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/club_sport")
	private DataSource dataSource;
	private RegionDAO regionDAO;
	private DepartementDAO departementDAO;
	private FederationDAO federationDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Accueil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		regionDAO = new RegionDAO(dataSource);
		departementDAO = new DepartementDAO(dataSource);
		federationDAO = new FederationDAO(dataSource);
		ArrayList<Region> regions = regionDAO.getRegions();
		ArrayList<Departement> departements = departementDAO.getDepartements();
		ArrayList<Federation> federations = federationDAO.getFederations();
		
		request.setAttribute("regions", regions);
		request.setAttribute("departements", departements);
		request.setAttribute("federations", federations);
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("./Index.jsp");
		dispatcher.forward(request, response);
		
	}

}
