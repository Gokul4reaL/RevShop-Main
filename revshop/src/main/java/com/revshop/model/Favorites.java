package com.revshop.model;

import java.sql.Timestamp;

public class Favorites {
    private String userId;
    private String productId;
    private Timestamp addedDate;
    private String productName;
    private String imageUrl;
    private double price;
	private double discountedPriceAtTime;

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

	// Default constructor
    public Favorites() {
        this.addedDate = new Timestamp(System.currentTimeMillis()); // Automatically set the added date
    }

    // Parameterized constructor
    public Favorites(String userId, String productId) {
        this.userId = userId;
        this.productId = productId;
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

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }

    // toString method to print favorite details
    @Override
    public String toString() {
        return "Favorites{" +
                "userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", addedDate=" + addedDate +
                '}';
    }
}
