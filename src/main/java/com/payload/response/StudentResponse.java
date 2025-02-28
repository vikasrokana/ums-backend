package com.payload.response;

import lombok.Data;

@Data
public class StudentResponse {
    private Long id;
    private Long courseId;
    private String courseCode;
    private Long semOrYear;
    private String optionalSubject;
    private String studentName;
    private String section;
    private String email;
    private String phone;
    private String enrollmentNumber;
    private String rollNumber;
    private Long userId;
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
