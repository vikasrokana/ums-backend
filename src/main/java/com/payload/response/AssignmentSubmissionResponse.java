package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class AssignmentSubmissionResponse {
    private Long id;
    private Long assignmentId; // FK to Assignment table
    private Long studentId;
    private String submissionUrl; // File URL/path
    private String remarks; // Optional remarks by faculty
    private Double obtainedMarks; // Marks awarded
    private String  submittedOn;
    private Boolean isLateSubmission = false;
    private Long evaluatedBy; // Faculty ID if evaluated
    private String evaluatedOn;
    private Long updatedBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
