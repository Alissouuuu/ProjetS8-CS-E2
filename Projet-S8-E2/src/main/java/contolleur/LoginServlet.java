package contolleur;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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
	private static final int MAX_TENTATIVES = 3;

	private UserDAO userDAO;

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
		
		// verifions si le user est déja connecté
		//HttpSession session = request.getSession(false);
	
		// Afficher la page de connexion
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vues/connexion/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loginEmail = request.getParameter("loginEmail");
		String loginPassword = request.getParameter("loginPassword");
		if (loginEmail.equals("admin") && loginPassword.equals("123")) {
            request.getSession().setAttribute("user", loginEmail);
            response.sendRedirect(request.getContextPath()+"/index"); 
        } else {
            request.setAttribute("error", "Identifiants invalides");
            System.out.println("erreur");
            request.getRequestDispatcher("/WEB-INF/vues/connexion/inscription.jsp").forward(request, response);
        }
	}
	
	
	
		// TODO Auto-generated method stub
		/*doGet(request, response);
		// Récupérer les paramètres du formulaire
		String email = request.getParameter("loginEmail");
		String password = request.getParameter("loginPassword");
		User user = userDAO.authenticate(email, password);
		if (user != null) {
			// faut verifier le role
			// aussi il faut reinitialiser le nbr de tentatievs de connexion
			// creation une session pour l'utilisateur
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			// Rediriger vers la page d'accueil
			response.sendRedirect(request.getContextPath() + "/accueil");

		} else {
			//incrementer le nbr de tentatives
			// int tentatives = logConnexionDAO.incrementTentatives(adresseIp);
            //  le message d'erreur
            String message = "Email ou mot de passe incorrect.";
            /*if (MAX_TENTATIVES - tentatives > 0) {
                message += " Il vous reste " + (MAX_TENTATIVES - tentatives) + " tentative(s).";
            } else {
                message = "Trop de tentatives échouées. Veuillez réessayer plus tard.";
            }*/
            
            // Afficher le message d'erreur
          //  request.setAttribute("erreur", message);
         //   RequestDispatcher dispatcher = request.getRequestDispatcher("/vues/login.jsp");
          //  dispatcher.forward(request, response);
		//}
	//}*/

}
