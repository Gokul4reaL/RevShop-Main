package com.revshop.model;

import java.sql.Timestamp;

public class Cart {
    private String userId;
    private String productId;
    private int quantity;
    private double priceAtTime;
    private double discountedPriceAtTime;
    private String status;
    private Timestamp addedDate;
    private String imageUrl; // Add imageUrl field
    private String productName;

	// Default constructor
    public Cart() {
        this.addedDate = new Timestamp(System.currentTimeMillis()); // Automatically set the added date
        this.status = "active"; // Default status is 'active'
    }

    // Parameterized constructor
    public Cart(String userId, String productId, int quantity, double priceAtTime, double discountedPriceAtTime) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
        this.discountedPriceAtTime = discountedPriceAtTime;
        this.status = "active"; // Default status
        this.addedDate = new Timestamp(System.currentTimeMillis()); // Automatically set the added date
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(double priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    public double getDiscountedPriceAtTime() {
        return discountedPriceAtTime;
    }

    public void setDiscountedPriceAtTime(double discountedPriceAtTime) {
        this.discountedPriceAtTime = discountedPriceAtTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }
    
 // Getter and setter for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

    // toString method to print cart details
    @Override
    public String toString() {
        return "Cart{" +
                "userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", priceAtTime=" + priceAtTime +
                ", discountedPriceAtTime=" + discountedPriceAtTime +
                ", status='" + status + '\'' +
                ", addedDate=" + addedDate +
                '}';
    }
}

