<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">

<title>SportiZone - Inscription</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>



<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/style2.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleHeader.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleFooter.css">
</head>
<body>
	<!-- header-->

 <jsp:include page="/WEB-INF/header.jsp" />

	<!-- Registration Form -->
	<div class="container my-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card shadow-sm">
					<div class="card-body p-4">
						<h2 class="text-center mb-2">Créez votre compte</h2>
						<p class="text-center text-muted mb-4">Rejoignez la communauté
							SportiZone</p>

					<form method="post" action="${pageContext.request.contextPath}/inscription" enctype="multipart/form-data">
					
							<div class="mb-3">
								<label for="nom" class="form-label">Nom</label>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-user text-muted"></i>
									</span> <input type="text" class="form-control" id="nom" name="nom"
										placeholder="Votre nom" required>
								</div>
							</div>
							<div class="mb-3">
								<label for="prenom" class="form-label">Prénom</label>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-user text-muted"></i>
									</span> <input type="text" class="form-control" name="prenom" id="prenom"
										placeholder="Votre prénom" required>
								</div>
							</div>
							<div class="mb-3">
								<label for="email" class="form-label">Email</label>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-envelope text-muted"></i>
									</span> <input type="email" class="form-control" name="email" id="email"
										placeholder="nom@exemple.fr" required>
								</div>
							</div>
							<div class="mb-3">
								<label for="metier" class="form-label">Fonction</label>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-circle-user text-muted"></i>
									</span> <input type="text" class="form-control" name="metier" id="metier"
										placeholder="Votre fonction" required>
								</div>
							</div>


							<div class="mb-3">
								<label for="password" class="form-label">Mot de passe</label>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-lock text-muted"></i>
									</span> <input type="password" class="form-control" name="password" id="password"
										placeholder="********" required>
								</div>
							</div>


							<div class="mb-3">
								<label for="file" class="form-label">Pieces
									justificatifs : </label>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-file-lines text-muted"></i>
									</span> <input type="file" class="form-control" id="file" name ="file" required>


								</div>
							</div>




							<div class="d-grid gap-2 mb-3">
								<button type="submit" class="btn btn-danger">S'inscrire</button>
							</div>

							<div class="text-center mt-4">
								<p class="mb-0">
									Vous avez déjà un compte? <a href="${pageContext.request.contextPath}/login"
										class="text-danger">Connectez-vous</a>
								</p>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Footer -->
	<footer class="text-white text-center py-3 mt-5">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>



</body>
</html>