<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
				<option value="commune">Commune</option>
				<option value="departement">Département</option>
				<option value="region">Région</option>
			</select>
			<input type="text" name="nom-zone">
		</div>
		<button type="submit">Rechercher</button>
	</form>
</body>
</html>