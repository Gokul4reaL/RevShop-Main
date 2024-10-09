package com.revshop.validator;

import com.revshop.dto.LoginDTO;
import com.revshop.dto.SellerRegisterDTO;

public class ValidateSeller {
	public static void validateLogin(LoginDTO request) throws IllegalArgumentException {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
    }
	
	public static void validateSellerRegistration(SellerRegisterDTO request) throws IllegalArgumentException {
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
	    if (request.getBusinessName() == null || request.getBusinessName().isEmpty()) {
	        throw new IllegalArgumentException("Business name is required");
	    }
	    if (request.getBusinessAddress() == null || request.getBusinessAddress().isEmpty()) {
	        throw new IllegalArgumentException("Business address is required");
	    }
	    if (request.getBusinessPhone() == null || request.getBusinessPhone().isEmpty()) {
	        throw new IllegalArgumentException("Business phone number is required");
	    }
	    if (request.getGstNumber() == null || request.getGstNumber().isEmpty()) {
	        throw new IllegalArgumentException("GST number is required");
	    }
	}


}
