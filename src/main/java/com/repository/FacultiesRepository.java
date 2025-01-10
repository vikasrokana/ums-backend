package com.repository;

import com.model.Faculties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FacultiesRepository extends JpaRepository<Faculties,Long> {
    @Query(value = "select * from faculties where id =:id and is_active =:isActive", nativeQuery = true)
    Faculties findByIdAndIsActive(Long id, Boolean isActive);

    List<Faculties> findByIsActive(Boolean isActive);
    @Transactional
    @Modifying
    @Query(value = "Update faculties set is_active =0 where id = :facultyId",nativeQuery = true)
    Integer deleteFaculty(Long facultyId);
    @Query(value = "select * from faculties where user_id =:userId and is_active =:isActive",nativeQuery = true)
    Faculties findByUserId(Long userId,Boolean isActive);

}
