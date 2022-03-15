package com.mamits.zini24user.ui.utils.commonClasses;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BeautifyDate {
    public String beautifyDate(String timestamp) {

        SimpleDateFormat fmt=null;
        try {
            String[] created_at = timestamp.split(" ");
            String[] date = created_at[0].split("-");
            if (date[0].length() == 2) {
                fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            } else {
                fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Date date;

        try {
            date = fmt.parse(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return getlongtoago(date.getTime());
    }

    public static String getlongtoago(long createdAt) {

        // Get msec from each, and subtract.
        long diff = new Date().getTime() - createdAt;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String time = null;
        if (diffDays > 0) {
            if (diffDays == 1) {
                time = diffDays + " day ago";
            } else {
                time = diffDays + " days ago";
            }
        } else {
            if (diffHours > 0) {
                if (diffHours == 1) {
                    time = diffHours + " hr ago";
                } else {
                    time = diffHours + " hrs ago";
                }
            } else {
                if (diffMinutes > 0) {
                    if (diffMinutes == 1) {
                        time = diffMinutes + " min ago";
                    } else {
                        time = diffMinutes + " mins ago";
                    }
                } else {
                    if (diffSeconds > 0) {
                        time = "just now";
                    }
                }

            }

        }
        return time;
    }
}
