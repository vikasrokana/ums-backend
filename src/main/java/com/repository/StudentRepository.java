package com.repository;

import com.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query(value = "select * from student where is_active=:isActive", nativeQuery = true)
    List<Student> findStudentFileList(Boolean isActive);
    @Query(value = "SELECT * FROM student WHERE course_id = :courseId AND sem_or_year = :semOrYear AND roll_number = :rollNumber", nativeQuery = true)
    List<Student> findByCourseIdAndSemOrYearAndRollNumber(Long courseId, String semOrYear, String rollNumber);
    @Query(value = "SELECT * FROM student WHERE course_id = :courseId AND sem_or_year = :semOrYear", nativeQuery = true)
    List<Student> findByCourseIdAndSemOrYear(Long courseId, String semOrYear);
    @Query(value = "select * from student where email =:email and is_active =:isActive", nativeQuery = true)
    Student findByEmail(String email,Boolean isActive);
}
