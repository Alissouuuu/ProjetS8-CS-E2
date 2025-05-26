/**
 * @author imane
 * @version 2.1
 */
package contolleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ActualiteClub;
import model.Evenement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.ActuDAO;
import dao.EventDAO;

/**
 * Servlet implementation class IndexMembreServlet
 */
@WebServlet("/indexMembre")
public class IndexMembreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexMembreServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		int userRole = getRoleFromCookies(request);
		if (userRole != 3) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		EventDAO dao = new EventDAO();
		List<Evenement> evenementsPasses;
		try {
			evenementsPasses = dao.getEvenementsPasses();
			request.setAttribute("evenementsPasses", evenementsPasses);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Evenement> evenementsAVenir;

		try {
			evenementsAVenir = dao.getEvenementsAVenir();
			request.setAttribute("evenementsAVenir", evenementsAVenir);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		ActuDAO daoActu = new ActuDAO();
		try {
			List<ActualiteClub> actualites = daoActu.listerActualites();
	        request.setAttribute("actualites", actualites);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.getRequestDispatcher("/WEB-INF/vues/membre/indexMembre.jsp").forward(request, response);

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
