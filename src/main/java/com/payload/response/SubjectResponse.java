package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class SubjectResponse {
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
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive;

}
