package com.service;

import com.model.Enrollment;
import com.model.Student;

public interface EnrollmentService {
    Enrollment enrollStudentInCourse(Long courseId, Student student);
}
