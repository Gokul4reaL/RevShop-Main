package com.revshop.controller;

import com.revshop.dto.*;
import com.revshop.model.Users;
import com.revshop.service.*;
import com.revshop.serviceimpl.*;
import com.revshop.util.DatabaseConnection;
import com.revshop.util.JWTUtil;
import com.revshop.validator.ValidateUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.Arrays;

@WebServlet("/registerUser")
public class RegisterServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService userService;

    @Override
    public void init() {
        userService = new UserImpl(); // Initialize the service implementation
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Failed to connect.");
        }
        
    	System.out.println("Received parameters:");
        request.getParameterMap().forEach((key, value) -> {
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        });
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String emailId = request.getParameter("emailId");
        String phoneNumber = request.getParameter("phoneNumber");
        Date dob = Date.valueOf(request.getParameter("dob")); // Convert string to SQL Date
        String password = request.getParameter("password");

        try {
            RegisterDTO registerRequest = new RegisterDTO(firstName, lastName, emailId, phoneNumber, dob, password);
            // Validate the incoming request parameters using Validator
            ValidateUser.validateRegistration(registerRequest); // Pass the request object for validation
            
            // Register the user via UserService
            Users newUser = userService.registerService(registerRequest); // Assuming it returns a Users object

            response.getWriter().write("Registration successful.");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Registration failed: " + e.getMessage());
        }
    }
}

