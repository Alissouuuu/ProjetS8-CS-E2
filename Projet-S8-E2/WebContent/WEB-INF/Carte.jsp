<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>OpenStreetMap</title>
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
        integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY="
        crossorigin=""/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://unpkg.com/leaflet.markercluster@1.4.1/dist/MarkerCluster.css">
        <link rel="stylesheet" href="https://unpkg.com/leaflet.markercluster@1.4.1/dist/MarkerCluster.Default.css"> 
        
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        
    </head>
    <body>
    	<h1 class="text-center mb-4" style="color: #000;">Recherche des clubs</h1>
    
    	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>


		<form action="Carte" method="get" class="mb-4">
		  <div class="row">
		

		    <div class="col-md-6">
		    
		    	<p>Choisissez la zone géographique de la recherche :</p>
		    	
		    <div class="mb-3">
		      <select class="form-control js-select2" name="searchVille">
		      		<option>-- Par ville --</option>
		        <c:forEach var="ville" items="${villes}">
		          	<option value="${ville}">${ville}</option>
		        </c:forEach>
		      </select>
		     </div>
		     
			<div class="mb-3">
		      <select class="form-control js-select2" name="searchDepartement">
		      		<option>-- Par département --</option>
		        <c:forEach var="departement" items="${departements}">
		          	<option value="${departement}">${departement}</option>
		        </c:forEach>
		      </select>
		      </div>
		      
		      <div class="mb-3">
		      <select class="form-control js-select2" name="searchRegion">
		      		<option>-- Par région --</option>
		        <c:forEach var="region" items="${regions}">
		          	<option value="${region}">${region}</option>
		        </c:forEach>
		      </select>
		      </div>
		      
		    </div>
		

		    <div class="col-md-6">
	
			 <div class="mb-3">	    
    			<p>Choisissez la fédération :</p>
		    
			      <select class="form-control js-select2" name="searchFederation">
			      		<option>-- Fédération --</option>
			        <c:forEach var="federation" items="${federations}">
			          	<option value="${federation}">${federation}</option>
			        </c:forEach>
			      </select>
		      </div>
		      
		      <div class="mb-3">
		      
				<div class="container mt-3">
				  <div class="row">
				
				    <div class="col-md-10">
				      <div class="dropdown">
				        <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
				          Rayon
				        </button>
				        <ul class="dropdown-menu p-3">
				          <li>
				            <label for="customRange3" class="form-label">Choisissez le rayon autour d'une ville : <span id="rayonValue">0</span> km</label>
				            <input type="range" class="form-range" min="0" max="50" step="10" id="rayon" value="0">
				          </li>
				          
				          <script>
							  const slider = document.getElementById("rayon");
							  const output = document.getElementById("rayonValue");
							
							  // Affiche la valeur initiale
							  output.textContent = slider.value;
							
							  // Met à jour la valeur affichée à chaque mouvement
							  slider.addEventListener("input", function () {
							    output.textContent = this.value;
							  });
						</script>
				          
				          
				        </ul>
				      </div>
				    </div>
				
				    <div class="col-md-1">
				      <button type="submit" class="btn btn-secondary">Rechercher</button>
				    </div>
				
				  </div>
				</div>

				
			</div>

		      <p>${input}</p>
		      

		      
		    </div>
		
		  </div>
		

		</form>

		
		<script>
		  $(document).ready(function() {
		    $('.js-select2').select2({
		      allowClear: true
		    });
		  });
		</script>
		
		<div class="row" style="width: 1000px; height: 400px; margin: auto;">			
        	<div id="divCarte" ></div>
        </div>
        
        <!-- Fichiers JavaScript -->
        <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
        integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo="
        crossorigin=""></script>
        <script src="https://unpkg.com/leaflet.markercluster@1.4.1/dist/leaflet.markercluster.js"></script>
        
        <!-- On vérifie que ce qui est envoyé par le servlet n'est pas null -->
		<%
		    Object latObj = request.getAttribute("lat");
		    Object lonObj = request.getAttribute("lon");
		    Object communeObj = request.getAttribute("commune");
		    Object clubsObj = request.getAttribute("clubs");
		    // boolean hasData = (latObj != null && lonObj != null && communeObj != null);
		    boolean hasData = (clubsObj != null);
		    
		%> 
		
        <script>
        	
        	// Si les variables ne sont pas nulles 
	        <% if (hasData) { %>
	        
<%-- 	        // On récupère la valeur des objets dans des constantes
	        const lat = <%= latObj %>;
	        const lon = <%= lonObj %>;
	        const commune = "<%= communeObj %>";   
        
	        // Verification des variables dans la console
            console.log("Commune :", commune);
            console.log("Latitude :", lat);
            console.log("Longitude :", lon); --%>
            
            // On vérifie qu'on veuille afficher les clubs dans toute la France
            <% if ("France".equals(request.getAttribute("zoneGeo"))) { %>
            
            	// Comme on regarde dans la France, on centre la vue de la carte sur la France
	            var carte = L.map('divCarte').setView([46, 2], 5); // Centre de la France
	            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
	                minZoom: 3,
	                maxZoom: 19,
	                attribution: '&copy; OpenStreetMap'
	            }).addTo(carte);
	            
	            // Tableau qui va contenir le groupe de marqueurs
	            var marqueurs = L.markerClusterGroup();

	            // On récupère la liste des clubs à afficher dans un tableau
	            var clubs = [
	                <c:forEach var="club" items="${clubs}" varStatus="loop">
	                {
	                    nom: "${club.libelle_club}",
	                    lat: ${club.lat},
	                    lon: ${club.lon},
	                    commune: "${club.commune}"
	                }<c:if test="${!loop.last}">,</c:if>
	                </c:forEach>
	            ];
	            
	            
	            // On parcourt le "tableau" clubs et on affiche le marqueur de chaque club contenu dans la liste
	            clubs.forEach((club) => { // => fonction fléchée car forEach prend en paramètre une fonction
	            						// car forEach exécute une fonction pour chaque élément du tableau 
	                var marqueur = L.marker([club.lat, club.lon]) // .addTo(carte); Pas besoin quand on utilise les clusters
	                marqueur.bindPopup("<strong>" + club.nom + "</strong><br>" + club.commune);
	                marqueurs.addLayer(marqueur); // On ajoute le marqueur au groupe de clusters
	            });

	            // On ajoute le groupe de clusters à la carte
	            carte.addLayer(marqueurs);
	            
			<% } %>
			
			// On vérifie qu'on veuille afficher les clubs d'une ville
            <% if ("Ville".equals(request.getAttribute("zoneGeo"))) { %>
            
            	// On récupère les constantes pour le centrage de la carte
		        const lat = <%= latObj %>;
		        const lon = <%= lonObj %>;
            
	            // Tableau qui va contenir le groupe de marqueurs
	            var marqueurs = L.markerClusterGroup();

	            // On récupère la liste des clubs à afficher dans un tableau
	            var clubs = [
	                <c:forEach var="club" items="${clubs}" varStatus="loop">
	                {
	                    nom: "${club.libelle_club}",
	                    lat: ${club.lat},
	                    lon: ${club.lon},
	                    commune: "${club.commune}"
	                }<c:if test="${!loop.last}">,</c:if>
	                </c:forEach>
	            ];
	            
            	// Comme on regarde dans une ville, on affiche la carte centrée sur la ville
	            var carte = L.map('divCarte').setView([lat, lon], 13);
	            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
	                minZoom: 3,
	                maxZoom: 19,
	                attribution: '&copy; OpenStreetMap'
	            }).addTo(carte);
	            
	            
	            // On parcourt le "tableau" clubs et on affiche le marqueur de chaque club contenu dans la liste
	            clubs.forEach((club) => { // => fonction fléchée car forEach prend en paramètre une fonction
	            						// car forEach exécute une fonction pour chaque élément du tableau 
	                var marqueur = L.marker([club.lat, club.lon]) // .addTo(carte); Pas besoin quand on utilise les clusters
	                marqueur.bindPopup("<strong>" + club.nom + "</strong><br>" + club.commune);
	                marqueurs.addLayer(marqueur); // On ajoute le marqueur au groupe de clusters
	            });

	            // On ajoute le groupe de clusters à la carte
	            carte.addLayer(marqueurs);
	            
			<% } %>
			
			// On vérifie qu'on veuille afficher les clubs d'un département
            <% if ("Departement".equals(request.getAttribute("zoneGeo"))) { %>
            
            	// On récupère les constantes pour le centrage de la carte
		        const lat = <%= latObj %>;
		        const lon = <%= lonObj %>;
            
	            // Tableau qui va contenir le groupe de marqueurs
	            var marqueurs = L.markerClusterGroup();

	            // On récupère la liste des clubs à afficher dans un tableau
	            var clubs = [
	                <c:forEach var="club" items="${clubs}" varStatus="loop">
	                {
	                    nom: "${club.libelle_club}",
	                    lat: ${club.lat},
	                    lon: ${club.lon},
	                    commune: "${club.commune}"
	                }<c:if test="${!loop.last}">,</c:if>
	                </c:forEach>
	            ];
	            
            	// Comme on regarde dans un département, on affiche la carte centrée sur le département
	            var carte = L.map('divCarte').setView([lat, lon], 9);
	            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
	                minZoom: 3,
	                maxZoom: 19,
	                attribution: '&copy; OpenStreetMap'
	            }).addTo(carte);
	            
	            
	            // On parcourt le "tableau" clubs et on affiche le marqueur de chaque club contenu dans la liste
	            clubs.forEach((club) => { // => fonction fléchée car forEach prend en paramètre une fonction
	            						// car forEach exécute une fonction pour chaque élément du tableau 
	                var marqueur = L.marker([club.lat, club.lon]) // .addTo(carte); Pas besoin quand on utilise les clusters
	                marqueur.bindPopup("<strong>" + club.nom + "</strong><br>" + club.commune);
	                marqueurs.addLayer(marqueur); // On ajoute le marqueur au groupe de clusters
	            });

	            // On ajoute le groupe de clusters à la carte
	            carte.addLayer(marqueurs);
	            
			<% } %>
			
			// On vérifie qu'on veuille afficher les clubs d'une région
            <% if ("Region".equals(request.getAttribute("zoneGeo"))) { %>
            
            	// On récupère les constantes pour le centrage de la carte
		        const lat = <%= latObj %>;
		        const lon = <%= lonObj %>;
            
	            // Tableau qui va contenir le groupe de marqueurs
	            var marqueurs = L.markerClusterGroup();

	            // On récupère la liste des clubs à afficher dans un tableau
	            var clubs = [
	                <c:forEach var="club" items="${clubs}" varStatus="loop">
	                {
	                    nom: "${club.libelle_club}",
	                    lat: ${club.lat},
	                    lon: ${club.lon},
	                    commune: "${club.commune}"
	                }<c:if test="${!loop.last}">,</c:if>
	                </c:forEach>
	            ];
	            
            	// Comme on regarde dans une région, on affiche la carte centrée sur la région
	            var carte = L.map('divCarte').setView([lat, lon], 9);
	            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
	                minZoom: 3,
	                maxZoom: 19,
	                attribution: '&copy; OpenStreetMap'
	            }).addTo(carte);
	            
	            
	            // On parcourt le "tableau" clubs et on affiche le marqueur de chaque club contenu dans la liste
	            clubs.forEach((club) => { // => fonction fléchée car forEach prend en paramètre une fonction
	            						// car forEach exécute une fonction pour chaque élément du tableau 
	                var marqueur = L.marker([club.lat, club.lon]) // .addTo(carte); Pas besoin quand on utilise les clusters
	                marqueur.bindPopup("<strong>" + club.nom + "</strong><br>" + club.commune);
	                marqueurs.addLayer(marqueur); // On ajoute le marqueur au groupe de clusters
	            });

	            // On ajoute le groupe de clusters à la carte
	            carte.addLayer(marqueurs);
	            
			<% } %>

