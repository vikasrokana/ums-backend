package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class FacultySubjectResponse {
    private Long id;
    private String facultyName;
    private String email;
    private String phone;
    private String qualification;
    private String experience;
    private String dob;
    private String gender;
    private String profilePic;
    private String joinDate;
    private String address;
    private Long pinCode;
    private String courseName;
    private Long semOrYear;
    private String position;
    private String subjectName;
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
