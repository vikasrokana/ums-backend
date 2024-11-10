package com.payload.request;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class StudentRequest {
    private Long courseId;
    private String semOrYear;
    private String optionalSubject;
    private String studentName;
    private String email;
    private String phone;
    private String dob;
    private String gender;
    private String address;
    private String pinCode;
    private String fatherName;
    private String fatherOccupation;
    private String motherName;
    private String motherOccupation;
    private String profilePic;
    private String admissionDate;
}
