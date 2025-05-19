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
		String valeurString = null,casActuel=null;
		int valeurInt=1,ancienneValeurInt,ecartDroite,ecartGauche,valeurIntMoinsUn,valeurIntMoinsDeux,valeurIntMoinsTrois,valeurIntPlusUn,valeurIntPlusDeux;
		HashMap<String,String> emplacements = new HashMap<>();
		if(request.getParameter("a") !=null) {
			valeurInt = 1;
			emplacements.put("etatBtnPrecedent", "disabled");
			emplacements.put("etatBtnPageA", "active");
			emplacements.put("etatBtnPageB", "");
			emplacements.put("etatBtnPageC", "");
			emplacements.put("etatBtnPageD", "");
			emplacements.put("etatBtnPageE", "");
			emplacements.put("etatBtnPageF", "disabled");
			emplacements.put("etatBtnPageG", "");
			emplacements.put("etatBtnSuivant", "");
			emplacements.put("pageA", "1");
			emplacements.put("pageB", "2");
			emplacements.put("pageC", "3");
			emplacements.put("pageD", "4");
			emplacements.put("pageE", "5");
			emplacements.put("pageF", "...");
			emplacements.put("pageG", "1395");
			emplacements.put("pageActuelle", "1");
			
		}
		else if(request.getParameter("b") !=null) {
			valeurString = "2";
			valeurInt = 2;
			emplacements.put("etatBtnPrecedent", "");
			emplacements.put("etatBtnPageA", "");
			emplacements.put("etatBtnPageB", "active");
			emplacements.put("etatBtnPageC", "");
			emplacements.put("etatBtnPageD", "");
			emplacements.put("etatBtnPageE", "");
			emplacements.put("etatBtnPageF", "disabled");
			emplacements.put("etatBtnPageG", "");
			emplacements.put("etatBtnSuivant", "");
			emplacements.put("pageA", "1");
			emplacements.put("pageB", "2");
			emplacements.put("pageC", "3");
			emplacements.put("pageD", "4");
			emplacements.put("pageE", "5");
			emplacements.put("pageF", "...");
			emplacements.put("pageG", "1395");
			emplacements.put("pageActuelle", "2");
		}
		else if(request.getParameter("c") !=null) {
			valeurString = request.getParameter("c");
			valeurInt = Integer.parseInt(valeurString);
			valeurIntPlusUn = valeurInt +1;
			valeurIntPlusDeux = valeurInt +2;
			if(!valeurString.equals("3")) {
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "disabled");
				emplacements.put("etatBtnPageC", "active");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "...");
				emplacements.put("pageC", valeurString);
				emplacements.put("pageD", String.valueOf(valeurIntPlusUn));
				emplacements.put("pageE", String.valueOf(valeurIntPlusDeux));
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", valeurString);
			}
			else {
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "");
				emplacements.put("etatBtnPageC", "active");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "2");
				emplacements.put("pageC", "3");
				emplacements.put("pageD", "4");
				emplacements.put("pageE", "5");
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "3");
			}
		}
		else if(request.getParameter("d") !=null) {
			valeurString = request.getParameter("d");
			valeurInt = Integer.parseInt(valeurString);
			valeurIntMoinsUn = valeurInt -1;
			valeurIntPlusUn = valeurInt+1;
			valeurIntPlusDeux = valeurInt + 2;
			ecartDroite = 1395-valeurInt;
			if(!valeurString.equals("4")) {
				if(ecartDroite == 2) {
					emplacements.put("etatBtnPrecedent", "");
					emplacements.put("etatBtnPageA", "");
					emplacements.put("etatBtnPageB", "disabled");
					emplacements.put("etatBtnPageC", "");
					emplacements.put("etatBtnPageD", "active");
					emplacements.put("etatBtnPageE", "");
					emplacements.put("etatBtnPageF", "disabled");
					emplacements.put("etatBtnPageG", "");
					emplacements.put("etatBtnSuivant", "");
					emplacements.put("pageA", "1");
					emplacements.put("pageB", "...");
					emplacements.put("pageC", String.valueOf(valeurIntMoinsUn));
					emplacements.put("pageD", valeurString);
					emplacements.put("pageE", String.valueOf(valeurIntPlusUn));
					emplacements.put("pageF", "...");
					emplacements.put("pageG", "1395");
					emplacements.put("pageActuelle", valeurString);
				}
				else {
					emplacements.put("etatBtnPrecedent", "");
					emplacements.put("etatBtnPageA", "");
					emplacements.put("etatBtnPageB", "disabled");
					emplacements.put("etatBtnPageC", "active");
					emplacements.put("etatBtnPageD", "");
					emplacements.put("etatBtnPageE", "");
					emplacements.put("etatBtnPageF", "disabled");
					emplacements.put("etatBtnPageG", "");
					emplacements.put("etatBtnSuivant", "");
					emplacements.put("pageA", "1");
					emplacements.put("pageB", "...");
					emplacements.put("pageC", valeurString);
					emplacements.put("pageD", String.valueOf(valeurIntPlusUn));
					emplacements.put("pageE", String.valueOf(valeurIntPlusDeux));
					emplacements.put("pageF", "...");
					emplacements.put("pageG", "1395");
					emplacements.put("pageActuelle", valeurString);
				}
			}
			else {
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "active");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "2");
				emplacements.put("pageC", "3");
				emplacements.put("pageD", "4");
				emplacements.put("pageE", "5");
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "4");
			}
		}
		else if(request.getParameter("e") !=null) {
			valeurString = request.getParameter("e");
			valeurInt = Integer.parseInt(valeurString);
			valeurIntMoinsUn = valeurInt -1;
			valeurIntMoinsDeux = valeurInt-2;
			valeurIntPlusUn = valeurInt+1;
			valeurIntPlusDeux = valeurInt+2;
			ecartDroite = 1395-valeurInt;
			if(ecartDroite == 1) {
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "disabled");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "active");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "...");
				emplacements.put("pageC", String.valueOf(valeurIntMoinsDeux));
				emplacements.put("pageD", String.valueOf(valeurIntMoinsUn));
				emplacements.put("pageE", valeurString);
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", valeurString);
			}
			else if(ecartDroite == 2){
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "disabled");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "active");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "...");
				emplacements.put("pageC", String.valueOf(valeurIntMoinsUn));
				emplacements.put("pageD", valeurString);
				emplacements.put("pageE", String.valueOf(valeurIntPlusUn));
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", valeurString);
			}
			else {
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "disabled");
				emplacements.put("etatBtnPageC", "active");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "...");
				emplacements.put("pageC", valeurString);
				emplacements.put("pageD", String.valueOf(valeurIntPlusUn));
				emplacements.put("pageE", String.valueOf(valeurIntPlusDeux));
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", valeurString);
			}
		}
		else if(request.getParameter("f") !=null) {
			valeurString = request.getParameter("f");
		}
		else if(request.getParameter("g") !=null) {
			valeurString = request.getParameter("g");
			valeurInt = Integer.parseInt(valeurString);
			valeurIntMoinsUn = valeurInt -1;
			valeurIntMoinsDeux = valeurInt-2;
			valeurIntMoinsTrois = valeurInt-3;
			emplacements.put("etatBtnPrecedent", "");
			emplacements.put("etatBtnPageA", "");
			emplacements.put("etatBtnPageB", "disabled");
			emplacements.put("etatBtnPageC", "");
			emplacements.put("etatBtnPageD", "");
			emplacements.put("etatBtnPageE", "");
			emplacements.put("etatBtnPageF", "disabled");
			emplacements.put("etatBtnPageG", "active");
			emplacements.put("etatBtnSuivant", "disabled");
			emplacements.put("pageA", "1");
			emplacements.put("pageB", "...");
			emplacements.put("pageC", String.valueOf(valeurIntMoinsTrois));
			emplacements.put("pageD", String.valueOf(valeurIntMoinsDeux));
			emplacements.put("pageE", String.valueOf(valeurIntMoinsUn));
			emplacements.put("pageF", "...");
			emplacements.put("pageG", "1395");
			emplacements.put("pageActuelle", "1395");
		}
		else if(request.getParameter("precedent") != null) {
			valeurString = request.getParameter("precedent");
			ancienneValeurInt = Integer.parseInt(valeurString);
			valeurInt = ancienneValeurInt - 1;
			valeurString = String.valueOf(valeurInt);
			valeurIntMoinsUn = valeurInt -1;
			valeurIntMoinsDeux = valeurInt -2;
			valeurIntMoinsTrois = valeurInt-3;
			valeurIntPlusUn = valeurInt +1;
			valeurIntPlusDeux = valeurInt + 2;
			ecartDroite = 1395-valeurInt;
			switch(valeurInt) {
			case 1:
				casActuel = "normal1";
				emplacements.put("etatBtnPrecedent", "disabled");
				emplacements.put("etatBtnPageA", "active");
				emplacements.put("etatBtnPageB", "");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "2");
				emplacements.put("pageC", "3");
				emplacements.put("pageD", "4");
				emplacements.put("pageE", "5");
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "1");
				break;
			case 2:
				casActuel = "normal2";
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "active");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "2");
				emplacements.put("pageC", "3");
				emplacements.put("pageD", "4");
				emplacements.put("pageE", "5");
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "2");
				break;
			case 3:
				casActuel = "normal3";
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "");
				emplacements.put("etatBtnPageC", "active");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "2");
				emplacements.put("pageC", "3");
				emplacements.put("pageD", "4");
				emplacements.put("pageE", "5");
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "3");
				break;
			case 4:
				casActuel = "normal4";
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "active");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "2");
				emplacements.put("pageC", "3");
				emplacements.put("pageD", "4");
				emplacements.put("pageE", "5");
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "4");
				break;
			case 1395:
				casActuel = "normal1395";
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "disabled");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "active");
				emplacements.put("etatBtnSuivant", "disabled");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "...");
				emplacements.put("pageC", String.valueOf(valeurIntMoinsTrois));
				emplacements.put("pageD", String.valueOf(valeurIntMoinsDeux));
				emplacements.put("pageE", String.valueOf(valeurIntMoinsUn));
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "1395");
				break;
			default:
				switch(ecartDroite) {
				case 1:
					casActuel = "default1";
					emplacements.put("etatBtnPrecedent", "");
					emplacements.put("etatBtnPageA", "");
					emplacements.put("etatBtnPageB", "disabled");
					emplacements.put("etatBtnPageC", "");
					emplacements.put("etatBtnPageD", "");
					emplacements.put("etatBtnPageE", "active");
					emplacements.put("etatBtnPageF", "disabled");
					emplacements.put("etatBtnPageG", "");
					emplacements.put("etatBtnSuivant", "");
					emplacements.put("pageA", "1");
					emplacements.put("pageB", "...");
					emplacements.put("pageC", String.valueOf(valeurIntMoinsDeux));
					emplacements.put("pageD", String.valueOf(valeurIntMoinsUn));
					emplacements.put("pageE", valeurString);
					emplacements.put("pageF", "...");
					emplacements.put("pageG", "1395");
					emplacements.put("pageActuelle", valeurString);
					break;
				case 2:
					casActuel = "default2";
					emplacements.put("etatBtnPrecedent", "");
					emplacements.put("etatBtnPageA", "");
					emplacements.put("etatBtnPageB", "disabled");
					emplacements.put("etatBtnPageC", "");
					emplacements.put("etatBtnPageD", "active");
					emplacements.put("etatBtnPageE", "");
					emplacements.put("etatBtnPageF", "disabled");
					emplacements.put("etatBtnPageG", "");
					emplacements.put("etatBtnSuivant", "");
					emplacements.put("pageA", "1");
					emplacements.put("pageB", "...");
					emplacements.put("pageC", String.valueOf(valeurIntMoinsUn));
					emplacements.put("pageD", valeurString);
					emplacements.put("pageE", String.valueOf(valeurIntPlusUn));
					emplacements.put("pageF", "...");
					emplacements.put("pageG", "1395");
					emplacements.put("pageActuelle", valeurString);
					break;
				default:
					casActuel = "defaultDefault";
					emplacements.put("etatBtnPrecedent", "");
					emplacements.put("etatBtnPageA", "");
					emplacements.put("etatBtnPageB", "disabled");
					emplacements.put("etatBtnPageC", "active");
					emplacements.put("etatBtnPageD", "");
					emplacements.put("etatBtnPageE", "");
					emplacements.put("etatBtnPageF", "disabled");
					emplacements.put("etatBtnPageG", "");
					emplacements.put("etatBtnSuivant", "");
					emplacements.put("pageA", "1");
					emplacements.put("pageB", "...");
					emplacements.put("pageC", valeurString);
					emplacements.put("pageD", String.valueOf(valeurIntPlusUn));
					emplacements.put("pageE", String.valueOf(valeurIntPlusDeux));
					emplacements.put("pageF", "...");
					emplacements.put("pageG", "1395");
					emplacements.put("pageActuelle", valeurString);
				}
			}
			
		}
		
		else if(request.getParameter("suivant") != null) {
			valeurString = request.getParameter("suivant");
			ancienneValeurInt = Integer.parseInt(valeurString);
			valeurInt = ancienneValeurInt + 1;
			valeurString = String.valueOf(valeurInt);
			valeurIntMoinsUn = valeurInt -1;
			valeurIntMoinsDeux = valeurInt -2;
			valeurIntMoinsTrois = valeurInt-3;
			valeurIntPlusUn = valeurInt +1;
			valeurIntPlusDeux = valeurInt + 2;
			ecartDroite = 1395-valeurInt;
			switch(valeurInt) {
			case 1:
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "active");
				emplacements.put("etatBtnPageB", "");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "2");
				emplacements.put("pageC", "3");
				emplacements.put("pageD", "4");
				emplacements.put("pageE", "5");
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "1");
				break;
			case 2:
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "active");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "2");
				emplacements.put("pageC", "3");
				emplacements.put("pageD", "4");
				emplacements.put("pageE", "5");
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "2");
				break;
			case 3:
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "");
				emplacements.put("etatBtnPageC", "active");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "2");
				emplacements.put("pageC", "3");
				emplacements.put("pageD", "4");
				emplacements.put("pageE", "5");
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "3");
				break;
			case 4:
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "active");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "");
				emplacements.put("etatBtnSuivant", "");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "2");
				emplacements.put("pageC", "3");
				emplacements.put("pageD", "4");
				emplacements.put("pageE", "5");
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "4");
				break;
			case 1395:
				emplacements.put("etatBtnPrecedent", "");
				emplacements.put("etatBtnPageA", "");
				emplacements.put("etatBtnPageB", "disabled");
				emplacements.put("etatBtnPageC", "");
				emplacements.put("etatBtnPageD", "");
				emplacements.put("etatBtnPageE", "");
				emplacements.put("etatBtnPageF", "disabled");
				emplacements.put("etatBtnPageG", "active");
				emplacements.put("etatBtnSuivant", "disabled");
				emplacements.put("pageA", "1");
				emplacements.put("pageB", "...");
				emplacements.put("pageC", String.valueOf(valeurIntMoinsTrois));
				emplacements.put("pageD", String.valueOf(valeurIntMoinsDeux));
				emplacements.put("pageE", String.valueOf(valeurIntMoinsUn));
				emplacements.put("pageF", "...");
				emplacements.put("pageG", "1395");
				emplacements.put("pageActuelle", "1395");
				break;
			default:
				switch(ecartDroite) {
				case 1:
					emplacements.put("etatBtnPrecedent", "");
					emplacements.put("etatBtnPageA", "");
					emplacements.put("etatBtnPageB", "disabled");
					emplacements.put("etatBtnPageC", "");
					emplacements.put("etatBtnPageD", "");
					emplacements.put("etatBtnPageE", "active");
					emplacements.put("etatBtnPageF", "disabled");
					emplacements.put("etatBtnPageG", "");
					emplacements.put("etatBtnSuivant", "");
					emplacements.put("pageA", "1");
					emplacements.put("pageB", "...");
					emplacements.put("pageC", String.valueOf(valeurIntMoinsDeux));
					emplacements.put("pageD", String.valueOf(valeurIntMoinsUn));
					emplacements.put("pageE", valeurString);
					emplacements.put("pageF", "...");
					emplacements.put("pageG", "1395");
					emplacements.put("pageActuelle", valeurString);
					break;
				case 2:
					emplacements.put("etatBtnPrecedent", "");
					emplacements.put("etatBtnPageA", "");
					emplacements.put("etatBtnPageB", "disabled");
					emplacements.put("etatBtnPageC", "");
					emplacements.put("etatBtnPageD", "active");
					emplacements.put("etatBtnPageE", "");
					emplacements.put("etatBtnPageF", "disabled");
					emplacements.put("etatBtnPageG", "");
					emplacements.put("etatBtnSuivant", "");
					emplacements.put("pageA", "1");
					emplacements.put("pageB", "...");
					emplacements.put("pageC", String.valueOf(valeurIntMoinsUn));
					emplacements.put("pageD", valeurString);
					emplacements.put("pageE", String.valueOf(valeurIntPlusUn));
					emplacements.put("pageF", "...");
					emplacements.put("pageG", "1395");
					emplacements.put("pageActuelle", valeurString);
					break;
				default:
					emplacements.put("etatBtnPrecedent", "");
					emplacements.put("etatBtnPageA", "");
					emplacements.put("etatBtnPageB", "disabled");
					emplacements.put("etatBtnPageC", "active");
					emplacements.put("etatBtnPageD", "");
					emplacements.put("etatBtnPageE", "");
					emplacements.put("etatBtnPageF", "disabled");
					emplacements.put("etatBtnPageG", "");
					emplacements.put("etatBtnSuivant", "");
					emplacements.put("pageA", "1");
					emplacements.put("pageB", "...");
					emplacements.put("pageC", valeurString);
					emplacements.put("pageD", String.valueOf(valeurIntPlusUn));
					emplacements.put("pageE", String.valueOf(valeurIntPlusDeux));
					emplacements.put("pageF", "...");
					emplacements.put("pageG", "1395");
					emplacements.put("pageActuelle", valeurString);
				}
			}
			
		}
		
		classementCommuneDAO = new ClassementCommuneDAO(dataSource);
		ArrayList<ClassementCommune> classementCommune = classementCommuneDAO.getClassement(valeurInt);
		String choix = "commune";
		request.setAttribute("classement", classementCommune);
		request.setAttribute("choix", choix);
		request.setAttribute("emplacements", emplacements);
		int variableRang = (Integer.parseInt(emplacements.get("pageActuelle"))-1)*25;
		request.setAttribute("variableRang", variableRang);
		request.setAttribute("casActuel", casActuel);
		RequestDispatcher dispatcher = request.getRequestDispatcher("./Classement.jsp");
		dispatcher.forward(request, response);
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
		emplacements.put("etatBtnPrecedent", "disabled");
		emplacements.put("etatBtnPageA", "active");
		emplacements.put("etatBtnPageB", "");
		emplacements.put("etatBtnPageC", "");
		emplacements.put("etatBtnPageD", "");
		emplacements.put("etatBtnPageE", "");
		emplacements.put("etatBtnPageF", "disabled");
		emplacements.put("etatBtnPageG", "");
		emplacements.put("etatBtnSuivant", "");
		emplacements.put("pageA", "1");
		emplacements.put("pageB", "2");
		emplacements.put("pageC", "3");
		emplacements.put("pageD", "4");
		emplacements.put("pageE", "5");
		emplacements.put("pageF", "...");
		emplacements.put("pageG", "1395");
		emplacements.put("pageActuelle", "1");
		
		request.setAttribute("choix", choix);
		request.setAttribute("emplacements", emplacements);
		request.setAttribute("variableRang", 0);
		RequestDispatcher dispatcher = request.getRequestDispatcher("./Classement.jsp");
		dispatcher.forward(request, response);
	}

}
