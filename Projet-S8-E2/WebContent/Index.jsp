<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.esigelec.models.Club" %>
<%@ page import="java.util.ArrayList" %>
<% ArrayList<Club> clubs = null;
boolean listeVide = false;
clubs = (ArrayList<Club>) request.getAttribute("clubs"); 
if(clubs == null)
	clubs = new ArrayList<>();

if(clubs.size() == 0)
	listeVide = true;

%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Rechercher des clubs</title>
</head>
<body>
	<h1>Rechercher des clubs en fonction de la fédération et zone géo</h1>
	<form method="POST" action="./Recherche">
		<div>
			<label for="federation">Fédération</label>
			<select id="federation" name="federation">
				<option value="tout">Tout</option>
				<option value="football">Football</option>
				<option value="basketball">Basketball</option>
				<option value="tennis">Tennis</option>
				<option value="natation">Natation</option>
				<option value="volleyball">Volleyball</option>
				<option value="handball">Handball</option>
				<option value="boxe">Boxe</option>
				<option value="kungfu">Kung-Fu</option>
				<option value="muaythai">Muay-Thai</option>
			</select>
		</div>
		<div>
			<label for="zone">Type de zone géographique</label>
			<select id="zone" name="zone">
				<option value="commune">Tout</option>
				<option value="commune">Commune</option>
				<option value="departement">Département</option>
				<option value="region">Région</option>
			</select>
			<input type="text" name="nom-zone">
		</div>
		<button type="submit">Rechercher</button>
	</form>
	<form method="POST" action="./Classement">
		<label for="choix">Voir le classement par :</label>
		<select id="choix" name="choix">
			<option value="choisir">Choisir</option>
			<option value="commune">Commune</option>
			<option value="region">Région</option>
		</select>
		<button type="submit">Voir</button>
	</form>
	<% if(!listeVide){
		for(Club club : clubs){
			%>
			<div>
				<h2><%=club.getNom() %></h2>
			</div>
		<%}
		
	}%>
</body>
</html>