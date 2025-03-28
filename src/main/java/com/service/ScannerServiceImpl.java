package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.model.Course;
import com.model.ExamFileRecord;
import com.model.MarkSheet;
import com.payload.request.MarkSheetRequest;
import com.repository.ExamFileRecordRepository;
import com.repository.MarkSheetRepository;
import com.repository.StudentRepository;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ScannerServiceImpl implements ScannerService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    MarkSheetRepository markSheetRepository;
    @Autowired
    AppUtils appUtils;

    @Autowired
    ExamFileRecordRepository examFileRecordRepository;
    private static final Logger logger = LoggerFactory.getLogger(ScannerServiceImpl.class);

    @Override
    public ExamFileRecord saveStudentFile(Long courseId, Long subjectId, MultipartFile file) throws IOException {
//        String uniqueNumber = UUID.randomUUID().toString(); // year
        ExamFileRecord examFileRecord1 = examFileRecordRepository.findLastUniqueNumber(true);
        String uniqueNumber;
        if(examFileRecord1 == null){
             uniqueNumber = appUtils.generateUniqueNumber(null);
        }
        else{
             uniqueNumber = appUtils.generateUniqueNumber(examFileRecord1.getUniqueNumber());
        }


        String originalFilename = file.getOriginalFilename();
        String filenameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
        String fileName = filenameWithoutExtension + uniqueNumber +".pdf";
        String storagePath = "C:\\Users\\User\\Documents\\upload\\";

        String fileUrl = storagePath + fileName;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);


        // Save file to local storage
        File dest = new File(storagePath + fileName );
        file.transferTo(dest);

        // Save file details in database
        ExamFileRecord examFileRecord = new ExamFileRecord();
        examFileRecord.setUniqueNumber(uniqueNumber);
        examFileRecord.setDate(strDate);
        examFileRecord.setFileUrl(fileUrl);
        examFileRecord.setCourseId(courseId);
        examFileRecord.setSubjectId(subjectId);
        examFileRecord.setCreatedOn(AppUtils.getCurrentIstTime());
        return examFileRecordRepository.save(examFileRecord);

    }

    @Override
    public List<ExamFileRecord> getExamFileList(Long courseId, Long subjectId) throws RecordNotFoundException {
        List<ExamFileRecord> examFileRecordList;
        examFileRecordList = examFileRecordRepository.getExamFileList(courseId,subjectId,true);
        if (examFileRecordList == null || examFileRecordList.isEmpty()) {
            logger.warn("exam file not found with found");
            return new ArrayList<>();
        }
        return examFileRecordList;
    }

    @Override
    public ExamFileRecord getExamSheetById(Long sheetId) {
        ExamFileRecord examFileRecord = examFileRecordRepository.findByIdAndIsActive(sheetId, true);
        if (examFileRecord == null) {
            logger.warn("Exam sheet not found with id: " + sheetId);
            return null; // Caller must handle null values
        }
        logger.info("Get exam sheet using id");
        return examFileRecord;
    }

    @Override
    public MarkSheet addMarks(MarkSheetRequest markSheetRequest, Long userId) {
        MarkSheet markSheet = new MarkSheet();
        Double totalMark =0.0;
        if(markSheetRequest.getId() != null){
            markSheet = markSheetRepository.findByMarkIdAndIsActive(markSheetRequest.getId(),true);
            markSheet.setUpdatedBy(userId);
            markSheet.setUpdatedOn(AppUtils.getCurrentIstTime());
        }
        else{
            markSheet.setCreatedBy(userId);
            markSheet.setCreatedOn(AppUtils.getCurrentIstTime());
        }
        if(markSheetRequest.getExamRecordId() !=null){
            markSheet.setExamRecordId(markSheetRequest.getExamRecordId());
        }
        if(markSheetRequest.getRollNumber() !=null){
            markSheet.setRollNumber(markSheetRequest.getRollNumber());
        }
        if (markSheetRequest.getSubjectId() != null) {
            markSheet.setSubjectId(markSheetRequest.getSubjectId());
        }
        if (markSheetRequest.getQ1() != null) {
            markSheet.setQ1(markSheetRequest.getQ1());
            totalMark += markSheetRequest.getQ1();
        }
        if (markSheetRequest.getQ2() != null) {
            markSheet.setQ2(markSheetRequest.getQ2());
            totalMark += markSheetRequest.getQ2();
        }
        if (markSheetRequest.getQ3() != null) {
            markSheet.setQ3(markSheetRequest.getQ3());
            totalMark += markSheetRequest.getQ3();
        }
        if (markSheetRequest.getQ4() != null) {
            markSheet.setQ4(markSheetRequest.getQ4());
            totalMark += markSheetRequest.getQ4();
        }
        if (markSheetRequest.getQ5() != null) {
            markSheet.setQ5(markSheetRequest.getQ5());
            totalMark += markSheetRequest.getQ5();
        }
        if (markSheetRequest.getQ6() != null) {
            markSheet.setQ6(markSheetRequest.getQ6());
            totalMark += markSheetRequest.getQ6();
        }
        if (markSheetRequest.getQ7() != null) {
            markSheet.setQ7(markSheetRequest.getQ7());
            totalMark += markSheetRequest.getQ7();
        }
        if (markSheetRequest.getQ8() != null) {
            markSheet.setQ8(markSheetRequest.getQ8());
            totalMark += markSheetRequest.getQ8();
        }
        if (markSheetRequest.getQ9() != null) {
            markSheet.setQ9(markSheetRequest.getQ9());
            totalMark += markSheetRequest.getQ9();
        }
        if (markSheetRequest.getQ10() != null) {
            markSheet.setQ10(markSheetRequest.getQ10());
            totalMark += markSheetRequest.getQ10();
        }
        if (markSheetRequest.getTotalMarks() != null) {
            markSheet.setTotalMarks(totalMark);
        }
        MarkSheet markSheet1 = markSheetRepository.save(markSheet);
        logger.info("mark sheet added successfully");
        return markSheet1;
    }

    @Override
    public MarkSheet getMarksById(Long marksId) throws RecordNotFoundException {
        MarkSheet markSheet = markSheetRepository.findByMarkIdAndIsActive(marksId,true);
        if(null == markSheet){
            throw new RecordNotFoundException("marks sheet not found with id:: " + marksId);
        }
        logger.info("Get marks sheet using id");
        return markSheet;
    }
}
