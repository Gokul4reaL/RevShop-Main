package com.revshop.serviceimpl;

import com.revshop.model.OrderItems; // Import your OrderItems model class
import com.revshop.repository.OrderItemsRepository; // Import your OrderItems repository
import com.revshop.service.OrderItemService;

import java.sql.SQLException;
import java.util.List;

public class OrderItemsImpl implements OrderItemService {

    @Override
    public void addOrderItem(OrderItems orderItem) throws SQLException {
        // Call the repository to add an order item
        OrderItemsRepository.addOrderItemRepo(orderItem);
    }

    @Override
    public List<OrderItems> getOrderItemsByOrderId(String orderId) throws SQLException {
        // Call the repository to retrieve order items by order ID
        return OrderItemsRepository.getOrderItemsByOrderIdRepo(orderId);
    }

    @Override
    public void updateOrderItem(OrderItems orderItem) throws SQLException {
        // Call the repository to update an order item
        OrderItemsRepository.updateOrderItemRepo(orderItem);
    }

    @Override
    public void removeOrderItem(String orderItemId) throws SQLException {
        // Call the repository to remove an order item
        OrderItemsRepository.removeOrderItemRepo(orderItemId);
    }

    @Override
    public void clearOrderItems(String orderId) throws SQLException {
        // Call the repository to clear order items for a specific order
        OrderItemsRepository.clearOrderItemsRepo(orderId);
    }
}
