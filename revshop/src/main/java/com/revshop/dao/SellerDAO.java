package com.revshop.dao;

import com.revshop.model.Sellers;
import com.revshop.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerDAO {
    private static final String INSERT_SELLER_SQL = "INSERT INTO Sellers (sellerId, firstName, lastName, emailId, phoneNumber, dob, businessName, businessAddress, businessPhone, gstNumber, hashedPassword) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SELLER_BY_EMAIL_SQL = "SELECT * FROM Sellers WHERE emailId = ?"; // Query to retrieve seller by email

    public void addSeller(Sellers seller) throws SQLException {
        // Get a connection to the database
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SELLER_SQL)) {
             
            // Set the values for the prepared statement
            preparedStatement.setString(1, seller.getSellerId());  // UUID
            preparedStatement.setString(2, seller.getFirstName());
            preparedStatement.setString(3, seller.getLastName());
            preparedStatement.setString(4, seller.getEmailId());
            preparedStatement.setString(5, seller.getPhoneNumber());
            preparedStatement.setDate(6, seller.getDob());
            preparedStatement.setString(7, seller.getBusinessName());
            preparedStatement.setString(8, seller.getBusinessAddress());
            preparedStatement.setString(9, seller.getBusinessPhone());
            preparedStatement.setString(10, seller.getGstNumber());
            preparedStatement.setString(11, seller.getHashedPassword());
            
            // Execute the query
            int result = preparedStatement.executeUpdate();
            
            if (result > 0) {
                System.out.println("Seller added successfully!");
            } else {
                System.out.println("Failed to add seller.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting seller", e);
        }
    }

    public Sellers getSellerByEmail(String email) throws SQLException {
        Sellers seller = null;

        // Get a connection to the database
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SELLER_BY_EMAIL_SQL)) {
             
            // Set the email parameter for the prepared statement
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Process the result set
            if (resultSet.next()) {
                seller = new Sellers(
                    resultSet.getString("sellerId"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("emailId"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getDate("dob"),
                    resultSet.getString("businessName"),
                    resultSet.getString("businessAddress"),
                    resultSet.getString("businessPhone"),
                    resultSet.getString("gstNumber"),
                    resultSet.getString("hashedPassword")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving seller by email", e);
        }

        return seller; // Return the seller object, or null if not found
    }
}