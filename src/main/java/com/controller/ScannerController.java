package com.controller;

import com.Utility.AppUtils;

import com.model.ExamFileRecord;
import com.model.MarkSheet;
import com.payload.request.MarkSheetRequest;
import com.payload.response.MessageResponse;
import com.service.MarkSheetService;
import com.service.ScannerService;
import io.swagger.annotations.ApiOperation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ScannerController {
    private static final Logger logger = LoggerFactory.getLogger(ScannerController.class);
    @Autowired
    ScannerService scannerService;
    @Autowired
    AppUtils appUtils;

    @Autowired
    MarkSheetService markSheetService;

    @ApiOperation(value = "This api will be using to store the pdf in database")
    @RequestMapping(value = "/admin/upload-file", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> storeFilePdf(@RequestParam(value = "courseId", required = true) Long courseId,
                                          @RequestParam(value = "subjectId", required = true) Long subjectId,
                                          @RequestParam(value = "files", required = true) MultipartFile[] files) throws Exception {

        try {
            List<ExamFileRecord> savedFiles = new ArrayList<>();
            for (MultipartFile file : files) {
                ExamFileRecord examFileRecord = scannerService.saveStudentFile(courseId, subjectId, file);
                savedFiles.add(examFileRecord);
            }
            return new ResponseEntity<>(savedFiles, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "This API will be used to get student exam sheet list")
    @RequestMapping(value = {"admin/get-exam-sheet-list"}, method = RequestMethod.GET)
    public ResponseEntity<?> getExamFileList(@RequestParam(value = "courseId",required = false) Long courseId,
                                             @RequestParam(value = "subjectId",required = false) Long subjectId ) throws Exception {
        try {
            List<ExamFileRecord> studentList = scannerService.getExamFileList(courseId,subjectId);
            return ResponseEntity.ok(studentList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get student pdf by id")
    @RequestMapping(value = {"/admin/get-exam-sheet-by-id"}, method = RequestMethod.GET)
    public ResponseEntity<?> getExamSheetById(@RequestParam(value = "sheetId", required = true) Long sheetId) throws Exception {
        try {
            ExamFileRecord examFileRecord = scannerService.getExamSheetById(sheetId);
            return ResponseEntity.ok(examFileRecord);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API Will be used to add mark of student")
    @RequestMapping(value = {"/admin/add-marks"}, method = RequestMethod.POST)
    public ResponseEntity<?> addMarks(@RequestBody MarkSheetRequest markSheetRequest, HttpServletRequest request) throws Exception {
        try {
            Long userId = appUtils.getUserId(request);
            MarkSheet markSheet = scannerService.addMarks(markSheetRequest, userId);
            return ResponseEntity.ok(markSheet);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API Will be used to update mark of student")
    @RequestMapping(value = {"/admin/update-marks"}, method = RequestMethod.POST)
    public ResponseEntity<?> updateMarks(@RequestBody MarkSheetRequest markSheetRequest, HttpServletRequest request) throws Exception {
        try {
            Long userId = appUtils.getUserId(request);
            MarkSheet markSheet = scannerService.addMarks(markSheetRequest, userId);
            return ResponseEntity.ok(markSheet);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get mark list of student by id")
    @RequestMapping(value = {"/admin/get-mark-by-id"}, method = RequestMethod.GET)
    public ResponseEntity<?> getMarksById(@RequestParam(value = "marksId", required = true) Long marksId) throws Exception {
        try {
            MarkSheet markSheet = scannerService.getMarksById(marksId);
            return ResponseEntity.ok(markSheet);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to get mark list of student")
    @RequestMapping(value = {"/admin/get-marks-list"}, method = RequestMethod.GET)
    public ResponseEntity<?> getMarksList() throws Exception {
        try {
            List<MarkSheet> markSheetList = markSheetService.getMarksList();
            return ResponseEntity.ok(markSheetList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
}
