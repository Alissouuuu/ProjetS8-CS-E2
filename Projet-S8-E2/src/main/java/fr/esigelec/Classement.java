package fr.esigelec;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.text.StringEscapeUtils;

import fr.esigelec.dao.ClassementCommuneDAO;
import fr.esigelec.dao.ClassementRegionDAO;
import fr.esigelec.models.ClassementCommune;
import fr.esigelec.models.ClassementRegion;

/**
 * Servlet implementation class Classement
 */
@WebServlet("/Classement")
public class Classement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassementRegionDAO classementRegionDAO;
	private ClassementCommuneDAO classementCommuneDAO;
	@Resource(name="jdbc/club_sport")
	private DataSource dataSource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Classement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String choix = (String) StringEscapeUtils.escapeHtml4(request.getParameter("choix"));
		if(choix.equals("commune")) {
			classementCommuneDAO = new ClassementCommuneDAO(dataSource);
			ArrayList<ClassementCommune> classementCommune = classementCommuneDAO.getClassement(1);
			request.setAttribute("classement", classementCommune);
		}
		else if(choix.equals("region")) {
			classementRegionDAO = new ClassementRegionDAO(dataSource);
			ArrayList<ClassementRegion> classementRegion = classementRegionDAO.getClassement(1);
			request.setAttribute("classement", classementRegion);
		}
		HashMap<String,String> emplacements = new HashMap<>();
		emplacements.put("btnPageA", "active");
		emplacements.put("btnPageB", "");
		emplacements.put("btnPageC", "");
		emplacements.put("btnPageD", "");
		emplacements.put("btnPageE", "");
		emplacements.put("btnPageF", "");
		emplacements.put("pageA", "1");
		emplacements.put("pageB", "2");
		emplacements.put("pageC", "3");
		emplacements.put("pageD", "4");
		emplacements.put("pageE", "...");
		emplacements.put("pageF", "550");
		emplacements.put("pageActuelle", "1");
		
		request.setAttribute("choix", choix);
		request.setAttribute("emplacements", emplacements);
		RequestDispatcher dispatcher = request.getRequestDispatcher("./Classement.jsp");
		dispatcher.forward(request, response);
	}

}
