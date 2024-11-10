package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class CourseResponse {
    private Long id;
    private String courseCode;
    private String courseName;
    private String semOrYear;
    private Long totalSemOrYear;
    private String description;
    private Double tuitionFee;
    private Double examFee;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive;
}
