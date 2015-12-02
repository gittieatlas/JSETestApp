package com.example.user.jsetestapp;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.Serializable;

public class Alert implements Serializable {
    public enum DayOfWeek {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
    int locationId;
    String locationName;
    DayOfWeek dayOfWeek;
    LocalDate date;
    LocalTime time;
    String alertText;

    public String getLocationName(String locationName){
        return locationName;
    }
    public void setLocationName(String locationName){
        this.locationName= locationName;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getAlertText() {
        return alertText;
    }

    public void setAlertText(String alertText) {
        this.alertText = alertText;
    }

    public Alert(){

    }
}