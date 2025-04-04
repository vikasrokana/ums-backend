package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long courseId;
    private Long subjectId;
    private Long facultyId;
    private String startDate;
    private String deadline;
    private Long marks;
    private String section;
    private String assignmentUrl;
    private Long createdBy;
    private Long updatedBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive =true;
}
