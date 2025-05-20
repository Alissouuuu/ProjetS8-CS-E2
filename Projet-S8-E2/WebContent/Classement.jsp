<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="fr.esigelec.models.*" %>
<%String choix = (String) request.getAttribute("choix");
String typeZones=null;
ArrayList<IClassement> classement = null;
HashMap<String,String> emplacements = null;
emplacements = (HashMap<String,String>) request.getAttribute("emplacements");
int variableRang = (Integer) request.getAttribute("variableRang");
String casActuel = (String) request.getAttribute("casActuel");


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
	</head>
	<body>
		<h1>Voici le classement des <%=typeZones%> en fonction de leur nombre de licencié(e)s</h1>
		<h2>Page actuelle : <%=emplacements.get("pageActuelle") %></h2>
		<h2>Cas actuel : <%=casActuel %></h2>
		<table>
			<thead>
				<tr>
					<th>Nom</th>
					<th>Nombre de licencié(e)s</th>
					<th>Rang</th>
				</tr>
			</thead>
			<tbody>

<% int licences;
ZoneGeo zoneGeo=null;
for(int i=0;i<classement.size();i++){
	licences = classement.get(i).getLicences();
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
		<td><%=variableRang+(i+1) %></td>
	</tr>
		
<% }%>
		</tbody>
		</table>
		<%if(choix.equals("commune")){
			%>
			<form method="get" action="./Classement">
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
			  </div>
			</form>
		<% }%>
		
		<a href="./Index.jsp"><button>Accueil</button></a>
		<a href="./ExportTableauPDF?choix=<%=choix%>"><button>Exporter en PDF</button></a>
		
		
	</body>
</html>