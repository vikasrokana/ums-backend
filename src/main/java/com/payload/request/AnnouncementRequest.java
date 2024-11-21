package com.payload.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class AnnouncementRequest{

    private Long id;
    private String title;
    private String description;
    private String audience;
    private String createdBy;
    private LocalDate date;
    private String priority;
}
