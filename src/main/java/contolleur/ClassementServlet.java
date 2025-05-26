/**
 * @author imane
 * @version 1.5
 * Servlet qui gère le classemen des licenciés
 */
package contolleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClassementServlet() {
		super();
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
	    if (userRole != 2) {
	        response.sendRedirect(request.getContextPath() + "/login");
	        return;
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
			regions = regionDAO.getListeRegion();
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
				}else {
					clubs = dao.rechercherParRegion(codeRegion);
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
				}else {
					clubs = dao.rechercherParCodePostal(cpParam);
				}
			}


			request.setAttribute("typeAffichage", typeAffichage);
			request.setAttribute("listeClubs", clubs); 
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
        System.out.println("Code postal saisi : " + cpParam);

        String ageParam = request.getParameter("age");
        String genreParam = request.getParameter("genre");
        List<Club> clubs = new ArrayList<>();
        ClubDAO dao = new ClubDAO();
        RegionDAO regionDAO = new RegionDAO();
      
        List<Region> regions;
		try {
			regions = regionDAO.getListeRegion();

			request.setAttribute("regions", regions);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        try {
        	
            if (regionParam != null && !regionParam.isEmpty()) {
                // Cas 1 : Région choisie
                int codeRegion = Integer.parseInt(regionParam);

                if (genreParam != null && !genreParam.isEmpty() && (ageParam == null || ageParam.isEmpty())) {
                    // Sous cas 1.1 : région + genre
                	 typeAffichage = "genre";
                    clubs = dao.rechercherParRegionEtGenre(codeRegion, genreParam);

                } else if (ageParam != null && !ageParam.isEmpty() && (genreParam == null || genreParam.isEmpty())) {
                    // Sous cas 1.2 : région + age
                	typeAffichage = "age";
                    clubs = dao.rechercherParRegionEtAge(codeRegion, ageParam);

                } else if (ageParam != null && !ageParam.isEmpty() && genreParam != null && !genreParam.isEmpty()) {
                    // Souscas 1.3 : région +age + genre
                	 typeAffichage = "lesDeux";
                    clubs = dao.rechercherParRegionEtGenreEtAge(codeRegion, genreParam, ageParam);
                }

            } else if (cpParam != null && !cpParam.isEmpty()) {
                // Cas 2 : Code postal choisi

                if (genreParam != null && !genreParam.isEmpty() && (ageParam == null || ageParam.isEmpty())) {
                    // Sous cas 2.1 : cp + genre
                	typeAffichage="genre";
                    clubs = dao.rechercherParCodeEtGenre(cpParam, genreParam);

                } else if (ageParam != null && !ageParam.isEmpty() && (genreParam == null || genreParam.isEmpty())) {
                    // 🔹 Sous cas 2.2 : cp + age
                	typeAffichage = "age";
                    clubs = dao.rechercherParCodeEtAge(cpParam, ageParam);

                } else if (ageParam != null && !ageParam.isEmpty() && genreParam != null && !genreParam.isEmpty()) {
                    // Sous-cas 2.3 : cp  + age + genre
                  
                    if (regionParam != null) {
                    	 typeAffichage = "lesDeux";
                        clubs = dao.rechercherParCodeEtGenreEtAge(cpParam, genreParam,ageParam);
                    } else {
                        // Si région manquante pour ce cas, ignore it
                        clubs = new ArrayList<>();
                    }
                }

            }
            
            request.setAttribute("typeAffichage", typeAffichage);
            request.setAttribute("genre", genreParam);
            request.setAttribute("age", ageParam);
            request.setAttribute("listeClubs", clubs);
            request.getRequestDispatcher("/WEB-INF/vues/elu/classement.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Erreur SQL : " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new ServletException("Code région invalide : " + regionParam, e);
        }
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
