<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>SportiZone - Partager une actu</title>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="../styles/styleCreateEvent.css">
<link rel="stylesheet" href="../styles/styleHeader.css">
</head>
<body>
	<!-- header membre == header + section-->
	<%@include file="headerMembre.jsp"%>
	<section class="hero-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-10">
					<div class="hero-content text-center">
						<h1 class="hero-title">Partager une actualité, résultat d'une compétition ou un conseil d'entrainement ou nutritionnel</h1>
					</div>
				</div>
			</div>
		</div>
	</section>
	
		<div class="container py-4">
		<div class="card shadow-lg mx-auto" style="max-width: 900px;">
			<div class="card-header card-header-custom">
				<h2 class="card-title  mb-0">Publier une actualité</h2>
				<p class="card-text text-light-emphasis medium mb-0"
					style="color: white !important;">Remplissez les informations
					pour les partager avec votre communauté</p>
			</div>
			<div class="card-body">
				<form method="post">
					<div class="mb-4">
					
							<label for="actu-name" class="form-label">Titre de l'actualité
								</label> <input type="text" class="form-control"
								id="actu-name">
						
					</div>
				
					<div class="mb-4">
						<label for="actu-description" class="form-label">Description
							</label>
						<textarea class="form-control" id="actu-description" rows="5"></textarea>
					</div>



					<div
						class="card-footer bg-light d-flex flex-column flex-sm-row gap-2 justify-content-end mt-4 p-3">
						<a href="index.jsp" class="btn btn-primary-custom">Annuler</a>

						<button type="submit" class="btn btn-primary-custom text-white">Partager l'actualité
							</button>
					</div>
				</form>
			</div>
		</div>
		</div>
	<div class="container py-4">
		<div class="card shadow-lg mx-auto" style="max-width: 900px;">
			<div class="card-header card-header-custom">
				<h2 class="card-title  mb-0">Partager un conseil</h2>
				<p class="card-text text-light-emphasis medium mb-0"
					style="color: white !important;">Remplissez les informations
					pour les partager avec votre communauté</p>
			</div>
			<div class="card-body">
				<form method="post">
					<div class="mb-4">
					
							<label for="conseil-name" class="form-label">Titre
								</label> <input type="text" class="form-control"
								id="conseil-name">
						
					</div>
				
					<div class="mb-4">
						<label for="conseil-description" class="form-label">Description
							</label>
						<textarea class="form-control" id="conseil-description" rows="5"
							placeholder="Décrivez votre événement, les règles, ce que les participants doivent apporter, etc."></textarea>
					</div>



					<div
						class="card-footer bg-light d-flex flex-column flex-sm-row gap-2 justify-content-end mt-4 p-3">
						<a href="index.jsp" class="btn btn-primary-custom">Annuler</a>

						<button type="submit" class="btn btn-primary-custom text-white">Partager l'actualité
							</button>
					</div>
				</form>
			</div>
		</div>
		</div>

	<div class="container py-4">
		<div class="card shadow-lg mx-auto" style="max-width: 900px;">
			<div class="card-header card-header-custom">
				<h2 class="card-title  mb-0">Résultats des compétitions</h2>
				<p class="card-text text-light-emphasis medium mb-0"
					style="color: white !important;">Remplissez les informations
					pour les partager avec votre communauté</p>
			</div>
			<div class="card-body">
				<form method="post">
					<div class="row mb-4">
						<div class="col-md-6 mb-3 mb-md-0">
							<label for="event-name" class="form-label">Nom de la
								comptétiton</label> <input type="text" class="form-control"
								id="event-name">
						</div>
						<div class="col-md-6 mb-3 mb-md-0">
							<label for="event-date" class="form-label">Date</label>
							<div class="position-relative">

								<input type="date" class="form-control form-control-icon"
									id="event-date">
							</div>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-6 mb-3 mb-md-0">
							<label for="event-location" class="form-label">Lieu</label>
							<div class="position-relative">

								<input type="text" class="form-control form-control-icon"
									id="event-location">
							</div>
						</div>
						<div class="col-md-6 mb-3 mb-md-0">
							<label for="max-participants" class="form-label">Score</label>
							<div class="position-relative">

								<input type="number" class="form-control form-control-icon"
									id="max-participants">
							</div>
						</div>

					</div>

					<div class="mb-4">
						<label for="event-description" class="form-label">Description
							</label>
						<textarea class="form-control" id="event-description" rows="5"
							placeholder="Décrivez votre événement, les règles, ce que les participants doivent apporter, etc."></textarea>
					</div>



					<div
						class="card-footer bg-light d-flex flex-column flex-sm-row gap-2 justify-content-end mt-4 p-3">
						<a href="index.jsp" class="btn btn-primary-custom">Annuler</a>

						<button type="submit" class="btn btn-primary-custom text-white">Partager les résultats
							</button>
					</div>
				</form>
			</div>
		</div>

		<div class="text-center mt-4">
			<a href="indexMembre.jsp" class="text-decoration-none"
				style="color: white; font-size: larger;">Retour à l'accueil</a>
		</div>
	</div>
	<footer class="text-white text-center py-3 mt-5">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>

</body>
</html>