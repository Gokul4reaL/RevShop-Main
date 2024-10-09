package com.revshop.controller;

import com.revshop.dto.*;
import org.json.JSONObject;
import com.revshop.model.Users;
import com.revshop.service.UserService;
import com.revshop.serviceimpl.UserImpl;
import com.revshop.validator.ValidateUser;
import com.revshop.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loginUser")
public class LoginServlet extends HttpServlet {

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
        String email = request.getParameter("emailId");
        String password = request.getParameter("password");

        try {
            LoginDTO loginRequest = new LoginDTO(email, password);
            // Validate the incoming request parameters using Validator
            ValidateUser.validateLogin(loginRequest); // Pass the request object for validation
            
            // Authenticate the user via UserService
            Users authenticatedUser = userService.loginService(loginRequest); // Assuming it returns a Users object

            // After successful login, generate a JWT token
            String token = JWTUtil.generateToken(authenticatedUser.getUserId());

            // Create JSON response with token and user details
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("token", token);
            jsonResponse.put("userId", authenticatedUser.getUserId());
            jsonResponse.put("firstName", authenticatedUser.getFirstName());
            jsonResponse.put("lastName", authenticatedUser.getLastName());
            jsonResponse.put("emailId", authenticatedUser.getEmailId());
            jsonResponse.put("phoneNumber", authenticatedUser.getPhoneNumber());
            jsonResponse.put("dob", authenticatedUser.getDob().toString());
            
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
