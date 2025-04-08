package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "assignment_submission")
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Boolean isActive = true;
}
