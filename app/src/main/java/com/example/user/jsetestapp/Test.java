package com.example.user.jsetestapp;

import org.joda.time.DateTime;

public class Test {

    public static enum DayOfWeek {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}

    public static enum Gender {MALE, FEMALE, BOTH}

    ;

    //long id;
    String location;
    DayOfWeek dayOfWeek;
    Gender gender;


    DateTime date;
    DateTime time;

    DateTime deadlineDate;
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

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public DateTime getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(DateTime deadlineDate) {
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
