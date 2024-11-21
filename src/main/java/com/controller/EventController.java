package com.controller;

import com.model.Course;
import com.model.Event;
import com.payload.request.EventRequest;
import com.payload.response.CourseResponse;
import com.payload.response.EventResponse;
import com.payload.response.MessageResponse;
import com.service.EventService;
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
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    EventService eventService;

    @ApiOperation(value = "This api will be used to create the events")
    @RequestMapping(value = {"/admin/add-event"}, method = RequestMethod.POST)
    public ResponseEntity<?>  AddEvent(@RequestBody EventRequest eventRequest) throws Exception{
        try{
            Long userId = 1L;
             Event event = eventService.addEvent(eventRequest, userId);
            return ResponseEntity.ok(event);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This api will be used to update the event")
    @RequestMapping(value = {"/admin/update-event"}, method = RequestMethod.POST)
    public ResponseEntity<?>  UpdateEvent(@RequestBody EventRequest eventRequest) throws Exception{
        try{
            Long userId = 1L;
            Event event = eventService.addEvent(eventRequest, userId);
            return ResponseEntity.ok(event);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This api will be used to get the event list")
    @RequestMapping(value = {"/admin/get-event-list"}, method = RequestMethod.GET)
    public ResponseEntity<?>  getEventList() throws Exception{
        try{
            List<EventResponse>  eventResponseList = eventService.getEventList();
            return ResponseEntity.ok(eventResponseList);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This api will be used to delete the event")
    @RequestMapping(value = {"/admin/delete-event"}, method = RequestMethod.DELETE)
    public ResponseEntity<?>  deleteEvent(@RequestParam(value = "eventId",required = true) Long eventId) throws Exception{
        try{
            Boolean isDeleted = eventService.deleteEvent(eventId);
            if(isDeleted){
                return ResponseEntity.ok(new MessageResponse(true,"Event deleted successfully"));
            } else {
                return ResponseEntity.ok(new MessageResponse(false,"Event not found"));
            }
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }
}
