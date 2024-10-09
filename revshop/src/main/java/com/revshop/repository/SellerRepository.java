package com.revshop.repository;

import java.sql.Date;
import java.sql.SQLException;
import com.revshop.dao.SellerDAO;
import com.revshop.model.Sellers;
import com.revshop.dto.*;
import org.mindrot.jbcrypt.BCrypt;

	public class SellerRepository {
	    // Registration logic
	    public static Sellers registerRepo(SellerRegisterDTO request) throws SQLException {
	        String firstName = request.getFirstName();
	        String lastName = request.getLastName();
	        String emailId = request.getEmailId();
	        String phoneNumber = request.getPhoneNumber();
	        Date dob = request.getDob();
	        String businessName = request.getBusinessName();
	        String businessAddress = request.getBusinessAddress();
	        String businessPhone = request.getBusinessPhone();
	        String gstNumber = request.getGstNumber();
	        String password = request.getPassword();

	        // Hash the user's password using BCrypt
	        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

	        // Create a new Users object
	        Sellers newSeller = new Sellers(firstName, lastName, emailId, phoneNumber, dob, businessName, businessAddress, businessPhone, gstNumber, hashedPassword);

	        // Call UsersDAO to add the user to the database
	        SellerDAO sellersDAO = new SellerDAO();  // Assuming usersDAO is properly instantiated
	        sellersDAO.addSeller(newSeller);

	        return newSeller; // Return the newly created user object
	    }

	    // Login logic
	    public static Sellers loginRepo(LoginDTO request) throws SQLException {
	        String email = request.getEmail();
	        String password = request.getPassword();

	        // Get the user by email
	        SellerDAO sellersDAO = new SellerDAO();  // Assuming usersDAO is properly instantiated
	        Sellers seller = sellersDAO.getSellerByEmail(email);

	        // Verify the user's password
	        if (seller != null) {
	            // Check if the provided password matches the stored hashed password
	            if (BCrypt.checkpw(password, seller.getHashedPassword())) {
	                return seller; // Return the authenticated user
	            } else {
	                throw new IllegalArgumentException("Invalid email or password");
	            }
	        } else {
	            throw new IllegalArgumentException("Invalid email or password");
	        }
	    }
	}
