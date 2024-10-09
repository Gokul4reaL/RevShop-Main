package com.revshop.dto;

import java.sql.Date;

public class SellerRegisterDTO {
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private Date dob;
    private String password;
    private String businessName;
    private String businessAddress;
    private String businessPhone;
    private String gstNumber;

    public SellerRegisterDTO(String firstName, String lastName, String emailId, String phoneNumber, 
                             Date dob, String password, String businessName, 
                             String businessAddress,String businessPhone, String gstNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.password = password;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.businessPhone = businessPhone;
        this.gstNumber = gstNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }
    
    public String getBusinessPhone() {
    	return businessPhone;
    }

    public String getGstNumber() {
        return gstNumber;
    }
}