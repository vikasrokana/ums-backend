package com.payload.request;

import lombok.Data;

@Data
public class ClassScheduleRequest {
    private Long id;
    private Long courseId;
    private Long subjectId;
    private Long facultyId;
    private String day;
    private String startTime;
    private String endTime;
    private String roomNo;
}
