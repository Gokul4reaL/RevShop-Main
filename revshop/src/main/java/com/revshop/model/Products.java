package com.revshop.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

public class Products {
    private String productId;          // Unique identifier for the product (UUID)
    private String sellerId;           // ID of the seller who added the product (Foreign Key, UUID)
    private String productName;        // Name of the product
    private double price;              // Price of the product
    private String description;        // Description of the product
    private int quantity;              // Available quantity of the product
    private double discountedPrice;    // Discounted price of the product
    private Date createdDate;          // Date when the product was created
    private boolean isPublished;        // Indicates if the product is published
    private String imageUrl;  
    private double avg_rating;// URL or path to the product image
    private Timestamp createdAt;       // Timestamp when the product was created
    private Timestamp updatedAt;       // Timestamp when the product was last updated

    // Default constructor
    public Products() {
        this.productId = UUID.randomUUID().toString();  // Automatically generate a UUID for productId
    }

    // Parameterized constructor
    public Products(String sellerId, String productName, double price, String description, 
                    int quantity, double discountedPrice, Date createdDate, boolean isPublished, 
                    String imageUrl) {
        this.productId = UUID.randomUUID().toString();  // Automatically generate a UUID for productId
        this.sellerId = sellerId;                       // Use the provided sellerId
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.discountedPrice = discountedPrice;
        this.createdDate = createdDate;
        this.isPublished = isPublished;
        this.imageUrl = imageUrl;                       // Initialize the image URL
        this.createdAt = new Timestamp(System.currentTimeMillis());  // Set current timestamp
    }

    public double getAvg_rating() {
			return avg_rating;
		}

		public void setAvg_rating(double avg_rating) {
			this.avg_rating = avg_rating;
		}

	// Parameterized constructor with productId (for updates)
    public Products(String productId, String sellerId, String productName, double price, String description, 
                    int quantity, double discountedPrice, Date createdDate, boolean isPublished, 
                    String imageUrl) {
        this.productId = productId;  // Use the provided productId
        this.sellerId = sellerId;    // Use the provided sellerId
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.discountedPrice = discountedPrice;
        this.createdDate = createdDate;
        this.isPublished = isPublished;
        this.imageUrl = imageUrl;    // Initialize the image URL
        this.createdAt = new Timestamp(System.currentTimeMillis());  // Set current timestamp
    }

		    public Products(String productId, String sellerId, String productName, double price, String description, 
		            int quantity, double discountedPrice, Date createdDate, boolean isPublished, 
		            String imageUrl, double avg_rating) {
		this.productId = productId;  // Use the provided productId
		this.sellerId = sellerId;    // Use the provided sellerId
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
		this.discountedPrice = discountedPrice;
		this.createdDate = createdDate;
		this.isPublished = isPublished;
		this.imageUrl = imageUrl;    // Initialize the image URL
		this.createdAt = new Timestamp(System.currentTimeMillis()); 
		this.avg_rating = avg_rating;// Set current timestamp
		}

    // Getters and Setters

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    // toString method to print the product details
    @Override
    public String toString() {
        return "Products{" +
                "productId='" + productId + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", discountedPrice=" + discountedPrice +
                ", createdDate=" + createdDate +
                ", isPublished=" + isPublished +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
