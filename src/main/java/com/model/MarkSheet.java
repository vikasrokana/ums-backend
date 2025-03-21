package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@Entity
@Table(name = "mark_sheet")
public class MarkSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long examRecordId;
    private String rollNumber;
    private Long  subjectId;
    private Double q1;
    private Double q2;
    private Double q3;
    private Double q4;
    private Double q5;
    private Double q6;
    private Double q7;
    private Double q8;
    private Double q9;
    private Double q10;
    private Double totalMarks;
    private Boolean isActive = true;
    private Long createdBy;
    private Long updatedBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;

}
