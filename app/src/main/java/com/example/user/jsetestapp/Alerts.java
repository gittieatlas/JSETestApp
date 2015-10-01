package com.example.user.jsetestapp;

import org.joda.time.DateTime;

public class Alerts {
    public static enum DayOfWeek{SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};

    DayOfWeek dayOfWeek;
    DateTime date, time;
    String message;

    public Alerts(){
        
    }
}
