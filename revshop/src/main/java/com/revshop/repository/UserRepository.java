package com.revshop.repository;

import java.sql.Date;
import java.sql.SQLException;
import com.revshop.dao.UserDAO;
import com.revshop.model.Users;
import com.revshop.dto.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserRepository {

    // Registration logic
    public static Users registerRepo(RegisterDTO request) throws SQLException {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String emailId = request.getEmailId();
        String phoneNumber = request.getPhoneNumber();
        Date dob = request.getDob();
        String password = request.getPassword();

        // Hash the user's password using BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Create a new Users object
        Users newUser = new Users(firstName, lastName, emailId, phoneNumber, dob, hashedPassword);

        // Call UsersDAO to add the user to the database
        UserDAO usersDAO = new UserDAO();  // Assuming usersDAO is properly instantiated
        usersDAO.addUser(newUser);

        return newUser; // Return the newly created user object
    }

    // Login logic
    public static Users loginRepo(LoginDTO request) throws SQLException {
        String email = request.getEmail();
        String password = request.getPassword();

        // Get the user by email
        UserDAO usersDAO = new UserDAO();  // Assuming usersDAO is properly instantiated
        Users user = usersDAO.getUserByEmail(email);

        // Verify the user's password
        if (user != null) {
            // Check if the provided password matches the stored hashed password
            if (BCrypt.checkpw(password, user.getHashedPassword())) {
                return user; // Return the authenticated user
            } else {
                throw new IllegalArgumentException("Invalid email or password");
            }
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}