package com.payload.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AttendanceRequest {
    private Long id;
    private String courseCode;
    private Long subjectCode;
    private String date;
    private String time;
    private Long studentId;
    private Long semOrYear;
    private String present;
    private String section;
    private Long createdBy;
    private Long updateBy;
}
