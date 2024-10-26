package com.dao;

import com.payload.response.AssignSubjectResponse;

import java.util.List;

public interface AssignSubjectDao {

    List<AssignSubjectResponse> getAssignFacultiesList() throws Exception;
}
