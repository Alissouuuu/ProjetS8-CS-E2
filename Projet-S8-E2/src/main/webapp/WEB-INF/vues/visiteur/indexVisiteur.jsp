<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- taglib : équivalent des import en html ici JSTL -->

<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        
        <title>Sportizone</title>
        
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
        integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY="
        crossorigin=""/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://unpkg.com/leaflet.markercluster@1.4.1/dist/MarkerCluster.css">
        <link rel="stylesheet" href="https://unpkg.com/leaflet.markercluster@1.4.1/dist/MarkerCluster.Default.css"> 
        
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styleMember.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styleHeader.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styleFlex.css">
        
    </head>
    <body>
    	<jsp:include page="/WEB-INF/header.jsp" />
    	
    
    	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

		<main>
			<h2>Filtres de recherche</h2>
			
			<div class = "container">
			<form method="get" action="IndexVisiteurServlet">
				<div class="row mt-4">
				
					<!-- Filtre Région -->
					<div class="col-md-4">
						<label class="form-label">Région :</label> 
						<select id="region" name="searchRegion" class="form-select js-select2">
							<option>-- Sélectionner une région --</option>
							<c:forEach var="region" items="${regions}">
					          	<option value="${region}">${region}</option>
					        </c:forEach>
						</select>
					</div>

					<!-- Filtre Département -->
					<div class="col-md-4">
						<label class="form-label">Département :</label> 
						<select id="departement" name="searchDepartement" class="form-select js-select2">
							<option>-- Sélectionner un département --</option>
							<c:forEach var="departement" items="${departements}">
					          	<option value="${departement}">${departement}</option>
					        </c:forEach>
						</select>
					</div>

					<!-- Filtre Ville -->
					<div class="col-md-4">
						<label class="form-label">Commune :</label> 
						<select id="commune" name="searchVille" class="form-select js-select2">
							<option>-- Sélectionner une commune --</option>
					        <c:forEach var="ville" items="${villes}">
					          	<option value="${ville}">${ville}</option>
					        </c:forEach>
						</select>
					</div>
				</div>
				
				<!-- filtre federation -->
				<div class="filtre-section mt-4 row" >
				<div class="col-md-4 align-self-start">
					<label class="form-label">Fédération sportive :</label> 
						<select id="federation" name="searchFederation" class="form-select js-select2f">
						<option >-- Sélectionner une fédération --</option>
						<c:forEach var="federation" items="${federations}">
				          	<option value="${federation}">${federation}</option>
				        </c:forEach>
					</select>
				</div>
					
				<!-- Checkbox Rechercher autour de moi -->
				<div class="col-md-4 d-flex justify-content-center align-items-center">
					<label style="visibility: hidden;"></label> 
					
					<div class="form-check form-switch" >
						<input class="form-check-input" type="checkbox" role="switch" id="useGeoLoc">
						<label class="form-check-label" for="useGeoLoc">Rechercher autour de moi</label>
					</div>
					
				</div>			
				
				
				<div class="col-md-4">
				<label class="form-label" style="margin-right:2rem;">Rayon  :</label> <label id="rangeValue" style="">
					<span id="rayonValue">0</span> Km</label>
					<!-- Balise span = div sans saut de ligne, balise inline -->
					 <input type="range" class="form-range" min="0" max="50" step="5" value="0" name="rayon" id="rayon">
					 
			    <!-- afficher la valeur du range-input -->
			    <script>
			    const slider = document.getElementById("rayon"); // On récupère l'input du rayon
				  const output = document.getElementById("rayonValue");// On récupère la valeur du slider
				
				  // Affiche la valeur initiale
				  output.textContent = slider.value;
				
				  // Met à jour la valeur affichée à chaque mouvement
				  slider.addEventListener("input", function () { // On écoute l'input 
				    output.textContent = this.value; // On met à jour la valeur du span avec la valeur du slider
				  });
			    </script>
			</div>				

			
			<!-- khas shi 7el l spacing please -->
				<div class="col-md-4"></div>
			</div>
			<!-- Bouton de soumission -->
			<div class="text-center mt-4">
				<button type="submit" class="btn btn-primary">Rechercher</button>
			</div>
		</form>
		
		<script>
		  $(document).ready(function() { // Permet d'excécuter ce qu'il y a à l'interieur une fois que la page est chargée
			  $('.js-select2').select2({

				  matcher: function(params, data) {
				    // Si pas de recherche, affiche tout
				    if ($.trim(params.term) === '') { // === égalité srticte (5 === '5' => false)
				      return data;
				    }

				    // Compare en minuscules si la donnée commence par la chaîne saisie
				    if (data.text.toLowerCase().startsWith(params.term.toLowerCase())) {
				      return data;
				    }

				    // Sinon, on ne retourne rien = pas d'affichage
				    return null;
				  }
				});

		  });
		</script>
		
		<script>
		  $(document).ready(function() { // Permet d'excécuter ce qu'il y a à l'interieur une fois que la page est chargée
		    $('.js-select2f').select2({ // Permet de cibler tous les éléments qui ont le tag select2
		    						// select2 composant JQuery qui permet de mettre une barre de recherche dynamique
		    });
		  });
		</script>
	</div>
	
	<p></p>
	
	<div id="divCarte" class = "mb-3" style="width: 1000px; height: 600px; margin: auto;"></div>

        
        <!-- Fichiers JavaScript Leaflet -->
        <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
        integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo="
        crossorigin=""></script>
        <script src="https://unpkg.com/leaflet.markercluster@1.4.1/dist/leaflet.markercluster.js"></script>
        


		<%
		    Object latObj = request.getAttribute("lat");
		    Object lonObj = request.getAttribute("lon");
		    Object communeObj = request.getAttribute("commune");
		    Object clubsObj = request.getAttribute("clubs");
		    
		    // On verifie que la liste des clubs n'est pas null
		    boolean hasData = (clubsObj != null);
		    
		%> 
		
   		<script>
   		
   	 <% if (hasData) { %>
	   	    const lat = <%= latObj %>;
	   	    const lon = <%= lonObj %>;
	   	    const zoneGeo = "<%= request.getAttribute("zoneGeo") %>";
	   	    
	   	    var zoomLevel = 5; // par défaut France
	   	    if (zoneGeo === "Ville") zoomLevel = 13; // Triple = : comparaison stricte, type et valeur
	   	    else if (zoneGeo === "Departement" || zoneGeo === "Region") zoomLevel = 8;
	   	    
	   	    var carte = L.map('divCarte').setView([lat, lon], zoomLevel);
	   	    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
	   	        minZoom: 3,
	   	        maxZoom: 19,
	   	        attribution: '&copy; OpenStreetMap'
	   	    }).addTo(carte);
	   	    
	   	    var marqueurs = L.markerClusterGroup();
	   	    var clubs = [
	   	        <c:forEach var="club" items="${clubs}" varStatus="loop">
	   	        {
	   	            nom: "${club.libelle_club}",
	   	            lat: ${club.lat},
	   	            lon: ${club.lon},
	   	            partH: ${club.totalH},
	   	            partF: ${club.totalF},
	   	            commune: "${club.commune}"
	   	        }<c:if test="${!loop.last}">,</c:if>
	   	        </c:forEach>
	   	    ];

	   	    clubs.forEach((club) => {
	   	        var marqueur = L.marker([club.lat, club.lon]);
	   	        marqueur.bindPopup(
	   	            "<strong>" + club.nom + "<br>" + 
	   	            "Hommes : " + club.partH + "<br>" +
	   	            "Femmes : " + club.partF
	   	        );
	   	        marqueurs.addLayer(marqueur);
	   	    });

	   	    carte.addLayer(marqueurs);
	   	    
	   	 <% }else { %>
	   	 
	     	// On affiche la localisation par défaut qui est centrée sur le centre de la France
	         var carte = L.map('divCarte').setView([46, 2], 5); // Paris par défaut
	         L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
	             minZoom: 3,
	             maxZoom: 19,
	             attribution: '&copy; OpenStreetMap'
	         }).addTo(carte);
         
	   	 <% } %>
   		</script>
	</main>
        
       	<footer class="text-white text-center py-3">
			<div class="container">
				<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
			</div>
		</footer>	
        
        <!-- Bootstrap JS -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
		
		<script>
