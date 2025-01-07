package com.service;

import com.Utility.AppUtils;
import com.model.Attendance;
import com.model.Course;
import com.payload.request.AttendanceRequest;
import com.repository.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService{

    @Autowired
    AttendanceRepository attendanceRepository;
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Override
    public Attendance addStudentAttendance(AttendanceRequest attendanceRequest, Long userId) {
        Attendance attendance = new Attendance();

        // If the request has an ID, update the existing record
        if (attendanceRequest.getId() != null) {
            attendance = attendanceRepository.findById(attendanceRequest.getId()).orElseThrow(() ->
                    new IllegalArgumentException("Attendance record not found for ID: " + attendanceRequest.getId()));
            attendance.setUpdatedOn(AppUtils.getCurrentIstTime());
            attendance.setUpdateBy(userId);
        } else {
            // If no ID, create a new attendance record
            attendance.setCreatedOn(AppUtils.getCurrentIstTime());
            attendance.setCreatedBy(userId);
        }

        // Map fields from AttendanceRequest to Attendance entity
        if (attendanceRequest.getCourseCode() != null) {
            attendance.setCourseCode(attendanceRequest.getCourseCode());
        }
        if (attendanceRequest.getSubjectCode() != null) {
            attendance.setSubjectCode(attendanceRequest.getSubjectCode());
        }
        if (attendanceRequest.getDate() != null) {
            attendance.setDate(attendanceRequest.getDate());
        }
        if (attendanceRequest.getTime() != null) {
            attendance.setTime(attendanceRequest.getTime());
        }
        if (attendanceRequest.getStudentId() != null) {
            attendance.setStudentId(attendanceRequest.getStudentId());
        }
        if (attendanceRequest.getSemOrYear() != null) {
            attendance.setSemOrYear(attendanceRequest.getSemOrYear());
        }
        if (attendanceRequest.getPresent() != null) {
            attendance.setPresent(attendanceRequest.getPresent());
        }
        if (attendanceRequest.getSection() != null) {
            attendance.setSection(attendanceRequest.getSection());
        }

        // Save attendance record to the repository
        Attendance savedAttendance = attendanceRepository.save(attendance);
        logger.info("Attendance record added/updated successfully for studentId: " + attendanceRequest.getStudentId());
        return savedAttendance;
    }

}
