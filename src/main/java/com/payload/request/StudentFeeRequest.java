package com.payload.request;

import lombok.Data;

@Data
public class StudentFeeRequest {
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
}
