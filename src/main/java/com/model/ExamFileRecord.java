package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="exam_file_record")
public class ExamFileRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uniqueNumber;
    private String fileUrl;
    private Long courseId;
    private Long subjectId;
    private String date;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive=true;
}
