package com.example.user.jsetestapp;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.Serializable;

public class Test implements Serializable {

    public static enum DayOfWeek {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};

    public static enum Gender {MALE, FEMALE, BOTH}

    ;

    //long id;
    int branchId;
    String location;
    DayOfWeek dayOfWeek;
    Gender gender;

    LocalDate date, deadlineDate;
    LocalTime time, deadlineTime;


    public Test() {

    }
    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public LocalDate getDate() {

        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(LocalTime deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGender(String gender) {
        try {
            this.gender = Gender.valueOf(gender);
        } catch (Exception ex) {
            this.gender = Gender.BOTH;
        }
    }
}
