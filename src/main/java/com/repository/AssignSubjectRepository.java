package com.repository;

import com.model.AssignSubject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignSubjectRepository extends JpaRepository<AssignSubject, Long> {
    @Query(value = "select * from assign_subject where id =:id and is_active=:isActive",nativeQuery = true)
    AssignSubject findByIdAndIsActive(Long id, Boolean isActive);
    @Query(value = "select * from assign_subject where faculty_id=:facultyId and is_active =:isActive",nativeQuery = true)
    List<AssignSubject> findByFacultyId(Long facultyId, Boolean isActive, Pageable pageable);
    @Query(value = "select * from assign_subject where course_id=:courseId and is_active =:isActive",nativeQuery = true)
    List<AssignSubject> findByCourseAndSem(Long courseId, Boolean isActive);
    @Query(value = "select * from assign_subject where course_id=:courseId and subject_id =:subjectId and is_active =:isActive",nativeQuery = true)
    Optional<AssignSubject> findBySubjectIdAndCourseId(Long courseId, Long subjectId, Boolean isActive);
    @Query(value = "select * from assign_subject where subject_id=:subjectId and is_active =:isActive",nativeQuery = true)
    List<AssignSubject> findBySubjectId(Long subjectId, Boolean isActive);
}
