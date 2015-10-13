package com.example.user.jsetestapp;

import android.app.Activity;

import java.util.ArrayList;

public class QueryMethods extends Activity {

    MainActivity mainActivity;

    public QueryMethods() {

    }

    public ArrayList<DataObject> getTestsArrayList() {

        mainActivity.testsArrayList = new ArrayList<DataObject>();

        //TODO add dataobjects to arraylist from query
        DataObject obj = new DataObject("Brooklyn - HASC",
                "Wednesday", "10:30 AM", "September 8 2015", "Registration Deadline: ", "September 7 2015");
        mainActivity.testsArrayList.add(obj);

        DataObject obj2 = new DataObject("Lakewood - BBY",
                "Tuesday", "12:15 PM", "November 15 2015", "Registration Deadline: ", "November 10 2015");
        mainActivity.testsArrayList.add(obj2);

        return mainActivity.testsArrayList;
    }

    public ArrayList<String> getLocationsArrayList() {
        mainActivity.locationsArrayList = new ArrayList<String>();

        mainActivity.locationsArrayList.add("Location");

        // TODO Fill array list with values from query
        mainActivity.locationsArrayList.add("Brooklyn");
        mainActivity.locationsArrayList.add("Lakewood");
        mainActivity.locationsArrayList.add("Monsey");

        return mainActivity.locationsArrayList;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

}