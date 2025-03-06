package com.service;

import com.exception.RecordNotFoundException;
import com.payload.response.StudentFeeResponse;

import java.util.List;

public interface StudentFeesService {
    List<StudentFeeResponse> findFeeList(Long userId,  String role) throws RecordNotFoundException;
}
