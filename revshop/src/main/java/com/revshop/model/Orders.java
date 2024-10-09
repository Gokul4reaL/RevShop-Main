package com.revshop.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class Orders {
    private String orderId;          // UUID as String
    private String userId;           // Reference to the user who placed the order
    private String status;           // Status of the order (e.g., pending, completed)
    private double totalAmount;       // Total amount for the order
    private String orderAddress; // Address for the order
    public String paymentMethod;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<OrderItems> orderItems; // List to hold order items

    // Default constructor
    public Orders() {
        this.orderId = UUID.randomUUID().toString(); // Generate a random UUID
    }

    // Parameterized constructor
    public Orders(String userId, double totalAmount, String orderAddress, String paymentMethod) {
        this.orderId = UUID.randomUUID().toString(); // Generate a random UUID
        this.userId = userId;
        this.orderAddress = orderAddress;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.createdAt = new Timestamp(System.currentTimeMillis());  // Set current timestamp
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

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    // toString method to print order details
    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderAddress='" + orderAddress + '\'' +
                '}';
    }
}
