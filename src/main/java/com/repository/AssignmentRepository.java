package com.repository;

import com.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {

   @Query(value = "select * from assignment where id=:id and is_active =:isActive", nativeQuery = true)
    Assignment findByIdAndIsActive(Long id,Boolean isActive);
    @Query(value = "select * from assignment where created_by=:userId and is_active =:isActive", nativeQuery = true)
    List<Assignment> findByUserId(Long userId, Boolean isActive);

    @Query("SELECT a FROM Assignment a WHERE a.subjectId IN :subjectIds AND a.isActive = :isActive")
    List<Assignment> findBySubjectIdInAndIsActive(@Param("subjectIds") List<Long> subjectIds,
                                                  @Param("isActive") Boolean isActive);

}
