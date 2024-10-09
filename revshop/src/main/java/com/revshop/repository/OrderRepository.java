package com.revshop.repository;

import com.revshop.dao.OrdersDAO;
import com.revshop.model.Orders;

import java.sql.SQLException;
import java.util.List;

public class OrderRepository {

    // Add a new order
    public static String createOrderRepo(Orders order) throws SQLException {
        // Create a new OrdersDAO instance and call its method to add a new order
        OrdersDAO ordersDAO = new OrdersDAO();
        return ordersDAO.createOrder(order);
    }

    // Retrieve an order by ID
    public static Orders getOrderByIdRepo(String orderId) throws SQLException {
        // Create a new OrdersDAO instance and call its method to retrieve an order by ID
        OrdersDAO ordersDAO = new OrdersDAO();
        return ordersDAO.getOrderById(orderId);
    }

    // Retrieve all orders for a specific user
    public static List<Orders> getOrdersByUserIdRepo(String userId) throws SQLException {
        // Create a new OrdersDAO instance and call its method to retrieve all orders for the user
        OrdersDAO ordersDAO = new OrdersDAO();
        return ordersDAO.getOrdersByUserId(userId);
    }

    // Update the status of an order
    public static void updateOrderStatusRepo(String orderId, String newStatus) throws SQLException {
        // Create a new OrdersDAO instance and call its method to update the order status
        OrdersDAO ordersDAO = new OrdersDAO();
        ordersDAO.updateOrderStatus(orderId, newStatus);
    }

    // Remove an order
    public static void deleteOrderRepo(String orderId) throws SQLException {
        // Create a new OrdersDAO instance and call its method to delete the order
        OrdersDAO ordersDAO = new OrdersDAO();
        ordersDAO.deleteOrder(orderId);
    }
}
