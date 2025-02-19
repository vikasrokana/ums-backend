package com.service;

import com.Utility.AppUtils;
import com.exception.RecordNotFoundException;
import com.model.Course;
import com.payload.request.CourseRequest;
import com.payload.response.CourseResponse;
import com.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    CourseRepository courseRepository;
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Override
    public Course addCourse(CourseRequest courseRequest, Long userId) {
        Course course = new Course();
        if(courseRequest.getId() != null){
            course = courseRepository.findById(courseRequest.getId()).get();
            course.setUpdatedOn(AppUtils.getCurrentIstTime());
            course.setUpdatedBy(userId);
            }
        else{
            course.setCreatedOn(AppUtils.getCurrentIstTime());
            course.setCreatedBy(userId);
        }
        if(null != courseRequest.getCourseCode()){
            course.setCourseCode(courseRequest.getCourseCode());
        }
        if(null != courseRequest.getCourseName()){
            course.setCourseName(courseRequest.getCourseName());
        }
        if(null != courseRequest.getSemOrYear()){
            course.setSemOrYear(courseRequest.getSemOrYear());
        }
        if(null != courseRequest.getTotalSemOrYear()){
            course.setTotalSemOrYear(courseRequest.getTotalSemOrYear());
        }
        if(null != courseRequest.getDescription()){
            course.setDescription(courseRequest.getDescription());
        }
        if(null != courseRequest.getExamFee()){
            course.setExamFee(courseRequest.getExamFee());
        }
        if(null != courseRequest.getTuitionFee()){
            course.setTuitionFee(courseRequest.getTuitionFee());
        }
        Course course1 = courseRepository.save(course);
        logger.info("course added successfully");
        return course1;
    }

    @Override
    public List<CourseResponse> getCourseList( Integer pageNumber) throws RecordNotFoundException {
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        List<CourseResponse> courseResponseList = new ArrayList<>();
        List<Course> courseList = courseRepository.findByIsActive(true, pageable);
        if(courseList.isEmpty()){
//            throw new RecordNotFoundException("course list is not found");
            logger.warn("course list is not found");
            return new ArrayList<>();
        }
        for(Course course: courseList){
            CourseResponse courseResponse= new CourseResponse();
            courseResponse.setId(course.getId());
            courseResponse.setCourseCode(course.getCourseCode());
            courseResponse.setCourseName(course.getCourseName());
            courseResponse.setDescription(course.getDescription());
            courseResponse.setSemOrYear(course.getSemOrYear());
            courseResponse.setTotalSemOrYear(course.getTotalSemOrYear());
            courseResponse.setExamFee(course.getExamFee());
            courseResponse.setTuitionFee(course.getTuitionFee());
            courseResponse.setCreatedOn(course.getCreatedOn());
            courseResponse.setUpdatedOn(course.getUpdatedOn());
            courseResponse.setIsActive(course.getIsActive());
            courseResponseList.add(courseResponse);
        }
        logger.info("get course list");
        return courseResponseList;
    }

    @Override
    public Boolean deleteCourse(Long courseId) {
        Integer isDeleted = courseRepository.deleteLabour(courseId);
        if( isDeleted != 0){
            logger.info("course deleted Successfully");
            return true;
        }
        return false;
    }

    @Override
    public Course getCourseById(Long courseId) throws RecordNotFoundException {
        Course course = courseRepository.findByIdAndIsActive(courseId,true);
        if(null == course){
            throw new RecordNotFoundException("course not found with id:: " + courseId);
        }
        logger.info("Get Course using id");
        return course;
    }


}
