package com.service;

import com.exception.RecordNotFoundException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.model.ExamFileRecord;
import com.model.Student;
import com.repository.ExamFileRecordRepository;
import com.repository.StudentRepository;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
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
    ExamFileRecordRepository examFileRecordRepository;

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
}
