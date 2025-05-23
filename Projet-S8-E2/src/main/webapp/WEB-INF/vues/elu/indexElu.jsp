<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>SportiZone - Elu inscrit</title>

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
<body class="d-flex flex-column" style="min-height:100vh;">
	<!-- header-->

	<%@include file="headerElu.jsp"%>
	<section class="hero-section" style=" flex-grow: 1;">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-10">
					<div class="hero-content text-center">
						<h1 class="hero-title">Bienvenus à SportiZone</h1>
						<p class="hero-subtitle">Une vision claire du sport en France, fédérations et territoire</p>
						<p class="hero-subtitle2">Explorez ces chiffres en quelques clics !</p>
						<div class="d-flex justify-content-center gap-3 mb-4">
							<a href="${pageContext.request.contextPath}/statistiques" class="btn btn-primary ">Statistiques licenciés</a> 
							<a href="./ChargementFiltres" class="btn btn-primary ">Classements par zones</a>
						</div>
						<p class="hero-subtitle2">Classement général :</p>
						<form method="POST" action="${pageContext.request.contextPath}/ClassementZone" class="d-flex justify-content-center gap-3 mb-4">
							<button class="btn btn-primary" name="choix" value="commune">Par commune</button>
							<button class="btn btn-primary" name="choix" value="region">Par région</button>	
						</form>
						
						
						

					</div>
				</div>
			</div>
		</div>

	</section>
	
	<footer class="text-white text-center py-3">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>
	<!-- Bootstrap JS Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
