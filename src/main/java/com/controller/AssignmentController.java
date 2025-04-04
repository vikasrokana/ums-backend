package com.controller;

import com.Utility.AppUtils;
import com.model.Assignment;
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
                                              @RequestParam(value = "title", required = false) String title,
                                              @RequestParam(value = "deadline", required = false) String deadline,
                                              @RequestParam(value = "section", required = false) String section,
                                              @RequestParam(value = "marks", required = false) Long marks,
                                              @RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request) throws Exception {

        try {
            Long userId = appUtils.getUserId(request);
            Assignment assignment = assignmentService.uploadAssignment(id,courseId, subjectId,title,deadline,section, marks, file, userId);
            return ResponseEntity.ok(assignment);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
}
