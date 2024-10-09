package com.revshop.controller;

import com.revshop.model.Cart;
import com.revshop.service.CartService;
import com.revshop.serviceimpl.CartImpl;
import com.revshop.util.JavaAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CartService cartService;

    @Override
    public void init() {
        cartService = new CartImpl(); // Initialize the service implementation
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Create an instance of the JavaAuthenticationFilter
        JavaAuthenticationFilter authFilter = new JavaAuthenticationFilter();

        // Use the filter to validate the JWT token
        if (!authFilter.validateToken(request, response)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return; // Exit if the token is invalid
        }

        try {
            // Get cart details from the request
            String userId = request.getParameter("userId"); // Retrieve the userId from the authenticated user
            String productId = request.getParameter("productId");
            String quantityStr = request.getParameter("quantity");
            String priceAtTimeStr = request.getParameter("priceAtTime");
            String discountedPriceAtTimeStr = request.getParameter("discountedPriceAtTime");

            // Check for null or empty values
            if (productId == null || quantityStr == null || priceAtTimeStr == null || discountedPriceAtTimeStr == null) {
                throw new IllegalArgumentException("All cart fields are required.");
            }

            // Parse quantity, price, and discounted price
            int quantity = Integer.parseInt(quantityStr.trim());
            double priceAtTime = Double.parseDouble(priceAtTimeStr.trim());
            double discountedPriceAtTime = Double.parseDouble(discountedPriceAtTimeStr.trim());

            // Create Cart object
            Cart cart = new Cart(userId, productId, quantity, priceAtTime, discountedPriceAtTime);

            // Add the cart item via CartService
            cartService.addCartItem(cart);

            // Send success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\":\"Product Added Succesfully\"}");

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(String.format("{\"error\":\"Failed to add product to cart: %s\"}", e.getMessage()));
        }
    }
}
