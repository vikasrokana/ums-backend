package com.payload.request;

import lombok.Data;

@Data
public class AssignSubjectRequest {
    private Long id;
    private Long courseId;
    private Long subjectId;
    private Long facultyId;
}