/*             // On ajoute le marqueur aux coordonnées
            var marqueur = L.marker([lat, lon]).addTo(carte);
            
            // On affiche un popup avec le nom de la ville qui deviendra par la suite
            // des informations lié au club
            marqueur.bindPopup('<p>' + commune + '</p>'); */
            
            // Si les variables sont nulles
        <% } else { %>
        
        	// On affiche la localisation par défaut qui est centrée sur le centre de la France
            var carte = L.map('divCarte').setView([46, 2], 5); // Paris par défaut
            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
                minZoom: 3,
                maxZoom: 19,
                attribution: '&copy; OpenStreetMap'
            }).addTo(carte);
        <% } %>
            
            //var tableauMarqueurs = [];

            // Initialisation de la carte 
            //var carte = L.map('divCarte').setView([48.852969, 2.349903], 13);
            
            // .map permet de charger une carte dans la div qu'on passe en paramètre
            // .setView permet de mettre à jour le point 
            // sur le lequel est centré la carte (Paris : 48.852969, 2.349903) 
            // 2e variable du .setView : zoom par défaut

            // Chargement des tuiles
            //L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
                // Permet de definir jusqu ou on peut zoomer sur la carte
            //minZoom: 3, 
            //maxZoom: 19,
            //attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
            //}).addTo(carte);

            //var marqueurs = L.markerClusterGroup();


            // Personnalisation du marqueur
            // var icone = L.icone({
            //     iconUrl: "images/icone.png", //dossier contenant l'icone
            //     iconSize: [50, 50],
            //     iconAnchor: [25, 50],
            //     popupAnchor: [0, -50]
            // })

/*             for (ville in villes){
                // Marqueur sur la carte avec PopUp
                var marqueur = L.marker([villes[ville].lat, villes[ville].lon]);// .addTo(carte); Inutile quand on utilise 
                                                                                // les markercluster
                marqueur.bindPopup('<p>'+ ville +'</p>');
                marqueurs.addLayer(marqueur); // Ajoute le marqueur au groupe de marqueurs

                // Ajoute le marqueur au tableau
                tableauMarqueurs.push(marqueur);
            }

            // On regroupe les marqueurs dans un groupeLeaflet
            var groupe = new L.featureGroup(tableauMarqueurs);

            // On adapte le zoom au groupe
            carte.fitBounds(groupe.getBounds().pad(0.5));

            carte.addLayer(marqueurs);  */
            
            //var marqueur = L.marker([lat, lon]).addTo(carte); 
			//marqueur.bindPopup('<p>'+ commune +'</p>');
            
        </script>
        
        <!-- Bootstrap JS -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>