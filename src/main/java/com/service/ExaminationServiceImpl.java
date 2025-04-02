package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.model.Course;
import com.model.Examination;
import com.model.Faculties;
import com.model.Student;
import com.payload.request.ExaminationRequest;
import com.payload.response.ExaminationResponse;
import com.repository.ExaminationRepository;
import com.repository.FacultiesRepository;
import com.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExaminationServiceImpl implements ExaminationService{

    @Autowired
    ExaminationRepository examinationRepository;
    @Autowired
    FacultiesRepository facultiesRepository;

    @Autowired
    AppUtils appUtils;

    private static final Logger logger = LoggerFactory.getLogger(ExaminationServiceImpl.class);
    @Override
    public Examination addExamination(ExaminationRequest examinationRequest, Long userId) {
        Examination examination = new Examination();

        if (examinationRequest.getId() != null) {
            examination = examinationRepository.findById(examinationRequest.getId()).orElse(new Examination());
            examination.setUpdatedOn(AppUtils.getCurrentIstTime());
            examination.setUpdatedBy(userId);
        } else {
            examination.setCreatedOn(AppUtils.getCurrentIstTime());
            examination.setCreatedBy(userId);
        }

        if (examinationRequest.getExamName() != null) {
            examination.setExamName(examinationRequest.getExamName());
        }
        if (examinationRequest.getCourseId() != null) {
            examination.setCourseId(examinationRequest.getCourseId());
        }
        if (examinationRequest.getSubjectId() != null) {
            examination.setSubjectId(examinationRequest.getSubjectId());
        }
        if (examinationRequest.getFacultyId() != null) {
            examination.setFacultyId(examinationRequest.getFacultyId());
        }
        if (examinationRequest.getDate() != null) {
            examination.setDate(examinationRequest.getDate());
        }
        if (examinationRequest.getTime() != null) {
            examination.setTime(examinationRequest.getTime());
        }
        if (examinationRequest.getDuration() != null) {
            examination.setDuration(examinationRequest.getDuration());
        }
        if (examinationRequest.getRoom() != null) {
            examination.setRoom(examinationRequest.getRoom());
        }
        if (examinationRequest.getStatus() != null) {
            examination.setStatus(examinationRequest.getStatus());
        }
        if (examinationRequest.getTotalQuestions() != null) {
            examination.setTotalQuestions(examinationRequest.getTotalQuestions());
        }

        Examination savedExamination = examinationRepository.save(examination);
        logger.info("Examination added successfully");

        return savedExamination;
    }

    @Override
    public List<ExaminationResponse> getExaminationList(String role, Long userId, Integer pageNumber) {
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        List<Examination> examinationList = new ArrayList<>();

        switch (role.toLowerCase()) {
            case "admin":
                examinationList = examinationRepository.findByIsActive(true, pageable);
                break;
            case "faculty":
                Faculties faculties = facultiesRepository.findByUserId(userId, true);
                if (faculties == null) {
                    logger.warn("No faculty found for userId: " + userId);
                    return Collections.emptyList();
                }
                examinationList = examinationRepository.findByFacultyId(faculties.getId(), true);
                break;
            default:
                logger.warn("Invalid role: " + role);
                return Collections.emptyList();
        }

        if (examinationList.isEmpty()) {
            logger.warn("No examinations found for role: " + role + ", userId: " + userId);
            return Collections.emptyList();
        }

        List<ExaminationResponse> examinationResponseList = examinationList.stream().map(exam -> {
            ExaminationResponse response = new ExaminationResponse();
            response.setId(exam.getId());
            response.setExamName(exam.getExamName());
            response.setCourseId(exam.getCourseId());
            response.setSubjectId(exam.getSubjectId());
            response.setFacultyId(exam.getFacultyId());
            response.setDate(exam.getDate());
            response.setTime(exam.getTime());
            response.setDuration(exam.getDuration());
            response.setRoom(exam.getRoom());
            response.setStatus(exam.getStatus());
            response.setTotalQuestions(exam.getTotalQuestions());
            response.setCreatedBy(exam.getCreatedBy());
            response.setUpdatedBy(exam.getUpdatedBy());
            response.setCreatedOn(exam.getCreatedOn());
            response.setUpdatedOn(exam.getUpdatedOn());
            response.setIsActive(exam.getIsActive());
            return response;
        }).collect(Collectors.toList());

        logger.info("Successfully retrieved " + examinationResponseList.size() + " examinations.");
        return examinationResponseList;
    }


    @Override
    public Boolean deleteExamination(Long examinationId) {
        Integer isDeleted = examinationRepository.deleteExaminations(examinationId);
        if( isDeleted != 0){
            logger.info("examination deleted Successfully");
            return true;
        }
        return false;
    }

    @Override
    public Examination getExaminationById(Long examId) throws RecordNotFoundException {
        Examination examination = examinationRepository.findByIdAndIsActive(examId,true);
        if(null == examination){
            throw new RecordNotFoundException("course not found with id:: " + examId);
        }
        logger.info("Get Course using id");
        return examination;
    }
}
