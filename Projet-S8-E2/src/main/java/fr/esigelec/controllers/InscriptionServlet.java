package fr.esigelec.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import fr.esigelec.dao.UserDAO;
import fr.esigelec.models.User;
import fr.esigelec.util.FichierValidateur;

/**
 * Servlet implementation class InscriptionServlet Servlet pour gérer
 * l'inscription des utilisateurs
 * 
 * @author imane
 * @version 1.0
 */
@WebServlet("/inscription")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	@Resource(name="jdbc/club_sport")
	private DataSource dataSource;
	
	@Override
	public void init() {
		userDAO = new UserDAO(dataSource);
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
		User user = new User();

		// recuperation des infos reçues
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String fonction = request.getParameter("metier");
		String password = request.getParameter("password");
		// verification des conformité des données
		// les champs nom et prenom accèptent les caractères miniscules, majiscules et
		// les tirets -
		if (!nom.matches("^[A-Za-z- ]+$") || !prenom.matches("^[A-Za-z- ]+$") || !fonction.matches("^[A-Za-z- ]+$")) {
			request.setAttribute("erreur", "Le nom et le prénom ne doivent contenir que des lettres et/ou des tirets.");
			request.getRequestDispatcher("/WEB-INF/vues/connexion/inscription.jsp").forward(request, response);
			return;
		} else {
			user.setNom(nom);
			user.setPrenom(prenom);
			user.setFonction(fonction);
		}
//verification de l'email : 1.verififer s'il est valide après verifier  s'il faut verifier si l'email existe deja dans la bdd
		boolean used = userDAO.emailEstUtilise(email);
		if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$") || used) {
			request.setAttribute("erreur", "Adresse email invalide.");
			request.getRequestDispatcher("/WEB-INF/vues/connexion/inscription.jsp").forward(request, response);
			return;
		} else
			user.setEmail(email);

		// verifier si le mdp recupere est une chaine de caractères et des chiifres : si
		// oui on le hache et le stocker dans la bdd
		if (password != null || estValideMotDePasse(password)) {
			String mdpHashe=hashPassword(password);
			user.setMdp(mdpHashe);

		} else {
			request.setAttribute("erreur", "Mot de passe invalide.");
			request.getRequestDispatcher("/WEB-INF/vues/connexion/inscription.jsp").forward(request, response);

		}

		// Traitons le fichier justificatif
		Part filePart = request.getPart("file");

		// lecture du fichier
		if (filePart == null || filePart.getSize() == 0) {
			request.setAttribute("erreur", "Aucun fichier n'a été téléchargé.");
			return;
		}
		if (!FichierValidateur.isFileValid(filePart)) {
			request.setAttribute("erreur", "Le fichier téléchargé est invalide. Veuillez respecter les critères.");

			return;
		}
		
		try {
            byte[] justificatif = FichierValidateur.processFile(filePart);
            // Ici, tu peux enregistrer le fichier ou effectuer d'autres actions nécessaires
    		user.setJustificatifDonnees(justificatif);

		} catch (IOException e) {
            response.getWriter().println("Erreur lors du traitement du fichier : " + e.getMessage());
        }
		user.setRole(0); // elu par defaut
		user.setStatutDemande(2); // par défaut en attente

		// creation d' un nouvel utilisateur

		try {
			boolean inserted = userDAO.create(user);
			if (inserted) {
				response.sendRedirect(request.getContextPath() + "/login");
			} else {
				request.setAttribute("erreur", "Erreur lors de l'enregistrement. Réessayez.");
				request.getRequestDispatcher("/WEB-INF/vues/connexion/inscription.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("erreur", "Une erreur technique est survenue.");
			request.getRequestDispatcher("/WEB-INF/vues/connexion/inscription.jsp").forward(request, response);
		}

		/*
		 * } else { // Erreur lors de l'inscription request.setAttribute("erreur",
		 * "Une erreur est survenue lors de l'inscription. Veuillez réessayer.");
		 * RequestDispatcher dispatcher =
		 * request.getRequestDispatcher("/vues/inscription.jsp");
		 * dispatcher.forward(request, response); }
		 */
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
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : hashedBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Erreur de hachage", e);
		}
	}
}
