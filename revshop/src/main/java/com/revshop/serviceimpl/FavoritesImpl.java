package com.revshop.serviceimpl;

import com.revshop.model.Favorites;
import com.revshop.repository.FavoritesRepository;
import com.revshop.service.FavoritesService;

import java.sql.SQLException;
import java.util.List;

public class FavoritesImpl implements FavoritesService {

    @Override
    public void addFavoriteItem(Favorites favorite) throws SQLException {
        // Call the repository to add an item to favorites
        FavoritesRepository.addFavoriteItemRepo(favorite);
    }

    @Override
    public void removeFavoriteItem(String userId, String productId) throws SQLException {
        // Call the repository to remove an item from favorites
        FavoritesRepository.removeFavoriteItemRepo(userId, productId);
    }

    @Override
    public List<Favorites> getFavoriteItemsByUserId(String userId) throws SQLException {
        // Call the repository to retrieve all favorite items for a specific user
        return FavoritesRepository.getFavoriteItemsByUserIdRepo(userId);
    }

    @Override
    public void clearFavorites(String userId) throws SQLException {
        // Call the repository to clear all favorites for a specific user
        FavoritesRepository.clearFavoritesRepo(userId);
    }
}
