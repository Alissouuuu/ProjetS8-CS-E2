<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.esigelec.models.Region" %>
<%@ page import="fr.esigelec.models.Departement" %>
<%@ page import="fr.esigelec.models.Federation" %>
<%@ page import="fr.esigelec.models.Commune" %>

<%
ArrayList<Region> regionsFiltres = null;
ArrayList<Departement> departementsFiltres = null;
ArrayList<Federation> federationsFiltres = null;

boolean regionsFiltresVide=false,departementsFiltresVide=false,federationsFiltresVide=false;

regionsFiltres = (ArrayList<Region>) request.getAttribute("regions");
departementsFiltres = (ArrayList<Departement>) request.getAttribute("departements");
federationsFiltres = (ArrayList<Federation>) request.getAttribute("federations");

if(regionsFiltres == null)
	regionsFiltres = new ArrayList<>();
if(regionsFiltres.size()==0)
	regionsFiltresVide = true;

if(departementsFiltres == null)
	departementsFiltres = new ArrayList<>();
if(departementsFiltres.size()==0)
	departementsFiltresVide = true;

if(federationsFiltres == null)
	federationsFiltres = new ArrayList<>();
if(federationsFiltres.size()==0)
	federationsFiltresVide = true;
%>	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Elu - Classement Licenciés</title>
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
	<!-- header-->

	<%@include file="headerElu.jsp"%>
	<main>

		<!-- Formulaire de recherche -->
		<div class="container">
			<h2>Filtres de recherche</h2>
			<form method="post" action="">
				<div class="row filtre-section">
					<!-- Filtre Région -->
					<div class="col-md-5">
						<label class="form-label">Région :</label> 
						<select name="region"
							class="form-select">
							<option value="">Sélectionner une région</option>
							<%if(!regionsFiltresVide){
								for(Region region : regionsFiltres){
									%>
									<option value="<%=region.getCodeRegion()%>"><%=region.getNom() %></option>
								<% }
							}
							%>
							

						</select>
					</div>
					<!-- Filtre Département -->
					<div class="col-md-5">
						<label class="form-label">Département :</label> 
						<select name="departement"
							class="form-select">
							<option value="">Sélectionner un département</option>
							<%if(!departementsFiltresVide){
								for(Departement departement : departementsFiltres){
									%>
									<option value="<%=departement.getCodeDepartement()%>"><%=departement.getNom() %></option>
								<% }
							}
							%>
							

						</select>
					</div>
					

					<!-- Filtre Ville -->
					<div class="col-md-5">
						<label class="form-label">Commune :</label> <select name="commune"
							class="form-select">
							<option value="">Sélectionner une commune</option>
							<option value=""></option>

						</select>
						<label class="form-label" for="code-postal">Ou son code postal :</label>
						<input type="text" name="code-postal" id="code-postal" class="form-control">
					</div>
				</div>
				
				<!-- Filtre Fédération -->
					<div class="col-md-5">
						<label class="form-label">Fédération :</label> 
						<select name="federation"
							class="form-select">
							<option value="">Sélectionner une fédération</option>
							<%if(!federationsFiltresVide){
								for(Federation federation : federationsFiltres){
									%>
									<option value="<%=federation.getCodeFederation()%>"><%=federation.getNom() %></option>
								<% }
							}
							%>
							

						</select>
					</div>

				<!-- filtre age et genre -->
				<div class="row filtre-section">
					<div class="col-md-5">


						<label for="filtre-age" class="form-label">Tranche d'âge :</label>
						<select id="age" name="age" class="form-select">
							<option value="" selected>-- Toutes les tranches --</option>
							<option value="moins18">Moins de 18 ans</option>
							<option value="18_25">18–25 ans</option>
							<option value="26_35">26–35 ans</option>
							<option value="36_50">36–50 ans</option>
							<option value="plus50">Plus de 50 ans</option>
						</select>


					</div>
					<div class="col-md-5">
						<label class="form-label">Genre :</label> <select
							id="genre" name="genre" class="form-select">

							<option value="femme">Femme</option>
							<option value="homme">Homme</option>

						</select>
						
					</div>
				
<p style="margin-top:1.25rem;"><em>Astuce : vous pouvez combiner les filtres pour un affichage plus précis.</em></p>
				</div>

				<!-- Bouton de soumission -->
				<div class="text-center mt-4">
					<button type="submit" class="btn btn-primary">Rechercher</button>
				</div>
			</form>
		</div>



	</main>
	<footer class="text-white text-center py-3">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>
</body>
</html>