package com.repository;

import com.model.MarkSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkSheetRepository extends JpaRepository<MarkSheet,Long> {
    @Query(value = "select * from mark_sheet where id=:id and is_active=:isActive", nativeQuery = true)
    MarkSheet findByMarkIdAndIsActive(Long id, Boolean isActive);

}
