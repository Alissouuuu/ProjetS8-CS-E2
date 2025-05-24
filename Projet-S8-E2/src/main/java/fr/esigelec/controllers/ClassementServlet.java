package fr.esigelec.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.sql.DataSource;

/**
 * Servlet implementation class ClassementServlet
 */
@WebServlet("/classement")
public class ClassementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/club_sport")
	DataSource dataSource;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vues/elu/classement.jsp");
		dispatcher.forward(request, response);
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String codeCommune = request.getParameter("commune");
		String codeDepartement = request.getParameter("departement");
		String codeRegion = request.getParameter("region");
		String codeFederation = request.getParameter("federation");
		String age = request.getParameter("age");
		String genre = request.getParameter("genre");
		
		if(codeFederation != null && !codeFederation.equals("all")) {
			//recherche par fédération
			switch(age) {
			case "moins15":
				break;
			case "15_24":
				break;
			case "35_49":
				break;
			case "50_79":
				break;
			case "80plus":
				break;
			default:
				//recherche sans critère d'age... à suivre
			}
		}
	}

}
