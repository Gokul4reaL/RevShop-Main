package com.revshop.serviceimpl;

import com.revshop.model.Orders; // Import your Order model class
import com.revshop.repository.OrderRepository; // Import your Order repository
import com.revshop.service.OrderService;

import java.sql.SQLException;
import java.util.List;

public class OrdersImpl implements OrderService {

    @Override
    public String createOrder(Orders order) throws SQLException {
        // Call the repository to create a new order
        return OrderRepository.createOrderRepo(order);
    }

    @Override
    public Orders getOrderById(String orderId) throws SQLException {
        // Call the repository to retrieve an order by its ID
        return OrderRepository.getOrderByIdRepo(orderId);
    }

    @Override
    public List<Orders> getOrdersByUserId(String userId) throws SQLException {
        // Call the repository to retrieve all orders for a specific user
        return OrderRepository.getOrdersByUserIdRepo(userId);
    }

    @Override
    public void updateOrderStatus(String orderId, String newStatus) throws SQLException {
        // Call the repository to update the status of an order
        OrderRepository.updateOrderStatusRepo(orderId, newStatus);
    }

    @Override
    public void deleteOrder(String orderId) throws SQLException {
        // Call the repository to delete an order
        OrderRepository.deleteOrderRepo(orderId);
    }
}
