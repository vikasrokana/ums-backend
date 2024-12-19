package com.controller;

import com.Utility.AppUtils;
import com.model.Student;
import com.payload.request.StudentRequest;
import com.payload.response.StudentFeeResponse;
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


//    @ApiOperation(value = "This API will be used to update student information")
//    @RequestMapping(value = {"/update-student/{id}"}, method = RequestMethod.PUT)
//    public ResponseEntity<?> updateStudent(@PathVariable("id") Long studentId, @RequestBody StudentRequest studentRequest, HttpServletRequest request) throws Exception {
//        try {
//            Student updatedStudent = studentService.updateStudent(studentId,studentRequest);
//            return ResponseEntity.ok("Student updated successfully!");
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw new Exception(e.getMessage());
//        }
//    }

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
    @RequestMapping(value = {"/get-student-by-id/{id}"}, method = RequestMethod.GET)
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
    @RequestMapping(value = {"/get-student-list"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStudentList(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String semOrYear,
            @RequestParam(required = false) String rollNumber,
            HttpServletRequest request) {

        try {
            List<Student> students = studentService.getStudentList(courseId, semOrYear, rollNumber);
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
            List<StudentFeeResponse> studentFeesList = studentService.getStudentFeeList();
            return ResponseEntity.ok(studentFeesList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error fetching student fee list");
        }
    }
}
