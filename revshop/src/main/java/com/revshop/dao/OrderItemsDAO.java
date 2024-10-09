package com.revshop.dao;

import com.revshop.model.OrderItems; // Import your OrderItems model class
import com.revshop.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsDAO {

    // SQL queries
    private static final String INSERT_ORDER_ITEM_SQL = "INSERT INTO OrderItems (order_item_id, order_id, product_id, seller_id, quantity, price_at_time, discounted_price_at_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ORDER_ITEMS_BY_ORDER_ID_SQL = 
            "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.seller_id, oi.quantity, " +
            "oi.price_at_time, oi.discounted_price_at_time, oi.added_date, p.image_url, p.product_name " +
            "FROM orderItems oi " +
            "JOIN products p ON oi.product_id = p.product_id " +
            "WHERE oi.order_id = ?";    private static final String UPDATE_ORDER_ITEM_SQL = "UPDATE OrderItems SET quantity = ?, price_at_time = ?, discounted_price_at_time = ? WHERE order_item_id = ?";
    private static final String DELETE_ORDER_ITEM_SQL = "DELETE FROM OrderItems WHERE order_item_id = ?";
    private static final String CLEAR_ORDER_ITEMS_SQL = "DELETE FROM OrderItems WHERE order_id = ?";

    // Add a new order item
    public void addOrderItem(OrderItems orderItem) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM_SQL)) {

            preparedStatement.setString(1, orderItem.getOrderItemId()); // Set the order_item_id
            preparedStatement.setString(2, orderItem.getOrderId());
            preparedStatement.setString(3, orderItem.getProductId());
            preparedStatement.setString(4, orderItem.getSellerId()); // Set the seller_id
            preparedStatement.setInt(5, orderItem.getQuantity());
            preparedStatement.setDouble(6, orderItem.getPriceAtTime());
            preparedStatement.setDouble(7, orderItem.getDiscountedPriceAtTime());
            
            String updateProductQuantitySQL = "UPDATE Products SET quantity = quantity - ? WHERE product_id = ?";

            try (PreparedStatement updateQuantityStatement = connection.prepareStatement(updateProductQuantitySQL)) {
                updateQuantityStatement.setInt(1, orderItem.getQuantity());
                updateQuantityStatement.setString(2, orderItem.getProductId());
                updateQuantityStatement.executeUpdate();
            }

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Order item added successfully!");
            } else {
                System.out.println("Failed to add order item.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting order item", e);
        }
    }

    // Retrieve order items by order ID
    public List<OrderItems> getOrderItemsByOrderId(String orderId) throws SQLException {
        List<OrderItems> orderItems = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_ITEMS_BY_ORDER_ID_SQL)) {

            preparedStatement.setString(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                OrderItems orderItem = new OrderItems();
                orderItem.setOrderItemId(resultSet.getString("order_item_id"));
                orderItem.setOrderId(resultSet.getString("order_id"));
                orderItem.setProductId(resultSet.getString("product_id"));
                orderItem.setSellerId(resultSet.getString("seller_id")); // Retrieve the seller_id
                orderItem.setQuantity(resultSet.getInt("quantity"));
                orderItem.setPriceAtTime(resultSet.getDouble("price_at_time"));
                orderItem.setDiscountedPriceAtTime(resultSet.getDouble("discounted_price_at_time"));
                orderItem.setAddedDate(resultSet.getTimestamp("added_date"));
                orderItem.setProductName(resultSet.getString("product_name"));

                // Retrieve and set the image URL
                String imageUrl = resultSet.getString("image_url");
                orderItem.setImageUrl(imageUrl);

                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving order items by order ID", e);
        }
        return orderItems;
    }

    // Update an order item
    public void updateOrderItem(OrderItems orderItem) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_ITEM_SQL)) {

            preparedStatement.setInt(1, orderItem.getQuantity());
            preparedStatement.setDouble(2, orderItem.getPriceAtTime());
            preparedStatement.setDouble(3, orderItem.getDiscountedPriceAtTime());
            preparedStatement.setString(4, orderItem.getOrderItemId());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Order item updated successfully!");
            } else {
                System.out.println("Failed to update order item.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating order item", e);
        }
    }

    // Remove an order item
    public void removeOrderItem(String orderItemId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_ITEM_SQL)) {

            preparedStatement.setString(1, orderItemId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Order item removed successfully!");
            } else {
                System.out.println("Failed to remove order item.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error removing order item", e);
        }
    }

    // Clear order items for a specific order
    public void clearOrderItems(String orderId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_ORDER_ITEMS_SQL)) {

            preparedStatement.setString(1, orderId);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Order items cleared successfully!");
            } else {
                System.out.println("Failed to clear order items.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error clearing order items", e);
        }
    }
}
