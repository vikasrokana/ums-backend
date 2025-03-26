package com.payload.request;

import lombok.Data;

@Data
public class ExaminationRequest {
    private Long id;
    private String examName;
    private Long courseId;
    private Long subjectId;
    private Long facultyId;
    private String date;
    private String time;
    private String duration;
    private String room;
    private String status;
    private Long totalQuestions;
}
