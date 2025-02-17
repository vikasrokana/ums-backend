package com.service;

import com.exception.RecordNotFoundException;
import com.model.Attendance;
import com.model.Course;
import com.payload.request.AttendanceRequest;
import com.payload.response.AttendanceResponse;

import java.util.List;

public interface AttendanceService {
  List<Attendance> addStudentAttendance(AttendanceRequest attendanceRequest, Long userId);

    List<AttendanceResponse> getStudentAttendance(String date, Long userId,String role) throws RecordNotFoundException;

  List<AttendanceResponse> getStudentOwnAttendance(Integer pageNumber, String  date, Long userId) throws RecordNotFoundException;
}
