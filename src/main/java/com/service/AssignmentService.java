package com.service;

import com.exception.RecordNotFoundException;
import com.model.Assignment;
import com.model.AssignmentSubmission;
import com.payload.response.AssignmentResponse;
import com.payload.response.AssignmentSubmissionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AssignmentService {
    Assignment uploadAssignment(Long id, Long courseId, Long subjectId, String title, String deadline, String section, Long marks, MultipartFile file, Long userId, String description) throws IOException;

    List<AssignmentResponse> getAssignmentList(String role, Long userId, Integer pageNumber);

    Boolean deleteAssignment(Long assignmentId);

    Assignment getAssignmentById(Long assignmentId) throws RecordNotFoundException;

    AssignmentSubmission assignmentSubmission(Long id, Long assignmentId, MultipartFile file, Long userId) throws IOException;

    List<AssignmentSubmissionResponse> getAssignmentSubmissionList(String role, Long userId, Integer pageNumber,Long courseId, Long subjectId);

    List<AssignmentSubmissionResponse> getAssignmentSubmissionStudentList(String role, Long userId, Integer pageNumber, Long subjectId);
}
