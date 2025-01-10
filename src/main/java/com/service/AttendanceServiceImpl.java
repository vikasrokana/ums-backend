package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.model.Attendance;
import com.payload.request.AttendanceRequest;
import com.payload.response.AttendanceResponse;
import com.repository.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<AttendanceResponse> getStudentAttendance(String date, Long userId) throws RecordNotFoundException {
        List<AttendanceResponse> attendanceResponseList = new ArrayList<>();
        List<Attendance> attendanceList = attendanceRepository.findByDateAndIsActive(date,userId,true);
        if(attendanceList.isEmpty()){
            throw new RecordNotFoundException("attendance list is not found");
        }
        for(Attendance attendance:attendanceList){
            AttendanceResponse attendanceResponse = new AttendanceResponse();
            attendanceResponse.setId(attendance.getId());
            attendanceResponse.setCourseCode(attendance.getCourseCode());
            attendanceResponse.setSubjectCode(attendance.getSubjectCode());
            attendanceResponse.setSemOrYear(attendance.getSemOrYear());
            attendanceResponse.setDate(attendance.getDate());
            attendanceResponse.setTime(attendance.getTime());
            attendanceResponse.setStudentId(attendance.getStudentId());
            attendanceResponse.setPresent(attendance.getPresent());
            attendanceResponse.setSection(attendance.getSection());
            attendanceResponse.setCreatedOn(attendance.getCreatedOn());
            attendanceResponse.setUpdatedOn(attendance.getUpdatedOn());
            attendanceResponse.setCreatedBy(attendance.getCreatedBy());
            attendanceResponse.setUpdateBy(attendance.getUpdateBy());
            attendanceResponseList.add(attendanceResponse);
        }
        logger.info("get the student attendance list");
        return attendanceResponseList;
    }

}
