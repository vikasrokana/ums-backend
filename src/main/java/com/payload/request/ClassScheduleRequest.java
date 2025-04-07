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

    // Rescheduling fields
    private String rescheduleDate;
    private String rescheduleStartTime;
    private String rescheduleEndTime;
    private String rescheduleRoomNo;
    private String rescheduleReason;

    private Boolean isRescheduled = false;
    private Boolean isApproved = null;
}
