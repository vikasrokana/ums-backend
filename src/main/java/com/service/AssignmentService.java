package com.service;

import com.model.Assignment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AssignmentService {
    Assignment uploadAssignment(Long id, Long courseId, Long subjectId, String title, String deadline, String section, Long marks, MultipartFile file, Long userId) throws IOException;
}
