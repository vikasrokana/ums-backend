package com.service;

import com.Utility.AppUtils;
import com.controller.FacultiesController;
import com.exception.RecordNotFoundException;
import com.model.*;
import com.payload.request.StudentFeeRequest;
import com.payload.request.StudentRequest;
import com.payload.response.StudentFeeResponse;
import com.payload.response.StudentResponse;
import com.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultiesRepository facultiesRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    AssignSubjectRepository assignSubjectRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Student registerStudent(StudentRequest studentRequest) {

        Student student = new Student();
        student.setStudentName(studentRequest.getStudentName());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());
        student.setDob(studentRequest.getDob());
        student.setGender(studentRequest.getGender());
        student.setOptionalSubject(studentRequest.getOptionalSubject());
        student.setProfilePic(studentRequest.getProfilePic());
        student.setAddress(studentRequest.getAddress());
        student.setPinCode(studentRequest.getPinCode());
        student.setFatherName(studentRequest.getFatherName());
        student.setFatherOccupation(studentRequest.getFatherOccupation());
        student.setMotherName(studentRequest.getMotherName());
        student.setMotherOccupation(studentRequest.getMotherOccupation());
        student.setAdmissionDate(studentRequest.getAdmissionDate());
        student.setCourseId(studentRequest.getCourseId());
        student.setSemOrYear(studentRequest.getSemOrYear());
        student.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        student.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getStudentList(Long courseId, Long semOrYear, String rollNumber, Integer pageNumber) {
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        return studentRepository.findByCourseIdAndSemOrYearAndRollNumber(courseId, semOrYear, rollNumber,pageable);
//        if (courseId != null && semOrYear != null && rollNumber != null) {
//            return studentRepository.findByCourseIdAndSemOrYearAndRollNumber(courseId, semOrYear, rollNumber,pageable);
//        } else if (courseId != null && semOrYear != null) {
//            return studentRepository.findByCourseIdAndSemOrYear(courseId, semOrYear, pageable);
//        } else {
//            return studentRepository.findAll();
//        }
    }

    @Override
    public Student StudentDetails(StudentRequest studentRequest, Long userId) {
        Student student = studentRepository.findByIdAndIsActive(studentRequest.getId(), true);
        if (studentRequest.getStudentName() != null) {
            student.setStudentName(studentRequest.getStudentName());
        }
        if (studentRequest.getPhone() != null) {
            student.setPhone(studentRequest.getPhone());
        }
        if (studentRequest.getDob() != null) {
            student.setDob(studentRequest.getDob());
        }
        if (studentRequest.getGender() != null) {
            student.setGender(studentRequest.getGender());
        }
        if (studentRequest.getOptionalSubject() != null) {
            student.setOptionalSubject(studentRequest.getOptionalSubject());
        }
        if (studentRequest.getProfilePic() != null) {
            student.setProfilePic(studentRequest.getProfilePic());
        }
        if (studentRequest.getEnrollmentNumber() != null) {
            student.setEnrollmentNumber(studentRequest.getEnrollmentNumber());
        }
        if (studentRequest.getAddress() != null) {
            student.setAddress(studentRequest.getAddress());
        }
        if (studentRequest.getPinCode() != null) {
            student.setPinCode(studentRequest.getPinCode());
        }
        if (studentRequest.getFatherName() != null) {
            student.setFatherName(studentRequest.getFatherName());
        }
        if (studentRequest.getFatherOccupation() != null) {
            student.setFatherOccupation(studentRequest.getFatherOccupation());
        }
        if (studentRequest.getMotherName() != null) {
            student.setMotherName(studentRequest.getMotherName());
        }
        if (studentRequest.getMotherOccupation() != null) {
            student.setMotherOccupation(studentRequest.getMotherOccupation());
        }
        if (studentRequest.getAdmissionDate() != null) {
            student.setAdmissionDate(studentRequest.getAdmissionDate());
        }
        if (studentRequest.getSemOrYear() != null) {
            student.setSemOrYear(studentRequest.getSemOrYear());
        }
        student.setUpdatedOn(AppUtils.getCurrentIstTime());
        Student student1 = studentRepository.save(student);
        return student1;
    }

    @Override
    public Student findStudentDetails(Long userId) throws RecordNotFoundException {
        Student student = studentRepository.findByUserIdAndIsActive(userId, true);
        if (null == student) {
            throw new RecordNotFoundException("student details not found with id:: " + userId);
        }
        logger.info("Get student detail");
        return student;
    }

    @Override
    public List<StudentFeeResponse> getStudentFeeList() throws RecordNotFoundException {
        List<StudentFeeResponse> studentFeeResponses = new ArrayList<>();
        List<StudentFees> studentFeesList = studentRepository.findByIsActive(true);
        if (studentFeesList.isEmpty()) {
            throw new RecordNotFoundException("student list is not found");
        }
        for (StudentFees fees : studentFeesList) {
            StudentFeeResponse studentFeeResponse = new StudentFeeResponse();
            studentFeeResponse.setId(fees.getId());
            studentFeeResponse.setStudentId(fees.getStudentId());
            studentFeeResponse.setEnrollmentFee(fees.getEnrollmentFee());
            studentFeeResponse.setTuitionFee(fees.getTuitionFee());
            studentFeeResponse.setExamFee(fees.getExamFee());
            studentFeeResponse.setPaidFee(fees.getPaidFee());
            studentFeeResponse.setDueFee(fees.getDueFee());
            studentFeeResponse.setPaidBy(fees.getPaidBy());
            studentFeeResponse.setDueDate(fees.getDueDate());
            studentFeeResponse.setCreatedBy(fees.getCreatedBy());
            studentFeeResponse.setUpdateBy(fees.getUpdateBy());
            studentFeeResponse.setCreatedOn(fees.getCreatedOn());
            studentFeeResponse.setUpdatedOn(fees.getUpdatedOn());
            studentFeeResponses.add(studentFeeResponse);
        }
        logger.info("get student fee list");
        return studentFeeResponses;
    }

    @Override
    public List<StudentResponse> getStudentByFacultyId(Long userId, Integer pageNumber) {
        List<StudentResponse> studentResponses = new ArrayList<>();
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        try {
            // Fetch faculty details
            Faculties faculty = facultiesRepository.findByUserId(userId, true);
            if (faculty == null) {
                throw new IllegalArgumentException("Faculty not found for userId: " + userId);
            }

            // Fetch assigned subjects
            List<AssignSubject> assignSubject = assignSubjectRepository.findByFacultyId(faculty.getId(), true, pageable);
            if (assignSubject.isEmpty()) {
                logger.warn("No assigned subjects found for facultyId: " + faculty.getId());
            }

            for (AssignSubject aSub : assignSubject) {
                // Fetch courseId using courseCode

                // find sem or Year from subject and then place in below code
                Subject subject = subjectRepository.findByIdAndIsActive(aSub.getSubjectId(),true);
//                Long courseId = courseRepository.findByCourseCode(aSub.getCourseCode());
//                if (courseId == null) {
//                    logger.warn("Course ID not found for courseCode: " + aSub.getCourseCode());
//                    continue; // Skip this iteration if courseId is null
//                }

                // Fetch student details
                List<Student> studentList = studentRepository.findByCourseIdAndSemOrYearAndSubject(subject.getCourseId(), subject.getSemOrYear(), pageable,true);
                if (studentList.isEmpty()) {
                    logger.warn("No student found for courseId: " + subject.getCourseId() + ", semOrYear: " + subject.getSemOrYear());
                    continue; // Skip this iteration if student is null
                }
                for (Student student : studentList) {
                    // Map student to studentResponse
                    StudentResponse studentResponse = new StudentResponse();
                    studentResponse.setId(student.getId());
                    studentResponse.setCourseId(student.getCourseId());
                    studentResponse.setAddress(student.getAddress());
                    studentResponse.setDob(student.getDob());
                    studentResponse.setEmail(student.getEmail());
                    studentResponse.setEnrollmentNumber(student.getEnrollmentNumber());
                    studentResponse.setFatherName(student.getFatherName());
                    studentResponse.setFatherOccupation(student.getFatherOccupation());
                    studentResponse.setGender(student.getGender());
                    studentResponse.setMotherName(student.getMotherName());
                    studentResponse.setMotherOccupation(student.getMotherOccupation());
                    studentResponse.setOptionalSubject(student.getOptionalSubject());
                    studentResponse.setPhone(student.getPhone());
                    studentResponse.setPinCode(student.getPinCode());
                    studentResponse.setProfilePic(student.getProfilePic());
                    studentResponse.setRollNumber(student.getRollNumber());
                    studentResponse.setSemOrYear(student.getSemOrYear());
                    studentResponse.setStudentName(student.getStudentName());
                    studentResponse.setUserId(student.getUserId());
                    // Add to the response list
                    studentResponses.add(studentResponse);
                }
            }
            logger.info("Successfully retrieved faculty student list for userId: " + userId);
        } catch (Exception e) {
            logger.error("Error while fetching student list for faculty with userId: " + userId, e);
            throw e; // Re-throw the exception to notify the caller
        }

        return studentResponses;
    }

    @Override
    public List<StudentResponse> getClassmateStudentList(Long userId, Integer pageNumber) {
        Pageable pageable = AppUtils.getPageRange(pageNumber);
        List<StudentResponse> studentResponses = new ArrayList<>();
        Student self = studentRepository.findByUserIdAndIsActive(userId,true);
        if(self == null){
            throw new IllegalArgumentException("Active student not found for userId: " + userId);
        }
        List<Student> classmateList = studentRepository.findByCourseIdAndSemOrYearAndUserId(self.getCourseId(),self.getSemOrYear(),userId,pageable);
        for(Student classmate: classmateList){
            StudentResponse student = new StudentResponse();
            student.setId(classmate.getId());
            student.setCourseId(classmate.getCourseId());
            student.setAddress(classmate.getAddress());
            student.setDob(classmate.getDob());
            student.setEmail(classmate.getEmail());
            student.setEnrollmentNumber(classmate.getEnrollmentNumber());
            student.setFatherName(classmate.getFatherName());
            student.setFatherOccupation(classmate.getFatherOccupation());
            student.setGender(classmate.getGender());
            student.setMotherName(classmate.getMotherName());
            student.setMotherOccupation(classmate.getMotherOccupation());
            student.setOptionalSubject(classmate.getOptionalSubject());
            student.setPhone(classmate.getPhone());
            student.setPinCode(classmate.getPinCode());
            student.setProfilePic(classmate.getProfilePic());
            student.setRollNumber(classmate.getRollNumber());
            student.setSemOrYear(classmate.getSemOrYear());
            student.setStudentName(classmate.getStudentName());
            student.setUserId(classmate.getUserId());
            studentResponses.add(student);
        }
        return studentResponses;
    }


    public Student findById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
    }

}
