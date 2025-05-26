package contolleur;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.ActuDAO;
import model.ActualiteClub;

/**
 * @author imane
 * @version 1.1
 * Servlet implementation class PartagerActuServlet
 */
@WebServlet("/partagerActu")
public class PartagerActuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PartagerActuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("userId") == null) {
	        response.sendRedirect(request.getContextPath() + "/login");
	        return;
	    }

	    int userRole = getRoleFromCookies(request);
	    if (userRole != 1) {
	        response.sendRedirect(request.getContextPath() + "/login");
	        return;
	    }
        ActuDAO dao = new ActuDAO();

	    List<ActualiteClub> actualites;
		try {
			actualites = dao.listerActualites();
	    	request.setAttribute("actualites", actualites);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Accès autorisé à la page partage actu");
	

		
		int userId= (int) session.getAttribute("userId");
		//int userRole= (int) session.getAttribute("userRole");

		session.setAttribute("userId", userId);
		session.setAttribute("userRole", userRole);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vues/membre/partagerActu.jsp");
		dispatcher.forward(request, response);
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActualiteClub actu = new ActualiteClub();

		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userId") != null) {
		    Integer userId = (Integer) session.getAttribute("userId");
		    actu.setIdUser(userId);
		}	
	try {
        actu.setTitreActu(sanitize(request.getParameter("titre_actu")));
        actu.setDescriptionActu(sanitize(request.getParameter("descriptionActu")));

        ActuDAO dao = new ActuDAO();
        boolean success = dao.ajouterActualite(actu);
       
        if (success) {
        	
            response.sendRedirect("indexMembre");
        } else {
            request.setAttribute("erreur", "Échec de l'ajout de l'actualité.");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vues/membre/partagerActu.jsp");
            rd.forward(request, response);
        }

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("erreur", "Erreur interne.");
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vues/membre/partagerActu.jsp");
        rd.forward(request, response);
    }
}

private String sanitize(String input) {
    return input == null ? null : input.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
}
private int getRoleFromCookies(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("userRole".equals(cookie.getName())) {
                try {
                    return Integer.parseInt(cookie.getValue());
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
        }
    }
    return -1;
}

}