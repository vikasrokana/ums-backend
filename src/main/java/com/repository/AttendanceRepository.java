package com.repository;

import com.model.Attendance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    @Query(value = "select * from attendance where date=:date and created_by=:userId and is_active=:isActive", nativeQuery = true)
    List<Attendance> findByDateAndIsActive(String date, Long userId, Boolean isActive);
    @Query(value = "select * from attendance where date =:date and student_id=:id and is_active =:isActive", nativeQuery = true)
    List<Attendance> findByDateAndStudentId(String date, Long id, Boolean isActive, Pageable pageable);
    @Query(value = "select * from attendance where student_id=:id and is_active =:isActive", nativeQuery = true)
    List<Attendance> findByStudentIdAndIsActive(Long id, Boolean isActive, Pageable pageable);
}
