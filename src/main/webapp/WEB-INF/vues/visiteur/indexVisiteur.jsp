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
			<form id="form" method="get" action="#">
				<div class="row mt-4">
				
					<!-- Filtre Région -->
					<div class="col-md-4">
						<label class="form-label">Région :</label> 
						<select id="region" name="searchRegion" class="form-select js-select2">
							<option>-- Sélectionner une région --</option>
						</select>
					</div>

					<!-- Filtre Département -->
					<div class="col-md-4">
						<label class="form-label">Département :</label> 
						<select id="departement" name="searchDepartement" class="form-select js-select2">
							<option>-- Sélectionner un département --</option>
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
					 <input type="range" class="form-range" min="0" max="30" step="5" value="0" name="rayon" id="rayon">
					 
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
		// Ici le filtrage pour la fédération rechercher ce qui contient l'input et pas qui commence par l'input
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

	</main>
        
       	<footer class="text-white text-center py-3">
			<div class="container">
				<p class="mb-0">© 2025 SportiZone. Tous droits réservés.</p>
			</div>
		</footer>	
        
        <!-- Bootstrap JS -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	
		<script >
		// Gestion des requêtes AJAX (Asynchronous Javascript And XML) des dropdown
		// Asynchronous => on ne recharge pas la page 
		
			  // On récupère le json ici
			 window.addEventListener('load', () => { // Au chargement de la jsp
				 // contextPath => chemin racine de l'app web dans le server
				  fetch('${pageContext.request.contextPath}/IndexVisiteurAPIServlet', {
					// On récupère des données sans vouloir les modifier
				    method: 'GET',
				  })
					// then attend que ce qui est avant soit résolu 
				 	// response.json() lit le coprs de la réponse et le convertit en objet à partir du json
				  .then(response => response.json())
				  	// data => Objet js converti
				  .then(data => {

 					//console.log("Réponse du servlet:", data); 
 					
				    // On récupère les objets js dans les variables
				    const communes = data.listeCommune;
				    const departements = data.listeDepartement;
				    const regions = data.listeRegion;
				    const federations = data.listeFederation;
				    
					// On créé les variables pour aller chercher les dropdown
				    const selectCommune = document.getElementById('commune');
				    const selectDepartement = document.getElementById('departement');
				    const selectRegion = document.getElementById('region');
				    const selectFederation = document.getElementById('federation');

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
				  // Gestion des erreur et affichage dans la console
				  .catch(err => console.error(err));
				});
		</script>
		
		<script>
		
		// Fonction qui permet de gerer les popup des gros clusterw 
		function ajouterClusterPopup(clusterGroup) {
				// On écoute l'evenement de clique sur le clustergroup
			  	clusterGroup.on('clusterclick', function (event) {
				// On récupère tous les marqueurs individuels 
			    const markers = event.layer.getAllChildMarkers();

				// Si on a + de 20 marqueurs dans le clustergroup
			    if (markers.length > 20) {
			    	
			      // Création du container
			      	// On créé une div (2e arg vide car pas de calsse css)
			      const container = L.DomUtil.create('div', '');
			      	// On définit la taille max
			      container.style.maxHeight = '200px';
			      	// Permet d'ajouter une barre de scrowl si la taille dépasse la hauteur max
			      container.style.overflowY = 'auto';
			      	// Ajoute un carde de 5px
			      container.style.padding = '5px';

			      // Titre
			      	// On créé une balise ici (balise/ classe css / élément parent)
			      const titre = L.DomUtil.create('strong', '', container);
			      // innerText permet d'afficher du contenu texte brut (pas de htlm)
			      titre.innerText = 'Clubs dans cette zone :';

			      // Liste
			      	// On créé un nouvel élément de type liste non ordonnée
			      const ul = L.DomUtil.create('ul', '', container);
			      	// Décalage à gauche pour faire l'effet d'un alinéa
			      ul.style.paddingLeft = '20px';
			      	// Permet de laisser des marges en haut et en bas (0 => pas sur les côtés)
			      ul.style.margin = '5px 0';

			      // Pour chaque marqueur 
			      markers.forEach(marker => {
			    		// On récupère les informations que l'on souhaite afficher 
			        const nom = marker.nomClub || '(Nom indisponible)';
			        const totalH = marker.totalH || '(Total homme indisponible)';
			        const totalF = marker.totalF || '(Total femme indisponible)';
			        	// On créé l'élément de la liste 
			        const li = L.DomUtil.create('li', '', ul);
			        	// Marge en bas 
			        li.style.marginBottom = '4px';
			        	// On définit le contenu en htlm que l'on souhaite afficher 
			        	// innerHTML permet d'afficher du contenu HTML
			        li.innerHTML = "<strong>"+ nom +"</strong>" +"<br> Homme :"+ totalH +"<br> Femmes :"+ totalF;
			      });

			      // Affiche la popup
			      	// On créé la popup avec une largeur de 300 px
			      L.popup({ maxWidth: 300 })
			      	// Affiche la popup à l'endroit où il a été cliqué, en récupérant la lat et la lon du cluster
			        .setLatLng(event.layer.getLatLng())
			        // On définit le contenu HTML de la popup avec le container
			        .setContent(container)
			        // On ouvre la popup sur la carte
			        .openOn(window.maCarte);

			      // Empêche le comportement par défaut (ici zoomer quand on clique sur le culster)
			      event.originalEvent.preventDefault();
			    }
			  });
			}

		
		// Fonction qui gère le chargement de la carte, prennant en paramètre le filtre
		function chargerCarte(filtre = null) {
			  // Autre façon de faire, on prépare l'url dans une variable
			  let url = '${pageContext.request.contextPath}/IndexVisiteurAPIServlet';
			  
			  if (filtre) {
				  // Permet de transformer l'objet filtre en requête URL
			    const params = new URLSearchParams(filtre);
				  // On ajoute la chaine à l'URL pour passer le filtre dans la requete GET
			    url += '?' + params.toString();
			  }

			  // Lance la requête HTTP (GET par défaut) ver l'URL
			  fetch(url)
			    .then(response => {
			    	// Si la requête à échoué, on retourne une erreur 
			      if (!response.ok) throw new Error("Erreur lors du chargement des données");
			    	// Sinon on la tranforme en js
			      return response.json();
			    })
			    .then(data => {
			    	// console.log(data.listeClubs)
			    	
			    	// On récupère les lat et lon qui on été passé en json 
			    	// Sinon par défaut on centre la carte sur la France
			      let lat = data.lat || 46;
			      let lon = data.lon || 2;
			      	//Zoom par défaut
			      let zoomLevel = 5;

			      	// On MAJ le zoom en fonction de la zone géographique 
			      	
			      if (data.zoneGeo === "GeoLoc") zoomLevel = 10; // autour de soi
			      else if (data.zoneGeo === "Ville") zoomLevel = 12;
			      else if (data.zoneGeo === "Departement") zoomLevel = 8;
			      else if (data.zoneGeo === "Region") zoomLevel = 7;
			      else if (data.zoneGeo === "France") zoomLevel = 5;

			      // Initialisation de la carte ou mise à jour si déjà créée
			      	// Si la carte est déjà créée
					if (window.maCarte) {
						// On met à jour la position
					  window.maCarte.setView([lat, lon], zoomLevel);
					// Si un groupe de marqueurs existe
					  if (window.mesMarqueurs) {
						// On supprime ces marqueurs 
					    window.mesMarqueurs.clearLayers();
					  } else {
						// Si le groupe de marqueurs n'existe pas
					    	// On créé le groupe de marqueurs 
					    window.mesMarqueurs = L.markerClusterGroup();
					
					    	// On écoute les clics sur le groupe de marqueurs
					    window.mesMarqueurs.on('click', function(e) {
					    	// e.layer = couche sur laquelle le clique est effectué
					    	// On s'assure qu'on clique sur un marqueur et non un cluster
					      if (e.layer instanceof L.Marker && !e.layer.getAllChildMarkers) {
					    	  // Ouvre la popup liée au marqueur
					        e.layer.openPopup();
					      }
					    });
					
					    // Appelle la fonction qui peremet de gérer le cas où il a + de 20 marqueurs
					    ajouterClusterPopup(window.mesMarqueurs);
					
					    // On ajoute le groupe de marqueurs à la carte
					    window.maCarte.addLayer(window.mesMarqueurs);
					  }
					} else {
						// Si la carte n'existe pas 
					  		// On l'initialise 
					  window.maCarte = L.map('divCarte').setView([lat, lon], zoomLevel);
					  L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
					    attribution: '&copy; OpenStreetMap contributors'
					  }).addTo(window.maCarte);
					
					  	// On créé le groupe de marqueurs
						window.mesMarqueurs = L.markerClusterGroup();
						
						// On écouteur le clic pour ouvrir le popup sur un marqueur individuel
						window.mesMarqueurs.on('click', function (e) {
						  // Vérifie que ce n’est pas un cluster
						  if (e.layer instanceof L.Marker && typeof e.layer.getAllChildMarkers !== 'function') {
							  // On ouvre la popup
						    e.layer.openPopup();
						  }
						});

						// Appelle la fonction qui peremet de gérer le cas où il a + de 20 marqueurs
					  ajouterClusterPopup(window.mesMarqueurs);
						// On ajoute les marqueurs à la carte 
					  window.maCarte.addLayer(window.mesMarqueurs);
					}
			      

			        // On récupère les data qu'on veut afficher dans les popup
					data.listeClubs.forEach((club) => {
/* 						  console.log(`Club[${idx}]:`, club);  
						  console.log(`libelle_club = «${club.libelle_club}»`); */
						  
						  // 
						  const marqueur = L.marker([club.lat, club.lon]);
			
						  //console.log("Structure du club :", JSON.stringify(club, null, 2));

						  // On récupère dans des variable ce qu'on veu afficher dans les popup
						  
						  const nom = club.libelle_club || '(pas de nom)';
						  const commune = club.commune || '(inconnue)';
						  // On convertit en String pour les popup
						  const nbHommes = String((club.totalH != null) ? club.totalH : '?');
						  const nbFemmes = String((club.totalF != null) ? club.totalF : '?');
						  
						  //console.log(`on attache ce nom au marqueur :`, nom);
						  
						  // On attache les paramètres que l'on veut afficher au marqueur pour les récupérer plus tard 
						  marqueur.nomClub = nom;
						  marqueur.totalH = nbHommes;
						  marqueur.totalF = nbFemmes;

							//console.log('club:', club);
							//console.log('libelle_club:', club['libelle_club']);
							//console.log('commune:', club['commune']);
							//console.log('totalH:', club['totalH']);
							//console.log('totalF:', club['totalF']);

							
							//const contenuPopup = `${nom}</strong><br/>Commune : ${commune}<br/>Hommes : ${nbHommes}<br/>Femmes : ${nbFemmes}`;

							//console.log("Contenu du popup :", contenuPopup);

							// On contruit le pop up avec les data
							marqueur.bindPopup("<strong>"+ nom +"</strong>" +"<br> Homme :"+ nbHommes +"<br> Femmes :"+ nbFemmes);
							//alert(`nom=${nom}, commune=${commune}, H=${nbHommes}, F=${nbFemmes}`);
							//marqueur.bindPopup("<strong>Test Popup</strong><br/>Ceci est un test");



						  // Assure l’ouverture du popup sur clic, même en cluster
						  marqueur.on('click', function() {
						    this.openPopup();
						  });

						  // On ajoute les marqueurs au groupe de marqueurs
						  window.mesMarqueurs.addLayer(marqueur);
						});




			    })
			    // Si on a une erreur 
			    .catch(err => {
			    	// On l'affiche dans la console
			      console.error(err);
			    	// Si la carte n'est pas initialisée
			      if (!window.maCarte) {
			    	// On l'initialise 
			        window.maCarte = L.map('divCarte').setView([46, 2], 5);
			        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
			          minZoom: 3,
			          maxZoom: 19,
			          attribution: '&copy; OpenStreetMap'
			        }).addTo(window.maCarte);
			      }
			    });
			}
		
		// Au chargement de la page, on affiche la carte sans filtre
		document.addEventListener('DOMContentLoaded', () => {
			// On charge la carte 
		  chargerCarte();
		});

		// Gestionnaire du formulaire
		// On écoute le submit du form
		document.getElementById('form').addEventListener('submit', function(event) {
			// Empêche la redirection vers l'action du form
		  event.preventDefault();
		
			// On récupère les données du form
		  const formData = new FormData(this);
			// On créé l'objet filtre
		  const filtre = {};
		
		  // On remplir l'objet filtre avec les valeurs non vides du formulaire
		  formData.forEach((value, key) => {
		    if (value.trim() !== '') filtre[key] = value.trim();
		  });
		
		  // Vérifier si la géolocalisation est cochée
		  const useGeoLoc = document.getElementById('useGeoLoc').checked;
		
		  // Si la géoloc est cochée
		  if (useGeoLoc) {
		    // Récupère la position actuelle du navigateur
		    if (navigator.geolocation) {
		    	// Accessible dans le contexte js des pages web
		      navigator.geolocation.getCurrentPosition(
		        function(position) {
		          // Ajoute les coordonnées à l'objet filtre
		          filtre['useGeoLoc'] = 'true';
		          filtre['lat'] = position.coords.latitude;
		          filtre['lon'] = position.coords.longitude;
		
		          // Appelle la fonction avec géoloc
		          chargerCarte(filtre);
		        },
		        // Gestion des erreurs 
		        function(error) {
		          alert("La géolocalisation a échoué ou a été refusée.");
		          // On envoie quand même le filtre sans géoloc
		          chargerCarte(filtre);
		        }
		      );
		    } else {
		      alert("La géolocalisation n'est pas supportée par ce navigateur.");
		      chargerCarte(filtre);
		    }
		  } else {
		    filtre['useGeoLoc'] = 'false';
		    chargerCarte(filtre);
		  }
		});

   		</script>

		
		
    </body>
</html>