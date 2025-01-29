package com.service;

import com.exception.RecordNotFoundException;
import com.model.AssignSubject;
import com.model.Faculties;
import com.payload.request.AssignSubjectRequest;
import com.payload.request.FacultiesRequest;
import com.payload.response.FacultiesResponse;

import java.util.List;

public interface FacultiesService {
    Faculties addFaculties(FacultiesRequest facultiesRequest,Long userId);

    List<FacultiesResponse> getFacultiesList() throws RecordNotFoundException;

    Faculties getFacultyById(Long facultyId) throws RecordNotFoundException;

    Boolean deleteFaculty(Long facultyId);

    AssignSubject assignSubject(AssignSubjectRequest assignSubjectRequest,Long userId);

    Faculties getFacultyDetails(Long userId) throws RecordNotFoundException;

}
