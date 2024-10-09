package com.revshop.repository;

import com.revshop.dao.OrderItemsDAO;
import com.revshop.model.OrderItems;

import java.sql.SQLException;
import java.util.List;

public class OrderItemsRepository {

    // Add a new order item
    public static void addOrderItemRepo(OrderItems orderItem) throws SQLException {
        // Create a new OrderItemsDAO instance and call its method to add a new order item
        OrderItemsDAO orderItemsDAO = new OrderItemsDAO();
        orderItemsDAO.addOrderItem(orderItem);
    }

    // Retrieve order items by order ID
    public static List<OrderItems> getOrderItemsByOrderIdRepo(String orderId) throws SQLException {
        // Create a new OrderItemsDAO instance and call its method to get order items by order ID
        OrderItemsDAO orderItemsDAO = new OrderItemsDAO();
        return orderItemsDAO.getOrderItemsByOrderId(orderId);
    }

    // Update an order item
    public static void updateOrderItemRepo(OrderItems orderItem) throws SQLException {
        // Create a new OrderItemsDAO instance and call its method to update an order item
        OrderItemsDAO orderItemsDAO = new OrderItemsDAO();
        orderItemsDAO.updateOrderItem(orderItem);
    }

    // Remove an order item
    public static void removeOrderItemRepo(String orderItemId) throws SQLException {
        // Create a new OrderItemsDAO instance and call its method to remove an order item
        OrderItemsDAO orderItemsDAO = new OrderItemsDAO();
        orderItemsDAO.removeOrderItem(orderItemId);
    }

    // Clear order items for a specific order
    public static void clearOrderItemsRepo(String orderId) throws SQLException {
        // Create a new OrderItemsDAO instance and call its method to clear order items
        OrderItemsDAO orderItemsDAO = new OrderItemsDAO();
        orderItemsDAO.clearOrderItems(orderId);
    }
}
