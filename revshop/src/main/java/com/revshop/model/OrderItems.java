package com.revshop.model;

import java.sql.Timestamp;
import java.util.UUID;

public class OrderItems {
    private String orderItemId;    // UUID as String
    private String orderId;         // Reference to the order
    private String sellerId;
    private String productId;       // Reference to the product being ordered
    private int quantity;           // Quantity of the product ordered
    private double priceAtTime;     // Price at the time of order
    private double discountedPriceAtTime; // Discounted price at the time of order
    private String imageUrl;  
    private String productName;// URL of the product image
    private Timestamp addedDate;    // Timestamp when the item was added to the order

    // Default constructor
    public OrderItems() {
        this.orderItemId = UUID.randomUUID().toString(); // Generate a random UUID
        this.addedDate = new Timestamp(System.currentTimeMillis()); // Set the current timestamp
    }

    // Parameterized constructor
    public OrderItems(String orderId, String productId, String sellerId, int quantity, double priceAtTime, double discountedPriceAtTime, String imageUrl, String productName) {
        this.orderItemId = UUID.randomUUID().toString(); // Generate a random UUID
        this.orderId = orderId;
        this.productId = productId;
        this.sellerId = sellerId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
        this.discountedPriceAtTime = discountedPriceAtTime;
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.addedDate = new Timestamp(System.currentTimeMillis()); // Set the current timestamp
    }
    
    public OrderItems(String orderId, String productId, String sellerId, int quantity, double priceAtTime, double discountedPriceAtTime) {
        this.orderItemId = UUID.randomUUID().toString(); // Generate a random UUID
        this.orderId = orderId;
        this.productId = productId;
        this.sellerId = sellerId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
        this.discountedPriceAtTime = discountedPriceAtTime;
        this.addedDate = new Timestamp(System.currentTimeMillis()); // Set the current timestamp
    }

    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	// Getters and Setters
    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }

    // toString method to print order item details
    @Override
    public String toString() {
        return "OrderItems{" +
                "orderItemId='" + orderItemId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", priceAtTime=" + priceAtTime +
                ", discountedPriceAtTime=" + discountedPriceAtTime +
                ", imageUrl='" + imageUrl + '\'' +
                ", addedDate=" + addedDate +
                '}';
    }
}
