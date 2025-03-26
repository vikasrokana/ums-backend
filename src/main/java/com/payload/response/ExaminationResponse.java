package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ExaminationResponse {
    private Long id;
    private String examName;
    private Long courseId;
    private Long subjectId;
    private Long facultyId;
    private String date;
    private String time;
    private String duration;
    private String room;
    private String status;
    private Long totalQuestions;
    private Long createdBy;
    private Long updatedBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive;
}
