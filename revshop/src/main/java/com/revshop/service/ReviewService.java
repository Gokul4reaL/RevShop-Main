package com.revshop.service;

import com.revshop.model.Reviews;

import java.sql.SQLException;
import java.util.List;

public interface ReviewService {

    // Method to add a review for a product
    void addReview(Reviews review) throws SQLException;

    // Method to update an existing review for a product
    void updateReview(Reviews review) throws SQLException;

    // Method to remove a review for a product by userId and productId
    void removeReview(String userId, String productId) throws SQLException;

    // Method to retrieve all reviews for a specific product
    List<Reviews> getReviewsByProductId(String productId) throws SQLException;

    // Method to retrieve a review for a product by a specific user
    Reviews getReviewByUserIdAndProductId(String userId, String productId) throws SQLException;

    // Method to get the average rating of a product
    double getAverageRatingByProductId(String productId) throws SQLException;
}
