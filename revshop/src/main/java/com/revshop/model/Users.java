package com.revshop.model;

import java.util.UUID;
import java.sql.Date;
import java.sql.Timestamp;

public class Users {
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private Date dob;
    private String hashedPassword;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public Users() {
        this.userId = UUID.randomUUID().toString();  // Automatically generate a UUID
    }

    // Parameterized constructor
    public Users(String firstName, String lastName, String emailId, String phoneNumber, Date dob, String hashedPassword) {
        this.userId = UUID.randomUUID().toString();  // Automatically generate a UUID
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.hashedPassword = hashedPassword;
        this.createdAt = new Timestamp(System.currentTimeMillis());  // Set current timestamp
    }
    
    public Users(String userId, String firstName, String lastName, String emailId, String phoneNumber, Date dob, String hashedPassword) {
        this.userId = userId;  // Use the provided userId
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.hashedPassword = hashedPassword;
        this.createdAt = new Timestamp(System.currentTimeMillis());  // Set current timestamp
    }


    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    // toString method to print the user details
    @Override
    public String toString() {
        return "Users{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dob=" + dob +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

