package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseCode;
    private Long subjectCode;
    private String date;
    private String time;
    private Long studentId;
    private String present;
    private String section;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Long createdBy;
    private Long updateBy;
    private Boolean isActive =true;
}
