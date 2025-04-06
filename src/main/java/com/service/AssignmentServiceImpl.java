package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.model.*;
import com.payload.response.AssignmentResponse;
import com.repository.AssignmentRepository;
import com.repository.FacultiesRepository;
import com.repository.StudentRepository;
import com.repository.SubjectRepository;
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


}
