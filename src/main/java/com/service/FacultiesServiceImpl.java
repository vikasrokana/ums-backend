package com.service;

import com.Utility.AppUtils;
import com.controller.FacultiesController;
import com.exception.RecordNotFoundException;
import com.model.AssignSubject;
import com.model.Faculties;
import com.payload.request.AssignSubjectRequest;
import com.payload.request.FacultiesRequest;
import com.payload.response.FacultiesResponse;
import com.repository.AssignSubjectRepository;
import com.repository.FacultiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacultiesServiceImpl implements FacultiesService{
    @Autowired
    FacultiesRepository facultiesRepository;

    @Autowired
    AssignSubjectRepository assignSubjectRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacultiesController.class);
    @Override
    public Faculties addFaculties(FacultiesRequest facultiesRequest) {
       Faculties faculties = new Faculties();

       if(facultiesRequest.getId() != null){
           faculties = facultiesRepository.findByIdAndIsActive(facultiesRequest.getId(), true);
           faculties.setUpdatedOn(AppUtils.getCurrentIstTime());
       }else {
           faculties.setCreatedOn(AppUtils.getCurrentIstTime());
       }

        if(null != facultiesRequest.getFacultyName()) {
            faculties.setFacultyName(facultiesRequest.getFacultyName());
        }
        if(null != facultiesRequest.getState()) {
            faculties.setState(facultiesRequest.getState());
        }
        if(null != facultiesRequest.getCity()) {
            faculties.setCity(facultiesRequest.getCity());
        }
        if(null != facultiesRequest.getEmail()) {
            faculties.setEmail(facultiesRequest.getEmail());
        }
        if(null != facultiesRequest.getPhone()) {
            faculties.setPhone(facultiesRequest.getPhone());
        }
        if(null != facultiesRequest.getQualification()) {
            faculties.setQualification(facultiesRequest.getQualification());
        }
        if(null != facultiesRequest.getExperience()) {
            faculties.setExperience(facultiesRequest.getExperience());
        }
        if(null != facultiesRequest.getDob()) {
            faculties.setDob(facultiesRequest.getDob());
        }
        if(null != facultiesRequest.getGender()) {
            faculties.setGender(facultiesRequest.getGender());
        }
        if(null != facultiesRequest.getProfilePic()) {
            faculties.setProfilePic(facultiesRequest.getProfilePic());
        }
        if(null != facultiesRequest.getLastLogin()) {
            faculties.setLastLogin(facultiesRequest.getLastLogin());
        }
        if(null != facultiesRequest.getPassword()) {
            faculties.setPassword(facultiesRequest.getPassword());
        }
        if(null != facultiesRequest.getActiveStatus()) {
            faculties.setActiveStatus(facultiesRequest.getActiveStatus());
        }
        if(null != facultiesRequest.getJoinDate()) {
            faculties.setJoinDate(facultiesRequest.getJoinDate());
        }
        if(null != facultiesRequest.getAddress()) {
            faculties.setAddress(facultiesRequest.getAddress());
        }
        if(null != facultiesRequest.getPinCode()) {
            faculties.setPinCode(facultiesRequest.getPinCode());
        }
        Faculties faculties1 = facultiesRepository.save(faculties);
        return faculties1;

    }

    @Override
    public List<FacultiesResponse> getFacultiesList() throws RecordNotFoundException {
        List<FacultiesResponse> facultiesResponseList = new ArrayList<>();
        List<Faculties> facultiesList = facultiesRepository.findByIsActive(true);
        if(facultiesList.isEmpty()){
            throw new RecordNotFoundException("faculties list is not found");
        }
        for(Faculties faculties: facultiesList){
            FacultiesResponse facultiesResponse = new FacultiesResponse();
            facultiesResponse.setId(faculties.getId());
            facultiesResponse.setFacultyName(faculties.getFacultyName());
            facultiesResponse.setState(faculties.getState());
            facultiesResponse.setCity(faculties.getCity());
            facultiesResponse.setEmail(faculties.getEmail());
            facultiesResponse.setPhone(faculties.getPhone());
            facultiesResponse.setQualification(faculties.getQualification());
            facultiesResponse.setExperience(faculties.getExperience());
            facultiesResponse.setDob(faculties.getDob());
            facultiesResponse.setGender(faculties.getGender());
            facultiesResponse.setProfilePic(faculties.getProfilePic());
            facultiesResponse.setLastLogin(faculties.getLastLogin());
            facultiesResponse.setPassword(faculties.getPassword());
            facultiesResponse.setActiveStatus(faculties.getActiveStatus());
            facultiesResponse.setJoinDate(faculties.getJoinDate());
            facultiesResponse.setAddress(faculties.getAddress());
            facultiesResponse.setPinCode(faculties.getPinCode());
            facultiesResponse.setCreatedOn(faculties.getCreatedOn());
            facultiesResponse.setUpdatedOn(faculties.getUpdatedOn());
            facultiesResponseList.add(facultiesResponse);
        }
        logger.info("get faculties list");
        return facultiesResponseList;
    }

    @Override
    public Faculties getFacultyById(Long facultyId) throws RecordNotFoundException {
        Faculties faculties = facultiesRepository.findByIdAndIsActive(facultyId,true);
        if(null == faculties){
            throw new RecordNotFoundException("faculty not found with id:: " + facultyId);
        }
        logger.info("Get faculty using id");
        return faculties;
    }

    @Override
    public Boolean deleteFaculty(Long facultyId) {
        Integer isDeleted = facultiesRepository.deleteFaculty(facultyId);
        if( isDeleted != 0){
            logger.info("faculty deleted Successfully");
            return true;
        }
        return false;
    }

    @Override
    public AssignSubject assignSubject(AssignSubjectRequest assignSubjectRequest) {
        AssignSubject assignSubject = new AssignSubject();
        if(assignSubjectRequest.getId() != null){
            assignSubject = assignSubjectRepository.findByIdAndIsActive(assignSubjectRequest.getId(),true);
            assignSubject.setUpdatedOn(AppUtils.getCurrentIstTime());
        }else{
            assignSubject.setCreatedOn(AppUtils.getCurrentIstTime());
        }
        if(assignSubjectRequest.getSubjectCode() !=null){
            assignSubject.setSubjectCode(assignSubjectRequest.getSubjectCode());
        }
        if(assignSubjectRequest.getCourseCode()!=null){
            assignSubject.setCourseCode(assignSubjectRequest.getCourseCode());
        }
        if(assignSubjectRequest.getSemOrYear()!=null){
            assignSubject.setSemOrYear(assignSubjectRequest.getSemOrYear());
        }
        if(assignSubjectRequest.getFacultyId()!=null){
            assignSubject.setFacultyId(assignSubjectRequest.getFacultyId());
        }
        if(assignSubjectRequest.getPosition()!=null){
            assignSubject.setPosition(assignSubjectRequest.getPosition());
        }
        AssignSubject assignSubject1 = assignSubjectRepository.save(assignSubject);
        return assignSubject1;
    }
}

