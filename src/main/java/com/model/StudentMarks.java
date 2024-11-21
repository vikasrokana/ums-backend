package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@Entity
@Table(name="student_marks")
public class StudentMarks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseCode;
    private String semOrYear;
    private Long subjectCode;
    private Long subjectId;
    private String rollNumber;
    private Double obtainedTheoryMarks;
    private Double obtainedPracticalMarks;
    private String grades;
    private String cgpa;
    private String aggregatedCgpa;
    private String reappear;
    private String pass;
    private String examDate;
    private String remarks;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Long createdBy;
    private Long updateBy;
    private Boolean isActive =true;

}
