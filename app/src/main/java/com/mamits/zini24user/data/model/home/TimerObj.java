package com.mamits.zini24user.data.model.home;

import java.io.Serializable;

public class TimerObj implements Serializable {
    int hour, min, sec;
    long totalMillis;

    public TimerObj(int hour, int min, int sec, long totalMillis) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
        this.totalMillis = totalMillis;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public long getTotalMillis() {
        return totalMillis;
    }

    public void setTotalMillis(long totalMillis) {
        this.totalMillis = totalMillis;
    }
}
