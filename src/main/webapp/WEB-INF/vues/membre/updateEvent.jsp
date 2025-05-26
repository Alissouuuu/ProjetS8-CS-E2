<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>SportiZone - Partager un évenement</title>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleCreateEvent.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleHeader.css">

</head>
<body>
	<!-- header membre == header + section-->

	<%@include file="headerMembre.jsp"%>
	<section class="hero-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-10">
					<div class="hero-content text-center">
						<h1 class="hero-title">Modifier un Événement Sportif</h1>


					</div>
				</div>
			</div>
		</div>
	</section>

	<div class="container py-4">
		<div class="card shadow-lg mx-auto" style="max-width: 900px;">
			<div class="card-header card-header-custom">
				<h2 class="card-title  mb-0">Détails de l'événement</h2>

			</div>
			<div class="card-body">
				<form method="post"
					action="${pageContext.request.contextPath}/EditEvent">
					<div class="row mb-4">
						<div class="col-md-6 mb-3 mb-md-0 w-100">
							<label for="event-name" class="form-label">Nom de
								l'événement<span class="text-danger">*</span>
							</label>
							<c:if test="${not empty erreurNom}">
								<small class="text-danger">${erreurNom}</small>
							</c:if>
							<input type="text" class="form-control" id="event-name"
								name="event-name" value="${evenement.nomEvenement}">
						</div>

					</div>

					<div class="row mb-4">
						<div class="col-md-6 mb-3 mb-md-0">
							<label for="event-date" class="form-label">Date<span
								class="text-danger">*</span></label>
							<c:if test="${not empty erreurDate}">
								<small class="text-danger">${erreurDate}</small>
							</c:if>
							<div class="position-relative">

								<input type="date" class="form-control" id="event-date"
									name="event-date" value="${evenement.dateEvenement}">
							</div>
						</div>
						<div class="col-md-6">
							<label for="event-time" class="form-label">Heure<span
								class="text-danger">*</span></label>
							<c:if test="${not empty erreurHeure}">
								<small class="text-danger">${erreurHeure}</small>
							</c:if>
							<div class="position-relative">

								<input type="time" class="form-control" id="event-time"
									name="event-time" value="${evenement.heureEvenement}">
							</div>
						</div>
					</div>



					<div class="row mb-4">
						<div class="col-md-6 mb-3 mb-md-0">
							<label for="event-location" class="form-label">Lieu<span
								class="text-danger">*</span></label>
							<c:if test="${not empty erreurLieu}">
								<small class="text-danger">${erreurLieu}</small>
							</c:if>
							<div class="position-relative">

								<input type="text" class="form-control" id="event-location"
									name="event-location" value="${evenement.lieuEvenement}">
							</div>
						</div>
						<div class="col-md-6 mb-3 mb-md-0">
							<label for="max-participants" class="form-label">Nombre
								maximum de participants<span class="text-danger">*</span>
							</label>
							<c:if test="${not empty erreurNombre}">
								<small class="text-danger">${erreurNombre}</small>
							</c:if>
							<div class="position-relative">


								<input type="number" class="form-control" id="max-participants"
									name="max-participants" value="${evenement.nbrMaxParticipants}">
							</div>
						</div>

					</div>

					<div class="mb-4">
						<label for="event-description" class="form-label">Description
							de l'événement<span class="text-danger">*</span>
						</label>
						<c:if test="${not empty erreurDescription}">
							<small class="text-danger">${erreurDescription}</small>
						</c:if>
						<textarea class="form-control" name="event-description"
							id="event-description" rows="5">
    ${evenement.descriptionEvenement}
</textarea>
					</div>
<input type="hidden" name="id" value="${evenement.idEvenement}" />

					<div
						class="card-footer bg-light d-flex flex-column flex-sm-row gap-2 justify-content-end mt-4 p-3">
						<a href="${pageContext.request.contextPath}/indexMembre"
							class="btn btn-primary-custom">Annuler</a>

						<button type="submit" class="btn btn-primary-custom text-white">Modifier
							l'événement</button>
					</div>
				</form>
			</div>
		</div>

		<div class="text-center mt-4">
			<a href="${pageContext.request.contextPath}/indexMembre"
				class="text-decoration-none"
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