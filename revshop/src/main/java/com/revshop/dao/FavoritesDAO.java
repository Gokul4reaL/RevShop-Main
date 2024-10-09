package com.revshop.dao;

import com.revshop.model.Favorites;
import com.revshop.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoritesDAO {

    // SQL queries
    private static final String INSERT_FAVORITE_ITEM_SQL = "INSERT INTO favorites (user_id, product_id) VALUES (?, ?)";
    private static final String DELETE_FAVORITE_ITEM_SQL = "DELETE FROM favorites WHERE user_id = ? AND product_id = ?";
    private static final String CLEAR_FAVORITES_SQL = "DELETE FROM favorites WHERE user_id = ?";

    // Add a new favorite item
    public void addFavoriteItem(Favorites favorite) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FAVORITE_ITEM_SQL)) {

            preparedStatement.setString(1, favorite.getUserId());
            preparedStatement.setString(2, favorite.getProductId());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Favorite item added successfully!");
            } else {
                System.out.println("Failed to add favorite item.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting favorite item", e);
        }
    }

    // Remove a favorite item
    public void removeFavoriteItem(String userId, String productId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FAVORITE_ITEM_SQL)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, productId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Favorite item removed successfully!");
            } else {
                System.out.println("Failed to remove favorite item.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error removing favorite item", e);
        }
    }

    public List<Favorites> getFavoriteItemsByUserId(String userId) throws SQLException {
        List<Favorites> favoriteItems = new ArrayList<>();
        
        // Corrected SQL query to retrieve favorite items along with product details (name, image_url)
        String SELECT_FAVORITE_ITEMS_WITH_PRODUCT_DETAILS_SQL = 
            "SELECT f.user_id, f.product_id, p.price, p.discounted_price, p.product_name, p.image_url " +
            "FROM favorites f " +
            "JOIN products p ON f.product_id = p.product_id " +
            "WHERE f.user_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FAVORITE_ITEMS_WITH_PRODUCT_DETAILS_SQL)) {
            
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Create a Favorites object
                Favorites favoriteItem = new Favorites(
                    resultSet.getString("user_id"),
                    resultSet.getString("product_id")
                );
                
                // Set product name and image URL
                favoriteItem.setProductName(resultSet.getString("product_name"));
                favoriteItem.setImageUrl(resultSet.getString("image_url"));
                favoriteItem.setPrice(resultSet.getDouble("price"));
                favoriteItem.setDiscountedPriceAtTime(resultSet.getDouble("discounted_price"));

                // Add the favorite item to the list
                favoriteItems.add(favoriteItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception (could be improved with proper logging)
            throw new SQLException("Error retrieving favorite items with product details", e);  // Re-throw if necessary
        }
        return favoriteItems;
    }

    // Clear all favorites for a specific user
    public void clearFavorites(String userId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_FAVORITES_SQL)) {

            preparedStatement.setString(1, userId);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Favorites cleared successfully!");
            } else {
                System.out.println("Failed to clear favorites.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error clearing favorites", e);
        }
    }
}
