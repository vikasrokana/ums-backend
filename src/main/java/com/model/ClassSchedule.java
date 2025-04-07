package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="class_schedule")
public class ClassSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Boolean isApproved = null; // null = pending, true = approved, false = rejected

    private Long createdBy;
    private Long updatedBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive =true;
}
