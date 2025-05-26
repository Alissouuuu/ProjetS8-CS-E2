
/**
 * @author imane
 * @version 1.2
 * servlet qui gère l'ajout des évènements dans la bdd
 * 
 */
package contolleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Evenement;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import dao.EventDAO;

/**
 * Servlet implementation class CreateEventServlet
 */
@WebServlet("/createEvent")
public class CreateEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// private EventDAO eventDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateEventServlet() {
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

		request.getRequestDispatcher("/WEB-INF/vues/membre/createEvent.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean hasError = false;

		try {
			String eventName = sanitize(request.getParameter("event-name"));
			String eventDateStr = request.getParameter("event-date");
			String eventTimeStr = request.getParameter("event-time");
			String eventLocation = sanitize(request.getParameter("event-location"));
			String description = sanitize(request.getParameter("event-description"));
			String nbrMaxiStr = request.getParameter("max-participants");

			// Verifications de champs recuperes
			if (eventName == null || !eventName.matches("^[A-Za-zÀ-ÿ0-9 '\\-]{3,50}$")) {
				request.setAttribute("erreurName", "Le nom doit contenir entre 3 et 50 caractères valides..");

			}
			LocalDate eventDate = null;
			try {
				eventDate = LocalDate.parse(eventDateStr);
				if (eventDate.isBefore(LocalDate.now())) {
					request.setAttribute("erreurDate", "La date doit être postérieure à aujourd'hui.");
					hasError = true;
				}
			} catch (Exception e) {
				request.setAttribute("erreurDate", "Date invalide.");
				hasError = true;
			}

			// Heure
			LocalTime eventTime = null;
			try {
				eventTime = LocalTime.parse(eventTimeStr);
			} catch (Exception e) {
				request.setAttribute("erreurHeure", "Heure invalide.");
				hasError = true;
			}

			// Lieu
			if (eventLocation == null || !eventLocation.matches("^[A-Za-zÀ-ÿ0-9 ',\\-]{3,60}$")) {
				request.setAttribute("erreurLieu", "Le lieu doit contenir entre 3 et 60 caractères valides.");
				hasError = true;
			}

// parsing le nbr en int et verifier s'il est <0
			int maxParticipants = 0;
			try {
				maxParticipants = Integer.parseInt(nbrMaxiStr);
				if (maxParticipants <= 0) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				request.setAttribute("erreurNombre", "Le nombre de participants doit être un entier positif.");
				hasError = true;
			}
			// Description
			if (description == null || description.trim().length() < 10) {
				request.setAttribute("erreurDescription", "La description doit contenir au moins 10 caractères.");
				hasError = true;
			}
			if (hasError) {
				request.getRequestDispatcher("/WEB-INF/vues/membre/createEvent.jsp").forward(request, response);
				return;
			}
			// Si tout est OK : insertion
			HttpSession session = request.getSession(false);
			int userId = (int) session.getAttribute("userId");
			Evenement evenement = new Evenement();
			evenement.setNomEvenement(eventName);
			evenement.setDateEvenement(eventDate);
			evenement.setHeureEvenement(eventTime);
			evenement.setLieuEvenement(eventLocation);
			evenement.setDescriptionEvenement(description);
			evenement.setNbrMaxParticipants(maxParticipants);
			evenement.setIdUser(userId);
			evenement.setIdClub(0); // par défaut

			EventDAO dao = new EventDAO();
			boolean created = dao.createEvent(evenement);

			if (created) {
				request.getSession().setAttribute("successMessage", "L'événement a été créé avec succès ! Vous pouvez le consulter sur les cards");
				response.sendRedirect("indexMembre"); // Redirection vers la page membre
				return;
			} else {
				request.setAttribute("erreur", "Échec lors de la création de l'événement.");
				request.getRequestDispatcher("/WEB-INF/vues/membre/createEvent.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erreur", "Erreur interne.");
			request.getRequestDispatcher("/WEB-INF/vues/membre/createEvent.jsp").forward(request, response);
		}
	}

	// eviter les failles XSS
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
