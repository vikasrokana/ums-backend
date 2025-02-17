package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.model.Event;
import com.payload.request.EventRequest;
import com.payload.response.EventResponse;
import com.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    EventRepository eventRepository;
    @Override
    public Event addEvent(EventRequest eventRequest, Long userId) {
        Event event = new Event();
        if(eventRequest.getId() != null){
            event = eventRepository.findById(eventRequest.getId()).get();
            event.setUpdatedOn(AppUtils.getCurrentIstTime());
            event.setUpdatedBy(userId);

        }
        else{
            event.setCreatedOn(AppUtils.getCurrentIstTime());
            event.setCreatedBy(userId);
        }
        if (eventRequest.getEventName() != null) {
            event.setEventName(eventRequest.getEventName());
        }
        if (eventRequest.getEventType() != null) {
            event.setEventType(eventRequest.getEventType());
        }
        if (eventRequest.getOrganizer() != null) {
            event.setOrganizer(eventRequest.getOrganizer());
        }
        if (eventRequest.getDate() != null) {
            event.setDate(eventRequest.getDate());
        }
        if (eventRequest.getTime() != null) {
            event.setTime(eventRequest.getTime());
        }
        if (eventRequest.getLocation() != null) {
            event.setLocation(eventRequest.getLocation());
        }
        if (eventRequest.getDescription() != null) {
            event.setDescription(eventRequest.getDescription());
        }
        if (eventRequest.getAudience() != null) {
            event.setAudience(eventRequest.getAudience());
        }
        if (eventRequest.getRegistrationRequired() !=null) {
            event.setRegistrationRequired(eventRequest.getRegistrationRequired());
        }
        if (eventRequest.getRegistrationDeadline() != null) {
            event.setRegistrationDeadline(eventRequest.getRegistrationDeadline());
        }
        if (eventRequest.getRegistrationLink() != null) {
            event.setRegistrationLink(eventRequest.getRegistrationLink());
        }
        if (eventRequest.getAuthorName() != null) {
            event.setAuthorName(eventRequest.getAuthorName());
        }
        if (eventRequest.getAuthorEmail() != null) {
            event.setAuthorEmail(eventRequest.getAuthorEmail());
        }
        if (eventRequest.getRemark() != null) {
            event.setRemark(eventRequest.getRemark());
        }
        Event event1 = eventRepository.save(event);
        logger.info("course added successfully");
        return event1;
    }
    @Override
    public List<EventResponse> getEventList() throws RecordNotFoundException {
        List<EventResponse> eventResponseList = new ArrayList<>();
        List<Event> eventList = eventRepository.findByIsActive(true);
        if(eventList.isEmpty()){
            throw new RecordNotFoundException("event list is not found");
        }
        for(Event event: eventList){
            EventResponse eventResponse = new EventResponse();
            eventResponse.setId(event.getId());
            eventResponse.setEventName(event.getEventName());
            eventResponse.setEventType(event.getEventType());
            eventResponse.setOrganizer(event.getOrganizer());
            eventResponse.setDate(event.getDate());
            eventResponse.setTime(event.getTime());
            eventResponse.setLocation(event.getLocation());
            eventResponse.setDescription(event.getDescription());
            eventResponse.setAudience(event.getAudience());
            eventResponse.setRegistrationRequired(event.getRegistrationRequired());
            eventResponse.setRegistrationDeadline(event.getRegistrationDeadline());
            eventResponse.setRegistrationLink(event.getRegistrationLink());
            eventResponse.setAuthorName(event.getAuthorName());
            eventResponse.setAuthorEmail(event.getAuthorEmail());
            eventResponse.setRemark(event.getRemark());
            eventResponse.setCreatedOn(event.getCreatedOn());
            eventResponse.setUpdatedOn(event.getUpdatedOn());
            eventResponse.setIsActive(event.getIsActive());
            eventResponseList.add(eventResponse);
        }
        logger.info("get event list");
        return eventResponseList;
    }
    @Override
    public Boolean deleteEvent(Long eventId) {
        Integer isDeleted = eventRepository.deleteEvent(eventId);
        if( isDeleted != 0){
            logger.info("event deleted Successfully");
            return true;
        }
        return false;
    }
}
