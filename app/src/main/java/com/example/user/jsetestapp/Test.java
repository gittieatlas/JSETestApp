package com.example.user.jsetestapp;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class Test {

    public static enum DayOfWeek {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};

    public static enum Gender {MALE, FEMALE, BOTH}

    ;

    //long id;
    String location;
    DayOfWeek dayOfWeek;
    Gender gender;


    LocalDate date;
    DateTime time;

    LocalDate deadlineDate;
    DateTime deadlineTime;


    public Test() {


//        location = "";
//        dayOfWeek = DayOfWeek.SUNDAY;
//        date = DateTime.now();
//        deadlineDate = DateTime.now();
//        time = DateTime.now();
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

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public DateTime getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(DateTime deadlineTime) {
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
