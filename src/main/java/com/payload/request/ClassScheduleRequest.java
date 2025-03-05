package com.payload.request;

import lombok.Data;

@Data
public class ClassScheduleRequest {
    private Long id;
    private Long courseId;
    private String subjectId;
    private String facultyId;
    private String day;
    private String startTime;
    private String endTime;
    private String roomNo;
}
