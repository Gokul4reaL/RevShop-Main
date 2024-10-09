package com.revshop.serviceimpl;

import com.revshop.model.Reviews;
import com.revshop.repository.ReviewsRepository;
import com.revshop.service.ReviewService;

import java.sql.SQLException;
import java.util.List;

public class ReviewsImpl implements ReviewService {

    @Override
    public void addReview(Reviews review) throws SQLException {
        // Call the repository to add a new review
        ReviewsRepository.addReviewRepo(review);
    }

    @Override
    public void updateReview(Reviews review) throws SQLException {
        // Call the repository to update an existing review
        ReviewsRepository.updateReviewRepo(review);
    }

    @Override
    public void removeReview(String userId, String productId) throws SQLException {
        // Call the repository to remove a review by userId and productId
        ReviewsRepository.removeReviewRepo(userId, productId);
    }

    @Override
    public List<Reviews> getReviewsByProductId(String productId) throws SQLException {
        // Call the repository to retrieve all reviews for a specific product
        return ReviewsRepository.getReviewsByProductIdRepo(productId);
    }

    @Override
    public Reviews getReviewByUserIdAndProductId(String userId, String productId) throws SQLException {
        // Call the repository to retrieve a review by userId and productId
        return ReviewsRepository.getReviewByUserIdAndProductIdRepo(userId, productId);
    }

    @Override
    public double getAverageRatingByProductId(String productId) throws SQLException {
        // Call the repository to calculate the average rating for a specific product
        return ReviewsRepository.getAverageRatingByProductIdRepo(productId);
    }
}
