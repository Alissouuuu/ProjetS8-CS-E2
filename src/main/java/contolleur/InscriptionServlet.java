/**
 * @author imane
 * @version 1.2
 * servlet qui gère les inscritptions
 */
package contolleur;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

import dao.UserDAO;
import model.User;
import util.FichierValidateur;

/**
 * Servlet implementation class InscriptionServlet Servlet pour gérer
 * l'inscription des utilisateurs
 * 
 * @author imane
 * @version 1.0
 */
@WebServlet("/inscription")
@MultipartConfig(maxRequestSize = 1024 * 1024 * 5 // 5 MB
)
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	String created;
	boolean inserted;
	User user = new User();
	boolean hasError = false;

	@Override
	public void init() {
		userDAO = new UserDAO();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InscriptionServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Afficher la page d'inscription
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vues/connexion/inscription.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// recuperation des infos reçues
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String fonction = request.getParameter("fonction");

		String password = request.getParameter("password");
		// verification des conformité des données
		// les champs nom et prenom accèptent les caractères miniscules, majiscules et
		// les tirets -
		if (!nom.matches("^[A-Za-z- ]+$") || !prenom.matches("^[A-Za-z- ]+$")) {
			request.setAttribute("erreurNom", "Le nom  ne doit contenir que des lettres et/ou des tirets.");
			hasError=true;
		} else {
			user.setNom(nom);
			user.setPrenom(prenom);
		}
		if (!prenom.matches("^[A-Za-z- ]+$")) {
			request.setAttribute("erreurPrenom", "Le prénom ne doit contenir que des lettres et/ou des tirets.");
			hasError = true;

		} else {
			user.setPrenom(prenom);
		}

//verification de l'email : 1.verififer s'il est valide après verifier  s'il faut verifier si l'email existe deja dans la bdd
		boolean used = userDAO.emailEstUtilise(email);
		if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$") || used) {
			request.setAttribute("erreurEmail", "Adresse email invalide.");
			hasError = true;

		} else
			user.setEmail(email);

		// verifier si le mdp recupere est une chaine de caractères et des chiifres : si
		// oui on le hache et le stocker dans la bdd
		if (password != null && estValideMotDePasse(password)) {
			String mdpHashe = hashPassword(password);
			user.setMdp(mdpHashe);

		} else {
			request.setAttribute("erreurMdp", "Mot de passe invalide.");
			hasError = true;
		}
// fonction choisie ou pas
		if (fonction == null || fonction.trim().isEmpty()) {
			request.setAttribute("erreurFonction", "Vous devez choisir une fonction.");
			hasError = true;

		} else
			user.setFonction(fonction);
		// Traitons le fichier justificatif
		Part filePart = request.getPart("file");

		// lecture du fichier
		if (filePart == null || filePart.getSize() == 0) {
			request.setAttribute("erreurFile", "Aucun fichier n'a été téléchargé.");
			hasError = true;
		} else {
			if (!FichierValidateur.isFileValid(filePart)) {
				request.setAttribute("erreurFile2",
						"Le fichier téléchargé est invalide. Veuillez respecter les critères.");
				hasError = true;
			}
		}
		try {
			byte[] justificatif = FichierValidateur.processFile(filePart);
			user.setJustificatifDonnees(justificatif);

		} catch (IOException e) {
			response.getWriter().println("Erreur lors du traitement du fichier : " + e.getMessage());
		}
		if (hasError) {
			request.getRequestDispatcher("/WEB-INF/vues/connexion/inscription.jsp").forward(request, response);
			return;
		}
		user.setRole(0); // visiteur par defaut
		user.setStatutDemande(200); // par défaut en attente

		// creation d' un nouvel utilisateur

		try {
			inserted = userDAO.create(user);

			if (inserted) {
				request.getRequestDispatcher("/WEB-INF/vues/connexion/inscription-success.jsp").forward(request,response);
				return;
			} else {
				request.setAttribute("erreur", "Erreur lors de l'enregistrement. Réessayez.");
				request.getRequestDispatcher("/WEB-INF/vues/connexion/inscription.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("erreur", "Une erreur technique est survenue.");
			request.getRequestDispatcher("/WEB-INF/vues/connexion/inscription.jsp").forward(request, response);
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

	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
}
