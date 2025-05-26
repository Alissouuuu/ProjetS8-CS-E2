package contolleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class IndexEluServlet
 */
@WebServlet("/indexElu")
public class IndexEluServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexEluServlet() {
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
	    if (userRole != 2) {
	        response.sendRedirect(request.getContextPath() + "/login");
	        return;
	    }

	    request.getRequestDispatcher("/WEB-INF/vues/elu/indexElu.jsp").forward(request, response);


		
		
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
