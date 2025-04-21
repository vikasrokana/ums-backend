package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.model.*;
import com.payload.response.AssignmentResponse;
import com.payload.response.AssignmentSubmissionResponse;
import com.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService{

    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultiesRepository facultiesRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    AssignmentSubmissionRepository assignmentSubmissionRepository;

    private static final Logger logger = LoggerFactory.getLogger(AssignmentServiceImpl.class);
    @Override
    public Assignment uploadAssignment(Long id, Long courseId, Long subjectId, String title, String deadline,
                                       String section, Long marks, MultipartFile file, Long userId,String description) throws IOException {
        Faculties faculties = facultiesRepository.findByUserId(userId, true);
        if (faculties == null) {
            throw new IllegalArgumentException("Faculty not found for userId: " + userId);
        }

        Assignment assignment;
        if (id != null) {
            assignment = assignmentRepository.findByIdAndIsActive(id, true);
            if (assignment == null) {
                throw new IllegalArgumentException("Assignment not found with id: " + id);
            }
            assignment.setUpdatedBy(faculties.getId());
            assignment.setUpdatedOn(AppUtils.getCurrentIstTime());
        } else {
            assignment = new Assignment();
            assignment.setCreatedOn(AppUtils.getCurrentIstTime());
            assignment.setCreatedBy(userId);
        }

        // Save the uploaded file
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new IllegalArgumentException("Invalid file name.");
            }

            String filenameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = filenameWithoutExtension + "_" + timestamp + ".pdf";

            String storagePath = "C:\\Users\\User\\Documents\\upload\\";
            File dest = new File(storagePath + fileName);
            file.transferTo(dest);

            assignment.setAssignmentUrl(dest.getAbsolutePath());
        }

        // Set basic fields
        if (courseId != null) assignment.setCourseId(courseId);
        if (subjectId != null) assignment.setSubjectId(subjectId);
        if (title != null) assignment.setTitle(title);
        if (section != null) assignment.setSection(section);
        if (marks != null) assignment.setMarks(marks);
        if (deadline != null) assignment.setDeadline(deadline);
        if(description != null) assignment.setDescription(description);

        assignment.setFacultyId(faculties.getId());

        // Set today's date as startDate
        String strDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        assignment.setStartDate(strDate);

        return assignmentRepository.save(assignment);
    }

    @Override
    public List<AssignmentResponse> getAssignmentList(String role, Long userId, Integer pageNumber) {
        List<AssignmentResponse> assignmentResponseList = new ArrayList<>();
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        List<Assignment> assignmentList = new ArrayList<>();

        if (role.equalsIgnoreCase("faculty")) {
            assignmentList = assignmentRepository.findByUserId(userId, true);
        }

        if (role.equalsIgnoreCase("student")) {
            Student student = studentRepository.findByUserIdAndIsActive(userId, true);
            if (student != null) {
                List<Subject> subjectList = subjectRepository.findByCourseIdAndSem(
                        student.getCourseId(), student.getSemOrYear(), true, pageable
                );

                List<Long> subjectIds = subjectList.stream()
                        .map(Subject::getId)
                        .collect(Collectors.toList());

                if (!subjectIds.isEmpty()) {
                    assignmentList = assignmentRepository.findBySubjectIdInAndIsActive(subjectIds, true);
                }
            }
        }

        for (Assignment assignment : assignmentList) {
            AssignmentResponse assignmentResponse = new AssignmentResponse();
            assignmentResponse.setId(assignment.getId());
            assignmentResponse.setTitle(assignment.getTitle());
            assignmentResponse.setCourseId(assignment.getCourseId());
            assignmentResponse.setSubjectId(assignment.getSubjectId());
            assignmentResponse.setFacultyId(assignment.getFacultyId());
            assignmentResponse.setDescription(assignment.getDescription());
            assignmentResponse.setStartDate(assignment.getStartDate());
            assignmentResponse.setDeadline(assignment.getDeadline());
            assignmentResponse.setMarks(assignment.getMarks());
            assignmentResponse.setSection(assignment.getSection());
            assignmentResponse.setAssignmentUrl(assignment.getAssignmentUrl());
            assignmentResponse.setCreatedBy(assignment.getCreatedBy());
            assignmentResponse.setUpdatedBy(assignment.getUpdatedBy());
            assignmentResponse.setCreatedOn(assignment.getCreatedOn());
            assignmentResponseList.add(assignmentResponse);
        }

        return assignmentResponseList;
    }

    @Override
    public Boolean deleteAssignment(Long assignmentId) {
        Integer isDeleted = assignmentRepository.deleteAssignment(assignmentId);
        if( isDeleted != 0){
            logger.info("Assignment deleted Successfully");
            return true;
        }
        return false;
    }

    @Override
    public Assignment getAssignmentById(Long assignmentId) throws RecordNotFoundException {
        Assignment assignment = assignmentRepository.findByIdAndIsActive(assignmentId,true);
        if(null == assignment){
            throw new RecordNotFoundException("Assignment not found with id:: " + assignmentId);
        }
        logger.info("Get Assignment using id");
        return assignment;
    }

    @Override
    public AssignmentSubmission assignmentSubmission(Long id, Long assignmentId, MultipartFile file, Long userId) throws IOException {
        AssignmentSubmission assignmentSubmission;

        if (id != null) {
            // Update existing submission
            assignmentSubmission = assignmentSubmissionRepository.findByIdAndIsActive(id, true);
            if (assignmentSubmission == null) {
                throw new IllegalArgumentException("Assignment submission not found with id: " + id);
            }
            assignmentSubmission.setUpdatedBy(userId);
            assignmentSubmission.setUpdatedOn(AppUtils.getCurrentIstTime());
        } else {
            // Create new submission
            assignmentSubmission = new AssignmentSubmission();
            assignmentSubmission.setCreatedOn(AppUtils.getCurrentIstTime());
            assignmentSubmission.setIsActive(true);
        }

        // Save the uploaded file
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new IllegalArgumentException("Invalid file name.");
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf('.')); // e.g., ".pdf"
            String filenameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = filenameWithoutExtension + "_" + timestamp + extension;

            String storagePath = "C:\\Users\\User\\Documents\\upload\\";
            File dest = new File(storagePath + fileName);
            file.transferTo(dest);

            assignmentSubmission.setSubmissionUrl(dest.getAbsolutePath());
        }

        // Fetch student by userId
        Student student = studentRepository.findByUserIdAndIsActive(userId, true);
        if (student == null) {
            throw new IllegalArgumentException("Student not found for userId: " + userId);
        }

        assignmentSubmission.setStudentId(student.getId());

        if (assignmentId != null) {
            assignmentSubmission.setAssignmentId(assignmentId);
        }

        // Set today's date as submission timestamp
        String strDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        assignmentSubmission.setSubmittedOn(strDate);

        return assignmentSubmissionRepository.save(assignmentSubmission);
    }

    @Override
    public List<AssignmentSubmissionResponse> getAssignmentSubmissionList(String role, Long userId, Integer pageNumber, Long courseId, Long subjectId) {
        List<AssignmentSubmissionResponse> assignmentSubmissionResponseList = new ArrayList<>();
        Pageable pageable = AppUtils.getPageRange(pageNumber);

        if ("faculty".equalsIgnoreCase(role)) {
            List<Assignment> assignmentList = assignmentRepository.findByUserIdSubjectIdCourseId(userId,courseId,subjectId, true);
            for (Assignment assignment : assignmentList) {
                List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findAllByAssignmentIdAndIsActive(assignment.getId(), true, pageable);
                for (AssignmentSubmission submission : submissions) {
                    AssignmentSubmissionResponse response = new AssignmentSubmissionResponse();
                    response.setId(submission.getId());
                    response.setAssignmentId(submission.getAssignmentId());
                    response.setStudentId(submission.getStudentId());
                    response.setSubmissionUrl(submission.getSubmissionUrl());
                    response.setRemarks(submission.getRemarks());
                    response.setObtainedMarks(submission.getObtainedMarks());
                    response.setSubmittedOn(submission.getSubmittedOn());
                    response.setIsLateSubmission(submission.getIsLateSubmission());
                    response.setEvaluatedBy(submission.getEvaluatedBy());
                    response.setEvaluatedOn(submission.getEvaluatedOn());
                    response.setUpdatedBy(submission.getUpdatedBy());
                    response.setCreatedOn(submission.getCreatedOn());
                    response.setUpdatedOn(submission.getUpdatedOn());

                    assignmentSubmissionResponseList.add(response);
                }
            }
        }
        // You can add "student" role handling here if needed.

        return assignmentSubmissionResponseList;
    }

    @Override
    public List<AssignmentSubmissionResponse> getAssignmentSubmissionStudentList(String role, Long userId, Integer pageNumber, Long subjectId) {
        List<AssignmentSubmissionResponse> assignmentSubmissionResponseList = new ArrayList<>();
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        Student student = studentRepository.findByUserIdAndIsActive(userId,true);
        if ("student".equalsIgnoreCase(role)) {
            List<Assignment> assignmentList = assignmentRepository.findByCourseAndSubjectId(student.getCourseId(),subjectId, true);
            for (Assignment assignment : assignmentList) {
                List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findAllByAssignmentIdAndIsActive(assignment.getId(), true, pageable);
                for (AssignmentSubmission submission : submissions) {
                    AssignmentSubmissionResponse response = new AssignmentSubmissionResponse();
                    response.setId(submission.getId());
                    response.setAssignmentId(submission.getAssignmentId());
                    response.setStudentId(submission.getStudentId());
                    response.setSubmissionUrl(submission.getSubmissionUrl());
                    response.setRemarks(submission.getRemarks());
                    response.setObtainedMarks(submission.getObtainedMarks());
                    response.setSubmittedOn(submission.getSubmittedOn());
                    response.setIsLateSubmission(submission.getIsLateSubmission());
                    response.setEvaluatedBy(submission.getEvaluatedBy());
                    response.setEvaluatedOn(submission.getEvaluatedOn());
                    response.setUpdatedBy(submission.getUpdatedBy());
                    response.setCreatedOn(submission.getCreatedOn());
                    response.setUpdatedOn(submission.getUpdatedOn());

                    assignmentSubmissionResponseList.add(response);
                }
            }
        }
        return assignmentSubmissionResponseList;
    }

    @Override
    public AssignmentSubmission getSubmissionAssignmentById(Long subAssignmentId) throws RecordNotFoundException {
        AssignmentSubmission assignment = assignmentSubmissionRepository.findByIdAndIsActive(subAssignmentId,true);
        if(null == assignment){
            throw new RecordNotFoundException("Assignment not found with id:: " + subAssignmentId);
        }
        logger.info("Get Assignment using id");
        return assignment;
    }

    @Override
    public Boolean deleteSubAssignment(Long subAssignmentId) {
        Integer isDeleted = assignmentSubmissionRepository.deleteAssignment(subAssignmentId);
        if( isDeleted != 0){
            logger.info("submitted Assignment deleted Successfully");
            return true;
        }
        return false;
    }


}
