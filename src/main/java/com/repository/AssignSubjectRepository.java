package com.repository;

import com.model.AssignSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignSubjectRepository extends JpaRepository<AssignSubject, Long> {
    @Query(value = "select * from assign_subject where id =:id and is_active=:isActive",nativeQuery = true)
    AssignSubject findByIdAndIsActive(Long id, Boolean isActive);
    @Query(value = "select * from assign_subject where faculty_id=:facultyId and is_active =:isActive",nativeQuery = true)
    List<AssignSubject> findByFacultyId(Long facultyId, Boolean isActive);
    @Query(value = "select * from assign_subject where course_id=:courseId and sem_or_year=:semOrYear and is_active =:isActive",nativeQuery = true)
    List<AssignSubject> findByCourseAndSem(Long courseId, Long semOrYear, Boolean isActive);
}
