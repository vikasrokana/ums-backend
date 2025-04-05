package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class AssignmentResponse {
    private Long id;
    private String title;
    private Long courseId;
    private Long subjectId;
    private Long facultyId;
    private String description;
    private String startDate;
    private String deadline;
    private Long marks;
    private String section;
    private String assignmentUrl;
    private Long createdBy;
    private Long updatedBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive;
}
