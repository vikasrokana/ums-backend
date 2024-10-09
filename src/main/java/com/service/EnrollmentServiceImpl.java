package com.service;

import com.model.*;
import com.repository.CourseRepository;
import com.repository.EnrollmentRepository;
import com.repository.FeeConfigurationRepository;
import com.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpl implements EnrollmentService{

    @Autowired
    EnrollmentRepository enrollmentRepository;
    @Autowired
    FeeRepository feeRepository;

    @Autowired
    FeeConfigurationRepository feeConfigurationRepository;

    @Autowired
    CourseRepository courseRepository;

    public Enrollment enrollStudentInCourse(Long courseId, Student student) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        StudentFees fee = new StudentFees();
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);

        double enrollmentFee = getEnrollmentFee(courseId);
        fee.setEnrollmentFee(enrollmentFee);
        fee.setStudent(student);
        fee.setIsEnrollmentFeePaid(true);

        feeRepository.save(fee);

        return enrollmentRepository.save(enrollment);
    }

    // Method to get the enrollment fee for a specific course
    public Double getEnrollmentFee(Long courseId) {
        FeeConfiguration feeConfig = feeConfigurationRepository.findByCourseId(courseId);
        if (feeConfig != null) {
            return feeConfig.getEnrollmentFee();
        }
        throw new RuntimeException("Enrollment fee not found for course id: " + courseId);
    }


}
