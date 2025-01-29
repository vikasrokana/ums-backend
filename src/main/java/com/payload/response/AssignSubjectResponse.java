package com.payload.response;

import lombok.Data;

@Data
public class AssignSubjectResponse {
    private String courseCode;
    private String semOrYear;
    private Long CourseId;
    private String subjectCode;
    private Long facultyId;
    private String position;
    private String date;
    private String facultyName;
    private String courseName;
    private String experience;
    private String qualification;
}
