<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout Confirmation</title>
    <link rel="stylesheet" href="css/checkoutConfirmationComponent.css"> <!-- Add your own styles -->    
</head>
<body>

    <!-- Navbar (reuse if needed) -->
    <nav class="navbar">
        <div class="logo">RevShop</div>
        <div class="menu">
            <a href="/revshop/userHomeComponent.jsp">Products</a>
            <a href="/revshop/cartComponent.jsp">My Cart</a>
            <a href="/revshop/favoritesComponent.jsp">Favorites</a>
            <a href="/revshop/ordersComponent.jsp">Orders</a>
            <a href="/revshop/profileComponent.jsp">Profile</a>
        </div>
        <div class="logout">
            <button onclick="handleLogout()">Logout</button>
        </div>
    </nav>

    <!-- Checkout Confirmation Section -->
    <section id="checkoutConfirmation" class="checkout-section">
        <h2>Checkout Confirmation</h2>
        
        <!-- Display Cart Items -->
        <div class="checkout-items" id="checkoutItemsContainer">
    <!-- Cart items will be dynamically inserted here -->
		    <div class="item-info">            
		        <h3>Delivery Address: </h3>
		        <label>
		            <textarea name="orderAddress" style="width: 100%; height: 90px; padding: 10px; border: 1px solid #ccc; border-radius: 5px; resize: none; font-size: 16px; color: #333;" placeholder="Enter your delivery address here..."></textarea>
		        </label>
		    </div>
		</div>
        

        <!-- Payment Options -->
        <div class="payment-options">
            <h3>Select Payment Method</h3>
            <label>
                <input type="radio" name="paymentMethod" value="cod" checked> Cash on Delivery
            </label>
            <label>
                <input type="radio" name="paymentMethod" value="online"> Online Payment
            </label>
        </div>

        <!-- Confirm Button -->
        <button class="confirm-btn" onclick="confirmOrder()">Confirm Order</button>
    </section>

    <!-- Footer -->
    <footer>
        <p>&copy; 2024 RevShop. All rights reserved.</p>
    </footer>

    <script src="js/checkoutConfirmationComponent.js"></script> <!-- Link to the JS file -->
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script> <!-- Razorpay SDK -->
    
</body>
</html>
