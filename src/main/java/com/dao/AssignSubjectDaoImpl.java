package com.dao;

import com.Utility.ConnectionUtilities;
import com.payload.response.AssignSubjectResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
@Repository
public class AssignSubjectDaoImpl implements AssignSubjectDao{
    static org.apache.log4j.Logger logger = Logger.getLogger(AssignSubjectDaoImpl.class);
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    @Override
    public List<AssignSubjectResponse> getAssignFacultiesList() throws Exception {
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
                    "JOIN faculties AS f ON f.id = a.faculty_id";
            rs = st.executeQuery(sql);
            logger.info("Executing Query: " + sql);
            while (rs.next()) {
                AssignSubjectResponse assignSubjectResponse = new AssignSubjectResponse();
                assignSubjectResponse.setFacultyId(rs.getLong("faculty_id"));
                assignSubjectResponse.setCourseCode(rs.getString("course_code"));
                assignSubjectResponse.setSubjectCode(rs.getLong("subject_code"));
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
}
