package com.revshop.dao;

import com.revshop.model.Orders; // Import your Order model class
import com.revshop.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrdersDAO {

    // SQL queries
	private static final String INSERT_ORDER_SQL = "INSERT INTO orders (order_id, user_id, total_amount, payment_method, order_status, order_address) VALUES (?, ?, ?, ?, 'pending', ?)";
    private static final String SELECT_ORDER_BY_ID_SQL = "SELECT * FROM orders WHERE order_id = ?";
    private static final String SELECT_ORDERS_BY_USER_ID_SQL = "SELECT * FROM orders WHERE user_id = ?";
    private static final String UPDATE_ORDER_STATUS_SQL = "UPDATE orders SET status = ? WHERE order_id = ?";
    private static final String DELETE_ORDER_SQL = "DELETE FROM orders WHERE order_id = ?";

    // Create a new order and return the generated order_id
    public String createOrder(Orders order) throws SQLException {
        String orderId = UUID.randomUUID().toString(); // Generate UUID for order_id

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_SQL)) {

            preparedStatement.setString(1, orderId);  // Use generated UUID
            preparedStatement.setString(2, order.getUserId());
            preparedStatement.setDouble(3, order.getTotalAmount());
            preparedStatement.setString(4, order.getPaymentMethod());
            preparedStatement.setString(5, order.getOrderAddress());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Order created successfully with ID: " + orderId);
            } else {
                System.out.println("Failed to create order.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting order", e);
        }

        return orderId; // Return the generated order_id
    }

    // Retrieve an order by ID
    public Orders getOrderById(String orderId) throws SQLException {
        Orders order = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID_SQL)) {

            preparedStatement.setString(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                order = new Orders();
                order.setOrderId(resultSet.getString("order_id"));
                order.setUserId(resultSet.getString("user_id"));
                order.setTotalAmount(resultSet.getDouble("total_amount"));
                order.setStatus(resultSet.getString("order_status"));
                order.setOrderAddress(resultSet.getString("order_address")); // Retrieve order address
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving order by ID", e);
        }
        return order;
    }

    // Retrieve all orders for a specific user
    public List<Orders> getOrdersByUserId(String userId) throws SQLException {
        List<Orders> orders = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_USER_ID_SQL)) {

            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Orders order = new Orders();
                order.setOrderId(resultSet.getString("order_id"));
                order.setUserId(resultSet.getString("user_id"));
                order.setTotalAmount(resultSet.getDouble("total_amount"));
                order.setStatus(resultSet.getString("order_status"));
                order.setOrderAddress(resultSet.getString("order_address")); // Retrieve order address
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving orders for user", e);
        }
        return orders;
    }

    // Update the status of an order
    public void updateOrderStatus(String orderId, String newStatus) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_SQL)) {

            preparedStatement.setString(1, newStatus);
            preparedStatement.setString(2, orderId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Order status updated successfully!");
            } else {
                System.out.println("Failed to update order status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating order status", e);
        }
    }

    // Delete an order
    public void deleteOrder(String orderId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_SQL)) {

            preparedStatement.setString(1, orderId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Order deleted successfully!");
            } else {
                System.out.println("Failed to delete order.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting order", e);
        }
    }
}
