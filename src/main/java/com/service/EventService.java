package com.service;

import com.exception.RecordNotFoundException;
import com.model.Event;
import com.payload.request.EventRequest;
import com.payload.response.EventResponse;

import java.util.List;

public interface EventService {

    Event addEvent(EventRequest eventRequest,Long userId);
    List<EventResponse> getEventList(Integer pageNumber) throws RecordNotFoundException;
    Boolean deleteEvent(Long eventId);

}
