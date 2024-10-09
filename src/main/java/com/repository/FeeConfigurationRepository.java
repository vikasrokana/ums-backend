package com.repository;

import com.model.FeeConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeConfigurationRepository extends JpaRepository<FeeConfiguration,Long> {
    FeeConfiguration findByCourseId(Long courseId);
}
