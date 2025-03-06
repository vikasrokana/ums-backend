package com.repository;

import com.model.StudentFees;
import com.payload.response.StudentFeeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<StudentFees,Long> {
@Query(value = "Select * from student_fees where is_active =:isActive",nativeQuery = true)
    List<StudentFees> findByIsActive(Boolean isActive);
    @Query(value = "Select * from student_fees where student_id =:id and is_active =:isActive",nativeQuery = true)
    List<StudentFees> findByStudentId(Long id, Boolean isActive);
}
