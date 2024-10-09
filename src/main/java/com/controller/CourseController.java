package com.controller;

import com.model.Course;
import com.payload.request.CourseRequest;
import com.payload.response.CourseResponse;
import com.payload.response.MessageResponse;
import com.service.CourseService;
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
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;
    @ApiOperation(value = "This API Will be used to add Course")
    @RequestMapping(value = {"/admin/add-course"},method = RequestMethod.POST)
    public ResponseEntity<?> addCourse(@RequestBody CourseRequest courseRequest, HttpServletRequest request) throws Exception {
        try{
           Course course = courseService.addCourse(courseRequest);
            return ResponseEntity.ok(course);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API Will be used to update Course")
    @RequestMapping(value = {"/admin/update-course"},method = RequestMethod.POST)
    public ResponseEntity<?> updateCourse(@RequestBody CourseRequest courseRequest, HttpServletRequest request) throws Exception {
        try{
            Course course = courseService.addCourse(courseRequest);
            return ResponseEntity.ok(course);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get course list")
    @RequestMapping(value = {"/admin/get-course-list"},method = RequestMethod.GET)
    public ResponseEntity<?> getCourseList(HttpServletRequest request) throws Exception {
        try {
            List<CourseResponse> courseResponseList = courseService.getCourseList();
            return ResponseEntity.ok(courseResponseList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be using to Delete Course")
    @RequestMapping(value = {"/user/delete-course"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCourse(@RequestParam(value = "courseId",required = true) Long courseId) throws Exception {
        try{
            Boolean isDeleted = courseService.deleteCourse(courseId);
            if(isDeleted){
                return ResponseEntity.ok(new MessageResponse(true,"Course deleted successfully"));
            } else {
                return ResponseEntity.ok(new MessageResponse(false,"Course not found"));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get Course by id")
    @RequestMapping(value = {"/admin/get-course-by-id"}, method = RequestMethod.GET)
    public ResponseEntity<?> getCourseById(@RequestParam(value = "courseId", required = true) Long courseId) throws Exception {
        try {
            Course course = courseService.getCourseById(courseId);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
}
