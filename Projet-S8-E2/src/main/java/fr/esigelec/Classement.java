package fr.esigelec;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;

import fr.esigelec.dao.ClassementCommuneDAO;
import fr.esigelec.dao.ClassementRegionDAO;
import fr.esigelec.dao.DBDAO;
import fr.esigelec.models.ClassementCommune;
import fr.esigelec.models.ClassementRegion;

/**
 * Servlet implementation class Classement
 */
@WebServlet("/Classement")
public class Classement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Classement() {
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
		String choix = (String) StringEscapeUtils.escapeHtml4(request.getParameter("choix"));
		if(DBDAO.connexion()) {
			if(choix.equals("commune")) {
				ArrayList<ClassementCommune> classementCommune = ClassementCommuneDAO.getClassement(1);
				request.setAttribute("classement", classementCommune);
			}
			else if(choix.equals("region")) {
				ArrayList<ClassementRegion> classementRegion = ClassementRegionDAO.getClassement(1);
				request.setAttribute("classement", classementRegion);
			}
			request.setAttribute("choix", choix);
		}
		DBDAO.deconnexion();
		RequestDispatcher dispatcher = request.getRequestDispatcher("./Classement.jsp");
		dispatcher.forward(request, response);
	}

}
