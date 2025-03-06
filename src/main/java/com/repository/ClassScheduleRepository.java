package com.repository;

import com.model.ClassSchedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule,Long> {
    List<ClassSchedule> findByIsActive(Boolean isActive, Pageable pageable);
    @Transactional
    @Modifying
    @Query(value = "Update class_schedule set is_active =0 where id = :classScheduleId",nativeQuery = true)
    Integer deleteClassSchedule(Long classScheduleId);
//
//    Course findByIdAndIsActive(Long courseId, Boolean isActive);
//    @Query(value = "select id from course where course_code =:courseCode", nativeQuery = true)
//    Long findByCourseCode(String courseCode);
}
