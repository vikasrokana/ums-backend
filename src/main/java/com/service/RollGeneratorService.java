package com.service;

import com.model.RollGenerator;
import com.payload.request.RollNumberRequest;

public interface RollGeneratorService {
    RollGenerator addRollNumber(RollNumberRequest rollNumberRequest);

    RollGenerator getRollNumberByCourseIdAndSem(Long courseId, String semOrYear);

    Boolean deleteRollNumber(Long id);
}
