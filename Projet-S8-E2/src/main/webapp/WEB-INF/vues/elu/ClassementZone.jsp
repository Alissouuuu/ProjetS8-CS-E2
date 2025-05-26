<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="fr.esigelec.models.*" %>
<%String choix = (String) request.getAttribute("choix");
String typeZones=null,provenance=null;
ArrayList<IClassement> classement = null;
HashMap<String,String> emplacements = null;
emplacements = (HashMap<String,String>) request.getAttribute("emplacements");
int variableRang = (Integer) request.getAttribute("variableRang");
String casActuel = (String) request.getAttribute("casActuel");
provenance = (String) request.getAttribute("provenance");



if(choix!=null && !choix.isBlank()){
	if(choix.equals("region")){
		classement = (ArrayList<IClassement>) request.getAttribute("classement");
		typeZones = "régions";
	}
	else if (choix.equals("commune")){
		classement = (ArrayList<IClassement>) request.getAttribute("classement");
		typeZones = "communes";
	}
}%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Classement</title>
		
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/styles/styleHeader.css">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/styles/styleMember.css">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/styles/styleFlex.css">
	</head>
	<body>
	<%@include file="headerElu.jsp"%>
		<main>
		<h1>Voici le classement des <%=typeZones%> en fonction de leur nombre de licencié(e)s</h1>
		<h2>Page actuelle : <%=emplacements.get("pageActuelle") %></h2>
		<table>
			<thead>
				<tr>
					<th>Nom</th>
					<th>Nombre total de licencié(e)s</th>
					<th>Nombre de licenciée(s)</th>
					<th>Nombre de licencié(s)</th>
					
					<th>Jeunes moins 15 ans</th><!-- faire pour tt les tranches d'âge -->
					<th>Jeunes filles moins 15 ans</th>
					<th>Jeunes garçons 15 ans</th>
					
					<th>Jeunes 15 à 24 ans</th>
					<th>Jeunes femmes 15 à 24 ans</th>
					<th>Jeunes hommes 15 à 24 ans</th>
					
					<th>Adultes 25 à 34 ans</th>
					<th>Femmes 25 à 34 ans</th>
					<th>Hommes 25 à 34 ans</th>
					
					<th>Adultes 35 à 49 ans</th>
					<th>Femmes 35 à 49 ans</th>
					<th>Hommes 35 à 49 ans</th>
					
					<th>Adultes 50 à 79 ans</th>
					<th>Femmes 50 à 79 ans</th>
					<th>Hommes 50 à 79 ans</th>
					
					<th>Séniors plus 80 ans</th>
					<th>Femmes plus 80 ans</th>
					<th>Hommes plus 80 ans</th>

					<th>Rang</th>
				</tr>
			</thead>
			<tbody>

