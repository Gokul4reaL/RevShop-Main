package com.revshop.dao;

import com.revshop.model.Users;
import com.revshop.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final String INSERT_USER_SQL = "INSERT INTO users (user_id, first_name, last_name, email_id, phone_number, dob, hashed_password) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_EMAIL_SQL = "SELECT * FROM users WHERE email_id = ?"; // Query to retrieve user by email

    public void addUser(Users user) throws SQLException {
        // Get a connection to the database
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
             
            // Set the values for the prepared statement
            preparedStatement.setString(1, user.getUserId());  // UUID
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmailId());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setDate(6, user.getDob());
            preparedStatement.setString(7, user.getHashedPassword());
            
            // Execute the query
            int result = preparedStatement.executeUpdate();
            
            if (result > 0) {
                System.out.println("User added successfully!");
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting user", e);
        }
    }

    public Users getUserByEmail(String email) throws SQLException {
        Users user = null;

        // Get a connection to the database
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_SQL)) {
             
            // Set the email parameter for the prepared statement
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Process the result set
            if (resultSet.next()) {
                user = new Users(
                    resultSet.getString("user_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email_id"),
                    resultSet.getString("phone_number"),
                    resultSet.getDate("dob"),
                    resultSet.getString("hashed_password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving user by email", e);
        }

        return user; // Return the user object, or null if not found
    }
}
