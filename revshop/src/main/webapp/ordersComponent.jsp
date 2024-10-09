<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Orders</title>
    <link rel="stylesheet" href="css/orderComponent.css"> <!-- Link to the CSS file -->
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
            <a href="/revshop/favoritesComponent.jsp">Favorites</a>
            <a id="ordersLink" class="active">Orders</a>
            <a href="/revshop/profileComponent.jsp">Profile</a>
        </div>
        <div class="logout">
            <button onclick="handleLogout()">Logout</button>
        </div>
    </nav>

    <!-- Orders Section -->
    <section id="orders" class="orders-section">
        <h2>My Orders</h2>

        <div class="order-items" id="orderItemsContainer">
            <!-- Order items will be dynamically inserted here -->
        </div>
    </section>

    <!-- Footer -->
    <footer>
        <p>&copy; 2024 RevShop. All rights reserved.</p>
    </footer>

    <script src="js/orderComponent.js"></script> <!-- Link to the JS file -->
</body>
</html>
