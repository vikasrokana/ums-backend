package com.service;

import com.exception.RecordNotFoundException;
import com.model.Student;
import com.model.StudentFees;
import com.payload.response.StudentFeeResponse;
import com.repository.FeeRepository;
import com.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentFeeServiceImpl implements StudentFeesService {
    private static final Logger logger = LoggerFactory.getLogger(StudentFeeServiceImpl.class);

    @Autowired
    FeeRepository feeRepository;

    @Autowired
    StudentRepository studentRepository;
    @Override
    public List<StudentFeeResponse> findFeeList(Long userId, String role) throws RecordNotFoundException {
        List<StudentFeeResponse> studentFeeResponses = new ArrayList<>();
        if(role.equals("student")){
            Student student = studentRepository.findByUserIdAndIsActive(userId,true);
            List<StudentFees> studentFeesList = feeRepository.findByStudentId(student.getId(), true);

        }
        List<StudentFees> studentFeesList = feeRepository.findByIsActive(true);

        if (studentFeesList.isEmpty()) {
            logger.warn("student list is not found");
            return new ArrayList<>();
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
}
