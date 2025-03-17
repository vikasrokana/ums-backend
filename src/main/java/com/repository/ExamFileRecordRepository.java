package com.repository;

import com.model.ExamFileRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamFileRecordRepository extends JpaRepository<ExamFileRecord,Long> {
    @Query(value = "SELECT * FROM exam_file_record " +
            "WHERE is_active = :isActive " +
            "AND (:courseId IS NULL OR course_id = :courseId) " +
            "AND (:subjectId IS NULL OR subject_id = :subjectId)",
            nativeQuery = true)
    List<ExamFileRecord> getExamFileList(Long courseId, Long subjectId, Boolean isActive);
    @Query(value = "select * from exam_file_record where id =:sheetId and is_active =:isActive", nativeQuery = true)
    ExamFileRecord findByIdAndIsActive(Long sheetId, Boolean isActive);
    @Query(value = "select * from exam_file_record where is_active =:isActive order by id desc limit 1", nativeQuery = true)
    ExamFileRecord findLastUniqueNumber(Boolean isActive);
}
