package com.service;

import com.Utility.AppUtils;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FacultiesServiceImpl implements FacultiesService{
    @Autowired
    FacultiesRepository facultiesRepository;

    @Autowired
    AssignSubjectRepository assignSubjectRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacultiesServiceImpl.class);
    @Override
    public Faculties addFaculties(FacultiesRequest facultiesRequest,Long userId) {
       Faculties faculties = new Faculties();

       if(facultiesRequest.getId() != null){
           faculties = facultiesRepository.findByIdAndIsActive(facultiesRequest.getId(), true);
           faculties.setUpdatedOn(AppUtils.getCurrentIstTime());
           faculties.setUpdatedBy(userId);
       }else {
           faculties.setCreatedOn(AppUtils.getCurrentIstTime());
           faculties.setCreatedBy(userId);
       }
        if(null != facultiesRequest.getFacultyName()) {
            faculties.setFacultyName(facultiesRequest.getFacultyName());
        }
        if(null != facultiesRequest.getPosition()){
            faculties.setPosition(facultiesRequest.getPosition());
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
    public List<FacultiesResponse> getFacultiesList(Integer pageNumber) throws RecordNotFoundException {
        List<FacultiesResponse> facultiesResponseList = new ArrayList<>();
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        List<Faculties> facultiesList = facultiesRepository.findByIsActive(true, pageable);
        if(facultiesList.isEmpty()){
//            throw new RecordNotFoundException("faculties list is not found");
            logger.warn("faculties list is not found");
            return new ArrayList<>();
        }
        for(Faculties faculties: facultiesList){
            FacultiesResponse facultiesResponse = new FacultiesResponse();
            facultiesResponse.setId(faculties.getId());
            facultiesResponse.setFacultyName(faculties.getFacultyName());
            facultiesResponse.setPosition(faculties.getPosition());
            facultiesResponse.setEmail(faculties.getEmail());
            facultiesResponse.setPhone(faculties.getPhone());
            facultiesResponse.setQualification(faculties.getQualification());
            facultiesResponse.setExperience(faculties.getExperience());
            facultiesResponse.setDob(faculties.getDob());
            facultiesResponse.setGender(faculties.getGender());
            facultiesResponse.setProfilePic(faculties.getProfilePic());
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
    public AssignSubject assignSubject(AssignSubjectRequest assignSubjectRequest,Long userId) {
        AssignSubject assignSubject = new AssignSubject();
        if(assignSubjectRequest.getId() != null){
            assignSubject = assignSubjectRepository.findByIdAndIsActive(assignSubjectRequest.getId(),true);
            assignSubject.setUpdatedOn(AppUtils.getCurrentIstTime());
            assignSubject.setUpdatedBy(userId);
        }else{
            assignSubject.setCreatedOn(AppUtils.getCurrentIstTime());
            assignSubject.setCreatedBy(userId);
        }
        if(assignSubjectRequest.getCourseId() !=null){
            assignSubject.setCourseId(assignSubjectRequest.getCourseId());
        }
        if(assignSubjectRequest.getSubjectId() != null){
            assignSubject.setSubjectId(assignSubjectRequest.getSubjectId());
        }
        if(assignSubjectRequest.getFacultyId() != null){
            assignSubject.setFacultyId(assignSubjectRequest.getFacultyId());
        }
        assignSubject.setDate(AppUtils.currentDate());
        AssignSubject assignSubject1 = assignSubjectRepository.save(assignSubject);
        return assignSubject1;
    }

    @Override
    public Faculties getFacultyDetails(Long userId) throws RecordNotFoundException {
        Faculties faculties = facultiesRepository.findByUserId(userId,true);
        if(null == faculties){
            throw new RecordNotFoundException("faculty details not found with id:: " + userId);
        }
        logger.info("Get faculty details id");
        return faculties;
    }

    @Override
    public List<FacultiesResponse> getFacultiesListBySubjectId(Long subjectId, Integer pageNumber) {
        List<FacultiesResponse> facultiesResponseList = new ArrayList<>();
        Pageable pageable = AppUtils.getPageRange(pageNumber);

        List<AssignSubject> assignSubjectList = assignSubjectRepository.findBySubjectId(subjectId, true);
        if (assignSubjectList.isEmpty()) {
            logger.info("No faculty assigned for subject ID: " + subjectId);
            return new ArrayList<>();
        }

        List<Long> facultyIds = assignSubjectList.stream()
                .map(AssignSubject::getFacultyId)
                .collect(Collectors.toList());

        List<Faculties> facultiesList = facultiesRepository.findByIdInAndIsActive(facultyIds, true);
        if (facultiesList.isEmpty()) {
            logger.info("No active faculties found for subject ID: " + subjectId);
            return new ArrayList<>();
        }

        for (Faculties faculty : facultiesList) {
            if (faculty == null) continue;  // Avoid NullPointerException

            FacultiesResponse facultiesResponse = new FacultiesResponse();
            facultiesResponse.setId(faculty.getId());
            facultiesResponse.setFacultyName(faculty.getFacultyName());
            facultiesResponse.setPosition(faculty.getPosition());
            facultiesResponse.setEmail(faculty.getEmail());
            facultiesResponse.setPhone(faculty.getPhone());
            facultiesResponse.setQualification(faculty.getQualification());
            facultiesResponse.setExperience(faculty.getExperience());
            facultiesResponse.setDob(faculty.getDob());
            facultiesResponse.setGender(faculty.getGender());
            facultiesResponse.setProfilePic(faculty.getProfilePic());
            facultiesResponse.setJoinDate(faculty.getJoinDate());
            facultiesResponse.setAddress(faculty.getAddress());
            facultiesResponse.setPinCode(faculty.getPinCode());
            facultiesResponse.setCreatedOn(faculty.getCreatedOn());
            facultiesResponse.setUpdatedOn(faculty.getUpdatedOn());

            facultiesResponseList.add(facultiesResponse);
        }

        logger.info("Successfully fetched faculty list for subject ID: " + subjectId);
        return facultiesResponseList;
    }

}

