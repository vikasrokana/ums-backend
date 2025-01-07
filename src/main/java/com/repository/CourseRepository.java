package com.repository;

import com.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findByIsActive(Boolean isActive);
    @Transactional
    @Modifying
    @Query(value = "Update course set is_active =0 where id = :courseId",nativeQuery = true)
    Integer deleteLabour(Long courseId);

    Course findByIdAndIsActive(Long courseId, Boolean isActive);
    @Query(value = "select id from course where course_code =:courseCode", nativeQuery = true)
    Long findByCourseCode(String courseCode);
}
