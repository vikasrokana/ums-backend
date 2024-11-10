package com.Utility;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

@Service
public class AppUtils {
    public static Timestamp getCurrentIstTime(){
//		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Calcutta"));
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        return new Timestamp(new Date().getTime());
    }

    public static String currentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);
        return strDate;
    }

    public static String generateEnrollmentNumber(String courseName) {
      // Get the current year
        int currentYear = Year.now().getValue();

        // Generate a unique 4-digit random number
        Random random = new Random();
        int uniqueNumber = 1000 + random.nextInt(9000); // Generates a number between 1000 and 9999

        // Format the course name to uppercase and take first three letters for consistency
        String courseCode = courseName.substring(0, Math.min(3, courseName.length())).toUpperCase();

        // Combine year, course code, and unique number to form the enrollment number
        return currentYear + "-" + courseCode + "-" + uniqueNumber;
    }
//    public Long getUserId(HttpServletRequest request) {
//        String role = getCurrentUserRole(request);
//        String jwt = tokenProvider.getJwtFromRequest(request);
//        Long userId = tokenProvider.getUserIdFromToken(jwt, role);
//        return userId;
//    }
}
