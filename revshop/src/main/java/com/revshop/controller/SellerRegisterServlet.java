package com.revshop.controller;

import com.revshop.dto.SellerRegisterDTO;
import com.revshop.model.Sellers;
import com.revshop.service.SellerService;
import com.revshop.serviceimpl.SellerImpl;
import com.revshop.util.DatabaseConnection;
import com.revshop.validator.ValidateSeller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.Arrays;

@WebServlet("/registerSeller")
public class SellerRegisterServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private SellerService sellerService;

    @Override
    public void init() {
        sellerService = new SellerImpl(); // Initialize the Seller service implementation
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

        // Fetch seller registration form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String emailId = request.getParameter("emailId");
        String phoneNumber = request.getParameter("phoneNumber");
        Date dob = Date.valueOf(request.getParameter("dob")); // Convert string to SQL Date
        String password = request.getParameter("password");
        String businessName = request.getParameter("businessName");
        String businessAddress = request.getParameter("businessAddress");
        String businessPhone = request.getParameter("businessPhone");
        String gstNumber = request.getParameter("gstNumber");

        try {
            // Create SellerRegisterDTO with the collected parameters
            SellerRegisterDTO sellerRequest = new SellerRegisterDTO(firstName, lastName, emailId, phoneNumber, dob, password, businessName, businessAddress, businessPhone, gstNumber);
            
            // Validate the incoming request parameters using Validator
            ValidateSeller.validateSellerRegistration(sellerRequest); // Validate the seller registration request
            
            // Register the seller via SellerService
            Sellers newSeller = sellerService.registerService(sellerRequest); // Assuming it returns a Sellers object

            response.getWriter().write("Seller registration successful.");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Seller registration failed: " + e.getMessage());
        }
    }
}