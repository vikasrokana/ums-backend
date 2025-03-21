package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.model.*;
import com.payload.request.ClassScheduleRequest;
import com.payload.response.ClassScheduleResponse;
import com.repository.*;
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
public class ClassScheduleServiceImpl implements ClassScheduleService{
    @Autowired
    ClassScheduleRepository classScheduleRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultiesRepository facultiesRepository;
    @Autowired
    SubjectRepository subjectRepository;
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
    public List<ClassScheduleResponse> getClassSchedule(Long userId, String role, Integer pageNumber) throws RecordNotFoundException {
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        List<ClassScheduleResponse> classScheduleResponseList;

        List<ClassSchedule> classScheduleList;

        if ("student".equals(role)) {
            Student student = studentRepository.findByUserIdAndIsActive(userId, true);
            List<Subject> subjectList = subjectRepository.findByCourseIdAndSem(student.getCourseId(), student.getSemOrYear(), true, pageable);

            // Fetch class schedules for all subjects in a single query to reduce database calls
            List<Long> subjectIds = subjectList.stream().map(Subject::getId).collect(Collectors.toList());
            classScheduleList = classScheduleRepository.findByCourseIdAndSubjectIdIn(student.getCourseId(), subjectIds, true);

        } else if ("faculty".equals(role)) {
            Faculties faculties = facultiesRepository.findByUserId(userId, true);
            classScheduleList = classScheduleRepository.findByFacultyId(faculties.getId(), true);

        } else {
            classScheduleList = classScheduleRepository.findByIsActive(true, pageable);
        }

        if (classScheduleList.isEmpty()) {
            logger.warn("Class schedule list is not found");
            return Collections.emptyList();
        }

        // Convert ClassSchedule to ClassScheduleResponse
        classScheduleResponseList = classScheduleList.stream().map(this::mapToResponse).collect(Collectors.toList());

        logger.info("Successfully fetched class schedule list");
        return classScheduleResponseList;
    }

    // Utility method to map ClassSchedule to ClassScheduleResponse
    private ClassScheduleResponse mapToResponse(ClassSchedule classSchedule) {
        List<Course> courseList = courseRepository.findAll();
        List<Subject> subjectList = subjectRepository.findAll();
        List<Faculties> facultyList = facultiesRepository.findAll();
        ClassScheduleResponse response = new ClassScheduleResponse();
        Course course = findCourseById(courseList,classSchedule.getCourseId());
        Subject subject = findSubjectById(subjectList,classSchedule.getSubjectId());
        Faculties faculty = findFacultyById(facultyList,classSchedule.getFacultyId());
        response.setId(classSchedule.getId());
        response.setCourseId(classSchedule.getCourseId());
        response.setCourseName(course.getCourseName());
        response.setSubjectId(classSchedule.getSubjectId());
        response.setSubjectName(subject.getSubjectName());
        response.setFacultyId(classSchedule.getFacultyId());
        response.setFacultyName(faculty.getFacultyName());
        response.setStartTime(classSchedule.getStartTime());
        response.setEndTime(classSchedule.getEndTime());
        response.setDay(classSchedule.getDay());
        response.setRoomNo(classSchedule.getRoomNo());
        return response;
    }

    public Course findCourseById(List<Course> courseList, Long courseId) {
        for (Course course : courseList) {
            if (course.getId() == courseId) {
                return course;
            }
        }
        return null;
    }

    public Subject findSubjectById(List<Subject> subjectList, Long subjectId) {
        for (Subject subject : subjectList) {
            if (subject.getId() == subjectId) {
                return subject;
            }
        }
        return null;
    }

    public Faculties findFacultyById(List<Faculties> facultyList, Long facultyId) {
        for (Faculties faculty : facultyList) {
            if (faculty.getId() == facultyId) {
                return faculty;
            }
        }
        return null;
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
