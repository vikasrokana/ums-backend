package com.payload.request;
import lombok.Data;
import java.util.List;

@Data
public class AttendanceRequest {

    private List<Attendance> attendanceList;

    @Data
    public static class Attendance {
        private Long id;
        private String courseCode;
        private String subjectCode;
        private String date;
        private String time;
        private Long studentId;
        private Long semOrYear;
        private Boolean present;
        private String section;

        public Attendance(Long id, String courseCode, String subjectCode, String date, String time, Long studentId, Long semOrYear, Boolean present, String section) {
            this.id = id;
            this.courseCode = courseCode;
            this.subjectCode = subjectCode;
            this.date = date;
            this.time = time;
            this.studentId = studentId;
            this.semOrYear = semOrYear;
            this.present = present;
            this.section = section;
        }
    }
}
