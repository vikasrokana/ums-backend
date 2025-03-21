package com.controller;

import com.Utility.AppUtils;
import com.dao.AssignSubjectDao;
import com.model.AssignSubject;
import com.model.Faculties;
import com.payload.request.AssignSubjectRequest;
import com.payload.request.FacultiesRequest;
import com.payload.response.AssignSubjectResponse;
import com.payload.response.FacultiesResponse;
import com.payload.response.FacultySubjectResponse;
import com.payload.response.MessageResponse;
import com.repository.AssignSubjectRepository;
import com.service.FacultiesService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class FacultiesController {
    private static final Logger logger = LoggerFactory.getLogger(FacultiesController.class);
    @Autowired
    AssignSubjectRepository assignSubjectRepository;
    @Autowired
    FacultiesService facultiesService;
    @Autowired
    AppUtils appUtils;
    @Autowired
    AssignSubjectDao assignSubjectDao;

    @ApiOperation(value = "This API Will be used to update faculties")
    @RequestMapping(value = {"/faculty/update-faculties"},method = RequestMethod.POST)
    public ResponseEntity<?> updateFaculties(@RequestBody FacultiesRequest facultiesRequest, HttpServletRequest request) throws Exception {
        try{
            Long userId = appUtils.getUserId(request);
            Faculties faculties = facultiesService.addFaculties(facultiesRequest,userId);
            return ResponseEntity.ok(faculties);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get faculties list")
    @RequestMapping(value = {"/admin/get-faculties-list"},method = RequestMethod.GET)
    public ResponseEntity<?> getFacultiesList(@RequestParam(value = "pageNumber", required = false)Integer pageNumber, HttpServletRequest request) throws Exception {
        try {
            List<FacultiesResponse> facultiesResponseList = facultiesService.getFacultiesList(pageNumber);
            return ResponseEntity.ok(facultiesResponseList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
    @ApiOperation(value = "This API will be used to get faculties list by assign subject id")
    @RequestMapping(value = {"/admin/get-faculties-list-by-subject-id"},method = RequestMethod.GET)
    public ResponseEntity<?> getFacultiesList(@RequestParam(value = "subjectId",required = true) Long subjectId,
            @RequestParam(value = "pageNumber", required = false)Integer pageNumber, HttpServletRequest request) throws Exception {
        try {
            List<FacultiesResponse> facultiesResponseList = facultiesService.getFacultiesListBySubjectId(subjectId, pageNumber);
            return ResponseEntity.ok(facultiesResponseList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get Faculties by id")
    @RequestMapping(value = {"/faculty/get-faculties-details"}, method = RequestMethod.GET)
    public ResponseEntity<?> getFacultyDetails(HttpServletRequest request) throws Exception {
        try {
            Long userId = appUtils.getUserId(request);
            Faculties faculties = facultiesService.getFacultyDetails(userId);
            return ResponseEntity.ok(faculties);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
    @ApiOperation(value = "This API will be used to get Faculties by id")
    @RequestMapping(value = {"/admin/get-faculties-by-id"}, method = RequestMethod.GET)
    public ResponseEntity<?> getSubjectById(@RequestParam(value = "facultyId", required = true) Long facultyId) throws Exception {
        try {
            Faculties faculties = facultiesService.getFacultyById(facultyId);
            return ResponseEntity.ok(faculties);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be using to Delete faculty")
    @RequestMapping(value = {"/admin/delete-faculty"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSubject(@RequestParam(value = "facultyId",required = true) Long facultyId) throws Exception {
        try{
            Boolean isDeleted = facultiesService.deleteFaculty(facultyId);
            if(isDeleted){
                return ResponseEntity.ok(new MessageResponse(true,"Faculty deleted successfully"));
            } else {
                return ResponseEntity.ok(new MessageResponse(false,"faculty not found"));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        } 
    }

    @ApiOperation(value = "This API will be used to assign a subject to a faculty")
    @RequestMapping(value = "/admin/assign-subject", method = RequestMethod.POST)
    public ResponseEntity<?> assignSubject(@RequestBody AssignSubjectRequest assignSubjectRequest, HttpServletRequest request) {
        try {
            Long userId = appUtils.getUserId(request);

            // Check if the subject is already assigned
            Optional<AssignSubject> existingAssignment = assignSubjectRepository
                    .findBySubjectIdAndCourseId(assignSubjectRequest.getCourseId(), assignSubjectRequest.getSubjectId(), true);

            if (existingAssignment.isPresent()) {
                return ResponseEntity.ok(new MessageResponse(true, "This subject is already assigned"));
            }

            // Assign the subject
            AssignSubject assignSubject = facultiesService.assignSubject(assignSubjectRequest, userId);
            return ResponseEntity.ok(assignSubject);

        } catch (Exception e) {
            logger.error("Error while assigning subject: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(false, "Failed to assign subject: " + e.getMessage()));
        }
    }


    @ApiOperation(value = "This API Will be used to update assign subject to faculty")
    @RequestMapping(value = {"/admin/update-assign-subject"},method = RequestMethod.POST)
    public ResponseEntity<?> updateAssignSubject(@RequestBody AssignSubjectRequest assignSubjectRequest, HttpServletRequest request) throws Exception {
        try{
//            Long userId= appUtils.getUserId(request);
            Long userId=appUtils.getUserId(request);
            AssignSubject assignSubject = facultiesService.assignSubject(assignSubjectRequest,userId);
            return ResponseEntity.ok(assignSubject);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get assign subject list")
    @RequestMapping(value = {"/faculty/get-assign-subject-list"},method = RequestMethod.GET)
    public ResponseEntity<?> getAssignSubjectList(@RequestParam(value = "pageNumber", required = false)Integer pageNumber, HttpServletRequest request) throws Exception {
        try {
            Long userId = appUtils.getUserId(request);
            String role = appUtils.getCurrentUserRole(request);
            List<AssignSubjectResponse> assignSubjectResponses = assignSubjectDao.getAssignFacultiesList(userId, role,pageNumber);
            return ResponseEntity.ok(assignSubjectResponses);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get assign faculty list to the student")
    @RequestMapping(value = {"/student/get-assign-faculty-to-student"},method = RequestMethod.GET)
    public ResponseEntity<?> getAssignFacultyListToStudent(@RequestParam(value = "pageNumber",required = false)Integer pageNumber,HttpServletRequest request) throws Exception {
        try {
            Long userId=appUtils.getUserId(request);
            List<FacultySubjectResponse> facultySubjectResponseList = assignSubjectDao.getAssignFacultiesListToStudent(userId, pageNumber);
            return ResponseEntity.ok(facultySubjectResponseList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

}
