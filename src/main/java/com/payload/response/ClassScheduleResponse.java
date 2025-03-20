package com.payload.response;

import lombok.Data;

@Data
public class ClassScheduleResponse {
    private Long id;
    private Long courseId;
    private String courseName;
    private Long subjectId;
    private String subjectName;
    private Long facultyId;
    private String facultyName;
    private String day;
    private String startTime;
    private String endTime;
    private String roomNo;
}
