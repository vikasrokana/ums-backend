package com.service;

import com.model.Course;
import com.payload.request.CourseRequest;

public interface CourseService {
    Course addCourse(CourseRequest courseRequest);
}
