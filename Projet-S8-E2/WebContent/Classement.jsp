<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.esigelec.models.*" %>
<%String choix = (String) request.getAttribute("choix");
String typeZones=null;
ArrayList<IClassement> classement = null;
if(choix!=null && choix.isBlank()){
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
	<div>
		<h2><%=zoneGeo.getNom()%> : <%=licences%> licencié(e)s</h2>
	</div>
	
<% }%>
</body>
</html>