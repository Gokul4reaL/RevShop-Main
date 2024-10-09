package com.revshop.controller;

import com.revshop.service.CartService;
import com.revshop.serviceimpl.CartImpl;
import com.revshop.util.JavaAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateCartQuantity")
public class UpdateCartQuantityServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CartService cartService;

    @Override
    public void init() {
        cartService = new CartImpl(); // Initialize the CartService implementation
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
        String quantityStr = request.getParameter("quantity");

        try {
            int quantity = Integer.parseInt(quantityStr); // Convert quantity to int
            
            // Call the service to update the cart item quantity
            cartService.updateCartItemQuantity(userId, productId, quantity); // Assuming this method exists in CartService
            
            // Send success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"success\": true}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"Invalid quantity format\"}");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Failed to update quantity: " + e.getMessage() + "\"}");
        }
    }
}
