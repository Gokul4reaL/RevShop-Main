package com.revshop.service;

import com.revshop.model.OrderItems; // Assuming you have an OrderItems model class
import java.sql.SQLException;
import java.util.List;

public interface OrderItemService {

    // Method for adding an order item
    void addOrderItem(OrderItems orderItem) throws SQLException;

    // Method for retrieving order items by order ID
    List<OrderItems> getOrderItemsByOrderId(String orderId) throws SQLException;

    // Method for updating an order item
    void updateOrderItem(OrderItems orderItem) throws SQLException;

    // Method for removing an order item
    void removeOrderItem(String orderItemId) throws SQLException;

    // Method for clearing order items for a specific order
    void clearOrderItems(String orderId) throws SQLException;
}
