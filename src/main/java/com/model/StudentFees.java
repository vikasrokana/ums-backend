package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "student_fees")
public class StudentFees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private Double enrollmentFee;
    private Double tuitionFee;
    private Double examFee;
    private Double paidFee;
    private Double dueFee;
    private String paidBy;
    private String date;
    private String dueDate;
    private Long createdBy;
    private Long updateBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive=true;
}
