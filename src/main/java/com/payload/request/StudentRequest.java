package com.payload.request;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class StudentRequest {
    private Long courseId;
    private String semOrYear;
    private String rollNumber;
    private String optionalSubject;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dob;
    private String gender;
    private String address;
    private String state;
    private String city;
    private String pinCode;
    private String fatherName;
    private String fatherOccupation;
    private String motherName;
    private String motherOccupation;
    private String profilePic;
    private Timestamp lastLogin;
    private LocalDate admissionDate;
    private String userId;
    private String password;
    private Boolean activeStatus = true;
}
