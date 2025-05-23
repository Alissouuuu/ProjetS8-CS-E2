package contolleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Club;
import model.Region;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ClubDAO;
import dao.RegionDAO;

/**
 * Servlet implementation class ClassementServlet
 */
@WebServlet("/classement")
public class ClassementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
		String typeAffichage = "";
    private static final int ELEMENTS_PAR_PAGE = 50;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClassementServlet() {
		super();
		// TODO Auto-generated constructor stub
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    int page1 = 1;
	    final int pageSize = 50; // constant
	    String pageParam = request.getParameter("page");
	    if (pageParam != null) {
	        try {
	            page1 = Integer.parseInt(pageParam);
	        } catch (NumberFormatException e) {
	            page1 = 1;
	        }
	    }

	    String regionParam = request.getParameter("region");
	    String cpParam = request.getParameter("cp");
	    String ageParam = request.getParameter("age");
	    String genreParam = request.getParameter("genre");

	    List<Club> clubs = new ArrayList<>();
	    ClubDAO dao = new ClubDAO();
	    RegionDAO regionDAO = new RegionDAO();
	    List<Region> regions;
	    try {
	        regions = regionDAO.getListRegion();
	        request.setAttribute("regions", regions);

	        String typeAffichage = "";

	        if (regionParam != null && !regionParam.isEmpty()) {
	            int codeRegion = Integer.parseInt(regionParam);

	            if (genreParam != null && !genreParam.isEmpty() && (ageParam == null || ageParam.isEmpty())) {
	                typeAffichage = "genre";
	                clubs = dao.rechercherParRegionEtGenre(codeRegion, genreParam);
	            } else if (ageParam != null && !ageParam.isEmpty() && (genreParam == null || genreParam.isEmpty())) {
	                typeAffichage = "age";
	                clubs = dao.rechercherParRegionEtAge(codeRegion, ageParam);
	            } else if (ageParam != null && !ageParam.isEmpty() && genreParam != null && !genreParam.isEmpty()) {
	                typeAffichage = "lesDeux";
	                clubs = dao.rechercherParRegionEtGenreEtAge(codeRegion, genreParam, ageParam);
	            }
	        } else if (cpParam != null && !cpParam.isEmpty()) {
	            if (genreParam != null && !genreParam.isEmpty() && (ageParam == null || ageParam.isEmpty())) {
	                typeAffichage = "genre";
	                clubs = dao.rechercherParCodeEtGenre(cpParam, genreParam);
	            } else if (ageParam != null && !ageParam.isEmpty() && (genreParam == null || genreParam.isEmpty())) {
	                typeAffichage = "age";
	                clubs = dao.rechercherParCodeEtAge(cpParam, ageParam);
	            } else if (ageParam != null && !ageParam.isEmpty() && genreParam != null && !genreParam.isEmpty()) {
	                if (regionParam != null) {
	                    typeAffichage = "lesDeux";
	                    clubs = dao.rechercherParCodeEtGenreEtAge(cpParam, genreParam, ageParam);
	                } else {
	                    clubs = new ArrayList<>();
	                }
	            }
	        }

	        // Pagination côté serveur
	        int totalClubs = clubs.size();
	        int totalPages = (int) Math.ceil((double) totalClubs / pageSize);

	        int start = (page1 - 1) * pageSize;
	        int end = Math.min(start + pageSize, totalClubs);
	        List<Club> clubsPage = clubs.subList(start, end);

	        request.setAttribute("typeAffichage", typeAffichage);
	        request.setAttribute("listeClubs", clubsPage);
	        request.setAttribute("totalPages", totalPages);
	        request.setAttribute("page1", page1);

	        // Passer aussi les filtres dans la requête pour pagination
	        request.setAttribute("region", regionParam);
	        request.setAttribute("cp", cpParam);
	        request.setAttribute("age", ageParam);
	        request.setAttribute("genre", genreParam);

	        request.getRequestDispatcher("/WEB-INF/vues/elu/classement.jsp").forward(request, response);

	    } catch (Exception e) {
	        throw new ServletException("Erreur BDD : " + e.getMessage(), e);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String regionParam = request.getParameter("region");
	    String cpParam = request.getParameter("cp");
	    String ageParam = request.getParameter("age");
	    String genreParam = request.getParameter("genre");

	    String redirectURL = "classement?page=1";

	    if (regionParam != null && !regionParam.isEmpty()) {
	        redirectURL += "&region=" + regionParam;
	    }
	    if (cpParam != null && !cpParam.isEmpty()) {
	        redirectURL += "&cp=" + cpParam;
	    }
	    if (ageParam != null && !ageParam.isEmpty()) {
	        redirectURL += "&age=" + ageParam;
	    }
	    if (genreParam != null && !genreParam.isEmpty()) {
	        redirectURL += "&genre=" + genreParam;
	    }

	    response.sendRedirect(redirectURL);
	}
}
