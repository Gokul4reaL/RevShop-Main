package com.revshop.serviceimpl;

import com.revshop.model.Cart;
import com.revshop.repository.CartRepository;
import com.revshop.service.CartService;

import java.sql.SQLException;
import java.util.List;

public class CartImpl implements CartService {

    @Override
    public void addCartItem(Cart cart) throws SQLException {
        // Call the repository to add an item to the cart
        CartRepository.addCartItemRepo(cart);
    }

    @Override
    public void updateCartItemQuantity(String userId, String productId, int newQuantity) throws SQLException {
        // Call the repository to update the quantity of an item in the cart
        CartRepository.updateCartItemQuantityRepo(userId, productId, newQuantity);
    }

    @Override
    public void removeCartItem(String userId, String productId) throws SQLException {
        // Call the repository to remove an item from the cart
        CartRepository.removeCartItemRepo(userId, productId);
    }

    @Override
    public List<Cart> getCartItemsByUserId(String userId) throws SQLException {
        // Call the repository to retrieve all items in the cart for a specific user
        return CartRepository.getCartItemsByUserIdRepo(userId);
    }

    @Override
    public void clearCart(String userId) throws SQLException {
        // Call the repository to clear the cart for a specific user
        CartRepository.clearCartRepo(userId);
    }
}
