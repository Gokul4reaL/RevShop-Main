package com.revshop.controller;

import com.revshop.service.CartService;
import com.revshop.serviceimpl.CartImpl; // Make sure this is your implementation class
import com.revshop.util.JavaAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/removeFromCart")
public class RemoveFromCartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CartService cartService;

    @Override
    public void init() {
        cartService = new CartImpl(); // Initialize the service implementation
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JavaAuthenticationFilter authFilter = new JavaAuthenticationFilter();

        // Use the filter to validate the JWT token
        if (!authFilter.validateToken(request, response)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return; // Exit if the token is invalid
        }

        String userId = request.getParameter("userId");
        String productId = request.getParameter("productId");

        try {
            // Call the service to remove the item from the cart
            cartService.removeCartItem(userId, productId); // Assuming this method exists in CartService

            // Send success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"success\":\"Product removed from the cart successfully!\"}");

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(String.format("{\"error\":\"Failed to remove product from the cart: %s\"}", e.getMessage()));
        }
    }
}
