package com.payload.response;

import lombok.Data;

@Data
public class ClassScheduleResponse {
    private Long id;
    private Long courseId;
    private String subjectId;
    private String facultyId;
    private String day;
    private String startTime;
    private String endTime;
    private String roomNo;
}
