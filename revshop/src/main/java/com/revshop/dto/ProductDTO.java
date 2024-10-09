package com.revshop.dto;

public class ProductDTO {
    private String sellerId;           // ID of the seller who added the product
    private String productName;        // Name of the product
    private double price;              // Price of the product
    private String description;        // Description of the product
    private int quantity;              // Available quantity of the product
    private double discountedPrice;    // Discounted price of the product
    private boolean isPublished;        // Indicates if the product is published
    private String imageUrl;           // URL or path to the product image

    public ProductDTO(String sellerId, String productName, double price, 
                      String description, int quantity, double discountedPrice, 
                      boolean isPublished, String imageUrl) {
        this.sellerId = sellerId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.discountedPrice = discountedPrice;
        this.isPublished = isPublished;
        this.imageUrl = imageUrl;
    }

    // Getters

    public String getSellerId() {
        return sellerId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
