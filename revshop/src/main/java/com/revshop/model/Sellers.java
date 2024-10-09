package com.revshop.model;

import java.util.UUID;
import java.sql.Date;
import java.sql.Timestamp;

public class Sellers {
    private String sellerId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private Date dob;
    private String businessName;
    private String businessAddress;
    private String businessPhone;
    private String gstNumber;
    private String hashedPassword;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public Sellers() {
        this.sellerId = UUID.randomUUID().toString();  // Automatically generate a UUID for sellerId
    }

    // Parameterized constructor
    public Sellers(String firstName, String lastName, String emailId, String phoneNumber, Date dob, 
                   String businessName, String businessAddress,String businessPhone, String gstNumber, String hashedPassword) {
        this.sellerId = UUID.randomUUID().toString();  // Automatically generate a UUID for sellerId
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.businessPhone = businessPhone;
        this.gstNumber = gstNumber;
        this.hashedPassword = hashedPassword;
        this.createdAt = new Timestamp(System.currentTimeMillis());  // Set current timestamp
    }

    // Parameterized constructor with sellerId (in case of updates)
    public Sellers(String sellerId, String firstName, String lastName, String emailId, String phoneNumber, Date dob, 
                   String businessName, String businessAddress,String businessPhone, String gstNumber, String hashedPassword) {
        this.sellerId = sellerId;  // Use the provided sellerId
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.businessPhone = businessPhone;
        this.gstNumber = gstNumber;
        this.hashedPassword = hashedPassword;
        this.createdAt = new Timestamp(System.currentTimeMillis());  // Set current timestamp
    }

    // Getters and Setters

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }
    
    public String getBusinessPhone() {
    	return businessPhone;
    }
    
    public void setBusinessPhone(String businessPhone) {
    	this.businessPhone = businessPhone;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // toString method to print the seller details
    @Override
    public String toString() {
        return "Sellers{" +
                "sellerId='" + sellerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dob=" + dob +
                ", businessName='" + businessName + '\'' +
                ", businessAddress='" + businessAddress + '\'' +
                ", gstNumber='" + gstNumber + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
