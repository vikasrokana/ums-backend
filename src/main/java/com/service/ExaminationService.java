package com.service;

import com.exception.RecordNotFoundException;
import com.model.Examination;
import com.payload.request.ExaminationRequest;
import com.payload.response.ExaminationResponse;

import java.util.List;

public interface ExaminationService {
    Examination addExamination(ExaminationRequest examinationRequest, Long userId);

    List<ExaminationResponse> getExaminationList(Integer pageNumber);

    Boolean deleteExamination(Long examinationId);

    Examination getExaminationById(Long examId) throws RecordNotFoundException;
}
