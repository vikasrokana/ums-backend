package com.controller;

import com.Utility.AppUtils;
import com.model.Course;
import com.model.Examination;
import com.payload.request.ExaminationRequest;
import com.payload.response.CourseResponse;
import com.payload.response.ExaminationResponse;
import com.payload.response.MessageResponse;
import com.service.ExaminationService;
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
public class ExaminationController {
    @Autowired
    AppUtils appUtils;
    private static final Logger logger = LoggerFactory.getLogger(ExaminationController.class);

    @Autowired
    ExaminationService examinationService;

    @ApiOperation(value = "This API Will be used to add examination")
    @RequestMapping(value = {"/admin/add-exam"},method = RequestMethod.POST)
    public ResponseEntity<?> addExamination(@RequestBody ExaminationRequest examinationRequest, HttpServletRequest request) throws Exception {
        try{
            Long userId = appUtils.getUserId(request);
            Examination examination = examinationService.addExamination(examinationRequest,userId);
            return ResponseEntity.ok(examination);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API Will be used to update examination")
    @RequestMapping(value = {"/admin/update-exam"},method = RequestMethod.POST)
    public ResponseEntity<?> updateExamination(@RequestBody ExaminationRequest examinationRequest, HttpServletRequest request) throws Exception {
        try{
            Long userId = appUtils.getUserId(request);
            Examination examination = examinationService.addExamination(examinationRequest,userId);
            return ResponseEntity.ok(examination);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get examination schedule list")
    @RequestMapping(value = {"admin/get-examination-list"},method = RequestMethod.GET)
    public ResponseEntity<?> getExaminationList(@RequestParam(value = "pageNumber", required = false)Integer pageNumber ,HttpServletRequest request) throws Exception {
        try {
            List<ExaminationResponse> examinationResponseList = examinationService.getExaminationList(pageNumber);
            return ResponseEntity.ok(examinationResponseList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be using to Delete examination")
    @RequestMapping(value = {"/admin/delete-examination"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteExamination(@RequestParam(value = "examinationId",required = true) Long examinationId) throws Exception {
        try{
            Boolean isDeleted = examinationService.deleteExamination(examinationId);
            if(isDeleted){
                return ResponseEntity.ok(new MessageResponse(true,"examination deleted successfully"));
            } else {
                return ResponseEntity.ok(new MessageResponse(false,"examination not found"));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get examination by id")
    @RequestMapping(value = {"/admin/get-examination-by-id"}, method = RequestMethod.GET)
    public ResponseEntity<?> getCourseById(@RequestParam(value = "examId", required = true) Long examId) throws Exception {
        try {
            Examination examination = examinationService.getExaminationById(examId);
            return ResponseEntity.ok(examination);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
}
