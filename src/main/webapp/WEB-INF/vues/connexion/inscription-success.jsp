<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleHeader.css">
<title>Inscription réussie</title>
<!-- Redirection après 5 secondes -->
<meta http-equiv="refresh"
	content="6;URL=${pageContext.request.contextPath}/index">
<style>

</style>
</head>
<body>
	<jsp:include page="/WEB-INF/header.jsp" />


	<section class="hero">
		<div class="container cont2">
			<div class="hero-content">


				<div class="hero-slogan">
					<p>
						<strong>Votre demande d'inscription sera traitée dans 24h.</strong>
					</p>
			
				</div>
			</div>
		</div>
	</section>
	<footer class="text-white text-center py-3 mt-5">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>

</body>
</html>
