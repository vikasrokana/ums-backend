package com.repository;

import com.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByIsActive (Pageable pageable, Boolean isActive);
    @Transactional
    @Modifying
    @Query(value = "Update subject set is_active =0 where id = :subjectId",nativeQuery = true)
    Integer deleteSubject(Long subjectId);

    Subject findByIdAndIsActive(Long subjectId,Boolean isActive);
    @Query(value = "Select * from subject where course_id =:courseId and is_active =:isActive",nativeQuery = true)
    List<Subject> findByCourseId(Long courseId, Boolean isActive);
    @Query(value = "Select * from subject where course_id =:courseId and is_active =:isActive And sem_or_year=:semOrYear",nativeQuery = true)
    List<Subject> findByCourseIdAndSem(Long courseId, Long semOrYear, Boolean isActive, Pageable pageable);
}
