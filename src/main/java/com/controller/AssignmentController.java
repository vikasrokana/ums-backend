package com.controller;

import com.Utility.AppUtils;
import com.model.Assignment;
import com.model.Course;
import com.payload.response.AssignmentResponse;
import com.payload.response.MessageResponse;
import com.service.AssignmentService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class AssignmentController {
    private static final Logger logger = LoggerFactory.getLogger(AssignmentController.class);

    @Autowired
    AssignmentService assignmentService;
    @Autowired
    AppUtils appUtils;

    @ApiOperation(value = "This api will be upload the assignment")
    @RequestMapping(value = "/faculty/upload-assignment", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAssignment(@RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "courseId", required = true) Long courseId,
                                              @RequestParam(value = "subjectId", required = false) Long subjectId,
                                              @RequestParam(value = "description", required = false) String description,
                                              @RequestParam(value = "title", required = false) String title,
                                              @RequestParam(value = "deadline", required = false) String deadline,
                                              @RequestParam(value = "section", required = false) String section,
                                              @RequestParam(value = "marks", required = false) Long marks,
                                              @RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) throws Exception {

        try {
            Long userId = appUtils.getUserId(request);
            Assignment assignment = assignmentService.uploadAssignment(id,courseId, subjectId,title,deadline,section, marks, file, userId,description);
            return ResponseEntity.ok(assignment);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get assignment list")
    @RequestMapping(value = {"student/get-assignment-list"},method = RequestMethod.GET)
    public ResponseEntity<?> getAssignmentList(@RequestParam(value = "pageNumber", required = false)Integer pageNumber ,HttpServletRequest request) throws Exception {
        try {
            String role = appUtils.getCurrentUserRole(request);
            Long userId = appUtils.getUserId(request);
            List<AssignmentResponse> assignmentResponseList = assignmentService.getAssignmentList(role,userId, pageNumber);
            return ResponseEntity.ok(assignmentResponseList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be using to Delete Assignment")
    @RequestMapping(value = {"/faculty/delete-assignment"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAssignment(@RequestParam(value = "assignmentId",required = true) Long assignmentId) throws Exception {
        try{
            Boolean isDeleted = assignmentService.deleteAssignment(assignmentId);
            if(isDeleted){
                return ResponseEntity.ok(new MessageResponse(true,"Assignment deleted successfully"));
            } else {
                return ResponseEntity.ok(new MessageResponse(false,"assignment not found"));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get Assignment by id")
    @RequestMapping(value = {"/faculty/get-assignment-by-id"}, method = RequestMethod.GET)
    public ResponseEntity<?> getAssignmentById(@RequestParam(value = "assignmentId", required = true) Long assignmentId) throws Exception {
        try {
            Assignment assignment = assignmentService.getAssignmentById(assignmentId);
            return ResponseEntity.ok(assignment);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
}
