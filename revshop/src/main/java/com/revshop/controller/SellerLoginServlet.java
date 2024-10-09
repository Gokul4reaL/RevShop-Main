package com.revshop.controller;

import com.revshop.dto.LoginDTO;
import com.revshop.model.Sellers;
import com.revshop.service.SellerService;
import com.revshop.serviceimpl.SellerImpl;
import com.revshop.validator.ValidateSeller;
import com.revshop.util.JWTUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import java.io.IOException;

@WebServlet("/loginSeller")
public class SellerLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private SellerService sellerService;

    @Override
    public void init() {
        sellerService = new SellerImpl(); // Initialize the service implementation
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("emailId");
        String password = request.getParameter("password");

        try {
            LoginDTO loginRequest = new LoginDTO(email, password);
            // Validate the incoming request parameters using Validator
            ValidateSeller.validateLogin(loginRequest); // Assume a similar validation class for sellers
            
            // Authenticate the seller via SellerService
            Sellers authenticatedSeller = sellerService.loginService(loginRequest); // Assuming it returns a Sellers object

            // After successful login, generate a JWT token
            String token = JWTUtil.generateToken(authenticatedSeller.getSellerId());
            
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("token", token);
            jsonResponse.put("sellerId", authenticatedSeller.getSellerId());
            jsonResponse.put("firstName", authenticatedSeller.getFirstName());
            jsonResponse.put("lastName", authenticatedSeller.getLastName());
            jsonResponse.put("emailId", authenticatedSeller.getEmailId());
            jsonResponse.put("phoneNumber", authenticatedSeller.getPhoneNumber());
            jsonResponse.put("dob", authenticatedSeller.getDob().toString());
            jsonResponse.put("businessName", authenticatedSeller.getBusinessName());
            jsonResponse.put("businessAddress", authenticatedSeller.getBusinessAddress());
            jsonResponse.put("businessPhone", authenticatedSeller.getBusinessPhone());
            jsonResponse.put("gstNumber", authenticatedSeller.getGstNumber());

            response.setHeader("Authorization", "Bearer " + token);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse.toString());
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Login failed: " + e.getMessage());
        }
    }
}
