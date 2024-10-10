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

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private Double enrollmentFee;
    private Boolean isEnrollmentFeePaid = false;

    private Double tuitionFee;
    private Boolean isTuitionFeePaid = false;

    private Double examFee;
    private Boolean isExamFeePaid = false;

    private Double hostelFee;
    private Boolean isHostelFeePaid = false;

    private Timestamp createdOn;
    private Timestamp updatedOn;
}
