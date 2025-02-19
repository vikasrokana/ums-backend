package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.model.Announcement;
import com.payload.request.AnnouncementRequest;
import com.payload.response.AnnouncementResponse;
import com.repository.AnnouncementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    AnnouncementRepository announcementRepository;
    @Override
    public Announcement addAnnouncement(AnnouncementRequest announcementRequest, Long userId) {
        Announcement announcement = new Announcement();
        if(announcementRequest.getId() != null){
            announcement = announcementRepository.findById(announcementRequest.getId()).get();
            announcement.setUpdatedOn(AppUtils.getCurrentIstTime());
        }
        else{
            announcement.setCreatedOn(AppUtils.getCurrentIstTime());
        }
        if (announcementRequest.getTitle() != null) {
            announcement.setTitle(announcementRequest.getTitle());
        }
        if (announcementRequest.getDescription() != null) {
            announcement.setDescription(announcementRequest.getDescription());
        }
        if (announcementRequest.getAudience() != null) {
            announcement.setAudience(announcementRequest.getAudience());
        }
        if (announcementRequest.getDate() != null) {
            announcement.setDate(announcementRequest.getDate());
        }
        if (announcementRequest.getPriority() != null) {
            announcement.setPriority(announcementRequest.getPriority());
        }

        if (announcementRequest.getCreatedBy() != null) {
            announcement.setCreatedBy(announcementRequest.getCreatedBy());
        }
        Announcement announcement1 = announcementRepository.save(announcement);
        logger.info("announcement added successfully");
        return announcement1;
    }
    @Override
    public List<AnnouncementResponse> getAnnouncementList(Integer pageNumber) throws RecordNotFoundException {
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        List<AnnouncementResponse> announcementResponseList = new ArrayList<>();
        List<Announcement> announcementList = announcementRepository.findByIsActive(true, pageable);
        if(announcementList.isEmpty()){
            logger.warn("No announcement records");
            return new ArrayList<>();
        }
        for(Announcement announcement: announcementList){
            AnnouncementResponse announcementResponse = new AnnouncementResponse();
            announcementResponse.setId(announcement.getId());
            announcementResponse.setTitle(announcement.getTitle());
            announcementResponse.setDescription(announcement.getDescription());
            announcementResponse.setAudience(announcement.getAudience());
            announcementResponse.setDate(announcement.getDate());
            announcementResponse.setPriority(announcement.getPriority());
            announcementResponse.setCreatedBy(announcement.getCreatedBy());
            announcementResponse.setCreatedOn(announcement.getCreatedOn());
            announcementResponse.setUpdatedOn(announcement.getUpdatedOn());
            announcementResponse.setIsActive(announcement.getIsActive());
            announcementResponseList.add(announcementResponse);
        }
        logger.info("get announcement list");
        return announcementResponseList;
    }
    @Override
    public Boolean deleteAnnouncement(Long announcementId) {
        Integer isDeleted = announcementRepository.deleteAnnouncement(announcementId);
        if( isDeleted != 0){
            logger.info("announcement deleted Successfully");
            return true;
        }
        return false;
    }
}
