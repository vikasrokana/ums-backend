package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String audience;
    private String date;
    private String priority;
    private String createdBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive = true;
}
