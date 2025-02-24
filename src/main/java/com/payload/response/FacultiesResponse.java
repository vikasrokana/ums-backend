package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
@Data
public class FacultiesResponse {
    private Long id;
    private String facultyName;
    private String email;
    private String phone;
    private String qualification;
    private String position;
    private String experience;
    private String dob;
    private String gender;
    private String profilePic;
    private String joinDate;
    private String address;
    private Long pinCode;
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
