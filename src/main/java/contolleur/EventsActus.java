package contolleur;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ActualiteClub;
import model.Evenement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.ActuDAO;
import dao.EventDAO;

/**
 * Servlet implementation class EventsActus
 */
@WebServlet("/eventsActus")
public class EventsActus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventsActus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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

		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vues/visiteur/eventsEtActu.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
