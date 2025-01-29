package com.payload.request;

import lombok.Data;

@Data
public class AssignSubjectRequest {
    private Long id;
    private Long courseId;
    private String courseCode;
    private Long semOrYear;
    private String subjectCode;
    private Long facultyId;
    private String position;
}
