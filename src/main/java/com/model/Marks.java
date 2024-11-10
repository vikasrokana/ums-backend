package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@Entity
@Table(name="marks")
public class Marks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseCode;
    private String semOrYear;
    private Long subjectCode;
    private Long subjectId;
    private Long studentId;
    private Long theoryMarks;
    private Long practicalMarks;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive =true;

}
