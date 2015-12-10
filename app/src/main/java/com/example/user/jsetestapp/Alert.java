package com.example.user.jsetestapp;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.Serializable;

public class Alert implements Serializable {
    // class members
    public enum DayOfWeek {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY}

    String locationName, alertText;
    DayOfWeek dayOfWeek;
    LocalDate date;
    LocalTime time;

    // constructor
    public Alert(){

    }

    /**
     * Retrieve locationName
     * @return String
     */
    public String getLocationName(String locationName){
        return locationName;
    }

    /**
     * Set locationName
     * @param locationName - variable of type String
     */
    public void setLocationName(String locationName){
        this.locationName= locationName;
    }

    /**
     * Retrieve DayOfWeek
     * @return DayOfWeek
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Set dayOfWeek
     * @param dayOfWeek - variable of type DayOfWeek
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Retrieve date
     * @return LocalDate
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set date
     * @param date - variable of type LocalDate
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Retrieve time
     * @return LocalTime
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Set time
     * @param time - variable of type LocalTime
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Retrieve alertText
     * @return String
     */
    public String getAlertText() {
        return alertText;
    }

    /**
     * Set alertText
     * @param alertText - variable of type String
     */
    public void setAlertText(String alertText) {
        this.alertText = alertText;
    }
}