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

						<form method="post"
							action="${pageContext.request.contextPath}/inscription"
							enctype="multipart/form-data" class="needs-validation" novalidate>

							<div class="mb-3">
							<p><span style="color: red;">*</span> Champs obligatoires</p>
							
								<label for="nom" class="form-label">Nom<span style="color: red;">*</span></label>
								<%
								if (request.getAttribute("erreurNom") != null) {
								%>
								<div
									style="color: red; text-align: center; font-weight: bold; margin-bottom: 20px;">
									<%=request.getAttribute("erreurNom")%>
								</div>
								<%
								}
								%>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-user text-muted"></i>
									</span> <input type="text" class="form-control" id="nom" name="nom"
										placeholder="Votre nom" required>
									<div class="invalid-feedback">Veuillez choisir un nom
										valide.</div>

								</div>
							</div>
							<div class="mb-3">
								<label for="prenom" class="form-label">Prénom<span style="color: red;">*</span></label>
								<%
								if (request.getAttribute("erreurPrenom") != null) {
								%>
								<div
									style="color: red; text-align: center; font-weight: bold; margin-bottom: 20px;">
									<%=request.getAttribute("erreurPrenom")%>
								</div>
								<%
								}
								%>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-user text-muted"></i>
									</span> <input type="text" class="form-control" name="prenom"
										id="prenom" placeholder="Votre prénom" required>
									<div class="invalid-feedback">Veuillez choisir un nom
										valide.</div>

								</div>
							</div>
							<div class="mb-3">
								<label for="email" class="form-label">Email<span style="color: red;">*</span></label>
								<%
									if (request.getAttribute("erreurEmail") != null) {
									%>
									<div
										style="color: red; text-align: center; font-weight: bold; margin-bottom: 20px;">
										<%=request.getAttribute("erreurEmail")%>
									</div>
									<%
									}
									%>
								<div class="input-group">
									
									<span class="input-group-text bg-white"> <i
										class="fas fa-envelope text-muted"></i>
									</span> <input type="email" class="form-control" name="email"
										id="email" placeholder="nom@exemple.fr" required>
									<div class="invalid-feedback">Veuillez saisir un email
										valide.</div>

								</div>
							</div>
							<div class="mb-3">
								<label for="fonction" class="form-label">Fonction<span style="color: red;">*</span></label>
								<%
									if (request.getAttribute("erreurFonction") != null) {
									%>
									<div
										style="color: red; text-align: center; font-weight: bold; margin-bottom: 20px;">
										<%=request.getAttribute("erreurFonction")%>
									</div>
									<%
									}
									%>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-circle-user text-muted"></i>
									</span> <select name="fonction" id="fonction" class="form-select">
										<option value="">Choisissez une fonction</option>
										<option value="maire">Maire</option>
										<option value="president fed">Président fédération</option>

										<option value="president club">President club</option>
										<option value="coach">Coach</option>
										<option value="animateur">Annimateur</option>
										<option value="trisorier">Trisoier club</option>
										<option value="assistant club">Assistant</option>
										<option value="benevole">Bénévole</option>
									</select>
								</div>
							</div>


							<div class="mb-3">
								<label for="password" class="form-label">Mot de passe<span style="color: red;">*</span></label>
								<%
								if (request.getAttribute("erreurMdp") != null) {
								%>
								<div
									style="color: red; text-align: center; font-weight: bold; margin-bottom: 20px;">
									<%=request.getAttribute("erreurMdp")%>
								</div>
								<%
								}
								%>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-lock text-muted"></i>
									</span> <input type="password" class="form-control" name="password"
										id="password" placeholder="********" required> <small
										id="passwordHelp" class="form-text text-muted">Veuillez
										choisir une mot de passe valide : Caractères majiscules et
										miniscule, chiffres et caractères spéciaux</small>
								</div>
							</div>


							<div class="mb-3">
								<label for="file" class="form-label">Pieces
									justificatifs :<span style="color: red;">*</span></label>
								<%
								if (request.getAttribute("erreurFile") != null) {
								%>
								<div
									style="color: red; text-align: center; font-weight: bold; margin-bottom: 20px;">
									<%=request.getAttribute("erreurFile")%>
								</div>
								<%
								}
								if (request.getAttribute("erreurFile2") != null) {
								%>
								<div
									style="color: red; text-align: center; font-weight: bold; margin-bottom: 20px;">
									<%=request.getAttribute("erreurFile2")%>
								</div>
								<%
								}
								%>
								<div class="input-group">
									<span class="input-group-text bg-white"> <i
										class="fas fa-file-lines text-muted"></i>
									</span> <input type="file" class="form-control " id="file" name="file"
										required>
								</div>
								<small id="nomHelp" class="form-text text-muted">Types
									de fichiers valides : pdf, png, jpeg, jpg</small>

							</div>




							<div class="d-grid gap-2 mb-3">
								<button type="submit" class="btn btn-danger">S'inscrire</button>
							</div>

							<div class="text-center mt-4">
								<p class="mb-0">
									Vous avez déjà un compte? <a
										href="${pageContext.request.contextPath}/login"
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


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>