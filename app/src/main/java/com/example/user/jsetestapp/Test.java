package com.example.user.jsetestapp;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.Serializable;
import java.util.Comparator;

public class Test implements Serializable , Comparable<Test>{


    @Override
    public int compareTo(Test t) {
        //sorts by date
        return getDate().compareTo(t.getDate());
    }

    public static enum DayOfWeek {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};

    public static enum Gender {MALE, FEMALE, BOTH};

    //long id;
    int branchId;
    String location;
    DayOfWeek dayOfWeek, deadlineDayOfWeek;
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

    public DayOfWeek getDeadlineDayOfWeek() {
        return deadlineDayOfWeek;
    }

    public void setDeadlineDayOfWeek(DayOfWeek deadlineDayOfWeek) {
        this.deadlineDayOfWeek = deadlineDayOfWeek;
    }

    public void setDeadlineDayOfWeek(String dayOfWeek) {
        try {
            this.deadlineDayOfWeek = DayOfWeek.valueOf(dayOfWeek);
        } catch (Exception ex) {
            this.deadlineDayOfWeek = DayOfWeek.SUNDAY;
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
//    public int getGender(Test test) {
//        return gender.ordinal();
//    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGender(int gender) {
        try {
            this.gender = Gender.values()[gender-1];
        } catch (Exception ex) {
            this.gender = Gender.BOTH;
        }
    }


}


class LocationDateComparator implements Comparator<Test> {
    public int compare(Test test1, Test test2) {
        int value1 = test1.getLocation().compareTo(test2.getLocation());
        if (value1==0){
            int value2= test1.getLocation().compareToIgnoreCase(test2.getLocation());
            if (value2 == 0){
                return test2.getDate().compareTo(test2.getDate());

            }
            else
                return value2;
        }
        return value1;
    }
}

