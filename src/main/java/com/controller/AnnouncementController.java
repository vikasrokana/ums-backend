package com.controller;

import com.model.Course;
import com.model.Announcement;
import com.payload.request.AnnouncementRequest;
import com.payload.response.CourseResponse;
import com.payload.response.AnnouncementResponse;
import com.payload.response.MessageResponse;
import com.service.AnnouncementService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class AnnouncementController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    AnnouncementService announcementService;

    @ApiOperation(value = "This api will be used to create the announcements")
    @RequestMapping(value = {"/admin/add-announcement"}, method = RequestMethod.POST)
    public ResponseEntity<?>  AddAnnouncement(@RequestBody AnnouncementRequest announcementRequest) throws Exception{
        try{
            Long userId = 1L;
            Announcement announcement = announcementService.addAnnouncement(announcementRequest, userId);
            return ResponseEntity.ok(announcement);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This api will be used to update the announcement")
    @RequestMapping(value = {"/admin/update-announcement"}, method = RequestMethod.POST)
    public ResponseEntity<?>  UpdateAnnouncement(@RequestBody AnnouncementRequest announcementRequest) throws Exception{
        try{
            Long userId = 1L;
            Announcement announcement = announcementService.addAnnouncement(announcementRequest, userId);
            return ResponseEntity.ok(announcement);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This api will be used to get the announcement list")
    @RequestMapping(value = {"/admin/get-announcement-list"}, method = RequestMethod.GET)
    public ResponseEntity<?>  getAnnouncementList() throws Exception{
        try{
            List<AnnouncementResponse>  announcementResponseList = announcementService.getAnnouncementList();
            return ResponseEntity.ok(announcementResponseList);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This api will be used to delete the announcement")
    @RequestMapping(value = {"/admin/delete-announcement"}, method = RequestMethod.DELETE)
    public ResponseEntity<?>  deleteAnnouncement(@RequestParam(value = "announcementId",required = true) Long announcementId) throws Exception{
        try{
            Boolean isDeleted = announcementService.deleteAnnouncement(announcementId);
            if(isDeleted){
                return ResponseEntity.ok(new MessageResponse(true,"Announcement deleted successfully"));
            } else {
                return ResponseEntity.ok(new MessageResponse(false,"Announcement not found"));
            }
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }
}
