package com.payload.request;
import lombok.Data;
import java.util.List;

@Data
public class AttendanceRequest {

    private List<Attendance> attendanceList;

    @Data
    public static class Attendance {
        private Long id;
        private Long courseId;
        private Long subjectId;
        private String date;
//        private String time;
        private Long studentId;
        private Long semOrYear;
        private Boolean present;
        private String section;

        public Attendance(Long id, Long courseId, Long subjectId, String date, String time, Long studentId, Long semOrYear, Boolean present, String section) {
            this.id = id;
            this.courseId = courseId;
            this.subjectId = subjectId;
            this.date = date;
//            this.time = time;
            this.studentId = studentId;
            this.semOrYear = semOrYear;
            this.present = present;
            this.section = section;
        }
    }
}
