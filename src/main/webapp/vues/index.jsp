<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>SportiZone - Gestion des clubs sportifs</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet" href="styles/style.css">
   <link rel="stylesheet" href="styles/styleHeader.css">
      <link rel="stylesheet" href="styles/styleFooter.css">
</head>
<body>
	<!-- Header -->
	<%@include file="header.jsp"%>
	<!-- Hero Section -->
	<section class="hero">
		<div class="container cont2">
			<div class="hero-content">
				<h1>Bienvenus à SportiZone</h1>
				<p class="hero-slogan">Découvrez votre sport, trouvez votre
					club, où que vous soyez</p>
				<div class="hero-buttons">
					<a href="visiteur/indexVisiteur.jsp" class="btn btn-access me-3">Accéder sans compte</a> <a
						href="connexion/login.jsp" class="btn btn-member">Espace réservé aux
						membres</a>
				</div>

			</div>
		</div>
	</section>

	<!-- Section de contenu -->
	<section class="content-section">
		<div class="container">
			<div class="content-text mb-5">
				<p>aLorem ipsum dolor sit amet. Aut distinctio doloremque</p>
			</div>
			<div class="content-text">
				<p>aLorem ipsum dolor sit amet. Aut distinctio doloremque 33
					aperiam enim et velit illo et consequatur inventore sed atquei</p>
			</div>
		</div>
	</section>

	<!-- Section À propos -->
	<section class="about-section">
		<div class="container">
			<h2>About SportiZone</h2>
			<div class="about-text mb-4">
				<p>Lorem ipsum dolor sit amet. Aut distinctio doloremque 33
					aperiam enim et velit illo et consequatur inventore sed atque
					alias. Qui molestias sint ut dolores consectetur et consectetur
					rerum At odit quam. Est nulla dolores ut reprehenderit beatae est
					obcaecati sunt ut quam ratione rem molestiae similique vel corporis
					voluptatem ut animi quia. Quo voluptatum fugiat non nobis
					reprehenderit et consectetur earum sed quia quos. Et odio
					consequuntur aut similique recusandae et libero animi est sunt
					illum At culpa ipsa. Et ratione fugiat ea voluptatem explicabo sed
					voluptatem quisquam sed assumenda deleniti. Ut fugit ipsam quo
					mollitia itaque et dolorem modi eos internos facere rem itaque
					quidem. Ut eveniet minus ut reprehenderit voluptatibus et optio
					deleniti ad vitae fugiat a magnam doloribus nam tempora culpa. In
					pariatur fuga non iusto ratione et odit officiis. Et fuga ipsum in
					voluptas facilis cum quia molestiae in minima aliquid sed
					voluptatem minus nam aliquid omnis in nihil adipisci.</p>
			</div>
			<div class="about-text">
				<p>Lorem ipsum dolor sit amet. Aut distinctio doloremque 33
					aperiam enim et velit illo et consequatur inventore sed atque
					alias. Qui molestias sint ut dolores consectetur et consectetur
					rerum At odit quam. Est nulla dolores ut reprehenderit beatae est
					obcaecati sunt ut quam ratione rem molestiae similique vel corporis
					voluptatem ut animi quia. Quo voluptatum fugiat non nobis
					reprehenderit et consectetur earum sed quia quos. Et odio
					consequuntur aut similique recusandae et libero animi est sunt
					illum At culpa ipsa. Et ratione fugiat ea voluptatem explicabo sed
					voluptatem quisquam sed assumenda deleniti. Ut fugit ipsam quo
					mollitia itaque et dolorem modi eos internos facere rem itaque
					quidem. Ut eveniet minus ut reprehenderit voluptatibus et optio
					deleniti ad vitae fugiat a magnam doloribus nam tempora culpa. In
					pariatur fuga non iusto ratione et odit officiis. Et fuga ipsum in
					voluptas facilis cum quia molestiae in minima aliquid sed
					voluptatem minus nam aliquid omnis in nihil adipisci.</p>

			</div>
		</div>
	</section>


	<!-- Footer -->
	<%@include file="footer.jsp"%>



</body>
</html>
