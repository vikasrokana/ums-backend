package com.Utility;

import com.config.AppProperties;
import com.nimbusds.jose.shaded.gson.Gson;
import com.security.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    AppProperties appProperties;

    @Autowired
    private TokenProvider tokenProvider;
    private static final Gson gson = new Gson();
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
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
    public Long getUserId(HttpServletRequest request) {
        String role = getCurrentUserRole(request);
        String jwt = tokenProvider.getJwtFromRequest(request);
        Long userId = tokenProvider.getUserIdFromToken(jwt, role);
        return userId;
    }

    public String getCurrentUserRole(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String role = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            Claims claims = Jwts.parser()
                    .setSigningKey(appProperties.getAuth().getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody();
            role = (String) claims.get("role");
            return role;
        }
        return null;
    }
}
