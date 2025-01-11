package com.controller;

import com.Utility.AppUtils;
import com.model.Subject;
import com.payload.request.SubjectRequest;
import com.payload.response.MessageResponse;
import com.payload.response.SubjectResponse;
import com.service.SubjectService;
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
public class SubjectController {

    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);
    @Autowired
    SubjectService subjectService;
    @Autowired
    AppUtils appUtils;

    @ApiOperation(value = "This API Will be used to add subject")
    @RequestMapping(value = {"/admin/add-subject"},method = RequestMethod.POST)
    public ResponseEntity<?> addSubject(@RequestBody SubjectRequest subjectRequest, HttpServletRequest request) throws Exception {
        try{
            Long userId =appUtils.getUserId(request);
            Subject subject = subjectService.addSubject(subjectRequest,userId);
            return ResponseEntity.ok(subject);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API Will be used to update subject")
    @RequestMapping(value = {"/admin/update-subject"},method = RequestMethod.POST)
    public ResponseEntity<?> updateSubject(@RequestBody SubjectRequest subjectRequest, HttpServletRequest request) throws Exception {
        try{
            Long userId = appUtils.getUserId(request);
            Subject subject = subjectService.addSubject(subjectRequest,userId);
            return ResponseEntity.ok(subject);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get subject list")
    @RequestMapping(value = {"/open/get-subject-list"},method = RequestMethod.GET)
    public ResponseEntity<?> getSubjectList(HttpServletRequest request) throws Exception {
        try {
            List<SubjectResponse> subjectResponseList = subjectService.getSubjectList();
            return ResponseEntity.ok(subjectResponseList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be using to Delete subject")
    @RequestMapping(value = {"/admin/delete-subject"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSubject(@RequestParam(value = "subjectId",required = true) Long subjectId) throws Exception {
        try{
            Boolean isDeleted = subjectService.deleteSubject(subjectId);
            if(isDeleted){
                return ResponseEntity.ok(new MessageResponse(true,"Subject deleted successfully"));
            } else {
                return ResponseEntity.ok(new MessageResponse(false,"Subject not found"));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get Subject by id")
    @RequestMapping(value = {"/admin/get-subject-by-id"}, method = RequestMethod.GET)
    public ResponseEntity<?> getSubjectById(@RequestParam(value = "subjectId", required = true) Long subjectId) throws Exception {
        try {
            Subject subject = subjectService.getSubjectById(subjectId);
            return ResponseEntity.ok(subject);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get Subject details by course id")
    @RequestMapping(value = {"/admin/get-subject-by-course-id"}, method = RequestMethod.GET)
    public ResponseEntity<?> getSubjectByCourseId(@RequestParam(value = "courseId", required = true) Long courseId) throws Exception {
        try {
            List<SubjectResponse> subjectResponseList = subjectService.getSubjectByCourseId(courseId);
            return ResponseEntity.ok(subjectResponseList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

}
