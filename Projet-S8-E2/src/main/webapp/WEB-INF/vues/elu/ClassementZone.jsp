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
		<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
		
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
		<table class="table">
			<thead style="th">
				<tr>
					<th scope="col" style="background-color: darkblue; color: white;">Nom</th>
					<th scope="col" style="background-color: darkblue; color: white;">Nombre total de licencié(e)s</th>
					<th scope="col" style="background-color: darkblue; color: white;">Nombre de licenciée(s)</th>
					<th scope="col" style="background-color: darkblue; color: white;">Nombre de licencié(s)</th>
					<% 
					if(provenance.equals("classementToutCritere")){%>
					<th scope="col" style="background-color: darkblue; color: white;">Jeunes moins 15 ans</th><!-- faire pour tt les tranches d'âge -->
					<th scope="col" style="background-color: darkblue; color: white;">Jeunes filles moins 15 ans</th>
					<th scope="col" style="background-color: darkblue; color: white;">Jeunes garçons 15 ans</th>
					
					<th scope="col" style="background-color: darkblue; color: white;">Jeunes 15 à 24 ans</th>
					<th scope="col" style="background-color: darkblue; color: white;">Jeunes femmes 15 à 24 ans</th>
					<th scope="col" style="background-color: darkblue; color: white;">Jeunes hommes 15 à 24 ans</th>
					
					<th scope="col" style="background-color: darkblue; color: white;">Adultes 25 à 34 ans</th>
					<th scope="col" style="background-color: darkblue; color: white;">Femmes 25 à 34 ans</th>
					<th scope="col" style="background-color: darkblue; color: white;">Hommes 25 à 34 ans</th>
					
					<th scope="col" style="background-color: darkblue; color: white;">Adultes 35 à 49 ans</th>
					<th scope="col" style="background-color: darkblue; color: white;">Femmes 35 à 49 ans</th>
					<th scope="col" style="background-color: darkblue; color: white;">Hommes 35 à 49 ans</th>
					
					<th scope="col" style="background-color: darkblue; color: white;">Adultes 50 à 79 ans</th>
					<th scope="col" style="background-color: darkblue; color: white;">Femmes 50 à 79 ans</th>
					<th scope="col" style="background-color: darkblue; color: white;">Hommes 50 à 79 ans</th>
					
					<th scope="col" style="background-color: darkblue; color: white;">Séniors plus 80 ans</th>
					<th scope="col" style="background-color: darkblue; color: white;">Femmes plus 80 ans</th>
					<th scope="col" style="background-color: darkblue; color: white;">Hommes plus 80 ans</th>
					<%} %>
					<th scope="col" style="background-color: darkblue; color: white;">Rang</th>
				</tr>
			</thead>
			<tbody>

<% int licences,licencesFemmes,licencesHommes,licencesHommes114=0,licencesHommes1524=0,licencesHommes2534=0,licencesHommes3549=0,licencesHommes5079=0,licencesHommes8099=0,
licencesFemmes114=0,licencesFemmes1524=0,licencesFemmes2534=0,licencesFemmes3549=0,licencesFemmes5079=0,licencesFemmes8099=0,
licencesTotales114=0,licencesTotales1524=0,licencesTotales2534=0,licencesTotales3549=0,licencesTotales5079=0,licencesTotales8099=0;
ZoneGeo zoneGeo=null;
for(int i=0;i<classement.size();i++){
	licences = classement.get(i).getLicences();
	licencesFemmes = classement.get(i).getLicencesFemmes();
	licencesHommes = classement.get(i).getLicencesHommes();
	if(provenance.equals("classementToutCritere")){
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
	}
	
	
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
		<% 
		if(provenance.equals("classementToutCritere")){%>
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
		<% }%>
		
			
		
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
			  	<button class="page-link" type="submit" name="precedent" value="<%=emplacements.get("pageActuelle")%>" <%=emplacements.get("etatBtnPrecedent") %>>&laquo;</button>
			    <button class="page-link" type="submit" name="a" value="<%=emplacements.get("pageA") %>" <%=emplacements.get("etatBtnPageA") %>><%=emplacements.get("pageA") %></button>
			    <button class="page-link" type="submit" name="b" value="<%=emplacements.get("pageB") %>" <%=emplacements.get("etatBtnPageB") %>><%=emplacements.get("pageB") %></button>
			    <button class="page-link" type="submit" name="c" value="<%=emplacements.get("pageC") %>" <%=emplacements.get("etatBtnPageC") %>><%=emplacements.get("pageC") %></button>
			    <button class="page-link" type="submit" name="d" value="<%=emplacements.get("pageD") %>" <%=emplacements.get("etatBtnPageD") %>><%=emplacements.get("pageD") %></button>
			    <button class="page-link" type="submit" name="e" value="<%=emplacements.get("pageE") %>" <%=emplacements.get("etatBtnPageE") %>><%=emplacements.get("pageE") %></button>
			    <button class="page-link" type="submit" name="f" value="<%=emplacements.get("pageF") %>" <%=emplacements.get("etatBtnPageF") %>><%=emplacements.get("pageF") %></button>
			    <button class="page-link" type="submit" name="g" value="<%=emplacements.get("pageG") %>" <%=emplacements.get("etatBtnPageG") %>><%=emplacements.get("pageG") %></button>
			    <button class="page-link" type="submit" name="suivant" value="<%=emplacements.get("pageActuelle")%>" <%=emplacements.get("etatBtnSuivant") %>>&raquo;</button>
			    <input type="hidden" name="provenance" value="<%= provenance %>" />
			  </div>
			</form>
		<% }%>
		
		<a  class="page-link" href="./indexElu"><button>Accueil</button></a>
		<a class="page-link" href="./ExportTableauPDF?choix=<%=choix%>"><button>Exporter en PDF</button></a>
		<a  class="page-link" href="./ExportTableauExcel?choix=<%=choix%>"><button>Exporter en XLSX</button></a>
		</main>
			<footer class="text-white text-center py-3">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>
	</body>
</html>