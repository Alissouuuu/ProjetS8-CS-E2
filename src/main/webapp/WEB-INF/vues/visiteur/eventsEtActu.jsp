<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Evenemnts & Actualités</title>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleMember.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}//styles/styleHeader.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}//styles/styleFlex.css">
</head>
<body>
	<!-- header-->
	<jsp:include page="/WEB-INF/header.jsp" />

	<!-- Formulaire de recherche -->
	<main>
		<div class="container">
			<!-- Événements passés -->
			<div class="row mb-3">
				<div class="col-md-5">

					<div class="feature-card">
						<h3 class="feature-title">Événements passés</h3>
						<c:forEach var="e" items="${evenementsPasses}">
							<div class="card mb-3">
								<div class="card-body">

									<h5 class="card-title">${e.nomEvenement}</h5>
									<p class="card-text">Passé le : ${e.dateEvenement}</p>

								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="col-md-5">

					<div class="feature-card">
						<h3 class="feature-title">Actualités partagées</h3>
						<c:forEach var="a" items="${actualites}">
							<div class="card mb-3">
								<div class="card-body">

									<h5 class="card-title">${a.titreActu}</h5>
									<p class="card-text">${a.descriptionActu}</p>

								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>


			<div class="row mt-3">

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
						</div>
					</div>
				</c:forEach>

			</div>
		</div>

	</main>
	<footer class="text-white text-center py-3">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>



</body>
</html>