/* 		window.addEventListener('load', () => {
			  console.log("Page chargée"); */
			  // On écoute la checkbox
/* 			  document.getElementById('useGeoLoc').addEventListener('change', function () {
			    console.log("Checkbox changée !");
		 			// Si la checkbox est check
				  if (this.checked) {
					    console.log("Checkbox checked !");
					  // On va chercher la position du navigateur 
				    if ("geolocation" in navigator) {
					    console.log("Géoloc activée!");
				      navigator.geolocation.getCurrentPosition(
				        (position) => {
				          const lat = position.coords.latitude;
				          const lon = position.coords.longitude;
				          console.log("Latitude :", lat, "Longitude :", lon);

				          envoyerPositionAuServlet(lat, lon);
				        },
				        (error) => {
				          console.error("Erreur de géolocalisation :", error.message);
				        }
				      );
				    } else {
				      console.log("La géolocalisation n'est pas supportée par ce navigateur.");
				    }
				  } else {
				    console.log("Géolocalisation désactivée par l'utilisateur.");
				  }
				});

			  function envoyerPositionAuServlet(lat, lon) {
				  const rayon = document.getElementById('rayon').value;
				  console.log("Position envoyée :", { lat, lon, rayon });

				  const data = new URLSearchParams();
				  data.append('lat', lat);
				  data.append('lon', lon);
				  data.append('rayon', rayon);

				  fetch('IndexVisiteurAPIServlet', {
				    method: 'GET',
				    body: '',
				    headers: {
				      'Content-Type': 'application/x-www-form-urlencoded',
				    }
				  })
				  .then(response => response.json())
				  .then(data => {
				    console.log("Réponse du servlet :", data);
				    
				    const clubsDansRayon = data.dansLeRayon;
				  })
				  .catch(error => console.error("Erreur fetch :", error));
				}
 
			
			}); */
		</script>
		
		
		<script >
		// Gestion des requêtes AJAX		  
			  // On récupère le json ici
			 window.addEventListener('load', () => { // Au chargement de la jsp
				  fetch('${pageContext.request.contextPath}/IndexVisiteurAPIServlet', {
				    method: 'GET',
				    headers: {
				      'Content-Type': 'application/x-www-form-urlencoded',
				    }
				  })
				  .then(response => response.json())
				  .then(data => {

/* 					  console.log("Réponse du servlet:", data); */
				    // On récupère les Json
				    const communes = data.listeCommune;
				    const departements = data.listeDepartement;
				    const regions = data.listeRegion;
				    const federations = data.listeFederation;
				    
					// On créé les variables pour aller chercher les dropdown
				    const selectCommune = document.getElementById('commune');
				    const selectDepartement = document.getElementById('departement');
				    const selectRegion = document.getElementById('region');
				    const selectFederation = document.getElementById('federation');
				
				    // Permet de vider les options existantes 	
/* 				    selectCommune.innerHTML = '<option value="">-- Sélectionner une commune --</option>';
				    selectDepartement.innerHTML = '<option value="">-- Sélectionner un département --</option>';
				    selectRegion.innerHTML = '<option value="">-- Sélectionner une région --</option>';
				    selectFederation.innerHTML = '<option value="">-- Sélectionner une fédération --</option>'; */
				
				    // Pour chaque élément dans la liste 
				    communes.forEach(commune => {
				      
				    	// On créé une otpion
			    		const option = document.createElement('option');
				    	// On ajoute la valeur de l'élément à l'option
				      	option.value = commune;
				    	// On affiche la valeur de l'élément 
				      	option.textContent = commune;
				    	// On ajoute l'option au dropdonw
				      	selectCommune.appendChild(option);
				    });
				
				    departements.forEach(departement => {
				      const option = document.createElement('option');
				      option.value = departement;
				      option.textContent = departement;
				      selectDepartement.appendChild(option);
				    });
				
				    regions.forEach(region => {
				      const option = document.createElement('option');
				      option.value = region;
				      option.textContent = region;
				      selectRegion.appendChild(option);
				    });
				
				    federations.forEach(federation => {
				      const option = document.createElement('option');
				      option.value = federation;
				      option.textContent = federation;
				      selectFederation.appendChild(option);
				    });
				  })
				  .catch(err => console.error(err));
				});


		</script>

		
		
    </body>
</html>