package com.repository;

import com.model.AssignSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignSubjectRepository extends JpaRepository<AssignSubject, Long> {
    @Query(value = "select * from assign_subject where id =:id and is_active=:isActive",nativeQuery = true)
    AssignSubject findByIdAndIsActive(Long id, Boolean isActive);
}
