package com.mamits.zini24user.ui.utils.commonClasses;

import com.mamits.zini24user.data.model.home.TimerObj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static TimerObj getTimeObjFromMillis(long difference) {
        if (difference > 0) {
            int hours = (int) (difference / (1000 * 60 * 60));
            int Mins = ((int) (difference / (1000 * 60))) % 60;
            int Secs = ((int) ((difference / 1000)) % 60);
            return new  TimerObj(
                    hours,
                    Mins,
                    Secs, difference
            );
        } else {
            return new  TimerObj(
                    0,
                    0,
                    0,
                     difference
            );
        }
    }

    public TimerObj checkTimeDifference(String endtime, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        TimerObj mTimerObj = new TimerObj(0, 0, 0, 0);
        Date date1;
        try {
            date1 = sdf.parse(time);
            Date date2 = sdf1.parse(endtime);
            long difference = date1.getTime() - date2.getTime();

            if (difference > 0) {
                int hours = (int) (difference / (1000 * 60 * 60));
                int Mins = (int) ((difference / (1000 * 60)) % 60);
                int Secs = (int) ((difference / 1000) % 60);
                mTimerObj = new TimerObj(hours, Mins, Secs, difference);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return mTimerObj;
    }

    public String addHrMinuteToDateStr(String dateString, boolean ishr, int unit) {
        String newDateStr = "";
        long millisToAdd = ishr ? ((1000 * 60 * 60) * unit) : ((1000 * 60) * unit);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date d = null;
        try {
            d = format.parse(dateString);
            d.setTime(d.getTime() + millisToAdd);
            newDateStr = format.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDateStr;
    }
}
