package com.revshop.dao;

import com.revshop.model.Cart;
import com.revshop.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // SQL queries
    private static final String INSERT_CART_ITEM_SQL = "INSERT INTO cart (user_id, product_id, quantity, price_at_time, discounted_price_at_time) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_CART_ITEM_QUANTITY_SQL = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
    private static final String DELETE_CART_ITEM_SQL = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
    private static final String CLEAR_CART_SQL = "DELETE FROM cart WHERE user_id = ?";

    // Add a new cart item
    public void addCartItem(Cart cart) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CART_ITEM_SQL)) {

            preparedStatement.setString(1, cart.getUserId());
            preparedStatement.setString(2, cart.getProductId());
            preparedStatement.setInt(3, cart.getQuantity());
            preparedStatement.setDouble(4, cart.getPriceAtTime());
            preparedStatement.setDouble(5, cart.getDiscountedPriceAtTime());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Cart item added successfully!");
            } else {
                System.out.println("Failed to add cart item.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting cart item", e);
        }
    }

    // Update cart item quantity
    public void updateCartItemQuantity(String userId, String productId, int newQuantity) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CART_ITEM_QUANTITY_SQL)) {

            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, productId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Cart item quantity updated successfully!");
            } else {
                System.out.println("Failed to update cart item quantity.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating cart item quantity", e);
        }
    }

    // Remove a cart item
    public void removeCartItem(String userId, String productId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CART_ITEM_SQL)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, productId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Cart item removed successfully!");
            } else {
                System.out.println("Failed to remove cart item.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error removing cart item", e);
        }
    }

    // Retrieve all cart items for a specific user
    public List<Cart> getCartItemsByUserId(String userId) throws SQLException {
        List<Cart> cartItems = new ArrayList<>();
        String SELECT_CART_ITEMS_BY_USER_ID_SQL = "SELECT c.user_id, c.product_id, c.quantity, c.price_at_time, c.discounted_price_at_time,p.product_name, p.image_url " +
                                                  "FROM cart c " +
                                                  "JOIN products p ON c.product_id = p.product_id " +
                                                  "WHERE c.user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CART_ITEMS_BY_USER_ID_SQL)) {

            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Create a Cart object
                Cart cartItem = new Cart(
                    resultSet.getString("user_id"),
                    resultSet.getString("product_id"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("price_at_time"),
                    resultSet.getDouble("discounted_price_at_time")
                );
                
                String productName = resultSet.getString("product_name");
                cartItem.setProductName(productName);
                
                // Get the image URL from the products table
                String imageUrl = resultSet.getString("image_url");

                // Set the image URL in the Cart object if you have a field for it (if not, you may need to add it to the model)
                cartItem.setImageUrl(imageUrl);

                // Add the cart item to the list
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving cart items with product images", e);
        }
        return cartItems;
    }

    // Clear the cart for a specific user
    public void clearCart(String userId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_CART_SQL)) {

            preparedStatement.setString(1, userId);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Cart cleared successfully!");
            } else {
                System.out.println("Failed to clear cart.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error clearing cart", e);
        }
    }
}
