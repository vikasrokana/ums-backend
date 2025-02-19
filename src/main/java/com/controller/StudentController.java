package com.controller;

import com.Utility.AppUtils;
import com.model.Student;
import com.payload.request.StudentRequest;
import com.payload.response.StudentFeeResponse;
import com.payload.response.StudentResponse;
import com.service.StudentFeesService;
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
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    AppUtils appUtils;
    @Autowired
    StudentService studentService;
    @Autowired
    StudentFeesService studentFeesService;

    @ApiOperation(value = "This API will be used for student update student details")
    @RequestMapping(value = {"student/update-student-details"}, method = RequestMethod.POST)
    public ResponseEntity<?> studentDetails(@RequestBody StudentRequest studentRequest, HttpServletRequest request) throws Exception {
        try {
            // Step 1: update the student
            Long userId = appUtils.getUserId(request);
            Student student = studentService.StudentDetails(studentRequest,userId);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }


    @ApiOperation(value = "This API will be used to get student details")
    @RequestMapping(value = {"student/get-student-details"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStudentDetails(HttpServletRequest request) throws Exception {
        try {
            Long userId =appUtils.getUserId(request);
            Student student = studentService.findStudentDetails(userId);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get student by id")
    @RequestMapping(value = {"student/get-student-by-id/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long studentId, HttpServletRequest request) throws Exception {
        try {
            Student student = studentService.findById(studentId);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
    @ApiOperation(value = "This API will be used to get the student list by course, semester/year, and roll number")
    @RequestMapping(value = {"faculty/get-student-list"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStudentList(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Long semOrYear,
            @RequestParam(required = false) String rollNumber,
            @RequestParam(required = false) Integer pageNumber,
            HttpServletRequest request) {

        try {
            List<Student> students = studentService.getStudentList(courseId, semOrYear, rollNumber, pageNumber);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching student list");
        }
    }

    @ApiOperation(value = "This API will be used to get the students fee list")
    @RequestMapping(value = {"admin/get-students-fee-list"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStudentsFeeList(HttpServletRequest request) {

        try {
            List<StudentFeeResponse> studentFeesList = studentFeesService.findFeeList();
            return ResponseEntity.ok(studentFeesList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching student fee list");
        }
    }

    @ApiOperation(value = "This API will be used to get student assign to a course")
    @RequestMapping(value = {"/faculty/get-student-by-faculty-id"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStudentByFacultyId(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, HttpServletRequest request) throws Exception {
        try {
            Long userId = appUtils.getUserId(request);
            List<StudentResponse> student = studentService.getStudentByFacultyId(userId, pageNumber);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get the classmate student list")
    @RequestMapping(value = {"student/get-classmate-students-list"}, method = RequestMethod.GET)
    public ResponseEntity<?> getClassmateStudentsList(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, HttpServletRequest request) {

        try {
            Long userId = appUtils.getUserId(request);
            List<StudentResponse> classmateStudentList = studentService.getClassmateStudentList(userId, pageNumber);
            return ResponseEntity.ok(classmateStudentList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching student classmate list");
        }
    }
}
