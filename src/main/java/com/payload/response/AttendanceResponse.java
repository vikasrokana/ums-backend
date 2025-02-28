package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AttendanceResponse {
    private Long id;
    private Long courseId;
    private Long subjectId;
    private Long semOrYear;
    private String date;
    private String time;
    private Long studentId;
    private String studentName;
    private String rollNumber;
    private String enrollmentNumber;
    private Boolean present;
    private String section;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Long createdBy;
    private Long updateBy;
}
