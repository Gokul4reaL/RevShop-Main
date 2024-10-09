package com.revshop.controller;

import com.revshop.model.Reviews;
import com.revshop.service.ReviewService;
import com.revshop.serviceimpl.ReviewsImpl;
import com.revshop.util.JavaAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addReview")
public class AddReviewServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ReviewService reviewService;

    @Override
    public void init() {
        reviewService = new ReviewsImpl(); // Initialize the service implementation
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
            // Get review details from the request
            String userId = request.getParameter("userId");
            String productId = request.getParameter("productId");
            String reviewDescription = request.getParameter("reviewDescription");
            double rating = Double.parseDouble(request.getParameter("rating"));

            // Check for null or empty values
            if (userId == null || productId == null || reviewDescription == null || rating < 0 || rating > 5) {
                throw new IllegalArgumentException("Valid userId, productId, reviewDescription, and rating are required.");
            }

            // Create Reviews object
            Reviews review = new Reviews(userId, productId, rating, reviewDescription);

            // Add the review via ReviewService
            reviewService.addReview(review);

            // Send success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\":\"Review added successfully!\"}");

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(String.format("{\"error\":\"Failed to add review: %s\"}", e.getMessage()));
        }
    }
}
