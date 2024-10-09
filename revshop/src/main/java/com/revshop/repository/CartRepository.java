package com.revshop.repository;

import com.revshop.dao.CartDAO;
import com.revshop.model.Cart;

import java.sql.SQLException;
import java.util.List;

public class CartRepository {

    // Add a new item to the cart
    public static void addCartItemRepo(Cart cart) throws SQLException {
        // Create a new CartDAO instance and call its method to add a new cart item
        CartDAO cartDAO = new CartDAO();
        cartDAO.addCartItem(cart);
    }

    // Update the quantity of a cart item
    public static void updateCartItemQuantityRepo(String userId, String productId, int newQuantity) throws SQLException {
        // Create a new CartDAO instance and call its method to update the quantity
        CartDAO cartDAO = new CartDAO();
        cartDAO.updateCartItemQuantity(userId, productId, newQuantity);
    }

    // Remove an item from the cart
    public static void removeCartItemRepo(String userId, String productId) throws SQLException {
        // Create a new CartDAO instance and call its method to remove the cart item
        CartDAO cartDAO = new CartDAO();
        cartDAO.removeCartItem(userId, productId);
    }

    // Retrieve all cart items for a specific user
    public static List<Cart> getCartItemsByUserIdRepo(String userId) throws SQLException {
        // Create a new CartDAO instance and call its method to get all cart items for the user
        CartDAO cartDAO = new CartDAO();
        return cartDAO.getCartItemsByUserId(userId);
    }

    // Clear all items from the cart for a specific user
    public static void clearCartRepo(String userId) throws SQLException {
        // Create a new CartDAO instance and call its method to clear the cart for the user
        CartDAO cartDAO = new CartDAO();
        cartDAO.clearCart(userId);
    }
}
