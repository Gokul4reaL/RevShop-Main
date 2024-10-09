package com.revshop.dao;

import com.revshop.model.Reviews;
import com.revshop.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewsDAO {

    // SQL queries
    private static final String INSERT_REVIEW_SQL = "INSERT INTO reviews (user_id, product_id, rating, review_description) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_REVIEW_SQL = "UPDATE reviews SET rating = ?, description = ? WHERE user_id = ? AND product_id = ?";
    private static final String DELETE_REVIEW_SQL = "DELETE FROM reviews WHERE user_id = ? AND product_id = ?";
    private static final String SELECT_REVIEWS_BY_PRODUCT_ID_SQL = "SELECT * FROM reviews WHERE product_id = ?";
    private static final String SELECT_REVIEW_BY_USER_ID_AND_PRODUCT_ID_SQL = "SELECT * FROM reviews WHERE user_id = ? AND product_id = ?";
    private static final String SELECT_AVERAGE_RATING_SQL = "SELECT AVG(rating) FROM reviews WHERE product_id = ?";

    // Add a new review
    public void addReview(Reviews review) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REVIEW_SQL)) {

            preparedStatement.setString(1, review.getUserId());
            preparedStatement.setString(2, review.getProductId());
            preparedStatement.setDouble(3, review.getRating());
            preparedStatement.setString(4, review.getReviewDescription());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Review added successfully!");
            } else {
                System.out.println("Failed to add review.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting review", e);
        }
    }

    // Update an existing review
    public void updateReview(Reviews review) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_REVIEW_SQL)) {

            preparedStatement.setDouble(1, review.getRating());
            preparedStatement.setString(2, review.getReviewDescription());
            preparedStatement.setString(3, review.getUserId());
            preparedStatement.setString(4, review.getProductId());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Review updated successfully!");
            } else {
                System.out.println("Failed to update review.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating review", e);
        }
    }

    // Remove a review
    public void removeReview(String userId, String productId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REVIEW_SQL)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, productId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Review removed successfully!");
            } else {
                System.out.println("Failed to remove review.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error removing review", e);
        }
    }

    // Retrieve all reviews for a specific product
    public List<Reviews> getReviewsByProductId(String productId) throws SQLException {
        List<Reviews> reviewsList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REVIEWS_BY_PRODUCT_ID_SQL)) {

            preparedStatement.setString(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Create a Reviews object
                Reviews review = new Reviews();
                review.setUserId(resultSet.getString("user_id"));
                review.setProductId(resultSet.getString("product_id"));
                review.setRating(resultSet.getDouble("rating"));
                review.setReviewDescription(resultSet.getString("review_description"));

                // Add the review to the list
                reviewsList.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving reviews by product ID", e);
        }
        return reviewsList;
    }

    // Retrieve a review for a product by a specific user
    public Reviews getReviewByUserIdAndProductId(String userId, String productId) throws SQLException {
        Reviews review = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REVIEW_BY_USER_ID_AND_PRODUCT_ID_SQL)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Create a Reviews object
                review = new Reviews();
                review.setUserId(resultSet.getString("user_id"));
                review.setProductId(resultSet.getString("product_id"));
                review.setRating(resultSet.getDouble("rating"));
                review.setReviewDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving review by user ID and product ID", e);
        }
        return review;
    }

    // Get the average rating of a product
    public double getAverageRatingByProductId(String productId) throws SQLException {
        double averageRating = 0.0;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AVERAGE_RATING_SQL)) {

            preparedStatement.setString(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                averageRating = resultSet.getDouble(1); // The first column of the result set
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error calculating average rating", e);
        }
        return averageRating;
    }
}
