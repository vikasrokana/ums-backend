package com.payload.request;

import lombok.Data;

@Data
public class MarkSheetRequest {
    private Long id;
    private Long examRecordId;
    private String rollNumber;
    private String  subjectCode;
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
}
