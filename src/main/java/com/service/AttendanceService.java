package com.service;

import com.exception.RecordNotFoundException;
import com.model.Attendance;
import com.payload.request.AttendanceRequest;
import com.payload.response.AttendanceResponse;

import java.util.List;

public interface AttendanceService {
  List<Attendance> addStudentAttendance(AttendanceRequest attendanceRequest, Long userId);

    List<AttendanceResponse> getStudentAttendance(Long courseId, Long subjectId, String section, String date, Long userId,String role, Integer pageNumber) throws RecordNotFoundException;

  List<AttendanceResponse> getStudentOwnAttendance(Integer pageNumber, String  date, Long userId) throws RecordNotFoundException;
}
