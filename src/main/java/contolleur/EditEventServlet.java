package contolleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Evenement;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import dao.EventDAO;

/**
 * Servlet implementation class EditEventServlet
 */
@WebServlet("/EditEvent")
public class EditEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditEventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
        try {
            EventDAO dao = new EventDAO();
            Evenement evenement = dao.getEvenementById(id);
            System.out.println("ID reçu : " + request.getParameter("id"));

            request.setAttribute("evenement", evenement);
            request.getRequestDispatcher("/WEB-INF/vues/membre/updateEvent.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("indexMembre");
        }
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nom = request.getParameter("event-name");
        LocalDate date = LocalDate.parse(request.getParameter("event-date"));
        LocalTime heure = LocalTime.parse(request.getParameter("event-time"));
        String lieu = request.getParameter("event-location");
        int max = Integer.parseInt(request.getParameter("max-participants"));
        String description = request.getParameter("event-description");

        Evenement e = new Evenement();
        e.setIdEvenement(id);
        e.setNomEvenement(nom);
        e.setDateEvenement(date);
        e.setHeureEvenement(heure);
        e.setLieuEvenement(lieu);
        e.setNbrMaxParticipants(max);
        e.setDescriptionEvenement(description);

        try {
            EventDAO dao = new EventDAO();
            dao.updateEvenement(e);
			request.getSession().setAttribute("successMessage", "L'événement a été mis à jour avec succès ! Vous pouvez le consulter sur les cards");

            response.sendRedirect("indexMembre");
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.sendRedirect("#");
        }
	}

}
