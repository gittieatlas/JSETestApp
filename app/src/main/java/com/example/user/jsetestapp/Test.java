package com.example.user.jsetestapp;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.Serializable;
import java.util.Comparator;

public class Test implements Serializable , Comparable<Test>{
    // class members
    public enum DayOfWeek {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY}

    public enum Gender {MALE, FEMALE, BOTH}

    int branchId;
    String location;
    DayOfWeek dayOfWeek, deadlineDayOfWeek;
    Gender gender;
    LocalDate date, deadlineDate;
    LocalTime time, deadlineTime;

    // constructor
    public Test() {

    }

    /**
     * Retrieve branchId
     * @return int
     */
    public int getBranchId() {
        return branchId;
    }

    /**
     * Set branchId
     * @param branchId- variable of type int
     */
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    /**
     * Retrieve location
     * @return String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set location
     * @param location- variable of type String
     */
    public void setLocation(String location) {
        this.location = location;
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
     * @param dayOfWeek- variable of type DayOfWeek
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Set dayOfWeek
     * @param dayOfWeek- variable of type String
     */
    public void setDayOfWeek(String dayOfWeek) {
        // dayOfWeek equals dayOfWeek
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek);
    }

    /**
     * Retrieve deadlineDayOfWeek
     * @return DayOfWeek
     */
    public DayOfWeek getDeadlineDayOfWeek() {
        return deadlineDayOfWeek;
    }

    /**
     * Set deadlineDayOfWeek
     * @param deadlineDayOfWeek- variable of type DayOfWeek
     */
    public void setDeadlineDayOfWeek(DayOfWeek deadlineDayOfWeek) {
        this.deadlineDayOfWeek = deadlineDayOfWeek;
    }

    /**
     * Set deadlineDayOfWeek
     * @param dayOfWeek- variable of type String
     */
    public void setDeadlineDayOfWeek(String dayOfWeek) {
        try {
            // deadlineDayOfWeek equals to deadlineDayOfWeek
            this.deadlineDayOfWeek = DayOfWeek.valueOf(dayOfWeek);
        } catch (Exception ex) {
            // deadlineDayOfWeek equals to sunday
            this.deadlineDayOfWeek = DayOfWeek.SUNDAY;
        }
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
     * @param date- variable of type LocalDate
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Retrieve deadlineDate
     * @return LocalDate
     */
    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    /**
     * Set deadlineDate
     * @param deadlineDate- variable of type LocalDate
     */
    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
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
     * @param time- variable of type LocalTime
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Retrieve deadlineTime
     * @return LocalTime
     */
    public LocalTime getDeadlineTime() {
        return deadlineTime;
    }

    /**
     * Set deadlineTime
     * @param deadlineTime- variable of type LocalTime
     */
    public void setDeadlineTime(LocalTime deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    /**
     * Retrieve gender
     * @return Gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Set gender
     * @param gender- variable of type Gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Set gender
     * @param gender- variable of type int
     */
    public void setGender(int gender) {
        try {
            // gender equals gender param minus 1
            this.gender = Gender.values()[gender-1];
        } catch (Exception ex) {
            // gender equals BOTH
            this.gender = Gender.BOTH;
        }
    }


    // Overriding the compareTo method
    @Override
    public int compareTo(Test t) {
        //sorts by date
        return getDate().compareTo(t.getDate());
    }
}

/**
 * LocationDateComparator class compares tests by location and date
 *
 */
class LocationDateComparator implements Comparator<Test> {
    /**
     * Function that compares 2 value.
     * @param test1 - of type Test
     * @param test2 - of type Test
     * @return int
     *
     */
    public int compare(Test test1, Test test2) {
        // compare location of test1 and test2 and store result in value1
        int value1 = test1.getLocation().compareTo(test2.getLocation());
        // if value1 is 0
        if (value1==0){
            // compare location of test1 and test2 and store result in value2
            int value2= test1.getLocation().compareToIgnoreCase(test2.getLocation());
            // if value2 is 0
            if (value2 == 0){
                return test2.getDate().compareTo(test2.getDate());
            } // if value2 is not 0
            else
                return value2;
        }
        return value1;
    }
}

