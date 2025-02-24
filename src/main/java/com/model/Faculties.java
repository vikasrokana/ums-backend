package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="faculties")
public class Faculties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String facultyName;
    private String email;
    private String phone;
    private String qualification;
    private String experience;
    private String position;
    private String dob;
    private String gender;
    private String profilePic;
    private String joinDate;
    private Long userId;
    private String address;
    private Long pinCode;
    private Boolean isVerified = false;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Long createdBy;
    private Long updatedBy;
    private Boolean isActive = true;


}
