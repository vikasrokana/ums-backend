package com.service;

import com.exception.RecordNotFoundException;
import com.model.Announcement;
import com.payload.request.AnnouncementRequest;
import com.payload.response.AnnouncementResponse;

import java.util.List;

public interface AnnouncementService {

    Announcement addAnnouncement(AnnouncementRequest announcementRequest,Long userId);
    List<AnnouncementResponse> getAnnouncementList() throws RecordNotFoundException;
    Boolean deleteAnnouncement(Long announcementId);

}
