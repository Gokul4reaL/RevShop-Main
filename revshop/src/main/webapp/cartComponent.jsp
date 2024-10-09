<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Cart</title>
    <link rel="stylesheet" href="css/cartComponent.css"> <!-- Link to the CSS file -->
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
            <a id="cartLink" class="active">My Cart</a>
            <a href="/revshop/favoritesComponent.jsp">Favorites</a>
            <a href="/revshop/ordersComponent.jsp">Orders</a>
            <a href="/revshop/profileComponent.jsp">Profile</a>
        </div>
        <div class="logout">
            <button onclick="handleLogout()">Logout</button>
        </div>
    </nav>

    <!-- Cart Section -->
    <section id="cart" class="cart-section">
        <h2>My Cart</h2>
        
        <div class="cart-items" id="cartItemsContainer">
            <!-- Cart items will be dynamically inserted here -->
        </div>

        <!-- Cart Summary -->
        <div class="cart-summary" id="cartSummary">
            <h3>Order Summary</h3>
            <p>Total Items: <span id="totalItems">0</span></p>
            <p>Total Price: Rs.<span id="totalPrice">0.00</span></p>
            <button class="checkout-btn" onclick="proceedToCheckout()">Proceed to Checkout</button>
        </div>
    </section>

    <!-- Footer -->
    <footer>
        <p>&copy; 2024 RevShop. All rights reserved.</p>
    </footer>

    <script src="js/cartComponent.js"></script> <!-- Link to the JS file -->
    
</body>
</html>
