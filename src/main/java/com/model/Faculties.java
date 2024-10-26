package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name="faculties")
public class Faculties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String facultyName;
    private String state;
    private String city;
    private String email;
    private String phone;
    private String qualification;
    private String experience;
    private String dob;
    private String gender;
    private String profilePic;
    private Date lastLogin;
    private String password;
    private Boolean activeStatus;
    private String joinDate;
    private String address;
    private Long pinCode;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive =true;


}
