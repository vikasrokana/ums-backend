package com.payload.response;

import lombok.Data;

@Data
public class AssignSubjectResponse {

    private Long CourseId;
    private Long subjectId;
    private Long facultyId;
    private String position;
    private Long semOrYear;
    private String date;
    private String facultyName;
    private String courseName;
    private String subjectName;
    private String experience;
    private String qualification;
    private String subjectCode;
    private String courseCode;
    private Long theoryMarks;
    private Long practicalMarks;
    private String subjectType;
}
