package com.revshop.service;

import com.revshop.model.Favorites;

import java.sql.SQLException;
import java.util.List;

public interface FavoritesService {

    // Method for adding an item to favorites
    void addFavoriteItem(Favorites favorite) throws SQLException;

    // Method for removing an item from favorites
    void removeFavoriteItem(String userId, String productId) throws SQLException;

    // Method to retrieve all favorite items for a specific user
    List<Favorites> getFavoriteItemsByUserId(String userId) throws SQLException;

    // Method to clear all favorites for a specific user
    void clearFavorites(String userId) throws SQLException;
}
