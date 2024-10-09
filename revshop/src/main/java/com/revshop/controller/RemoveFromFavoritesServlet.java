package com.revshop.controller;

import com.revshop.service.FavoritesService;
import com.revshop.serviceimpl.FavoritesImpl;
import com.revshop.util.JavaAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/removeFromFavorites")
public class RemoveFromFavoritesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private FavoritesService favoritesService;

    @Override
    public void init() {
        favoritesService = new FavoritesImpl(); // Initialize the service implementation
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
            // Get favorite details from the request
            String userId = request.getParameter("userId");
            String productId = request.getParameter("productId");

            // Check for null or empty values
            if (userId == null || productId == null) {
                throw new IllegalArgumentException("Both userId and productId are required.");
            }

            // Remove the favorite item via FavoritesService
            favoritesService.removeFavoriteItem(userId, productId);

            // Send success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\":\"Product removed from favorites successfully!\"}");

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(String.format("{\"error\":\"Failed to remove product from favorites: %s\"}", e.getMessage()));
        }
    }
}
