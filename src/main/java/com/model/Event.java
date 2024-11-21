package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@Table(name="event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventName;
    private String eventType;
    private String organizer;
    private LocalDate date;
    private String time;
    private String location;
    private String description;
    private String audience;
    private boolean registrationRequired;
    private LocalDate registrationDeadline;
    private String registrationLink;
    private String authorName;
    private String authorEmail;
    private String remark;
    private Long createdBy;
    private Long updatedBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive =true;
}
