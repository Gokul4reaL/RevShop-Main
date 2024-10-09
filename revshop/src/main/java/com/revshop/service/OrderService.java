package com.revshop.service;

import com.revshop.model.Orders; // Assuming you have an Order model class
import java.sql.SQLException;
import java.util.List;

public interface OrderService {

    // Method for creating a new order
    String createOrder(Orders order) throws SQLException;

    // Method for retrieving an order by its ID
    Orders getOrderById(String orderId) throws SQLException;

    // Method for retrieving all orders for a specific user
    List<Orders> getOrdersByUserId(String userId) throws SQLException;

    // Method for updating the status of an order
    void updateOrderStatus(String orderId, String newStatus) throws SQLException;

    // Method for deleting an order
    void deleteOrder(String orderId) throws SQLException;
}
