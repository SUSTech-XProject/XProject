package com.ooad.xproject.vo;

import com.ooad.xproject.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.util.Date;

@Getter
@Setter
public class EventInstCreationVO {
    private int eaTaskId;
    private String date;        // format yyyy-MM-dd
    private String startTime;   // format HH:mm:ss
    private int duration;       // in Minutes
    private int counts;

    public Date getDate() throws ParseException {
        String str = date.strip() + " 00:00:00";
        return TimeUtils.strToDateStandard(str);
    }

    public Date getStartTime() throws ParseException {
        String str = date.strip() + " " + startTime.strip();
        return TimeUtils.strToDateStandard(str);
    }
}
