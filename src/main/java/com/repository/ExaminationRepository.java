package com.repository;

import com.model.Examination;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {
    @Query(value = "select * from examination where is_active=:isActive", nativeQuery = true)
    List<Examination> findByIsActive(Boolean isActive, Pageable pageable);
    @Transactional
    @Modifying
    @Query(value = "Update examination set is_active =0 where id = :examinationId",nativeQuery = true)
    Integer deleteExaminations(Long examinationId);

    @Query(value = "select * from examination where id =:examId and is_active=:isActive", nativeQuery = true)
    Examination findByIdAndIsActive(Long examId, Boolean isActive);
}
