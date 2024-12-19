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
    ExamFileRecordRepository examFileRecordRepository;
    private static final Logger logger = LoggerFactory.getLogger(ScannerServiceImpl.class);

    @Override
    public String scanFileAndGeneratePDF(File convFile) throws FileNotFoundException, DocumentException, TesseractException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
        tesseract.setLanguage("eng");
        String resultText = tesseract.doOCR(convFile);
        System.out.println("OCR Result: " + resultText);

        // Step 2: Convert the text to PDF
        Document document = new Document();
        String pdfFilePath = "C:\\Users\\User\\Documents\\output.pdf";  // Define your local storage path
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
        document.open();

        // Add text to PDF with proper encoding
        Font font = FontFactory.getFont(FontFactory.HELVETICA,BaseFont.WINANSI, BaseFont.EMBEDDED);
        document.add(new Paragraph(resultText, font));

        document.close();
        writer.close();

        // Clean up the temporary file
        convFile.delete();

        // Return the path to the saved PDF
        return pdfFilePath;
    }

    @Override
    public String scanAndGeneratePDF(PDDocument document, MultipartFile file) throws IOException {

        // Load the JPG image
        PDImageXObject image = PDImageXObject.createFromByteArray(document, file.getBytes(), null);
        PDPage page = document.getPage(0);
        // Add the image to the PDF
        // Get the dimensions of the image
        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();

        // Get the dimensions of the PDF page
        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();

        // Calculate the scaling factor to fit the image within the page
        float scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);

        // Calculate the new width and height of the image
        float scaledWidth = imageWidth * scale;
        float scaledHeight = imageHeight * scale;

        // Center the image on the page
        float xOffset = (pageWidth - scaledWidth) / 2;
        float yOffset = (pageHeight - scaledHeight) / 2;

        // Add the image to the PDF with scaling
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(image, xOffset, yOffset, scaledWidth, scaledHeight);
        contentStream.close();

        // Get the current date and format it
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Define the file name and path
        String fileName = "converted_" + date + ".pdf";
        String filePath =  "C:\\Users\\User\\Documents\\" + fileName; // Update this path as per your local storage location

        // Save the PDF to local storage
        document.save(new File(filePath));
        document.close();

        return filePath;
    }

    @Override
    public ExamFileRecord saveStudentFile(MultipartFile file) throws IOException {
        String uniqueNumber = UUID.randomUUID().toString();
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
        return examFileRecordRepository.save(examFileRecord);

    }

    @Override
    public List<ExamFileRecord> getExamFileList() throws RecordNotFoundException {
        List<ExamFileRecord> examFileRecordList;
        examFileRecordList = examFileRecordRepository.getExamFileList(true);
        if (examFileRecordList == null || examFileRecordList.isEmpty()) {
            throw new RecordNotFoundException("exam file not found with found");
        }
        return examFileRecordList;
    }

    @Override
    public ExamFileRecord getExamSheetById(Long sheetId) throws RecordNotFoundException {
        ExamFileRecord examFileRecord = examFileRecordRepository.findByIdAndIsActive(sheetId,true);
        if(null == examFileRecord){
            throw new RecordNotFoundException("exam sheet not found with id:: " + sheetId);
        }
        logger.info("Get exam sheet using id");
        return examFileRecord;
    }

    @Override
    public MarkSheet addMarks(MarkSheetRequest markSheetRequest, Long userId) {
        MarkSheet markSheet = new MarkSheet();
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
        if (markSheetRequest.getSubjectCode() != null) {
            markSheet.setSubjectCode(markSheetRequest.getSubjectCode());
        }
        if (markSheetRequest.getQ1() != null) {
            markSheet.setQ1(markSheetRequest.getQ1());
        }
        if (markSheetRequest.getQ2() != null) {
            markSheet.setQ2(markSheetRequest.getQ2());
        }
        if (markSheetRequest.getQ3() != null) {
            markSheet.setQ3(markSheetRequest.getQ3());
        }
        if (markSheetRequest.getQ4() != null) {
            markSheet.setQ4(markSheetRequest.getQ4());
        }
        if (markSheetRequest.getQ5() != null) {
            markSheet.setQ5(markSheetRequest.getQ5());
        }
        if (markSheetRequest.getQ6() != null) {
            markSheet.setQ6(markSheetRequest.getQ6());
        }
        if (markSheetRequest.getQ7() != null) {
            markSheet.setQ7(markSheetRequest.getQ7());
        }
        if (markSheetRequest.getQ8() != null) {
            markSheet.setQ8(markSheetRequest.getQ8());
        }
        if (markSheetRequest.getQ9() != null) {
            markSheet.setQ9(markSheetRequest.getQ9());
        }
        if (markSheetRequest.getQ10() != null) {
            markSheet.setQ10(markSheetRequest.getQ10());
        }
        if (markSheetRequest.getTotalMarks() != null) {
            markSheet.setTotalMarks(markSheetRequest.getTotalMarks());
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
