package com.controller;

import com.Utility.AppUtils;
import com.model.Attendance;
import com.model.Course;
import com.payload.request.AttendanceRequest;
import com.payload.request.CourseRequest;
import com.payload.response.FacultiesResponse;
import com.payload.response.StudentResponse;
import com.repository.AttendanceRepository;
import com.service.AttendanceService;
import com.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class AttendanceController {
    @Autowired
    AppUtils appUtils;
    @Autowired
    AttendanceService attendanceService;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    @ApiOperation(value = "This API Will be used to add attendance of student")
    @RequestMapping(value = {"/faculty/add-student-attendance"},method = RequestMethod.POST)
    public ResponseEntity<?> addStudentAttendance(@RequestBody AttendanceRequest attendanceRequest, HttpServletRequest request) throws Exception {
        try{
            Long userId = appUtils.getUserId(request);
            Attendance attendance = attendanceService.addStudentAttendance(attendanceRequest,userId);
            return ResponseEntity.ok(attendance);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }
    @ApiOperation(value = "This API Will be used to update attendance of student")
    @RequestMapping(value = {"/faculty/update-student-attendance"},method = RequestMethod.POST)
    public ResponseEntity<?> updateStudentAttendance(@RequestBody AttendanceRequest attendanceRequest, HttpServletRequest request) throws Exception {
        try{
            Long userId = appUtils.getUserId(request);
            Attendance attendance = attendanceService.addStudentAttendance(attendanceRequest,userId);
            return ResponseEntity.ok(attendance);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }
}
