package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.model.ClassSchedule;
import com.payload.request.ClassScheduleRequest;
import com.payload.response.ClassScheduleResponse;
import com.repository.ClassScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassScheduleServiceImpl implements ClassScheduleService{
    @Autowired
    ClassScheduleRepository classScheduleRepository;
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Override
    public ClassSchedule addClassSchedule(ClassScheduleRequest classScheduleRequest, Long userId) {
        ClassSchedule classSchedule = new ClassSchedule();
        if(classScheduleRequest.getId() != null){
            classSchedule = classScheduleRepository.findById(classScheduleRequest.getId()).get();
            classSchedule.setUpdatedOn(AppUtils.getCurrentIstTime());
            classSchedule.setUpdatedBy(userId);
        }
        else{
            classSchedule.setCreatedOn(AppUtils.getCurrentIstTime());
            classSchedule.setCreatedBy(userId);
        }
        if(null != classScheduleRequest.getCourseId()){
            classSchedule.setCourseId(classScheduleRequest.getCourseId());
        }
        if(null != classScheduleRequest.getSubjectId()){
            classSchedule.setSubjectId(classScheduleRequest.getSubjectId());
        }
        if(null != classScheduleRequest.getFacultyId()){
            classSchedule.setFacultyId(classScheduleRequest.getFacultyId());
        }
        if(null != classScheduleRequest.getStartTime()){
            classSchedule.setStartTime(classScheduleRequest.getStartTime());

        }
        if(null != classScheduleRequest.getEndTime()){
            classSchedule.setEndTime(classScheduleRequest.getEndTime());

        }
        if(null != classScheduleRequest.getDay()){
            classSchedule.setDay(classScheduleRequest.getDay());

        }
        if(null != classScheduleRequest.getRoomNo()){
            classSchedule.setRoomNo(classScheduleRequest.getRoomNo());

        }


        ClassSchedule classSchedule1 = classScheduleRepository.save(classSchedule);
        logger.info("course added successfully");
        return classSchedule1;
    }

    @Override
    public List<ClassScheduleResponse> getClassSchedule(Integer pageNumber) throws RecordNotFoundException {
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        List<ClassScheduleResponse> ClassScheduleResponseList = new ArrayList<>();
        List<ClassSchedule> classScheduleList = classScheduleRepository.findByIsActive(true, pageable);
        if(classScheduleList.isEmpty()){
//          throw new RecordNotFoundException("course list is not found");
            logger.warn("class schedule list is not found");
            return new ArrayList<>();
        }
        for(ClassSchedule classSchedule: classScheduleList){
            ClassScheduleResponse classScheduleResponse= new ClassScheduleResponse();
            classScheduleResponse.setId(classSchedule.getId());
            classScheduleResponse.setCourseId(classSchedule.getCourseId());
            classScheduleResponse.setSubjectId(classSchedule.getSubjectId());
            classScheduleResponse.setFacultyId(classSchedule.getFacultyId());
            classScheduleResponse.setStartTime(classSchedule.getStartTime());
            classScheduleResponse.setEndTime(classSchedule.getEndTime());
            classScheduleResponse.setDay(classSchedule.getDay());
            classScheduleResponse.setRoomNo(classSchedule.getRoomNo());
            ClassScheduleResponseList.add(classScheduleResponse);
        }
        logger.info("get class schedule list");
        return ClassScheduleResponseList;
    }

    @Override
    public Boolean deleteClassSchedule(Long classScheduleId) {
        Integer isDeleted = classScheduleRepository.deleteClassSchedule(classScheduleId);
        if( isDeleted != 0){
            logger.info("class schedule deleted Successfully");
            return true;
        }
        return false;
    }

}
