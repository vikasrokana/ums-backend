package com.service;

import com.Utility.AppUtils;
import com.controller.SubjectController;
import com.exception.RecordNotFoundException;
import com.model.Student;
import com.model.Subject;
import com.payload.request.SubjectRequest;
import com.payload.response.SubjectResponse;
import com.repository.StudentRepository;
import com.repository.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService{

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    StudentRepository studentRepository;

    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);
    @Override
    public Subject addSubject(SubjectRequest subjectRequest,Long userId) {
        Subject subject = new Subject();

        // Check if the subjectRequest has an ID (update existing subject or create new)
        if (subjectRequest.getId() != null) {
            subject = subjectRepository.findById(subjectRequest.getId()).orElse(new Subject());
            subject.setUpdatedOn(AppUtils.getCurrentIstTime());  // Set updated time
            subject.setUpdatedBy(userId);
        } else {
            subject.setCreatedOn(AppUtils.getCurrentIstTime());  // Set created time
            subject.setCreatedBy(userId);
        }

        // Set or update fields from subjectRequest to subject entity
        if (null != subjectRequest.getCourseId()) {
            subject.setCourseId(subjectRequest.getCourseId());
        }
        if(null != subjectRequest.getSubjectName()){
            subject.setSubjectName(subjectRequest.getSubjectName());
        }
        if (null != subjectRequest.getSubjectCode()) {
            subject.setSubjectCode(subjectRequest.getSubjectCode());
        }
        if (null != subjectRequest.getSemOrYear()) {
            subject.setSemOrYear(subjectRequest.getSemOrYear());
        }
        if (null != subjectRequest.getSubjectType()) {
            subject.setSubjectType(subjectRequest.getSubjectType());
        }
        if (null != subjectRequest.getTheoryMarks()) {
            subject.setTheoryMarks(subjectRequest.getTheoryMarks());
        }
        if (null != subjectRequest.getPracticalMarks()) {
            subject.setPracticalMarks(subjectRequest.getPracticalMarks());
        }
        if (null != subjectRequest.getDescription()) {
            subject.setDescription(subjectRequest.getDescription());
        }
        if (null != subjectRequest.getStatus()) {
            subject.setStatus(subjectRequest.getStatus());
        }
        if (null != subjectRequest.getSyllabus()) {
            subject.setSyllabus(subjectRequest.getSyllabus());
        }

        // Save the subject entity to the database
        Subject savedSubject = subjectRepository.save(subject);

        // Log the successful addition of the subject
        logger.info("Subject added successfully");

        return savedSubject;
    }

    @Override
    public List<SubjectResponse> getSubjectList() throws RecordNotFoundException {
        List<SubjectResponse> subjectResponseList = new ArrayList<>();
        List<Subject> subjectList = subjectRepository.findByIsActive(true);
        if(subjectList.isEmpty()){
            throw new RecordNotFoundException("subject list is not found");
        }
        for(Subject subject: subjectList){
            SubjectResponse subjectResponse = new SubjectResponse();
            subjectResponse.setId(subject.getId());
            subjectResponse.setCourseId(subject.getCourseId());
            subjectResponse.setSubjectName(subject.getSubjectName());
            subjectResponse.setSubjectCode(subject.getSubjectCode());
            subjectResponse.setSemOrYear(subject.getSemOrYear());
            subjectResponse.setSubjectType(subject.getSubjectType());
            subjectResponse.setTheoryMarks(subject.getTheoryMarks());
            subjectResponse.setPracticalMarks(subject.getPracticalMarks());
            subjectResponse.setDescription(subject.getDescription());
            subjectResponse.setStatus(subject.getStatus());
            subjectResponse.setSyllabus(subject.getSyllabus());
            subjectResponse.setCreatedOn(subject.getCreatedOn());
            subjectResponse.setUpdatedOn(subject.getUpdatedOn());
            subjectResponse.setIsActive(subject.getIsActive());
            subjectResponseList.add(subjectResponse);
        }
        logger.info("get subject list");
        return subjectResponseList;
    }

    @Override
    public Boolean deleteSubject(Long subjectId) {
        Integer isDeleted = subjectRepository.deleteSubject(subjectId);
        if( isDeleted != 0){
            logger.info("subject deleted Successfully");
            return true;
        }
        return false;
    }

    @Override
    public Subject getSubjectById(Long subjectId) throws RecordNotFoundException {
        Subject subject = subjectRepository.findByIdAndIsActive(subjectId,true);
        if(null == subject){
            throw new RecordNotFoundException("subject not found with id:: " + subjectId);
        }
        logger.info("Get subject using id");
        return subject;
    }

    @Override
    public List<SubjectResponse> getSubjectByCourseId(Long courseId) {
       List<SubjectResponse> subjectResponseList = new ArrayList<>();
       List<Subject> subjectList = subjectRepository.findByCourseId(courseId,true);
       for(Subject subject: subjectList){
           SubjectResponse subjectResponse = new SubjectResponse();
           subjectResponse.setId(subject.getId());
           subjectResponse.setCourseId(subject.getCourseId());
           subjectResponse.setSubjectName(subject.getSubjectName());
           subjectResponse.setSubjectCode(subject.getSubjectCode());
           subjectResponse.setSemOrYear(subject.getSemOrYear());
           subjectResponse.setSubjectType(subject.getSubjectType());
           subjectResponse.setTheoryMarks(subject.getTheoryMarks());
           subjectResponse.setPracticalMarks(subject.getPracticalMarks());
           subjectResponse.setDescription(subject.getDescription());
           subjectResponse.setStatus(subject.getStatus());
           subjectResponse.setSyllabus(subject.getSyllabus());
           subjectResponse.setCreatedOn(subject.getCreatedOn());
           subjectResponse.setUpdatedOn(subject.getUpdatedOn());
           subjectResponse.setIsActive(subject.getIsActive());
           subjectResponseList.add(subjectResponse);
       }
        logger.info("Get subject using course Id");
        return subjectResponseList;
    }

    @Override
    public List<SubjectResponse> getAssignedSubjectOfStudent(Long userId) {
        Student student = studentRepository.findByUserIdAndIsActive(userId,true);
        List<SubjectResponse> subjectResponseList = new ArrayList<>();
        List<Subject> subjectList = subjectRepository.findByCourseIdAndSem(student.getCourseId(),student.getSemOrYear(), true);
        for(Subject subject: subjectList){
            SubjectResponse subjectResponse = new SubjectResponse();
            subjectResponse.setId(subject.getId());
            subjectResponse.setCourseId(subject.getCourseId());
            subjectResponse.setSubjectName(subject.getSubjectName());
            subjectResponse.setSubjectCode(subject.getSubjectCode());
            subjectResponse.setSemOrYear(subject.getSemOrYear());
            subjectResponse.setSubjectType(subject.getSubjectType());
            subjectResponse.setTheoryMarks(subject.getTheoryMarks());
            subjectResponse.setPracticalMarks(subject.getPracticalMarks());
            subjectResponse.setDescription(subject.getDescription());
            subjectResponse.setStatus(subject.getStatus());
            subjectResponse.setSyllabus(subject.getSyllabus());
            subjectResponse.setCreatedOn(subject.getCreatedOn());
            subjectResponse.setUpdatedOn(subject.getUpdatedOn());
            subjectResponse.setIsActive(subject.getIsActive());
            subjectResponseList.add(subjectResponse);
        }
        logger.info("get subject using course id and subject id");
        return subjectResponseList;
    }


}



