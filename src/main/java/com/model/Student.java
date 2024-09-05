package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private String rollNo;
    private String fileName;
    private String subject;
    private String fileUrl;
    private String phone;
    private String email;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive =true;
}
