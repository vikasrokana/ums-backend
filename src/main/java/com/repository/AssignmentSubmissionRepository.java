package com.repository;

import com.model.AssignmentSubmission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {

    @Query(value = "select * from assignment_submission where id =:id and is_active =:isActive",nativeQuery = true)
    AssignmentSubmission findByIdAndIsActive(Long id,Boolean isActive);
    @Query(value = "select * from assignment_submission where assignment_id =:id and is_active =:isActive",nativeQuery = true)
    AssignmentSubmission findByAssignmentId(Long id, Boolean isActive);
    @Query(value = "select * from assignment_submission where assignment_id =:id and is_active =:isActive",nativeQuery = true)
    List<AssignmentSubmission> findAllByAssignmentIdAndIsActive(Long id, Boolean isActive, Pageable pageable);
}
