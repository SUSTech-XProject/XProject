package com.ooad.xproject.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class TimeUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String[] weekDays =
            {"Sunday ", " Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public static Date strToDateStandard(String str) throws ParseException {
        return sdf.parse(str);
    }

    public static String dateToStrStandard(Date date) {
        return sdf.format(date);
    }

    public static String getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }
}
