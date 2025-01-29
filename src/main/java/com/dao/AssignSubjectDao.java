package com.dao;

import com.payload.response.AssignSubjectResponse;
import com.payload.response.FacultiesResponse;
import com.payload.response.FacultySubjectResponse;

import java.sql.SQLException;
import java.util.List;

public interface AssignSubjectDao {

    List<AssignSubjectResponse> getAssignFacultiesList() throws Exception;

    List<FacultySubjectResponse> getAssignFacultiesListToStudent(Long userId) throws SQLException;
}
