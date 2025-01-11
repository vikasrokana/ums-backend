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
    public List<Attendance> addStudentAttendance(AttendanceRequest attendanceRequest, Long userId) {
        List<AttendanceRequest.Attendance> attendanceList = attendanceRequest.getAttendanceList();
        List<Attendance> attendanceList1 = new ArrayList<>();
        for(AttendanceRequest.Attendance attendance1: attendanceList) {
            Attendance attendance = new Attendance();
            // If the request has an ID, update the existing record
            if (attendance1.getId() != null) {
                attendance = attendanceRepository.findById(attendance1.getId()).orElseThrow(() ->
                        new IllegalArgumentException("Attendance record not found for ID: " + attendance1.getId()));
                attendance.setUpdatedOn(AppUtils.getCurrentIstTime());
                attendance.setUpdateBy(userId);
            } else {
                // If no ID, create a new attendance record
                attendance.setCreatedOn(AppUtils.getCurrentIstTime());
                attendance.setCreatedBy(userId);
            }

            // Map fields from AttendanceRequest to Attendance entity
            if (attendance1.getCourseCode() != null) {
                attendance.setCourseCode(attendance1.getCourseCode());
            }
            if (attendance1.getSubjectCode() != null) {
                attendance.setSubjectCode(attendance1.getSubjectCode());
            }
            if (attendance1.getDate() != null) {
                attendance.setDate(attendance1.getDate());
            }
            if (attendance1.getTime() != null) {
                attendance.setTime(attendance1.getTime());
            }
            if (attendance1.getStudentId() != null) {
                attendance.setStudentId(attendance1.getStudentId());
            }
            if (attendance1.getSemOrYear() != null) {
                attendance.setSemOrYear(attendance1.getSemOrYear());
            }
            if (attendance1.getPresent() != null) {
                attendance.setPresent(attendance1.getPresent());
            }
            if (attendance1.getSection() != null) {
                attendance.setSection(attendance1.getSection());
            }
            logger.info("Attendance record added/updated successfully for studentId: " + attendance1.getStudentId());
            attendanceList1.add(attendance);
        }
        // Save attendance record to the repository
        List< Attendance> savedAttendanceList = attendanceRepository.saveAll(attendanceList1);

        return savedAttendanceList;
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
