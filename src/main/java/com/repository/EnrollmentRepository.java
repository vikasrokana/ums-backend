package com.repository;

import com.model.Enrollment;
import com.model.FeeConfiguration;
import com.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    FeeConfiguration findByCourseId(Long courseId);
}
