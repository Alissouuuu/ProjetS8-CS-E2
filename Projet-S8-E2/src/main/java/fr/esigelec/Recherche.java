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

import fr.esigelec.dao.ClubDAO;
import fr.esigelec.dao.DBDAO;
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
		
		//à suivre...  il faut faire des listes pour tous les cas de filtres (ville,departement,region,federation) afin de s'en servir dans un switch ou des if
		String zone = (String) StringEscapeUtils.escapeHtml4(request.getParameter("zone"));
		String nomZone = (String) StringEscapeUtils.escapeHtml4(request.getParameter("nom-zone"));
		String federation = (String) StringEscapeUtils.escapeHtml4(request.getParameter("federation"));
		String requeteMilieu = null;
		if(federation == null || federation.isEmpty() || federation.isBlank())
			federation = "tout";
		if(nomZone == null || nomZone.isEmpty() || nomZone.isBlank())
			zone = "tout";
		
		if(!nomZone.isEmpty() && nomZone != null)
			requeteMilieu = "WHERE code_postal.code_postal="+nomZone;
		
		if(DBDAO.connexion()) {
			ArrayList<Club> clubs = ClubDAO.getClubs(1, requeteMilieu);
			request.setAttribute("zone",zone);
			request.setAttribute("nom-zone",nomZone);
			request.setAttribute("federation",federation);
			request.setAttribute("clubs",clubs);
			DBDAO.deconnexion();
		}
		
		/*HttpSession session = request.getSession();
		session.setAttribute("clubs", clubsTout);*/
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("./Index.jsp");
		dispatcher.forward(request, response);
		//response.sendRedirect("./Index.jsp");
		
		
		
		

		
	}

}
