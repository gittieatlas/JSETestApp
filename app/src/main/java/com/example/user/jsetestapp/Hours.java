package com.example.user.jsetestapp;

import org.joda.time.DateTime;

public class Hours {
    public static enum DayOfWeek {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};

    String name;
    DayOfWeek dayOfWeek;
    //DateTime startTime, endTime;
    String startTime, endTime;
    //String dayOfWeek;

    public Hours() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
//
//    public DateTime getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(DateTime startTime) {
//        this.startTime = startTime;
//    }
//
//    public DateTime getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(DateTime endTime) {
//        this.endTime = endTime;
//    }

    public void setDayOfWeek(String dayOfWeek) {
        try {
            this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek);
        } catch (Exception ex) {
            this.dayOfWeek = DayOfWeek.SUNDAY;
        }
    }

public String getStartTime() {
    return startTime;
}

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

//    public String getDayOfWeek() {
//        return dayOfWeek;
//    }
//    public void setDayOfWeek(String dayOfWeek) {
//        this.dayOfWeek = dayOfWeek;
//    }
}
