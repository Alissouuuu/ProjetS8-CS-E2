<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">

<title>SportiZone - Connexion</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/style2.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleHeader.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleFooter.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

</head>
<body>
	<!-- header-->
	<jsp:include page="/WEB-INF/header.jsp" />

	<!-- Login Form -->
	<div class="container my-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card shadow-sm">
					<div class="card-body p-4">
						<h2 class="text-center mb-2">Espace réservé aux membres</h2>
						<p class="text-center text-muted mb-4">Connectez-vous pour
							accéder à votre espace</p>

						<!-- Affichage des messages d'erreur ou de succès -->
						
					
  
   <c:if test = "${created eq 'user inscrit'}">
         <p>My salary is:  <c:out value = "${salary}"/><p>
      </c:if>

						



						<form method="post" action="login" class="was-validated"
							novalidate>
							<div class="mb-3">
								<label for="loginEmail" class="form-label">Email</label>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-envelope text-muted"></i>
									</span> <input type="email" class="form-control" id="loginEmail"
										name="loginEmail" placeholder="nom@exemple.com" required>

								</div>
							</div>

							<div class="mb-3">
								<label for="loginPassword" class="form-label">Mot de
									passe</label>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-lock text-muted"></i>
									</span> <input type="password" class="form-control" id="loginPassword"
										name="loginPassword" placeholder="********" required>
								</div>
							</div>

							<div class="d-grid gap-2 mb-3">
								<button type="submit" class="btn btn-danger">Se
									connecter</button>
							</div>

							<div class="text-center mt-4">
								<p class="mb-0">
									Vous n'avez pas de compte? <a
										href="${pageContext.request.contextPath}/inscription"
										class="text-danger">Inscrivez-vous</a>
								</p>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<footer class="text-white text-center py-3 mt-5"
		style="position: fixed; bottom: 0px !important;">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>
</body>
</html>
