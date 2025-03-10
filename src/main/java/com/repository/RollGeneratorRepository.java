package com.repository;

import com.model.RollGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RollGeneratorRepository extends JpaRepository<RollGenerator, Long> {

    @Query(value = "select * from roll_generator where is_active =:isActive and id =:id", nativeQuery = true)
    RollGenerator findByIdAndIsActive(Long id, Boolean isActive);
    @Query(value = "select * from roll_generator where is_active =:isActive and course_id =:courseId and" +
            " sem_or_year =:semOrYear", nativeQuery = true)
    RollGenerator findByCourseIdAndSem(Long courseId, String semOrYear, Boolean isActive);
    @Transactional
    @Modifying
    @Query(value = "Update roll_generator set is_active = 0 where id = :id",nativeQuery = true)
    Integer deleteRollNumber(Long id);
}
