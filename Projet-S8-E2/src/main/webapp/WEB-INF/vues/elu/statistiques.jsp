<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Elu - Statistiques</title>
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
	<!-- Formulaire de recherche -->
	<main>
		<div class="container">
			<h2>Filtres de recherche</h2>
			<form method="post" action="Recherche">
				<div class="row filtre-section mt-4">
					<!-- Filtre Région -->
					<div class="col-md-4">
						<label class="form-label">Région :</label> <select name="region"
							class="form-select">
							<option value="">Sélectionner une région</option>
							<option value=""></option>

						</select>
					</div>

					<!-- Filtre Département -->
					<div class="col-md-4">
						<label class="form-label">Département :</label> <select
							name="departement" class="form-select">
							<option value="">Sélectionner un département</option>

							<option value=""></option>

						</select>
					</div>

					<!-- Filtre Ville -->
					<div class="col-md-4">
						<label class="form-label">Commune :</label> <select name="commune"
							class="form-select">
							<option value="">Sélectionner une commune</option>
							<option value=""></option>

						</select>
					</div>
				</div>
				<!-- filtre federation -->
				<div class="row filtre-section mt-4">
				<div class="col align-self-start">
					<label for="filtre-age" class="form-label">Fédération
						sportive :</label> 
						<select id="federation" name="federation"
						class="form-select " style="width:31.8%">
						<option value="" selected>Sélectionner une fédération</option>
						<option value=""></option>
					</select>
				</div>
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