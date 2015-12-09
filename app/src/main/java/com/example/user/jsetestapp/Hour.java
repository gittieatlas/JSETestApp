
package com.example.user.jsetestapp;

import org.joda.time.LocalTime;

import java.io.Serializable;

public class Hour implements Serializable{
    // class members
    public enum DayOfWeek {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY}

    String name;
    DayOfWeek dayOfWeek;
    LocalTime startTime, endTime;

    // constructor
    public Hour() {

    }

    /**
     * Retrieve name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param name- variable of type String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieve dayOfWeek
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
     * Retrieve startTime
     * @return LocalTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Set startTime
     * @param startTime - variable of type LocalTime
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Retrieve endTime
     * @return LocalTime
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Set endTime
     * @param endTime - variable of type LocalTime
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}