package com.service;

import com.model.Attendance;
import com.model.Course;
import com.payload.request.AttendanceRequest;

public interface AttendanceService {
    Attendance addStudentAttendance(AttendanceRequest attendanceRequest, Long userId);
}
