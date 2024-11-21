package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class AnnouncementResponse {


    private Long id;
    private String title;
    private String description;
    private String audience;
    private LocalDate date;
    private String priority;
    private String createdBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;

    private Boolean isActive;
}
