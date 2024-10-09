package com.revshop.model;

import java.sql.Timestamp;

public class Reviews {
    private String userId;
    private String productId;
    private double rating;
    private String reviewDescription;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String productName;
    private String imageUrl;
    private double price;
    private double discountedPriceAtTime;

    // Default constructor
    public Reviews() {
        this.createdAt = new Timestamp(System.currentTimeMillis()); // Automatically set the creation time
        this.updatedAt = new Timestamp(System.currentTimeMillis()); // Automatically set the update time
    }

    // Parameterized constructor
    public Reviews(String userId, String productId, double rating, String reviewDescription) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.reviewDescription = reviewDescription;
        this.createdAt = new Timestamp(System.currentTimeMillis()); // Automatically set the creation time
        this.updatedAt = new Timestamp(System.currentTimeMillis()); // Automatically set the update time
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountedPriceAtTime() {
        return discountedPriceAtTime;
    }

    public void setDiscountedPriceAtTime(double discountedPriceAtTime) {
        this.discountedPriceAtTime = discountedPriceAtTime;
    }

    // toString method to print review details
    @Override
    public String toString() {
        return "Reviews{" +
                "userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", rating=" + rating +
                ", reviewDescription='" + reviewDescription + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
