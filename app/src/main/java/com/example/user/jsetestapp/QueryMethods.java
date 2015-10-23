package com.example.user.jsetestapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryMethods extends Activity {

    MainActivity mainActivity;
    //variables
    // URL to get locationsJsonArray JSON
    private static String locations_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/locations.php";

    // URL to get testsJsonArray JSON
    private static String tests_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/tests.php";

    // URL to get hoursJsonArray JSON
    private static String hours_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/library_hours.php";


    // JSON Node names - locations
    private static final String TAG_LOCATIONS = "branches";
    private static final String TAG_NAME = "name";

    // JSON Node names - tests
    private static final String TAG_TESTS = "tests";
    private static final String TAG_LOCATION = "NAME";
    private static final String TAG_DATE = "date";
    private static final String TAG_TIME = "time";
    private static final String TAG_CLOSING_DATE = "closingDate";
    private static final String TAG_CLOSING_TIME = "closingTime";
    private static final String TAG_GENDER = "gender";

    // JSON Node names - hours
    private static final String TAG_HOURS = "hours";
    private static final String TAG_LIBRARY_LOCATION = "name";
    private static final String TAG_DAY_OF_WEEK = "dayOfWeek";
    private static final String TAG_OPENING_TIME = "openingTime";
    private static final String TAG_DURATION = "duration";

    // locationsJsonArray JSONArray
    JSONArray locationsJsonArray = null;
    // testsJsonArray JSONArray
    JSONArray testsJsonArray = null;
    // hoursJsonArray JSONArray
    JSONArray hoursJsonArray = null;

    String pid;


    public QueryMethods() {

    }

    public void setUpLocationsArrayList() {

        mainActivity.locationsArrayList = new ArrayList<String>();
        mainActivity.locationsArrayList.add("Location");

        // Calling async task to get json
        new GetLocations().execute();
    }

    public void setUpTestsArrayList() {

        mainActivity.testsFilteredArrayList = new ArrayList<DataObject>();
        mainActivity.testsArrayList = new ArrayList<Test>();


        // Calling async task to get json
        new GetTests().execute();
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
                    hours.getEndTime().toString());

            mainActivity.hoursFilteredArrayList.add(obj);
        }

        return mainActivity.hoursFilteredArrayList;
    }




    public void filterTestsArray(String location, String dayOfWeek) {
//        if (location.equals(null) && dayOfWeek.equals(null)) {
//            filter();
//        } else if (!location.equals(null) && dayOfWeek.equals(null)) {
//            filterByLocation(location);
//        } else if (location.equals(null) && !dayOfWeek.equals(null)) {
//            filterByDayOfWeek(dayOfWeek);
//        } else if (!location.equals(null) && !dayOfWeek.equals(null)) {
//            filterByLocationAndDayOfWeek(location, dayOfWeek);
//        }
        filter();

    }

    public void filter() {
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

    public void filterByLocation(String location) {
        for (Test test : mainActivity.testsArrayList) {
            //if test.Gender == user.gender then do...
            if (test.location.equals(location)) {
                DataObject obj = new DataObject(test.getLocation(),
                        "Wednesday",
                        test.getTime().toString(),
                        test.getDate().toString(),
                        "Registration Deadline: ",
                        test.getDeadlineDate().toString() + " " + test.getDeadlineTime().toString());
                mainActivity.testsFilteredArrayList.add(obj);
            }
        }
    }

    public void filterByDayOfWeek(String dayOfWeek) {
        for (Test test : mainActivity.testsArrayList) {
            //if test.Gender == user.gender then do...
            if (test.dayOfWeek.toString().equals(dayOfWeek)) {
                DataObject obj = new DataObject(test.getLocation(),
                        "Wednesday",
                        test.getTime().toString(),
                        test.getDate().toString(),
                        "Registration Deadline: ",
                        test.getDeadlineDate().toString() + " " + test.getDeadlineTime().toString());
                mainActivity.testsFilteredArrayList.add(obj);
            }
        }
    }

    public void filterByLocationAndDayOfWeek(String location, String dayOfWeek) {
        for (Test test : mainActivity.testsArrayList) {
            //if test.Gender == user.gender then do...
            if (test.location.equals(location) && test.dayOfWeek.toString().equals(dayOfWeek)) {
                DataObject obj = new DataObject(test.getLocation(),
                        "Wednesday",
                        test.getTime().toString(),
                        test.getDate().toString(),
                        "Registration Deadline: ",
                        test.getDeadlineDate().toString() + " " + test.getDeadlineTime().toString());
                mainActivity.testsFilteredArrayList.add(obj);
            }
        }
    }


//    static final DateTimeFormatter hours24 = DateTimeFormat.forPattern("HH:mm:ss");
//    static final DateTimeFormatter hours12 = DateTimeFormat.forPattern("hh:mm:ssa");
//    static String convertTo12HoursFormat(String format24hours) {
//        return hours12.print(hours24.parseDateTime(format24hours));
//    }




    /**
     * Async task class to get json by making HTTP call
     */
    private class GetLocations extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            // Showing progress dialog
//            pDialog = new ProgressDialog(mainActivity.getApplicationContext());
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(locations_url, ServiceHandler.GET);

            Log.d("Response Location: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    locationsJsonArray = jsonObj.getJSONArray(TAG_LOCATIONS);

                    // looping through All Locations
                    for (int i = 0; i < locationsJsonArray.length(); i++) {

                        JSONObject c = locationsJsonArray.getJSONObject(i);

                        String name = c.getString(TAG_NAME);
                        mainActivity.locationsArrayList.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the locations_url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetTests extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            // Showing progress dialog
//            pDialog = new ProgressDialog(mainActivity.getApplicationContext());
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(tests_url, ServiceHandler.GET);

            Log.d("Response Tests: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    testsJsonArray = jsonObj.getJSONArray(TAG_TESTS);

                    // looping through All Tests
                    for (int i = 0; i < testsJsonArray.length(); i++) {

                        JSONObject c = testsJsonArray.getJSONObject(i);

                        Test test = new Test();

                        test.location = c.getString(TAG_LOCATION);
                        test.date = LocalDate.parse(c.getString(TAG_DATE));
                        test.setDayOfWeek(Test.DayOfWeek.values()[(test.getDate().getDayOfWeek() - 1)].toString());
                        test.time = LocalTime.parse(new StringBuilder(c.getString(TAG_TIME)).insert(c.getString(TAG_TIME).length()-2,":").toString());
                        test.deadlineDate = LocalDate.parse(c.getString(TAG_CLOSING_DATE));
                        test.deadlineTime = LocalTime.parse(new StringBuilder(c.getString(TAG_CLOSING_TIME)).insert(c.getString(TAG_CLOSING_TIME).length()-2,":").toString());
                        test.setGender(c.getString(TAG_GENDER));

                        mainActivity.testsArrayList.add(test);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the tests_url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }

    public LocalDate convertStringToLocalDate(String stringDate){

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
//            // Showing progress dialog
//            pDialog = new ProgressDialog(mainActivity.getApplicationContext());
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();

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
                        //hours.dayOfWeek = c.getString(TAG_DAY_OF_WEEK);
                        hours.setDayOfWeek(c.getString(TAG_DAY_OF_WEEK));
                        hours.startTime = LocalTime.parse(c.getString(TAG_OPENING_TIME));
                        hours.endTime = c.getString(TAG_DURATION);

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