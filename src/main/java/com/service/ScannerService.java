package com.service;

import com.exception.RecordNotFoundException;
import com.model.ExamFileRecord;
import com.model.MarkSheet;
import com.payload.request.MarkSheetRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface ScannerService {

    ExamFileRecord saveStudentFile(Long courseId, Long subjectId, MultipartFile file) throws IOException;

    List<ExamFileRecord> getExamFileList(Long courseId, Long subjectId) throws RecordNotFoundException;

    ExamFileRecord getExamSheetById(Long sheetId) throws RecordNotFoundException;

    MarkSheet addMarks(MarkSheetRequest markSheetRequest, Long userId);

    MarkSheet getMarksById(Long marksId) throws RecordNotFoundException;
}
