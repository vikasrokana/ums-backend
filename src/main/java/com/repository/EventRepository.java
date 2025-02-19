package com.repository;
import com.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByIsActive(Boolean isActive, Pageable pageable);
    @Transactional
    @Modifying
    @Query(value = "Update event set is_active =0 where id = :eventId",nativeQuery = true)
    Integer deleteEvent(Long eventId);
}
