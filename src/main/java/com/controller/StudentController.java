package com.controller;

import com.model.Enrollment;
import com.model.Student;
import com.payload.request.StudentRequest;
import com.service.StudentService;
import com.service.EnrollmentService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentService studentService;

    @Autowired
    EnrollmentService enrollmentService;

    @ApiOperation(value = "This API will be used for student registration and automatic course enrollment")
    @RequestMapping(value = {"/register-student"}, method = RequestMethod.POST)
    public ResponseEntity<?> registerStudent(@RequestBody StudentRequest studentRequest, HttpServletRequest request) throws Exception {
        try {
            // Step 1: Register the student
            Student student = studentService.registerStudent(studentRequest);

            // Step 2: Automatically enroll the student in the selected course
            Enrollment enrollment = enrollmentService.enrollStudentInCourse(studentRequest.getCourseId(), student);

            return ResponseEntity.ok("Student registered and enrolled successfully!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }


    @ApiOperation(value = "This API will be used to update student information")
    @RequestMapping(value = {"/update-student/{id}"}, method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long studentId, @RequestBody StudentRequest studentRequest, HttpServletRequest request) throws Exception {
        try {
            Student updatedStudent = studentService.updateStudent(studentId,studentRequest);
            return ResponseEntity.ok("Student updated successfully!");
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
}
