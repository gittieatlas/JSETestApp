package com.example.user.jsetestapp;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

public class QueryMethods extends Activity {

    MainActivity mainActivity;

    public QueryMethods() {

    }

    public void setUpLocationsArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.locationsArrayList = (ArrayList<Location>) bundle.getSerializable("locationsArrayList");

    }

    public void setUpLocationsNameArrayList() {
        mainActivity.locationsNameArrayList = new ArrayList<String>();
        mainActivity.locationsNameArrayList.add("Location");
        for (Location location : mainActivity.locationsArrayList) {
            mainActivity.locationsNameArrayList.add(location.getName());
        }
    }

    public void setUpBranchesNameArrayList() {
        mainActivity.branchesNameArrayList = new ArrayList<String>();
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.branchesNameArrayList = (ArrayList<String>) bundle.getSerializable("branchesNameArrayList");

    }

    public void setUpTestsArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.testsArrayList = (ArrayList<Test>) bundle.getSerializable("testsArrayList");
    }

    public void setUpTestsFilteredArrayList() {

       mainActivity.testsFilteredArrayList = new ArrayList<DataObject>();
    }

    public void setUpHoursArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.hoursArrayList = (ArrayList<Hours>) bundle.getSerializable("hoursArrayList");
    }

    public void setUpHoursFilteredArrayList() {

        mainActivity.hoursFilteredArrayList = new ArrayList<HoursDataObject>();
    }

    public ArrayList<DataObject> getTestsArrayList() {

        mainActivity.testsFilteredArrayList = new ArrayList<DataObject>();

        for (Test test : mainActivity.testsArrayList) {
            //if test.Gender == user.gender then do...
            DataObject obj = new DataObject(test.getLocation(),
                    "Wednesday",
                    test.getTime().toString(),
                    //mainActivity.helperMethods.convertLocalDateToString(test.getDate()),
                    test.getDate().toString("MMMMMMMMM dd yyyy"),
                    "Registration Deadline: ",
                    test.getDeadlineDate().toString() + " " + test.getDeadlineTime().toString());

            mainActivity.testsFilteredArrayList.add(obj);
        }

        return mainActivity.testsFilteredArrayList;
    }


    public ArrayList<HoursDataObject> getFilteredHoursArrayList(String location) {

        if (mainActivity.getHoursFilteredArrayList()!= null)
            mainActivity.getHoursFilteredArrayList().clear();
        for (Hours hours : mainActivity.hoursArrayList) {

            if (hours.getName().equals(location)) {

                HoursDataObject obj = new HoursDataObject(mainActivity.helperMethods.firstLetterCaps(hours.getDayOfWeek().toString()),

                        hours.getStartTime().toString("hh:mm a"),
                        hours.getEndTime().toString("hh:mm a"));

                mainActivity.hoursFilteredArrayList.add(obj);
            }
        }
        return mainActivity.getHoursFilteredArrayList();
    }

    public void filter(String location, String dayOfWeek) {
        for (Test test : mainActivity.testsArrayList) {
            //if test.Gender == user.gender then do...
            DataObject obj = new DataObject(test.getLocation(),
                    mainActivity.helperMethods.firstLetterCaps(test.getDayOfWeek().toString()),
                    test.getTime().toString("hh:mm a"),
                    test.getDate().toString("MMMM dd yyyy"),
                    "Registration Deadline: ",
                    test.getDeadlineDate().toString("MMMM dd yyyy") + " " + test.getDeadlineTime().toString("hh:mm a"));
            mainActivity.testsFilteredArrayList.add(obj);
        }
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}


