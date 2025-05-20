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

<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styleMember.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}//styles/styleHeader.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}//styles/styleFlex.css">
</head>
<body>
	<!-- header-->
<jsp:include page="/WEB-INF/header.jsp" />
	
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
				<div class="filtre-section mt-4 row" >
				<div class="col-md-4 align-self-start">
					<label for="filtre-age" class="form-label">Fédération
						sportive :</label> 
						<select id="federation" name="federation"
						class="form-select ">
						<option value="" selected>Sélectionner une fédération</option>
						<option value=""></option>
					</select>
				</div>
					<div class="col-md-4">
					<label for="filtre-age" class="form-label" style="margin-right:2rem;">Rayon  :</label> <label id="rangeValue" style="">50 Km</label>
						 <input type="range" class="form-range" min="0" max="20" value="5" id="rangeInput">
						 
				
						 
				</div>
				<!-- khas shi 7el l spacing please -->
						<div class="col-md-4"></div>
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
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- afficher la valeur du ange-input -->
    <script>
        const rangeInput = document.getElementById('rangeInput');
        const rangeValue = document.getElementById('rangeValue');
        
        rangeInput.addEventListener('input', function() {
            rangeValue.textContent = this.value+" Km";
        });
    </script>
	
</body>
</html>