package com.repository;

import com.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    @Query(value = "select * from attendance where date=:date and created_by=:userId and is_active=:isActive", nativeQuery = true)
    List<Attendance> findByDateAndIsActive(String date, Long userId, Boolean isActive);
}
