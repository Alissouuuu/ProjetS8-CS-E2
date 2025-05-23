<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, model.Region,model.Departement,model.Federation, model.Club"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Elu - Statistiques</title>
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

</head>
<body>
	<!-- header-->

	<%@include file="headerElu.jsp"%>
	<!-- Formulaire de recherche -->
	<main>
		<div class="container">
			<h2>Filtres de recherche</h2>
			<form method="post" action="">
				<div class="row filtre-section mt-4">
					<!-- Filtre Région -->
					<div class="col-md-4">
						<label class="form-label">Région :</label> <select name="region"
							class="form-select">
							<option value="">Sélectionner une région</option>
							<%
							List<Region> regions = (List<Region>) request.getAttribute("regions");
							if (regions != null) {
								for (Region region : regions) {
							%>
							<option value="<%=region.getCodeRegion()%>">
								<%=region.getLibelleRegion()%>
							</option>
							<%
							}
							}
							%>

						</select>
					</div>

					<!-- Filtre Département -->
					<div class="col-md-4">
						<label class="form-label">Département :</label> <select
							name="departement" class="form-select">
							<option value="">Sélectionner un département</option>
							<%
							List<Departement> departements = (List<Departement>) request.getAttribute("departements");
							for (Departement departement : departements) {
							%>
							<option value="<%=departement.getCodeDepartement()%>">
								<%=departement.getLibelleDepartement()%>
							</option>

							<%
							}
							%>

						</select>
					</div>

					<!-- Filtre Ville -->
					<div class="col-md-4">


						<label for="cp" class="form-label">Code postal de la
							commune </label> <input type="text" class="form-control" id="cp"
							name="cp" placeholder="Entrez un code postal">

					</div>
				</div>
				<!-- filtre federation -->

					<div class="row filtre-section mt-4">
						<div class="col align-self-start">
							<label for="filtre-age" class="form-label">Fédération
								sportive :</label> 
								<select id="federation" name="federation"
								class="form-select " style="width: 31.8%">
								<option value="">Sélectionner une fédération</option>
								<%
								List<Federation> federations = (List<Federation>) request.getAttribute("federations");
								for (Federation federation : federations) {
								%>
								<option value="<%=federation.getCodeFederation()%>">
									<%=federation.getLibelleFedeation()%>
								</option>
								<%
								}
								%>
							</select>
						</div>
					</div>

				<!-- Bouton de soumission -->
				<div class="text-center mt-4">
					<button type="submit" class="btn btn-primary">Rechercher</button>
				</div>
			</form>


		<canvas id="clubChart" width="800" height="400"></canvas>

<%
    List<Club> listeClubs = (List<Club>) request.getAttribute("listeClubs");
    if (listeClubs != null && !listeClubs.isEmpty()) {
        StringBuilder clubLabels = new StringBuilder();
        StringBuilder femmesData = new StringBuilder();
        StringBuilder hommesData = new StringBuilder();

        for (int i = 0; i < listeClubs.size(); i++) {
            Club club = listeClubs.get(i);
            clubLabels.append("\"").append(club.getLibelleClub()).append("\"");
            femmesData.append(club.getTotalLicenciesFemme());
            hommesData.append(club.getTotalLicenciesHomme());

            if (i < listeClubs.size() - 1) {
                clubLabels.append(",");
                femmesData.append(",");
                hommesData.append(",");
            }
        }
%>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.1/chart.min.js" integrity="sha512-L0Shl7nXXzIlBSUUPpxrokqq4ojqgZFQczTYlGjzONGTDAcLremjwaWv5A+EDLnxhQzY5xUZPWLOLqYRkY0Cbw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.1/chart.min.js" integrity="sha512-L0Shl7nXXzIlBSUUPpxrokqq4ojqgZFQczTYlGjzONGTDAcLremjwaWv5A+EDLnxhQzY5xUZPWLOLqYRkY0Cbw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
      <canvas id="pyramideChart" width="600" height="400"></canvas>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    const ctx = document.getElementById('clubChart').getContext('2d');
    const clubChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [<%= clubLabels.toString() %>],
            datasets: [
                {
                    label: 'Femmes',
                    data: [<%= femmesData.toString() %>],
                    backgroundColor: 'rgba(255, 99, 132, 0.7)' // rose
                },
                {
                    label: 'Hommes',
                    data: [<%= hommesData.toString() %>],
                    backgroundColor: 'rgba(54, 162, 235, 0.7)' // bleu
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'top' },
                title: { display: true, text: 'Nombre de licenciés par club' }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Nombre de licenciés'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Clubs'
                    }
                }
            }
        }
    });
</script>

<%
    }
%>
			</table>



















		</div>
	</main>
	<footer class="text-white text-center py-3">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>
</body>
</html>