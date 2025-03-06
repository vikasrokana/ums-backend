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
    @Query(value = "Select * from class_schedule where course_id =:courseId and subject_id =:id and is_active =:isActive", nativeQuery = true)
    ClassSchedule findByCourseIdAndSubjectId(Long courseId, Long id, Boolean isActive);
    @Query(value = "Select * from class_schedule where faculty_id =:id and is_active =:isActive", nativeQuery = true)
    List<ClassSchedule> findByFacultyId(Long id, Boolean isActive);
    @Query(value = "SELECT * FROM class_schedule WHERE course_id = :courseId AND subject_id IN (:subjectIds) AND is_active = :isActive", nativeQuery = true)
    List<ClassSchedule> findByCourseIdAndSubjectIdIn(Long courseId, List<Long> subjectIds, Boolean isActive);
}
