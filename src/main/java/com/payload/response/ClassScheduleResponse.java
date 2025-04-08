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
    // Rescheduling fields
    private String rescheduleDate;
    private String rescheduleStartTime;
    private String rescheduleEndTime;
    private String rescheduleRoomNo;
    private String rescheduleReason;

    private Boolean isRescheduled = false;
    private Boolean isApproved = null;
}
