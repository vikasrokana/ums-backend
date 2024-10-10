package com.payload.request;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class SubjectRequest {
    private Long id;
    private Long courseId;
    private Long subjectCode;
    private String semOrYear;
    private String subjectType;
    private Long theoryMarks;
    private Long practicalMarks;
    private String description;
    private String status;
    private String syllabus;
}
