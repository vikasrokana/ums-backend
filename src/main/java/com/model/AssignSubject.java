package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="assign_subject")
public class AssignSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseCode;
    private String semOrYear;
    private Long subjectCode;
    private Long facultyId;
    private String position;
    private String date;
    private Long createdBy;
    private Long updatedBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive =true;
}
