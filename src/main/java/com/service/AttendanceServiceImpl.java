package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.model.Attendance;
import com.model.Student;
import com.payload.request.AttendanceRequest;
import com.payload.response.AttendanceResponse;
import com.repository.AttendanceRepository;
import com.repository.FacultiesRepository;
import com.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService{

    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    FacultiesRepository facultiesRepository;

    @Autowired
    StudentRepository studentRepository;
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
            if (attendance1.getCourseId() != null) {
                attendance.setCourseId(attendance1.getCourseId());
            }
            if (attendance1.getSubjectId() != null) {
                attendance.setSubjectId(attendance1.getSubjectId());
            }
            if (attendance1.getDate() != null) {
                attendance.setDate(attendance1.getDate());
            }
//            if (attendance1.getTime() != null) {
//                attendance.setTime(attendance1.getTime());
//            }
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


//    @Override
//    public List<AttendanceResponse> getStudentAttendance(String courseCode, String subjectCode, String date, Long userId, String role, Integer pageNumber) throws RecordNotFoundException {
//        List<Attendance> attendanceList;
//        Pageable pageable = AppUtils.getPageRange(pageNumber);
//        // Ensure correct string comparison
//        if ("admin".equals(role)) {
//            attendanceList = attendanceRepository.findForAdminByDateAndIsActive(date, true, pageable);
//        } else {
//            attendanceList = attendanceRepository.findByDateAndIsActive(date, userId, true,pageable);
//        }
//
//        // Return an empty list instead of throwing an exception
//        if (attendanceList.isEmpty()) {
//            logger.warn("No attendance records found for date: {}", date);
//            return new ArrayList<>();
//        }
//
//        // Convert Attendance to AttendanceResponse using Java Streams
//        List<AttendanceResponse> attendanceResponseList = attendanceList.stream().map(attendance -> {
//            AttendanceResponse response = new AttendanceResponse();
//            response.setId(attendance.getId());
//            response.setCourseCode(attendance.getCourseCode());
//            response.setSubjectCode(attendance.getSubjectCode());
//            response.setSemOrYear(attendance.getSemOrYear());
//            response.setDate(attendance.getDate());
//            response.setTime(attendance.getTime());
//            response.setStudentId(attendance.getStudentId());
//            response.setPresent(attendance.getPresent());
//            response.setSection(attendance.getSection());
//            response.setCreatedOn(attendance.getCreatedOn());
//            response.setUpdatedOn(attendance.getUpdatedOn());
//            response.setCreatedBy(attendance.getCreatedBy());
//            response.setUpdateBy(attendance.getUpdateBy());
//            return response;
//        }).collect(Collectors.toList());
//
//        logger.info("Retrieved {} attendance records for date: {}", attendanceResponseList.size(), date);
//        return attendanceResponseList;
//    }

    @Override
    public List<AttendanceResponse> getStudentAttendance(Long courseId, Long subjectId, String section, String date, Long userId, String role, Integer pageNumber) throws RecordNotFoundException {
        List<Attendance> attendanceList;
        List<Student> studentList;
        List<AttendanceResponse> attendanceResponseList = new ArrayList<>();
        Pageable pageable = AppUtils.getPageRange(pageNumber);

        studentList = studentRepository.findByCourseIdAndSectionAndIsActive(courseId, section, true);
        attendanceList = attendanceRepository.findByCourseIdAndSectionAndSubjectIdAndDateAndIsActive(courseId, section, subjectId, date, true, pageable);


        for (Student student : studentList) {
            Attendance attendance = findAttendanceByStudentId(attendanceList, student.getId());

            AttendanceResponse response = new AttendanceResponse();
            response.setStudentId(student.getId());
            response.setRollNumber(student.getRollNumber());
            response.setEnrollmentNumber(student.getEnrollmentNumber());
            response.setStudentName(student.getStudentName());
            response.setCourseId(courseId);
            response.setSection(student.getSection());
            response.setSemOrYear(student.getSemOrYear());
            response.setSubjectId(subjectId);

            if (attendance != null) {
                response.setId(attendance.getId());
                response.setDate(attendance.getDate());
                response.setTime(attendance.getTime());
                response.setPresent(attendance.getPresent());
                response.setCreatedOn(attendance.getCreatedOn());
                response.setUpdatedOn(attendance.getUpdatedOn());
                response.setCreatedBy(attendance.getCreatedBy());
                response.setUpdateBy(attendance.getUpdateBy());
            } else {
                response.setPresent(false); // Default to absent if no record exists
            }

            attendanceResponseList.add(response);
        }

        return attendanceResponseList;
    }


        public Attendance findAttendanceByStudentId(final List<Attendance> list, final Long studentId){
            return list.stream().filter(o -> o.getStudentId().equals(studentId)).findFirst().orElse(null);
        }

    @Override
    public List<AttendanceResponse> getStudentOwnAttendance(Integer pageNumber, String date, Long userId) throws RecordNotFoundException {
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        List<AttendanceResponse> attendanceResponseList = new ArrayList<>();
        Student student = studentRepository.findByUserIdAndIsActive(userId,true);
        List<Attendance> attendanceList;
        if(date!= null){
        attendanceList = attendanceRepository.findByDateAndStudentId(date,student.getId(), true,pageable);}
        else{
            attendanceList = attendanceRepository.findByStudentIdAndIsActive(student.getId(),true, pageable);
        }

        if(attendanceList.isEmpty()){
            logger.warn("No attendance records found for date: {}", date);
            return new ArrayList<>();
        }
        for(Attendance attendance:attendanceList){
            AttendanceResponse attendanceResponse = new AttendanceResponse();
            attendanceResponse.setId(attendance.getId());
            attendanceResponse.setCourseId(attendance.getCourseId());
            attendanceResponse.setSubjectId(attendance.getSubjectId());
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
