package com.payload.request;

import lombok.Data;

@Data
public class AssignSubjectRequest {
    private Long id;
    private String courseCode;
    private String semOrYear;
    private Long subjectCode;
    private Long facultyId;
    private String position;
}
