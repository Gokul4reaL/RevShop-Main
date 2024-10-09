<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buyer Home</title>
    <link rel="stylesheet" href="css/userHomeComponent.css"> <!-- Link to the CSS file -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"> <!-- For icons -->
</head>
<body>

<div id="loader-overlay">
        <div class="loader"></div>
    </div>

    <!-- Navbar -->
    <nav class="navbar">
        <div class="logo">RevShop</div>
        <div class="menu">
	        <a id="productsLink" class="active">Products</a>
	        <a href="/revshop/cartComponent.jsp" id="cartLink">My Cart</a>
	        <a href="/revshop/favoritesComponent.jsp" id="favoritesLink">Favorites</a>
	        <a href="/revshop/ordersComponent.jsp" id="ordersLink">Orders</a>
	        <a href="/revshop/profileComponent.jsp" id="profileLink">Profile</a>
        </div>
        <div class="logout">
            <button onclick="handleLogout()">Logout</button>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero">
        <h1>Welcome to RevShop, <span id="username">User</span></h1>
        <p>Browse through our products and shop the best deals!</p>
    </section>

    <!-- Product Listing -->
    <section id="products" class="products-section">
        <h2>Our Products</h2>
        
        <!-- Filters Section -->
        <div class="filters">
            <input type="text" id="productNameFilter" placeholder="Search by product name" />
            <input type="text" id="descriptionFilter" placeholder="Search by description" />
            <button onclick="filterProducts()">Filter</button>
        </div>
        
        <div class= "products-container-wrapper" id = "productsContainerWrapper">
        <div class="products-container" id="productsContainer">
            <!-- Product cards will be dynamically inserted here -->
        </div>
        </div>

        <!-- Pagination Controls -->
        <div class="pagination">
            <button id="prevPage" onclick="changePage(currentPage - 1)">Previous</button>
            <span id="pageInfo">Page 1</span>
            <button id="nextPage" onclick="changePage(currentPage + 1)">Next</button>
        </div>
    </section>

    <!-- Footer -->
    <footer>
        <p>&copy; 2024 RevShop. All rights reserved.</p>
    </footer>

    <script src="js/userHomeComponent.js"></script> <!-- Link to your JS file -->
</body>
</html>
