package com.example.user.jsetestapp;

import org.joda.time.LocalTime;

public class Hours {
    public static enum DayOfWeek {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};

    //TODO change startTime dataType in database to LocalTime
    String name;
    DayOfWeek dayOfWeek;
    String endTime;
    LocalTime startTime;

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

    public void setDayOfWeek(String dayOfWeek) {
        try {
            this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek);
        } catch (Exception ex) {
            this.dayOfWeek = DayOfWeek.SUNDAY;
        }
    }

public LocalTime getStartTime() {
    return startTime;
}

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
