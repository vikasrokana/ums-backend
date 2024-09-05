package com.controller;

import com.model.ExamFileRecord;
import com.model.Student;
import com.payload.response.MessageResponse;
import com.service.ScannerService;
import io.swagger.annotations.ApiOperation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.sax.SAXResult;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ScannerController {
    private static final Logger logger = LoggerFactory.getLogger(ScannerController.class);
    @Autowired
    ScannerService scannerService;

    @ApiOperation(value = "This api will be using to scan and make pdf of text")
    @RequestMapping(value = "/admin/scan-file", method = RequestMethod.POST)
    public ResponseEntity<?> scanFile(@RequestParam("file") MultipartFile file ) throws Exception {
        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            String pdfFilePath = scannerService.scanFileAndGeneratePDF(convFile);
            return ResponseEntity.ok(new MessageResponse(false, "file scan successful." + "C:\\Users\\User\\Documents\\output.pdf"));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }


    @ApiOperation(value = "This api will be using to scan and change into pdf")
    @RequestMapping(value = "/admin/scan-file-pdf", method = RequestMethod.POST)
    public ResponseEntity<?> scanFilePdf(@RequestParam("file") MultipartFile file ) throws Exception {
        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            String pdfFilePath = scannerService.scanAndGeneratePDF(document,file);
            return ResponseEntity.ok(new MessageResponse(false, "file scan successful." + "C:\\Users\\User\\Documents\\"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This api will be using to store the pdf in database")
    @RequestMapping(value = "/admin/upload-file", method = RequestMethod.POST)
    public ResponseEntity<?> storeFilePdf(@RequestParam("file") MultipartFile file
                                         ) throws Exception {
            try {
                ExamFileRecord examFileRecord = scannerService.saveStudentFile(file);
                return new ResponseEntity<>(examFileRecord, HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @ApiOperation(value = "This API will be used to get student file list")
    @RequestMapping(value = {"admin/get-exam-file-list"}, method = RequestMethod.GET)
    public ResponseEntity<?> getExamFileList() throws Exception {
        try {
            List<ExamFileRecord> studentList = scannerService.getExamFileList();
            return ResponseEntity.ok(studentList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
}
