package contolleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import java.util.*;
import java.io.*;
import dao.*;
import modelCarte.*;
/**
 * Servlet implementation class Carte
 */
@WebServlet("/IndexVisiteurServlet")
public class IndexVisiteurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexVisiteurServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		


		
        // Forward vers la JSP
        request.getRequestDispatcher("/WEB-INF/vues/visiteur/indexVisiteur.jsp").forward(request, response);
        
	}

	
    
	
}