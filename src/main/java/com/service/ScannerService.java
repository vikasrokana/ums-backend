package com.service;

import com.exception.RecordNotFoundException;
import com.lowagie.text.DocumentException;
import com.model.ExamFileRecord;
import com.model.Student;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ScannerService {

    String scanFileAndGeneratePDF(File convFile) throws FileNotFoundException, DocumentException, TesseractException;

    String scanAndGeneratePDF(PDDocument document, MultipartFile file) throws IOException;

    ExamFileRecord saveStudentFile(MultipartFile file) throws IOException;

    List<ExamFileRecord> getExamFileList() throws RecordNotFoundException;
}
