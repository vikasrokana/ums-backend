package com.model;

import lombok.Data;

import javax.persistence.*;
import java.security.Timestamp;

@Data
@Entity
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseCode;
    private String courseName;
    private String semOrYear;
    private Long totalSemOrYear;
    private String description;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive =true;
}
