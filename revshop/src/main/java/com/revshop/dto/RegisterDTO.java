package com.revshop.dto;

import java.sql.Date;

public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private Date dob;
    private String password;

    public RegisterDTO(String firstName, String lastName, String emailId, String phoneNumber, Date dob, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.password = password;
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
}