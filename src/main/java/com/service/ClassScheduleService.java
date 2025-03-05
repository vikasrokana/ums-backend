package com.service;
import com.exception.RecordNotFoundException;
import com.model.ClassSchedule;
import com.payload.request.ClassScheduleRequest;
import com.payload.response.ClassScheduleResponse;
import com.payload.response.CourseResponse;

import java.util.List;

public interface ClassScheduleService {
    ClassSchedule addClassSchedule(ClassScheduleRequest classScheduleRequest, Long userId);
    List<ClassScheduleResponse> getClassSchedule(Integer pageNumber) throws RecordNotFoundException;

    Boolean deleteClassSchedule(Long classScheduleId);
}
