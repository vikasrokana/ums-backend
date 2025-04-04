package com.service;

import com.Utility.AppUtils;
import com.model.Assignment;
import com.model.Faculties;
import com.repository.AssignmentRepository;
import com.repository.FacultiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AssignmentServiceImpl implements AssignmentService{

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    FacultiesRepository facultiesRepository;
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

}
