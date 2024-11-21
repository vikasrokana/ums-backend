package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class StudentFeeResponse {
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
}
