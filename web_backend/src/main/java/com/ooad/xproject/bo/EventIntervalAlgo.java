package com.ooad.xproject.bo;

import com.ooad.xproject.vo.EventInstCreationVO;
import org.apache.commons.math3.util.Pair;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventIntervalAlgo {
    public static List<Pair<Date, Date>> generateTimeIntervals(EventInstCreationVO eventInstCreationVO) throws ParseException {
        Date startTime = eventInstCreationVO.getStartTime();
        long timestamp = startTime.getTime();
        long duration = eventInstCreationVO.getDuration() * 1000 * 60;        // parse minute to ms
        List<Pair<Date, Date>> timeList = new ArrayList<>(eventInstCreationVO.getCounts());

        for (int i = 0; i < eventInstCreationVO.getCounts(); i++) {
            Date start = new Date(timestamp);
            timestamp += duration;
            Date end = new Date(timestamp + (i + 1) * eventInstCreationVO.getDuration());

            Pair<Date, Date> pair = new Pair<>(start, end);
            timeList.add(pair);
        }
        return timeList;
    }
}
