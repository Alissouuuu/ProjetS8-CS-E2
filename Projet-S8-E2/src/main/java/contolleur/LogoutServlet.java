package contolleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class LogoutServlet
 * servlet qui gère les deconnexions des users
 * @author imane
 * @version 1.0
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     *  déconnecter l'utilisateur
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // recuperer la session actuelle
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Invalider la session
            session.invalidate();
        }
        
        // Rediriger vers la page de connexion
        response.sendRedirect(request.getContextPath() + "/index");
    }

}
