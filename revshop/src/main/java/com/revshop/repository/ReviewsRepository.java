package com.revshop.repository;

import com.revshop.dao.ReviewsDAO;
import com.revshop.model.Reviews;

import java.sql.SQLException;
import java.util.List;

public class ReviewsRepository {

    // Add a new review
    public static void addReviewRepo(Reviews review) throws SQLException {
        // Create a new ReviewsDAO instance and call its method to add a new review
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        reviewsDAO.addReview(review);
    }

    // Update an existing review
    public static void updateReviewRepo(Reviews review) throws SQLException {
        // Create a new ReviewsDAO instance and call its method to update an existing review
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        reviewsDAO.updateReview(review);
    }

    // Remove a review by userId and productId
    public static void removeReviewRepo(String userId, String productId) throws SQLException {
        // Create a new ReviewsDAO instance and call its method to remove the review
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        reviewsDAO.removeReview(userId, productId);
    }

    // Retrieve all reviews for a specific product
    public static List<Reviews> getReviewsByProductIdRepo(String productId) throws SQLException {
        // Create a new ReviewsDAO instance and call its method to get all reviews for the product
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        return reviewsDAO.getReviewsByProductId(productId);
    }

    // Retrieve a review for a product by a specific user
    public static Reviews getReviewByUserIdAndProductIdRepo(String userId, String productId) throws SQLException {
        // Create a new ReviewsDAO instance and call its method to get the review by userId and productId
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        return reviewsDAO.getReviewByUserIdAndProductId(userId, productId);
    }

    // Get the average rating of a product
    public static double getAverageRatingByProductIdRepo(String productId) throws SQLException {
        // Create a new ReviewsDAO instance and call its method to calculate the average rating for the product
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        return reviewsDAO.getAverageRatingByProductId(productId);
    }
}
