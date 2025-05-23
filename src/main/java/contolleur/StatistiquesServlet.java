package contolleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
import model.Club;
import model.Departement;
import model.Federation;
import model.Region;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ClubDAO;
import dao.DepartementDAO;
import dao.RegionDAO;
import dao.FederationDAO;

/**
 * Servlet implementation class StatistiquesServlet
 */
@WebServlet("/statistiques")
public class StatistiquesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// HttpSession session = request.getSession(false);
		/*
		 * if (session == null || session.getAttribute("userId") == null ||
		 * session.getAttribute("userRole") == null) {
		 * response.sendRedirect(request.getContextPath() +"/index"); return; } int role
		 * = (int) session.getAttribute("userRole"); /* if (role != 0) {
		 * response.sendRedirect(request.getContextPath() +"/index"); return; } else {
		 * RequestDispatcher dispatcher =
		 * request.getRequestDispatcher("/WEB-INF/vues/membre/statistiques.jsp");
		 * dispatcher.forward(request, response); }
		 */

		/*
		 * for (String trancheAge : Club.getTrancheAge()) { totalHommes +=
		 * club.getLicencies().get(Genre.HOMME).get(trancheAge); totalFemmes +=
		 * club.getLicencies().get(Genre.FEMME).get(trancheAge); }
		 */

		try {
			DepartementDAO departementDAO = new DepartementDAO();
			List<Departement> departements = departementDAO.getListDepartement();
			// System.out.println(departements.size());
			request.setAttribute("departements", departements);

		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException("Erreur BDD : " + e.getMessage(), e);
		}
		try {
			RegionDAO regionDAO = new RegionDAO();
			List<Region> regions = regionDAO.getListRegion();

			request.setAttribute("regions", regions);

		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException("Erreur BDD : " + e.getMessage(), e);
		}
		try {
			FederationDAO federationDAO = new FederationDAO();
			List<Federation> federations = federationDAO.getListFederation();
			System.out.println("fede" + federations.size());

			request.setAttribute("federations", federations);

		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException("Erreur BDD : " + e.getMessage(), e);
		}
		// Rediriger vers la JSP

		request.getRequestDispatcher("/WEB-INF/vues/elu/statistiques.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//HttpSession session = request.getSession(false);
		/*
		 * if (session == null || session.getAttribute("userId") == null ||
		 * session.getAttribute("userRole") == null) {
		 * response.sendRedirect(request.getContextPath() +"/index"); return; } int role
		 * = (int) session.getAttribute("userRole"); if (role != 0) {
		 * response.sendRedirect(request.getContextPath() +"/index"); return; }else {
		 * RequestDispatcher dispatcher =
		 * request.getRequestDispatcher("/WEB-INF/vues/elu/classement.jsp");
		 * dispatcher.forward(request, response); }
		 * 
		 */
		String regionParam = request.getParameter("region");
		String cpParam = request.getParameter("cp");
		System.out.println("cp"+cpParam);

		String departementParam = request.getParameter("departement");

		String federationParam = request.getParameter("federation");
		System.out.println("federation"+federationParam);

		List<Club> clubs = new ArrayList<>();
		
		
		ClubDAO dao = new ClubDAO();
		try {
			
			
			if (cpParam != null && !cpParam.isEmpty()) {
				// Cas 2 : Code postal choisi
				if (federationParam != null && !federationParam.isEmpty()) {
					// Sous-cas 1.1 : région + federation
					clubs = dao.rechercherParCodeEtFederation(cpParam, federationParam);
				} else {
					// Sous-cas 1.3 : région seule
					clubs = dao.rechercherParCodePostal(cpParam);
				}

			} else if (departementParam != null && !departementParam.isEmpty()) {
				// Cas 3 : departement choisi
				if (federationParam != null && !federationParam.isEmpty()) {
					// Sous-cas 1.1 : departement + federation
					clubs = dao.rechercherParDepartementEtFederation(departementParam, federationParam);
				} else {
					// Sous-cas 1.3 : departement seul
					clubs = dao.rechercherParDepartement(departementParam);
				} 
			}
				else if (regionParam != null && !regionParam.isEmpty()) {
					// Cas 1 : Région choisie
					int codeRegion = Integer.parseInt(regionParam);

					if (federationParam != null && !federationParam.isEmpty()) {
						// Sous-cas 1.1 : région + federation
						clubs = dao.rechercherParRegionEtFederation(codeRegion, federationParam);
					} else {
						// Sous-cas 1.2 : région seule
						clubs = dao.rechercherParRegion(codeRegion);
					}

				} else if(federationParam != null && !federationParam.isEmpty()) {
					clubs = dao.rechercherParFederation(federationParam);
				}
			
			try {
				DepartementDAO departementDAO = new DepartementDAO();
				List<Departement> departements = departementDAO.getListDepartement();
				// System.out.println(departements.size());
				request.setAttribute("departements", departements);

			} catch (SQLException | ClassNotFoundException e) {
				throw new ServletException("Erreur BDD : " + e.getMessage(), e);
			}
			try {
				RegionDAO regionDAO = new RegionDAO();
				List<Region> regions = regionDAO.getListRegion();

				request.setAttribute("regions", regions);

			} catch (SQLException | ClassNotFoundException e) {
				throw new ServletException("Erreur BDD : " + e.getMessage(), e);
			}
			try {
				FederationDAO federationDAO = new FederationDAO();
				List<Federation> federations = federationDAO.getListFederation();
				System.out.println("fede" + federations.size());

				request.setAttribute("federations", federations);

			} catch (SQLException | ClassNotFoundException e) {
				throw new ServletException("Erreur BDD : " + e.getMessage(), e);
			}
			// Rediriger vers la JSP


			request.setAttribute("listeClubs", clubs);
			request.getRequestDispatcher("/WEB-INF/vues/elu/statistiques.jsp").forward(request, response);

		} catch (SQLException e) {
			throw new ServletException("Erreur SQL : " + e.getMessage(), e);
		} catch (NumberFormatException e) {
			throw new ServletException("Code région invalide : " + regionParam, e);
		}
	}

}
