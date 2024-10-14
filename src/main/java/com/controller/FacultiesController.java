package com.controller;

import com.model.Faculties;
import com.payload.request.FacultiesRequest;
import com.payload.response.FacultiesResponse;
import com.payload.response.MessageResponse;
import com.service.FacultiesService;
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
public class FacultiesController {
    private static final Logger logger = LoggerFactory.getLogger(FacultiesController.class);

    @Autowired
    FacultiesService facultiesService;

    @ApiOperation(value = "This API Will be used to add faculties")
    @RequestMapping(value = {"/admin/add-faculties"},method = RequestMethod.POST)
    public ResponseEntity<?> addFaculties(@RequestBody FacultiesRequest facultiesRequest, HttpServletRequest request) throws Exception {
        try{
            Faculties faculties = facultiesService.addFaculties(facultiesRequest);
            return ResponseEntity.ok(faculties);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API Will be used to update faculties")
    @RequestMapping(value = {"/admin/update-faculties"},method = RequestMethod.POST)
    public ResponseEntity<?> updateFaculties(@RequestBody FacultiesRequest facultiesRequest, HttpServletRequest request) throws Exception {
        try{
            Faculties faculties = facultiesService.addFaculties(facultiesRequest);
            return ResponseEntity.ok(faculties);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get faculties list")
    @RequestMapping(value = {"/admin/get-faculties-list"},method = RequestMethod.GET)
    public ResponseEntity<?> getFacultiesList(HttpServletRequest request) throws Exception {
        try {
            List<FacultiesResponse> facultiesResponseList = facultiesService.getFacultiesList();
            return ResponseEntity.ok(facultiesResponseList);

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
}
