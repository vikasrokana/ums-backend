package com.Utility;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

@Service
public class AppUtils {
    public static Timestamp getCurrentIstTime(){
//		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Calcutta"));
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        return new Timestamp(new Date().getTime());
    }
}
