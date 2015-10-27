package com.example.user.jsetestapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryMethods extends Activity {

    MainActivity mainActivity;

    // URL to get hoursJsonArray JSON
    private static String hours_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/library_hours.php";

    // JSON Node names - hours
    private static final String TAG_HOURS = "hours";
    private static final String TAG_LIBRARY_LOCATION = "name";
    private static final String TAG_DAY_OF_WEEK = "dayOfWeek";
    private static final String TAG_OPENING_TIME = "openingTime";
    private static final String TAG_DURATION = "duration";

    // hoursJsonArray JSONArray
    JSONArray hoursJsonArray = null;

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

    public void setUpTestsArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.testsArrayList = (ArrayList<Test>) bundle.getSerializable("testsArrayList");
    }

    public void setUpTestsFilteredArrayList() {

       mainActivity.testsFilteredArrayList = new ArrayList<DataObject>();
    }

    public void setUpHoursArrayList() {

        mainActivity.hoursFilteredArrayList = new ArrayList<HoursDataObject>();
        mainActivity.hoursArrayList = new ArrayList<Hours>();

        // Calling async task to get json
        new GetHours().execute();
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


    public ArrayList<HoursDataObject> getHoursArrayList() {

        mainActivity.hoursFilteredArrayList = new ArrayList<HoursDataObject>();

        for (Hours hours : mainActivity.hoursArrayList) {

            HoursDataObject obj = new HoursDataObject(mainActivity.helperMethods.firstLetterCaps(hours.getDayOfWeek().toString()),

                    hours.getStartTime().toString("hh:mm a"),
                    hours.getEndTime().toString("hh:mm a"));

            mainActivity.hoursFilteredArrayList.add(obj);
        }

        return mainActivity.hoursFilteredArrayList;
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

    public LocalDate convertStringToLocalDate(String stringDate) {

        List strList = new ArrayList(); // this part iterates
        strList.addAll(Arrays.asList(stringDate.split("-")));
        strList.toArray(); // you have an array with all the split string.

        int year = Integer.parseInt(strList.get(0).toString());
        int month = Integer.parseInt(strList.get(1).toString());
        int day = Integer.parseInt(strList.get(2).toString());

        LocalDate date = new LocalDate(year, month, day);
        return date;
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetHours extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(hours_url, ServiceHandler.GET);

            Log.d("Response Hours: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    hoursJsonArray = jsonObj.getJSONArray(TAG_HOURS);

                    // looping through All Tests
                    for (int i = 0; i < hoursJsonArray.length(); i++) {

                        JSONObject c = hoursJsonArray.getJSONObject(i);

                        Hours hours = new Hours();
                        hours.name = c.getString(TAG_LIBRARY_LOCATION);
                        String day = c.getString(TAG_DAY_OF_WEEK);
                        hours.setDayOfWeek(Hours.DayOfWeek.values()[(Integer.parseInt(day)-1)]);
                        hours.startTime = LocalTime.parse(c.getString(TAG_OPENING_TIME));
                        LocalTime duration = LocalTime.parse(c.getString(TAG_DURATION));
                        hours.endTime = hours.getStartTime().plusHours(duration.getHourOfDay()).plusMinutes(duration.getMinuteOfHour());

                        mainActivity.hoursArrayList.add(hours);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the hours_url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}


