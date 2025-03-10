package com.controller;

import com.model.Course;
import com.model.RollGenerator;
import com.payload.request.RollNumberRequest;
import com.payload.response.MessageResponse;
import com.service.RollGeneratorService;
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
public class RollGeneratorController {
    private static final Logger logger = LoggerFactory.getLogger(RollGeneratorController.class);

    @Autowired
    RollGeneratorService rollGeneratorService;

    @ApiOperation(value = "This API Will be used to add roll number series")
    @RequestMapping(value = {"/admin/add-roll-number"},method = RequestMethod.POST)
    public ResponseEntity<?> addRollNumber(@RequestBody RollNumberRequest rollNumberRequest, HttpServletRequest request) throws Exception {
        try{

            RollGenerator rollGenerator = rollGeneratorService.addRollNumber(rollNumberRequest);
            return ResponseEntity.ok(rollGenerator);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API Will be used to update roll number series")
    @RequestMapping(value = {"/admin/update-roll-number"},method = RequestMethod.POST)
    public ResponseEntity<?> updateRollNumber(@RequestBody RollNumberRequest rollNumberRequest, HttpServletRequest request) throws Exception {
        try{

            RollGenerator rollGenerator = rollGeneratorService.addRollNumber(rollNumberRequest);
            return ResponseEntity.ok(rollGenerator);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get roll number by course id and sem or Year")
    @RequestMapping(value = {"/open/get-rollNumber-by-course-and-sem"}, method = RequestMethod.GET)
    public ResponseEntity<?> getCourseById(@RequestParam(value = "courseId", required = true) Long courseId,
                                           @RequestParam(value = "semOrYear",required = true) String semOrYear ) throws Exception {
        try {
            RollGenerator rollGenerator = rollGeneratorService.getRollNumberByCourseIdAndSem(courseId,semOrYear);
            return ResponseEntity.ok(rollGenerator);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be using to Delete roll number")
    @RequestMapping(value = {"/admin/delete-roll-number"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRollNumber(@RequestParam(value = "id",required = true) Long id) throws Exception {
        try{
            Boolean isDeleted = rollGeneratorService.deleteRollNumber(id);
            if(isDeleted){
                return ResponseEntity.ok(new MessageResponse(true,"roll number deleted successfully"));
            } else {
                return ResponseEntity.ok(new MessageResponse(false,"roll number not found"));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

}
