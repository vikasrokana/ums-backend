package com.repository;
import com.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByIsActive(Boolean isActive);
    @Transactional
    @Modifying
    @Query(value = "Update announcement set is_active =0 where id = :announcementId",nativeQuery = true)
    Integer deleteAnnouncement(Long announcementId);
}
