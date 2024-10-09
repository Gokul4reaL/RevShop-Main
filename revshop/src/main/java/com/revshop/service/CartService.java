package com.revshop.service;

import com.revshop.model.Cart;

import java.sql.SQLException;
import java.util.List;

public interface CartService {

    // Method for adding an item to the cart
    void addCartItem(Cart cart) throws SQLException;

    // Method for updating the quantity of an item in the cart
    void updateCartItemQuantity(String userId, String productId, int newQuantity) throws SQLException;

    // Method for removing an item from the cart
    void removeCartItem(String userId, String productId) throws SQLException;

    // Method to retrieve all items in the cart for a specific user
    List<Cart> getCartItemsByUserId(String userId) throws SQLException;

    // Method to clear the cart for a specific user
    void clearCart(String userId) throws SQLException;
}
