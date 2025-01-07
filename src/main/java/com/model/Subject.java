package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Long createdBy;
    private Long updatedBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive =true;
}
