package com.dao;

import com.Utility.AppUtils;
import com.Utility.ConnectionUtilities;
import com.model.AssignSubject;
import com.model.Faculties;
import com.model.Student;
import com.payload.response.AssignSubjectResponse;
import com.payload.response.FacultiesResponse;
import com.payload.response.FacultySubjectResponse;
import com.repository.AssignSubjectRepository;
import com.repository.FacultiesRepository;
import com.repository.StudentRepository;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class AssignSubjectDaoImpl implements AssignSubjectDao{
    static org.apache.log4j.Logger logger = Logger.getLogger(AssignSubjectDaoImpl.class);
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultiesRepository facultiesRepository;

    @Autowired
    AssignSubjectRepository assignSubjectRepository;
    @Override
    public List<AssignSubjectResponse> getAssignFacultiesList(Integer pageNumber) throws Exception {
        Integer pageSize = 10; // Define the page size (adjust as needed)
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1; // Default to the first page
        }
        Integer offset = (pageNumber - 1) * pageSize; // Calculate offset
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;
        List<AssignSubjectResponse> assignSubjectResponseList = new ArrayList<>();
        String sql = "";
        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            sql = "SELECT a.*, c.*, f.* FROM ums.course AS c " +
                    "JOIN assign_subject AS a ON c.course_code = a.course_code " +
                    "JOIN faculties AS f ON f.id = a.faculty_id " +
                    "LIMIT " + pageSize + " OFFSET " + offset; // Applying pagination
            rs = st.executeQuery(sql);
            logger.info("Executing Query: " + sql);
            while (rs.next()) {
                AssignSubjectResponse assignSubjectResponse = new AssignSubjectResponse();
                assignSubjectResponse.setFacultyId(rs.getLong("faculty_id"));
                assignSubjectResponse.setCourseCode(rs.getString("course_code"));
                assignSubjectResponse.setCourseId(rs.getLong("course_id"));
                assignSubjectResponse.setSubjectCode(rs.getString("subject_code"));
                assignSubjectResponse.setSemOrYear(rs.getString("sem_or_year"));
                assignSubjectResponse.setPosition(rs.getString("position"));
                assignSubjectResponse.setDate(rs.getString("date"));
                assignSubjectResponse.setFacultyName(rs.getString("faculty_name"));
                assignSubjectResponse.setCourseName(rs.getString("course_name"));
                assignSubjectResponse.setExperience(rs.getString("experience"));
                assignSubjectResponse.setQualification(rs.getString("qualification"));
                assignSubjectResponseList.add(assignSubjectResponse);
            }

        }
        catch (SQLException ex2) {
            logger.error("Error in Executing Query: " + sql);
            throw new Exception(ex2.getMessage());
        }catch (Exception e) {
            logger.error("Error in Executing Query: " + sql);
            throw new Exception(e.getMessage());
        } finally {
            ConnectionUtilities.closeConnectionResource(con, st, rs);
        }
        return assignSubjectResponseList;
    }

    @Override
    public List<FacultySubjectResponse> getAssignFacultiesListToStudent(Long userId, Integer pageNumber) throws SQLException {
        Integer pageSize = 10; // Define the page size (adjust as needed)
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1; // Default to the first page
        }
        Integer offset = (pageNumber - 1) * pageSize; // Calculate offset
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null; // Use PreparedStatement for SQL queries
        List<FacultySubjectResponse> facultySubjectResponseList = new ArrayList<>();
        String sql = "";

        // Retrieve the student details
        Student student = studentRepository.findByUserIdAndIsActive(userId, true);
        if (student == null) {
            throw new IllegalArgumentException("Student not found for the given userId.");
        }

        // Retrieve the assigned subjects
        List<AssignSubject> assignSubjectList = assignSubjectRepository.findByCourseAndSem(student.getCourseId(), student.getSemOrYear(), true);
        for (AssignSubject assignSubject : assignSubjectList) {
            // Retrieve the faculty details
            Faculties facultiesList = facultiesRepository.findByIdAndIsActive(assignSubject.getFacultyId(), true);
            if (facultiesList != null) {
                try {
                    // Establish database connection
                    con = dataSource.getConnection();
                    sql = "SELECT c.course_name, s.sem_or_year, s.subject_name " +
                            "FROM course AS c " +
                            "INNER JOIN subject AS s ON c.id = s.course_id " +
                            "WHERE s.course_id = ? AND s.sem_or_year = ? AND s.subject_code = ? AND c.is_active = ?" +
                            "LIMIT " + pageSize + " OFFSET " + offset;

                    // Use PreparedStatement for parameterized queries
                    pstmt = con.prepareStatement(sql);
                    pstmt.setLong(1, assignSubject.getCourseId());
                    pstmt.setLong(2, assignSubject.getSemOrYear());
                    pstmt.setString(3, assignSubject.getSubjectCode());
                    pstmt.setBoolean(4, true); // Adjust based on database boolean representation

                    // Execute the query
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        FacultySubjectResponse facRes = new FacultySubjectResponse();
                        facRes.setId(facultiesList.getId());
                        facRes.setFacultyName(facultiesList.getFacultyName());
                        facRes.setEmail(facultiesList.getEmail());
                        facRes.setPhone(facultiesList.getPhone());
                        facRes.setQualification(facultiesList.getQualification());
                        facRes.setExperience(facultiesList.getExperience());
                        facRes.setDob(facultiesList.getDob());
                        facRes.setGender(facultiesList.getGender());
                        facRes.setProfilePic(facultiesList.getProfilePic());
                        facRes.setJoinDate(facultiesList.getJoinDate());
                        facRes.setAddress(facultiesList.getAddress());
                        facRes.setPinCode(facultiesList.getPinCode());
                        facRes.setCourseName(rs.getString("course_name"));
                        facRes.setSemOrYear(rs.getLong("sem_or_year"));
                        facRes.setPosition(assignSubject.getPosition());
                        facRes.setSubjectName(rs.getString("subject_name"));
                        facultySubjectResponseList.add(facRes);
                    }
                } catch (SQLException e) {
                    throw new SQLException("Error while fetching data: " + e.getMessage(), e);
                } finally {
                    // Ensure resources are properly closed
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (con != null) con.close();
                }
            }
        }
        return facultySubjectResponseList;
    }

}
