package fr.esigelec.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.sql.DataSource;

import fr.esigelec.dao.CommuneDAO;
import fr.esigelec.models.Commune;

/**
 * Servlet implementation class ChargementCommunes
 */
@WebServlet("/ChargementCommunes")
public class ChargementCommunes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/club_sport")
	DataSource dataSource;
	private CommuneDAO communeDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChargementCommunes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String codeDepartement = request.getParameter("codeDepartement");
		communeDAO = new CommuneDAO(dataSource);
		ArrayList<Commune> communes = communeDAO.getCommunesParDepartement(codeDepartement);
		Commune commune = null;
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print("[");
		
		for(int i=0; i < communes.size();i++) {
			commune = communes.get(i);
			out.print("{\"code\":\""+commune.getCodeCommune()+"\", \"nom\":\""+commune.getNom()+"\"}");
			if(i < communes.size()-1)
				out.print(",");
		}
		out.print("]");
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
