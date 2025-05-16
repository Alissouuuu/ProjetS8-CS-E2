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
		<td><%=i+1 %></td>
	</tr>
		
<% }%>
		</tbody>
		</table>
		<div class="pagination">
			<a href="#">&laquo;</a>
			<a href="./Classement?choix=<%=choix %>&code=a&numPage=<%=emplacements.get("pageA") %>" class="<%=emplacements.get("btnPageA") %>"><%=emplacements.get("pageA") %></a>
			<a href="./Classement?choix=<%=choix %>&code=b&numPage=<%=emplacements.get("pageB") %>" class="<%=emplacements.get("btnPageB") %>"><%=emplacements.get("pageB") %></a> <!--pageA -->
			<a href="./Classement?choix=<%=choix %>&code=c&numPage=<%=emplacements.get("pageC") %>" class="<%=emplacements.get("btnPageC") %>"><%=emplacements.get("pageC") %></a>
			<a href="./Classement?choix=<%=choix %>&code=d&numPage=<%=emplacements.get("pageD") %>" class="<%=emplacements.get("btnPageD") %>"><%=emplacements.get("pageD") %></a>
			<a href="./Classement?choix=<%=choix %>&code=e&numPage=<%=emplacements.get("pageE") %>" class="<%=emplacements.get("btnPageE") %>"><%=emplacements.get("pageE") %></a>
			<a href="./Classement?choix=<%=choix %>&code=f&numPage=<%=emplacements.get("pageF") %>" class="<%=emplacements.get("btnPageF") %>"><%=emplacements.get("pageF") %></a>
			<a href="#">&raquo;</a>
		</div>
	</body>
</html>