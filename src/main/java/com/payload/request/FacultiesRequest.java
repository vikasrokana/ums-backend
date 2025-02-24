package com.payload.request;

import lombok.Data;
@Data
public class FacultiesRequest {
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
}
