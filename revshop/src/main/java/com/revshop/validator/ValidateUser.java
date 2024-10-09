package com.revshop.validator;

import com.revshop.dto.*;

public class ValidateUser {
    public static void validateLogin(LoginDTO request) throws IllegalArgumentException {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
    }
    
    public static void validateRegistration(RegisterDTO request) throws IllegalArgumentException {
        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (request.getEmailId() == null || request.getEmailId().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required");
        }
        if (request.getDob() == null) {
            throw new IllegalArgumentException("Date of birth is required");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
    }
    
   }

