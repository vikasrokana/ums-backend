package com.service;

import com.exception.RecordNotFoundException;
import com.model.Course;
import com.payload.request.CourseRequest;
import com.payload.response.CourseResponse;

import java.util.List;

public interface CourseService {
    Course addCourse(CourseRequest courseRequest,Long userId);

    List<CourseResponse> getCourseList() throws RecordNotFoundException;

    Boolean deleteCourse(Long courseId);

    Course getCourseById(Long courseId) throws RecordNotFoundException;
}
