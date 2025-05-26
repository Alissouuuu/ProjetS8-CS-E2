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
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;
import dao.UserDAO;
import model.User;

/**
 * Servlet implementation class LoginServlet une servlet pour gérer la connexion
 * des user
 * 
 * @author imane
 * @version 1.0
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDAO userDAO;
	private User user;
	private boolean used;
	private String loginEmail;
	private String loginPassword;

	/**
	 * initialisation du dao
	 */
	@Override
	public void init() {
		userDAO = new UserDAO();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vues/connexion/login.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//recuperons l email et le mdp du login et les verifions  leur conformité avec nos exigences
		loginEmail = request.getParameter("loginEmail");
		loginPassword = request.getParameter("loginPassword");
		used = userDAO.emailEstUtilise(loginEmail);
		if (loginEmail == null || !loginEmail.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$") || used) {
			request.setAttribute("error", "Email ou mot de passe incorrect.");
			request.getRequestDispatcher("/WEB-INF/vues/connexion/login.jsp").forward(request, response);
			return;
		}
		if (loginPassword == null || !estValideMotDePasse(loginPassword)) {
			request.setAttribute("error", "Email ou mot de passe incorrect.");

			request.getRequestDispatcher("/WEB-INF/vues/connexion/login.jsp").forward(request, response);
			return;
		}
		
		// 2 eme filtre verfication BDD

		user = userDAO.findByEmail(loginEmail);
		if (user != null && this.verifyPassword(loginPassword, user.getMdp())) {

			HttpSession session = request.getSession(true);
	        session.setAttribute("userId", user.getIdUser());

	        // creation cookie rôle
	        int role = user.getRole();
	        Cookie roleCookie = new Cookie("userRole", String.valueOf(role));
	        roleCookie.setMaxAge(60 * 60); // 1h
	        roleCookie.setPath("/");
	        response.addCookie(roleCookie);
		
	        // redirection vers servlet selon le rôle
	        if (role == 2) {
	            response.sendRedirect(request.getContextPath() + "/indexElu");
	        } else if (role == 3) {
	            response.sendRedirect(request.getContextPath() + "/indexMembre");
	        } else {
	            response.sendRedirect(request.getContextPath() + "/login");
	        }
		
		}else {
			request.setAttribute("error", "Email ou mot de passe incorrect.");
	        request.getRequestDispatcher("/WEB-INF/vues/connexion/login.jsp").forward(request, response);
		}
			
		
	}

	public static boolean estValideMotDePasse(String motDePasse) {
		// verifier d'abord si le mdp contient des caractères miniscule et majiscules,
		// des chiffres et des caracères speciaux
		if (!Pattern.compile("[a-zA-Z]").matcher(motDePasse).find()
				|| !Pattern.compile("[0-9]").matcher(motDePasse).find()
				|| !Pattern.compile("[@/?!']").matcher(motDePasse).find()) {
			System.out.println(
					"Le mot de passe doit contenir au moins une lettre, un chiffre et un caractère spécial (@/?!').");
			
			return false;
		}
		return true;

	}

	public boolean verifyPassword(String password, String hashed) {
		return BCrypt.checkpw(password, hashed);
	}


}
