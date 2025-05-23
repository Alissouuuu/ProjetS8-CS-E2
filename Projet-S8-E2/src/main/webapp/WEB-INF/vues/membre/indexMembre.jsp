<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
						<p class="hero-subtitle">Un événement, une actualité, une
							information ?</p>
						<p class="hero-subtitle2">Partagez-les en quelques clics et
							tenez le club informé !</p>
						<div class="d-flex justify-content-center gap-3 mb-4">
							<a href="${pageContext.request.contextPath}/createEvent" class="btn btn-primary ">Créer un
								événement</a> 
								<a href="${pageContext.request.contextPath}/partagerActu" class="btn btn-primary ">Publier une
								actualité</a>
						</div>
						<div class="d-flex justify-content-center mt-3">
                        <form method="post" class="w-100" style="max-width: 700px;">
                            <!-- modifier la cotisation -->
                            <div class="d-flex align-items-center mb-3 gap-3">
                                <label for="cotisation" class="form-label mb-0" style="width: 180px; text-align: right;">
                                    Montant de cotisations :
                                </label>
                                <input type="text" class="form-control" id="cotisation" style="flex: 1;">
                                <a href="${pageContext.request.contextPath}/indexMembre" class="btn btn-secondary">Annuler</a>
                                <button type="submit" class="btn btn-primary btn-3 text-white">Partager</button>
                            </div>

                            <!-- modifier le nbr de licenciés -->
                            <div class="d-flex align-items-center mb-3 gap-3">
                                <label for="licencies" class="form-label mb-0" style="width: 180px; text-align: right;">
                                    Nombre de licenciés :
                                </label>
                                <input type="text" class="form-control" id="licencies" style="flex: 1;">
                                <a href="${pageContext.request.contextPath}/indexMembre" class="btn btn-secondary">Annuler</a>
                                <button type="submit" class="btn btn-primary btn-3 text-white">Partager</button>
                            </div>
                        </form>
                    </div>
						
						
						

					</div>
				</div>
			</div>
		</div>

	</section>
	<!-- Features Section -->
	<section class="features-section">
		<div class="container">
			<div class="row">
				<!-- Feature 1 -->
				<div class="col-md-4">
					<div class="feature-card">
						<h3 class="feature-title">Événements passés</h3>
						<p class="feature-text">Revoir vos événements passés </p>
						<a href="#" class="feature-link">Voir plus</a>
					</div>
				</div>

				<!-- Feature 2 -->
				<div class="col-md-4">
					<div class="feature-card">
						<h3 class="feature-title">Événement  à venir 1</h3>
						<p class="feature-text">bla bla bla </p>
						<a href="#" class="feature-link">Voir plus</a>
					</div>
				</div>

				<!-- Feature 3 -->
				<div class="col-md-4">
					<div class="feature-card">
						<h3 class="feature-title">Événement  à venir 2</h3>
						<p class="feature-text">bla bla bla bla </p>
						<a href="#" class="feature-link">Voir plus</a>
					</div>
				</div>
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
