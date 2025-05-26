/**
 * @author imane
 * @version 1.0
 * servlet qui supprime les actualités 
 */
package contolleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import dao.ActuDAO;

/**
 * Servlet implementation class DeleteActu
 */
@WebServlet("/DeleteActu")
public class DeleteActu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteActu() {
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
	int id = Integer.parseInt(request.getParameter("id"));
		
		try {
			ActuDAO dao = new ActuDAO();
			dao.supprimerActu(id);
			System.out.println("Suppression de l'actu avec ID : " + id);
			
			response.sendRedirect(request.getContextPath() + "/indexMembre");

		} catch (SQLException e) {
			e.printStackTrace(); 
		
	
	}
	}

}
