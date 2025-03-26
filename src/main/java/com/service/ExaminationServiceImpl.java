package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.model.Course;
import com.model.Examination;
import com.payload.request.ExaminationRequest;
import com.payload.response.ExaminationResponse;
import com.repository.ExaminationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExaminationServiceImpl implements ExaminationService{

    @Autowired
    ExaminationRepository examinationRepository;

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
    public List<ExaminationResponse> getExaminationList(Integer pageNumber) {
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        List<ExaminationResponse> examinationResponseList = new ArrayList<>();
        List<Examination> examinationList = examinationRepository.findByIsActive(true, pageable);
        if (examinationList.isEmpty()) {
            logger.warn("Examination list is empty");
            return new ArrayList<>();
        }

        for (Examination examination : examinationList) {
            ExaminationResponse examinationResponse = new ExaminationResponse();

            examinationResponse.setId(examination.getId());
            examinationResponse.setExamName(examination.getExamName());
            examinationResponse.setCourseId(examination.getCourseId());
            examinationResponse.setSubjectId(examination.getSubjectId());
            examinationResponse.setFacultyId(examination.getFacultyId());
            examinationResponse.setDate(examination.getDate());
            examinationResponse.setTime(examination.getTime());
            examinationResponse.setDuration(examination.getDuration());
            examinationResponse.setRoom(examination.getRoom());
            examinationResponse.setStatus(examination.getStatus());
            examinationResponse.setTotalQuestions(examination.getTotalQuestions());
            examinationResponse.setCreatedBy(examination.getCreatedBy());
            examinationResponse.setUpdatedBy(examination.getUpdatedBy());
            examinationResponse.setCreatedOn(examination.getCreatedOn());
            examinationResponse.setUpdatedOn(examination.getUpdatedOn());
            examinationResponse.setIsActive(examination.getIsActive());

            examinationResponseList.add(examinationResponse);
        }
        logger.info("get examination");
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
