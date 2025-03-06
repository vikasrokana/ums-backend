package com.controller;

import com.Utility.AppUtils;
import com.model.ClassSchedule;
import com.payload.request.ClassScheduleRequest;
import com.payload.response.ClassScheduleResponse;
import com.payload.response.MessageResponse;
import com.service.ClassScheduleService;
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
public class ClassScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);
    @Autowired
    ClassScheduleService classScheduleService;
    @Autowired
    AppUtils appUtils;

    @ApiOperation(value = "This API Will be used to add class schedule")
    @RequestMapping(value = {"/admin/add-class-schedule"},method = RequestMethod.POST)
    public ResponseEntity<?> addClassSchedule(@RequestBody ClassScheduleRequest classScheduleRequest, HttpServletRequest request) throws Exception {
        try{
            Long userId =appUtils.getUserId(request);
            ClassSchedule classSchedule = classScheduleService.addClassSchedule(classScheduleRequest,userId);
            return ResponseEntity.ok(classSchedule);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API Will be used to update class schedule")
    @RequestMapping(value = {"/admin/update-class-schedule"},method = RequestMethod.POST)
    public ResponseEntity<?> updateSubject(@RequestBody ClassScheduleRequest classScheduleRequest, HttpServletRequest request) throws Exception {
        try{
            Long userId = appUtils.getUserId(request);
            ClassSchedule classSchedule = classScheduleService.addClassSchedule(classScheduleRequest,userId);
            return ResponseEntity.ok(classSchedule);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get class schedule list")
    @RequestMapping(value = {"/student/get-class-schedule-list"},method = RequestMethod.GET)
    public ResponseEntity<?> getClassScheduleList(@RequestParam(value = "pageNumber",required = false)Integer pageNumber, HttpServletRequest request) throws Exception {
        try {
            String role = appUtils.getCurrentUserRole(request);
            Long userId = appUtils.getUserId(request);
            List<ClassScheduleResponse> classScheduleResponseList = classScheduleService.getClassSchedule(userId,role,pageNumber);
            return ResponseEntity.ok(classScheduleResponseList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be using to Delete class Schedule")
    @RequestMapping(value = {"/admin/delete-class-schedule"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSubject(@RequestParam(value = "classScheduleId",required = true) Long classScheduleId) throws Exception {
        try{
            Boolean isDeleted = classScheduleService.deleteClassSchedule(classScheduleId);
            if(isDeleted){
                return ResponseEntity.ok(new MessageResponse(true,"Class schedule deleted successfully"));
            } else {
                return ResponseEntity.ok(new MessageResponse(false,"Class schedule not found"));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }
}