<% int licences,licencesFemmes,licencesHommes,licencesHommes114,licencesHommes1524,licencesHommes2534,licencesHommes3549,licencesHommes5079,licencesHommes8099,
licencesFemmes114,licencesFemmes1524,licencesFemmes2534,licencesFemmes3549,licencesFemmes5079,licencesFemmes8099,
licencesTotales114,licencesTotales1524,licencesTotales2534,licencesTotales3549,licencesTotales5079,licencesTotales8099;
ZoneGeo zoneGeo=null;
for(int i=0;i<classement.size();i++){
	licences = classement.get(i).getLicences();
	licencesFemmes = classement.get(i).getLicencesFemmes();
	licencesHommes = classement.get(i).getLicencesHommes();
	licencesHommes114 = classement.get(i).getLicencesHommes114();
	licencesHommes1524 = classement.get(i).getLicencesHommes1524();
	licencesHommes2534 = classement.get(i).getLicencesHommes2534();
	licencesHommes3549 = classement.get(i).getLicencesHommes3549();
	licencesHommes5079 = classement.get(i).getLicencesHommes5079();
	licencesHommes8099 = classement.get(i).getLicencesHommes8099();
	licencesFemmes114 = classement.get(i).getLicencesFemmes114();
	licencesFemmes1524 = classement.get(i).getLicencesFemmes1524();
	licencesFemmes2534 = classement.get(i).getLicencesFemmes2534();
	licencesFemmes3549 = classement.get(i).getLicencesFemmes3549();
	licencesFemmes5079 = classement.get(i).getLicencesFemmes5079();
	licencesFemmes8099 = classement.get(i).getLicencesFemmes8099();
	licencesTotales114 = classement.get(i).getLicencesTotales114();
	licencesTotales1524 = classement.get(i).getLicencesTotales1524();
	licencesTotales2534 = classement.get(i).getLicencesTotales2534();
	licencesTotales3549 = classement.get(i).getLicencesTotales3549();
	licencesTotales5079 = classement.get(i).getLicencesTotales5079();
	licencesTotales8099 = classement.get(i).getLicencesTotales8099();
	
	if(choix.equals("region")){
		ClassementRegion cr = (ClassementRegion) classement.get(i);
		zoneGeo = cr.getRegion();
	}
	else if(choix.equals("commune")){
		ClassementCommune cc = (ClassementCommune) classement.get(i);
		zoneGeo = cc.getCommune();
	}
	%>
	<tr>
		<td><%=zoneGeo.getNom()%></td>
		<td><%=licences%></td>
		<td><%=licencesFemmes%></td>
		<td><%=licencesHommes%></td>
		
		<td><%=licencesTotales114%></td>
		<td><%=licencesFemmes114%></td>
		<td><%=licencesHommes114%></td>
		
		<td><%=licencesTotales1524%></td>
		<td><%=licencesFemmes1524%></td>
		<td><%=licencesHommes1524%></td>
		
		<td><%=licencesTotales2534%></td>
		<td><%=licencesFemmes2534%></td>
		<td><%=licencesHommes2534%></td>
		
		<td><%=licencesTotales3549%></td>
		<td><%=licencesFemmes3549%></td>
		<td><%=licencesHommes3549%></td>
		
		<td><%=licencesTotales5079%></td>
		<td><%=licencesFemmes5079%></td>
		<td><%=licencesHommes5079%></td>
		
		<td><%=licencesTotales8099%></td>
		<td><%=licencesFemmes8099%></td>
		<td><%=licencesHommes8099%></td>		
		
		<td><%=variableRang+(i+1) %></td>
	</tr>
		
<% }%>
		</tbody>
		</table>
		<%if(choix.equals("commune")){
			System.out.println(provenance);
			%>
			<form method="get" action="./ClassementZone">
			  <div class="pagination">
			  	<button type="submit" name="precedent" value="<%=emplacements.get("pageActuelle")%>" <%=emplacements.get("etatBtnPrecedent") %>>&laquo;</button>
			    <button type="submit" name="a" value="<%=emplacements.get("pageA") %>" <%=emplacements.get("etatBtnPageA") %>><%=emplacements.get("pageA") %></button>
			    <button type="submit" name="b" value="<%=emplacements.get("pageB") %>" <%=emplacements.get("etatBtnPageB") %>><%=emplacements.get("pageB") %></button>
			    <button type="submit" name="c" value="<%=emplacements.get("pageC") %>" <%=emplacements.get("etatBtnPageC") %>><%=emplacements.get("pageC") %></button>
			    <button type="submit" name="d" value="<%=emplacements.get("pageD") %>" <%=emplacements.get("etatBtnPageD") %>><%=emplacements.get("pageD") %></button>
			    <button type="submit" name="e" value="<%=emplacements.get("pageE") %>" <%=emplacements.get("etatBtnPageE") %>><%=emplacements.get("pageE") %></button>
			    <button type="submit" name="f" value="<%=emplacements.get("pageF") %>" <%=emplacements.get("etatBtnPageF") %>><%=emplacements.get("pageF") %></button>
			    <button type="submit" name="g" value="<%=emplacements.get("pageG") %>" <%=emplacements.get("etatBtnPageG") %>><%=emplacements.get("pageG") %></button>
			    <button type="submit" name="suivant" value="<%=emplacements.get("pageActuelle")%>" <%=emplacements.get("etatBtnSuivant") %>>&raquo;</button>
			    <input type="hidden" name="provenance" value="<%= provenance %>" />
			  </div>
			</form>
		<% }%>
		
		<a href="./indexElu"><button>Accueil</button></a>
		<a href="./ExportTableauPDF?choix=<%=choix%>"><button>Exporter en PDF</button></a>
		<a href="./ExportTableauExcel?choix=<%=choix%>"><button>Exporter en XLSX</button></a>
		</main>
			<footer class="text-white text-center py-3">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>
	</body>
</html>