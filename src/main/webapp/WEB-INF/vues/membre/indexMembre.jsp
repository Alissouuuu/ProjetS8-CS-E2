<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>SportiZone - Membre inscrit</title>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleMember.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleHeader.css">

</head>
<body>
	<!-- header-->

	<%@include file="headerMembre.jsp"%>
	<section class="hero-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-10">
					<div class="hero-content text-center">
						<h1 class="hero-title">Bienvenus à SportiZone</h1>
						<c:if test="${not empty successMessage}">
							<div class="alert alert-success alert-dismissible fade show"
								role="alert">
								${successMessage}
								<button type="button" class="btn-close" data-bs-dismiss="alert"
									aria-label="Fermer"></button>
							</div>
						</c:if>

						<p class="hero-subtitle">Un événement, une actualité, une
							information ?</p>
						<p class="hero-subtitle2">Partagez-les en quelques clics et
							tenez le club informé !</p>
						<div class="d-flex justify-content-center gap-3 mb-4">
							<a href="${pageContext.request.contextPath}/createEvent"
								class="btn btn-primary ">Créer un événement</a> <a
								href="${pageContext.request.contextPath}/partagerActu"
								class="btn btn-primary ">Publier une actualité</a>
						</div>
					</div>
				</div>
			</div>
		</div>

	</section>

	<!-- Section -->
	<section class="features-section">
		<div class="container">
			<div class="row">

				<!-- Événements passés -->
				<div class="col-md-4">
					<div class="feature-card">
						<h3 class="feature-title">Événements passés</h3>
						<ul>
							<c:forEach var="e" items="${evenementsPasses}">
								<li>${e.nomEvenement},passéle${e.dateEvenement}</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<!--Actualités -->
				<div class="col-md-4">
					<div class="feature-card">
						<h3 class="feature-title">Actualités partagées</h3>
						<c:forEach var="a" items="${actualites}">
							<div class="card mb-3">
								<div class="card-body">

									<h5 class="card-title">${a.titreActu}</h5>
									<p class="card-text">${a.descriptionActu}</p>


									<form action="${pageContext.request.contextPath}/DeleteActu"
										method="post" class="d-flex justify-content-center"
										onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer cette actulité ?');">
										<input type="hidden" name="id" value="${a.idActu}" />
										<button type="submit" class="btn btn-primary btn-3 text-white">Supprimer</button>
									</form>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>


				<!-- Événements à venir (1 card par événement) -->
				<c:forEach var="e" items="${evenementsAVenir}">
					<div class="col-md-4">
						<div class="feature-card">
							<h3 class="feature-title">Evenement à venir</h3>

							<h5 class="feature-title"
								style="font-size: 1.2em; color: black !important;">Nom de
								l'évenement : ${e.nomEvenement}</h5>
							<p class="feature-text">${e.descriptionEvenement}</p>
							<p class="feature-text">Date : ${e.dateEvenement}</p>
							<p class="feature-text">Heure : ${e.heureEvenement}</p>
							<p class="feature-text">Lieu : ${e.lieuEvenement}</p>
							<p class="feature-text">Nombre maximum :
								${e.nbrMaxParticipants}</p>
							<form action="${pageContext.request.contextPath}/EditEvent"
								method="get" class="d-flex justify-content-center"">

								<input type="hidden" name="id" value="${e.idEvenement}" />
								<button type="submit"
									class="btn btn-primary btn-3 text-white mb-2">Modifier</button>
							</form>

							<form action="${pageContext.request.contextPath}/DeleteEvent"
								method="post" class="d-flex justify-content-center"
								"
								onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer cet événement ?');">
								<input type="hidden" name="id" value="${e.idEvenement}" />
								<button type="submit" class="btn btn-primary btn-3 text-white">Supprimer</button>
							</form>
						</div>
					</div>
				</c:forEach>

			</div>
		</div>
	</section>

	<footer class="text-white text-center py-3 mt-5">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>
	<!-- Bootstrap JS Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
