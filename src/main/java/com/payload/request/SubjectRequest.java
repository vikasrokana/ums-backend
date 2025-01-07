package com.payload.request;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class SubjectRequest {
    private Long id;
    private Long courseId;
    private String subjectCode;
    private String subjectName;
    private Long semOrYear;
    private String subjectType;
    private Long theoryMarks;
    private Long practicalMarks;
    private String description;
    private String status;
    private String syllabus;
}
