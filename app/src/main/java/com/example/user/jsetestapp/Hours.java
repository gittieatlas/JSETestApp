
package com.example.user.jsetestapp;

import org.joda.time.LocalTime;

import java.io.Serializable;

public class Hours implements Serializable{
    public static enum DayOfWeek {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};

    //TODO change startTime dataType in database to LocalTime
//TODO is duration min out of 60 or %?
    String name;
    DayOfWeek dayOfWeek;
    LocalTime startTime, endTime;

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

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}