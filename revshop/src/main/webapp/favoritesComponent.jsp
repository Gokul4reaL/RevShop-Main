<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Favorites</title>
    <link rel="stylesheet" href="css/favoritesComponent.css"> <!-- Using the same CSS file as cartComponent -->
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
            <a href="/revshop/userHomeComponent.jsp">Products</a>
            <a href="/revshop/cartComponent.jsp">My Cart</a>
            <a id="favoritesLink" class="active">Favorites</a>
            <a href="/revshop/ordersComponent.jsp">Orders</a>
            <a href="/revshop/profileComponent.jsp">Profile</a>
        </div>
        <div class="logout">
            <button onclick="handleLogout()">Logout</button>
        </div>
    </nav>

    <!-- Favorites Section -->
    <section id="favorites" class="cart-section">
        <h2>My Favorites</h2>

        <div class="cart-items" id="favoritesItemsContainer">
            <!-- Favorite items will be dynamically inserted here -->
        </div>
    </section>

    <!-- Footer -->
    <footer>
        <p>&copy; 2024 RevShop. All rights reserved.</p>
    </footer>

    <script src="js/favoritesComponent.js"></script> <!-- Link to the JS file -->
</body>
</html>
