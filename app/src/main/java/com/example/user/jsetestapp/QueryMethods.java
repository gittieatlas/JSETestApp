package com.example.user.jsetestapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QueryMethods extends Activity {

    MainActivity mainActivity;

    // URL to get locationsJsonArray JSON
    private static String locations_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/locations.php";

    // JSON Node names
    private static final String TAG_LOCATIONS = "branches";
    private static final String TAG_NAME = "name";

    // locationsJsonArray JSONArray
    JSONArray locationsJsonArray = null;



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


    public void setUpLocationsArrayList() {

        mainActivity.locationsArrayList = new ArrayList<String>();
        mainActivity.locationsArrayList.add("Location");

        // Calling async task to get json
        new getLocations().execute();
    }


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class getLocations extends AsyncTask<Void, Void, Void> {

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

            Log.d("Response: ", "> " + jsonStr);

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

}