package com.repository;

import com.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {

   @Query(value = "select * from assignment where id=:id and is_active =:isActive", nativeQuery = true)
    Assignment findByIdAndIsActive(Long id,Boolean isActive);
}
