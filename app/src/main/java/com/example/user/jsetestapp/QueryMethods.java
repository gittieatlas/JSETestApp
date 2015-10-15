package com.example.user.jsetestapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QueryMethods extends Activity {

    MainActivity mainActivity;

    // URL to get locationsJsonArray JSON
    private static String locations_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/locations.php";

    // URL to get testsJsonArray JSON
    private static String tests_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/tests.php";

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

    // locationsJsonArray JSONArray
    JSONArray locationsJsonArray = null;
    // testsJsonArray JSONArray
    JSONArray testsJsonArray = null;


    String pid;

    public QueryMethods() {

    }

    public ArrayList<DataObject> getTestsArrayList() {

        mainActivity.testsFitlteredArrayList = new ArrayList<DataObject>();

        for (Test test : mainActivity.testsArrayList) {
            //if test.Gender == user.gender then do...
            DataObject obj = new DataObject(test.getLocation(),
                    "Wednesday",
                    test.getTime().toString(),
                    test.getDate().toString(),
                    "Registration Deadline: ",
                    test.getDeadlineDate().toString() + " " + test.getDeadlineTime().toString());

            mainActivity.testsFitlteredArrayList.add(obj);
        }

        return mainActivity.testsFitlteredArrayList;
    }


    public void setUpLocationsArrayList() {

        mainActivity.locationsArrayList = new ArrayList<String>();
        mainActivity.locationsArrayList.add("Location");

        // Calling async task to get json
        new GetLocations().execute();
    }

    public void setUpTestsArrayList() {

        mainActivity.testsFitlteredArrayList = new ArrayList<DataObject>();
        mainActivity.testsArrayList = new ArrayList<Test>();


        // Calling async task to get json
        new GetTests().execute();
    }


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
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
                        test.date = DateTime.parse(c.getString(TAG_DATE));
                        test.time = DateTime.parse(c.getString(TAG_TIME));
                        test.deadlineDate = DateTime.parse(c.getString(TAG_CLOSING_DATE));
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

}