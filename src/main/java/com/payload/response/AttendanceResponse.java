package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AttendanceResponse {
    private Long id;
    private String courseCode;
    private String subjectCode;
    private Long semOrYear;
    private String date;
    private String time;
    private Long studentId;
    private Boolean present;
    private String section;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Long createdBy;
    private Long updateBy;
}
