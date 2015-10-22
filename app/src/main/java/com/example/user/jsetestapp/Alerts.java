package com.example.user.jsetestapp;

import org.joda.time.DateTime;

public class Alerts {
    public static enum DayOfWeek {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};

    DayOfWeek dayOfWeek;
    DateTime date, time;
    String message;

    public Alerts(){
        
    }
}
