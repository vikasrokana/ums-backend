package com.service;

import com.exception.RecordNotFoundException;
import com.model.Subject;
import com.payload.request.SubjectRequest;
import com.payload.response.CourseResponse;
import com.payload.response.SubjectResponse;

import java.util.List;

public interface SubjectService {
    Subject addSubject(SubjectRequest subjectRequest);

    List<SubjectResponse> getSubjectList() throws RecordNotFoundException;

    Boolean deleteSubject(Long subjectId);

    Subject getSubjectById(Long subjectId) throws RecordNotFoundException;

    List<SubjectResponse> getSubjectByCourseId(Long courseId, String semOrYear);
}
