package com.model;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long courseId;
    private String courseCode;
    private Long semOrYear;
    private String enrollmentNumber;
    private String rollNumber;
    private String section;
    private String optionalSubject;
    private String studentName;
    private String email;
    private String phone;
    private String  dob;
    private String gender;
    private String address;
    private String pinCode;
    private String fatherName;
    private String fatherOccupation;
    private String motherName;
    private String motherOccupation;
    private String profilePic;
    private String admissionDate;
    private Long userId;
    private Boolean isActive = true;
    private Long createdBy;
    private Long updatedBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
