package com.repository;

import com.model.ExamFileRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamFileRecordRepository extends JpaRepository<ExamFileRecord,Long> {
    @Query(value = "select * from exam_file_record where is_active=:isActive", nativeQuery = true)
    List<ExamFileRecord> getExamFileList(Boolean isActive);
    @Query(value = "select * from exam_file_record where id =:sheetId and is_active =:isActive", nativeQuery = true)
    ExamFileRecord findByIdAndIsActive(Long sheetId, Boolean isActive);
}
