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
    private Long subjectCode;
    private String semOrYear;
    private String subjectType;
    private Long theoryMarks;
    private Long practicalMarks;
    private String description;
    private String status;
    private String syllabus;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive =true;
}
