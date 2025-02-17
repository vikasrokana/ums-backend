package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class EventResponse {
    private Long id;
    private String eventName;
    private String eventType;
    private String organizer;
    private String  date;
    private String time;
    private String location;
    private String description;
    private String audience;
    private Boolean registrationRequired;
    private String  registrationDeadline;
    private String registrationLink;
    private String authorName;
    private String authorEmail;
    private String remark;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive;
}
