package com.revshop.validator;

import com.revshop.dto.*;

public class ValidateProduct {
    public static void validateProduct(ProductDTO request) throws IllegalArgumentException {
        if (request.getSellerId() == null || request.getSellerId().isEmpty()) {
            throw new IllegalArgumentException("Seller ID is required");
        }
        if (request.getProductName() == null || request.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Product description is required");
        }
        if (request.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
        if (request.getQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }
        if (request.getDiscountedPrice() < 0) {
            throw new IllegalArgumentException("Discounted price cannot be negative");
        }
        if (request.getImageUrl() == null || request.getImageUrl().isEmpty()) {
            throw new IllegalArgumentException("Product image URL is required");
        }
        // Additional validations can be added as needed
    }
    
    public static void validateEditProduct(EditProductDTO request) {
    	if (request.getProductId() == null || request.getProductId().isEmpty()) {
            throw new IllegalArgumentException("Product ID is required");
        }
    	if (request.getSellerId() == null || request.getSellerId().isEmpty()) {
            throw new IllegalArgumentException("Seller ID is required");
        }
        if (request.getProductName() == null || request.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Product description is required");
        }
        if (request.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
        if (request.getQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }
        if (request.getDiscountedPrice() < 0) {
            throw new IllegalArgumentException("Discounted price cannot be negative");
        }
        if (request.getCreatedDate() == null) {
            throw new IllegalArgumentException("Created Date is required");
        }
        if (request.getImageUrl() == null || request.getImageUrl().isEmpty()) {
            throw new IllegalArgumentException("Product image URL is required");
        }
    }
}
