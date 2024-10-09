package com.payload.request;

import lombok.Data;

@Data
public class CourseRequest {
    private Long id;
    private String courseCode;
    private String courseName;
    private String semOrYear;
    private Long totalSemOrYear;
    private String description;
}
