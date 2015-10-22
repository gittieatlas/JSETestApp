package com.example.user.jsetestapp;

import android.os.AsyncTask;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rochel on 10/22/2015.
 */
public class DataMethods {

    //fragments
    SplashActivity splashActivity;
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
                        test.time = DateTime.parse(c.getString(TAG_TIME));
                        test.deadlineDate = LocalDate.parse(c.getString(TAG_CLOSING_DATE));
                        test.deadlineTime = DateTime.parse(c.getString(TAG_CLOSING_TIME));
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
                        hours.startTime = c.getString(TAG_OPENING_TIME);
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





    public void setSplashActivity(SplashActivity splashActivity) {
        this.splashActivity = splashActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
