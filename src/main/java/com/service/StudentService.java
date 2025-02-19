package com.service;

import com.exception.RecordNotFoundException;
import com.model.Student;
import com.model.StudentFees;
import com.payload.request.StudentRequest;
import com.payload.response.StudentFeeResponse;
import com.payload.response.StudentResponse;

import java.util.List;

public interface StudentService {
    Student registerStudent(StudentRequest studentRequest);
    Student findById(Long StudentId);

    List<Student> getStudentList(Long courseId, Long semOrYear, String rollNumber, Integer pageNumber);

    Student StudentDetails(StudentRequest studentRequest,Long userId);


    Student findStudentDetails(Long userId) throws RecordNotFoundException;

    List<StudentFeeResponse> getStudentFeeList() throws RecordNotFoundException;

    List<StudentResponse> getStudentByFacultyId(Long userId, Integer pageNumber);

    List<StudentResponse> getClassmateStudentList(Long userId, Integer pageNumber);
}
