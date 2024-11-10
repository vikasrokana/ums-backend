package com.service;

import com.model.Student;
import com.payload.request.StudentRequest;

import java.util.List;

public interface StudentService {
    Student registerStudent(StudentRequest studentRequest);
    Student findById(Long StudentId);

    List<Student> getStudentList(Long courseId, String semOrYear, String rollNumber);

    Student StudentDetails(StudentRequest studentRequest);
}
