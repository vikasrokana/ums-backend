package com.controller;

import com.Utility.AppUtils;
import com.model.Attendance;
import com.payload.request.AttendanceRequest;
import com.payload.response.AttendanceResponse;
import com.service.AttendanceService;
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
           List< Attendance> attendance = attendanceService.addStudentAttendance(attendanceRequest,userId);
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
           List< Attendance> attendance = attendanceService.addStudentAttendance(attendanceRequest,userId);
            return ResponseEntity.ok(attendance);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get student attendance list for faculty")
    @RequestMapping(value = {"faculty/get-student-attendance-list"},method = RequestMethod.GET)
    public ResponseEntity<?> getStudentAttendanceList(@RequestParam(value = "date",required = true) String date ,HttpServletRequest request) throws Exception {
        try {
            Long userId = appUtils.getUserId(request);
            List<AttendanceResponse> attendanceResponseList = attendanceService.getStudentAttendance(date,userId);
            return ResponseEntity.ok(attendanceResponseList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    //working on it
    @ApiOperation(value = "This API will be used to get student attendance list for student")
    @RequestMapping(value = {"student/get-student-own-attendance-list"},method = RequestMethod.GET)
    public ResponseEntity<?> getStudentOwnAttendanceList(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "date",required = false) String date ,
                                                         HttpServletRequest request) throws Exception {
        try {
            Long userId = appUtils.getUserId(request);
            List<AttendanceResponse> attendanceResponseList = attendanceService.getStudentOwnAttendance(pageNumber,date,userId);
            return ResponseEntity.ok(attendanceResponseList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
}
