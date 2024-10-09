<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Landing Page</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/landingComponent.css">
</head>
<body>

    <!-- Carousel Section -->
    <div id="landingCarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#landingCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#landingCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#landingCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
        </div>

        <div class="carousel-inner">
    <!-- Slide 1 -->
    <div class="carousel-item active">
        <img src="images/image1.jpg" class="d-block w-100" alt="Slide 1">
        <div class="carousel-caption d-none d-md-block">
            <h2>Welcome to RevShop</h2>
            <p>Find your next adventure with just a click.</p>
            <div class="role-selection">
                <h3 class="role-question">Are you a Buyer or a Seller?</h3>
                <a href="buyerRegistration.jsp" class="btn btn-primary role-btn">Buyer</a>
                <a href="sellerRegistration.jsp" class="btn btn-secondary role-btn">Seller</a>
            </div>
        </div>
    </div>
    <!-- Slide 2 -->
    <div class="carousel-item">
        <img src="images/image2.jpg" class="d-block w-100" alt="Slide 2">
        <div class="carousel-caption d-none d-md-block">
            <h2>Shop the Latest Trends</h2>
            <p>Explore the latest collection, just for you.</p>
            <div class="role-selection">
                <h3 class="role-question">Are you a Buyer or a Seller?</h3>
                <a href="buyerRegistration.jsp" class="btn btn-primary role-btn">Buyer</a>
                <a href="sellerRegistration.jsp" class="btn btn-secondary role-btn">Seller</a>
            </div>
        </div>
    </div>
    <!-- Slide 3 -->
    <div class="carousel-item">
        <img src="images/image3.jpg" class="d-block w-100" alt="Slide 3">
        <div class="carousel-caption d-none d-md-block">
            <h2>Unbeatable Prices</h2>
            <p>Get the best deals on your favorite items.</p>
            <div class="role-selection">
                <h3 class="role-question">Are you a Buyer or a Seller?</h3>
                <a href="buyerRegistration.jsp" class="btn btn-primary role-btn">Buyer</a>
                <a href="sellerRegistration.jsp" class="btn btn-secondary role-btn">Seller</a>
            </div>
        </div>
    </div>
</div>

        <!-- Carousel Controls -->
        <button class="carousel-control-prev" type="button" data-bs-target="#landingCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#landingCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Custom JS -->
    <script src="js/landingComponent.js"></script>
</body>
</html>
