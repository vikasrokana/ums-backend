package com.controller;

import com.model.Course;
import com.payload.request.CourseRequest;
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
}
