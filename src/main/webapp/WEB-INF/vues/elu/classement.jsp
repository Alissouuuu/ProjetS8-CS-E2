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
					la commune </em>
			</p>
			<form method="get" action="">
				<div class="row filtre-section">
					<!-- Filtre Région -->
					<div class="col-md-5">
						<label class="form-label">Région :</label> <select name="region"
							class="form-select">
							<c:out value="${regions}" />

							<option value="">Sélectionner une région</option>
							<%
							List<Region> regions = (List<Region>) request.getAttribute("regions");
							for (Region region : regions) {
							%>
							<option value="<%=region.getCodeRegion()%>">
								<%=region.getLibelleRegion()%>
							</option>
							<%
							}
							%>
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
							<option value="" selected>-- Toutes les tranches --</option>
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
		int pageSize = 50; // Nombre d'éléments par page
		
	    int page1 = (request.getAttribute("page") != null) ? (int) request.getAttribute("page") : 1;

		String type = (String) request.getAttribute("typeAffichage");
		String ageParam = request.getParameter("age");
		String genreParam = request.getParameter("genre");
		List<Club> listeClubs = (List<Club>) request.getAttribute("listeClubs");
		 // Récupérer le paramètre page1 depuis l'URL
	    if (request.getParameter("page") != null) {
	        try {
	            page1 = Integer.parseInt(request.getParameter("page"));
	        } catch (NumberFormatException e) {
	            page1 = 1; // Valeur par défaut 
	        }
	    }
	   if ("genre".equals(type)) {
			if (listeClubs != null && !listeClubs.isEmpty()) {
		%>

		<!-- Canvas pour Chart.js -->
		<canvas id="barChart" width="600" height="400"></canvas>

		<script>

    const labels = [
        <%for (int i = 0; i < listeClubs.size(); i++) {
	out.print("\"" + listeClubs.get(i).getLibelleClub() + "\"");
	if (i < listeClubs.size() - 1)
		out.print(",");
}%>
    ];

    const dataValues = [
        <%for (int i = 0; i < listeClubs.size(); i++) {
			out.print(listeClubs.get(i).getTotalLicencies());
			if (i < listeClubs.size() - 1)
				out.print(",");
		}%>
    ];

    //  couleur selon le genre
    const genre = "<%=genreParam%>";
    const barColor = genre === "F" ? "rgba(255, 99, 132, 0.7)" : "rgba(54, 162, 235, 0.7)";

    //  l'histogramme
    const ctx = document.getElementById('barChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Nombre de licenciés',
                data: dataValues,
                backgroundColor: barColor
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Nombre de licenciés par club'
                },
                legend: {
                    display: false
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    max: 2000, //  de l'axe Y
                    ticks: {
                        stepSize: 200 // 'espacement des divisions
                    },
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

		

		else {
		%>
		<p>Aucun club trouvé pour les filtres sélectionnés.</p>
		<%
		}
		}else
		{
			
	        if (listeClubs != null && !listeClubs.isEmpty()) {
	        	 int totalClubs = listeClubs.size();
	     	    int totalPages = (int) Math.ceil((double) totalClubs / pageSize);
	     	    int start = (page1 - 1) * pageSize;
	     	    int end = Math.min(start + pageSize, totalClubs);
	     		
	    %>
	          <table border="1">
        <tr>
            <th>Nom du club</th>
            <th>Nombre de licenciés</th>
        </tr>
        <%
            for (int i = start; i < end; i++) {
                Club club = listeClubs.get(i);
        %>
            <tr>
                <td><%= club.getLibelleClub() %></td>
                <td><%= club.getTotalLicencies() %></td>
            </tr>
        <%
            }
        %>
    </table>

    <!-- Liens de pagination -->
  <div>
    <%
         totalPages = (Integer) request.getAttribute("totalPages");
         page1 = (Integer) request.getAttribute("page1");
        String region = (String) request.getAttribute("region");
        String cp = (String) request.getAttribute("cp");
        String age = (String) request.getAttribute("age");
        String genre = (String) request.getAttribute("genre");

        String extraParams = "";
        if (region != null && !region.isEmpty()) extraParams += "&region=" + region;
        if (cp != null && !cp.isEmpty()) extraParams += "&cp=" + cp;
        if (age != null && !age.isEmpty()) extraParams += "&age=" + age;
        if (genre != null && !genre.isEmpty()) extraParams += "&genre=" + genre;

        for (int i = 1; i <= totalPages; i++) {
            if (i == page1) {
                out.print("<strong>" + i + "</strong> ");
            } else {
                out.print("<a href='classement?page=" + i + extraParams + "'>" + i + "</a> ");
            }
        }

    %>
</div>
  
  
	    <%
	        } else {
	    %>
	        <p>Aucun club trouvé pour les filtres sélectionnés.</p>
	    <%
	        }
		}
	    %>

		

		<!-- chart pyramide -->







	</main>

	<footer class="text-white text-center py-3">
		<div class="container">
			<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
		</div>
	</footer>
</body>
</html>