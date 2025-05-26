<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*, model.Region,model.Club"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Elu - Classement Licenciés</title>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleHeader.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleMember.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/styleFlex.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.1/chart.min.js"
	integrity="sha512-L0Shl7nXXzIlBSUUPpxrokqq4ojqgZFQczTYlGjzONGTDAcLremjwaWv5A+EDLnxhQzY5xUZPWLOLqYRkY0Cbw=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.1/chart.min.js"
	integrity="sha512-L0Shl7nXXzIlBSUUPpxrokqq4ojqgZFQczTYlGjzONGTDAcLremjwaWv5A+EDLnxhQzY5xUZPWLOLqYRkY0Cbw=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>

</head>
<body>
	<!-- header-->

	<%@include file="headerElu.jsp"%>
	<main>

		<!-- Formulaire de recherche -->
		<div class="container">
			<h2>Filtres de recherche</h2>
			<p style="margin-top: 1.25rem;">
				<em>Vous devez choisir une région ou saisir le code postal de
					la commune, puis sélectionner le filtre de votre choix </em>
			</p>
			<form method="post"
				action="${pageContext.request.contextPath}/classement">
				<div class="row filtre-section">
					<!-- Filtre Région -->
					<div class="col-md-5">
						<label class="form-label">Région :</label> <select name="region"
							class="form-select">
							<c:out value="${regions}" />

							<option value="">Sélectionner une région</option>
							<c:forEach var="region" items="${regions}">
								<option value="${region.codeRegion}">${region.libelleRegion}</option>
							</c:forEach>

						</select>
					</div>


					<!-- Filtre Ville -->
					<div class="col-md-5">

						<label for="cp" class="form-label">Code postal de la
							commune </label> <input type="text" class="form-control" id="cp"
							name="cp" placeholder="Entrez un code postal">
					</div>
				</div>

				<!-- filtre age et genre -->
				<div class="row filtre-section">
					<div class="col-md-5">


						<label for="filtre-age" class="form-label">Tranche d'âge :</label>
						<select id="age" name="age" class="form-select">
							<option value="" selected>Toutes les tranches d'âge</option>
							<option value="1_4">1-4 ans</option>
							<option value="5_9">5-9 ans</option>
							<option value="10_14">10-14 ans</option>
							<option value="15_19">15-19 ans</option>

							<option value="20_24">20-24 ans</option>
							<option value="25_29">25-29 ans</option>
							<option value="30_34">30-34 ans</option>
							<option value="35_39">35-39 ans</option>
							<option value="40_44">40-44 ans</option>
							<option value="45_49">45-49 ans</option>
							<option value="50_54">50-54 ans</option>
							<option value="55_59">55-59 ans</option>
							<option value="60_64">60-64 ans</option>
							<option value="65_69">65-69 ans</option>
							<option value="70_74">70-74 ans</option>
							<option value="75_79">75-79 ans</option>
							<option value="80_99">Plus de 80 ans</option>
						</select>


					</div>
					<div class="col-md-5">
						<label class="form-label">Genre :</label> <select id="genre"
							name="genre" class="form-select">
							<option value="">Choisissez le genre</option>

							<option value="F">Femme</option>
							<option value="H">Homme</option>

						</select>

					</div>

					<p style="margin-top: 1.25rem;">
						<em>Astuce : vous pouvez combiner les filtres pour un
							affichage plus précis.</em>
					</p>
				</div>

				<!-- Bouton de soumission -->
				<div class="text-center mt-4">
					<button type="submit" class="btn btn-primary">Rechercher</button>
				</div>
			</form>
		</div>

		<%
		List<Club> listeClubs = (List<Club>) request.getAttribute("listeClubs");
		String typeAffichage = (String) request.getAttribute("typeAffichage");
		String genre = (String) request.getAttribute("genre");
		String age = (String) request.getAttribute("age");
		if (listeClubs != null && !listeClubs.isEmpty()) {
		%>
		<canvas id="graphiqueClubs" width="800" height="600"></canvas>

		<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
		<script>
        const labels = <%=listeClubs.stream().map(c -> "\"" + c.getLibelleClub().replace("\"", "") + "\"").toList()%>;
        const data = <%=listeClubs.stream().map(c -> c.getTotalLicencies()).toList()%>;

        const typeAffichage = "<%=typeAffichage%>";
        const genre = "<%=genre != null ? genre : ""%>";

        let couleur = 'rgba(54, 162, 235, 0.6)'; // bleu par défaut
        if (typeAffichage === "genre" && genre.toLowerCase() === "f") {
            couleur = 'rgba(255, 99, 132, 0.6)'; // rose pour femme
        }

        const maxValue = Math.max(...data);

        new Chart(document.getElementById("graphiqueClubs"), {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Nombre de licenciés',
                    data: data,
                    backgroundColor: couleur,
                    borderColor: couleur.replace('0.6', '1'),
                    borderWidth: 1
                }]
            },
            options: {
                indexAxis: 'y', // ✅ Barres horizontales
                responsive: true,
                scales: {
                    x: {
                        beginAtZero: true,
                        suggestedMax: maxValue + 10 // marge de lisibilité
                    },
                    y: {
                        ticks: {
                            autoSkip: false,
                            font: { size: 12 }
                        }
                    }
                },
                plugins: {
                    legend: {
                        display: false
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return context.parsed.x + " licenciés";
                            }
                        }
                    }
                }
            }
        });
    </script>

		<%
		} else {
		%>
		<p>Aucun club trouvé pour les filtres sélectionnés.</p>
		<%
		}
		%>
	</main>

	<footer class="text-white text-center py-3">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>
</body>
</html>