package com.revshop.repository;

import com.revshop.dao.FavoritesDAO;
import com.revshop.model.Favorites;

import java.sql.SQLException;
import java.util.List;

public class FavoritesRepository {

    // Add a new item to favorites
    public static void addFavoriteItemRepo(Favorites favorite) throws SQLException {
        // Create a new FavoritesDAO instance and call its method to add a new favorite item
        FavoritesDAO favoritesDAO = new FavoritesDAO();
        favoritesDAO.addFavoriteItem(favorite);
    }

    // Remove an item from favorites
    public static void removeFavoriteItemRepo(String userId, String productId) throws SQLException {
        // Create a new FavoritesDAO instance and call its method to remove the favorite item
        FavoritesDAO favoritesDAO = new FavoritesDAO();
        favoritesDAO.removeFavoriteItem(userId, productId);
    }

    // Retrieve all favorite items for a specific user
    public static List<Favorites> getFavoriteItemsByUserIdRepo(String userId) throws SQLException {
        // Create a new FavoritesDAO instance and call its method to get all favorite items for the user
        FavoritesDAO favoritesDAO = new FavoritesDAO();
        return favoritesDAO.getFavoriteItemsByUserId(userId);
    }

    // Clear all items from favorites for a specific user
    public static void clearFavoritesRepo(String userId) throws SQLException {
        // Create a new FavoritesDAO instance and call its method to clear the favorites for the user
        FavoritesDAO favoritesDAO = new FavoritesDAO();
        favoritesDAO.clearFavorites(userId);
    }
}
