package com.service;

import com.exception.RecordNotFoundException;
import com.model.Subject;
import com.payload.request.SubjectRequest;
import com.payload.response.SubjectResponse;

import java.util.List;

public interface SubjectService {
    Subject addSubject(SubjectRequest subjectRequest,Long userId);

    List<SubjectResponse> getSubjectList(Integer pageNumber) throws RecordNotFoundException;

    Boolean deleteSubject(Long subjectId);

    Subject getSubjectById(Long subjectId) throws RecordNotFoundException;

    List<SubjectResponse> getSubjectByCourseId(Long courseId);

    List<SubjectResponse> getAssignedSubjectOfStudent(Long userId, Integer pageNumber);
}
