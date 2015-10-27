package com.example.user.jsetestapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    //Controls


    //Activities HelperClasses Classes;


    //Fragments
    MainActivity mainActivity;

    //Variables
    Boolean isInternetPresent = false;
    ConnectionDetector cd;  // Connection detector class
    Boolean gotLocations = false;
    Boolean gotTests = false;
    ArrayList<Location> locationsArrayList;
    ArrayList<Test> testsArrayList;

    // URL to get locationsJsonArray JSON
    //private static String locations_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/locations.php";
    private static String locations_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/locations2.php";

    // URL to get testsJsonArray JSON
    private static String tests_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/tests.php";

    // URL to get hoursJsonArray JSON
    private static String hours_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/library_hours.php";


    // JSON Node names - locations
    private static final String TAG_LOCATIONS = "locations";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_PHONE = "phoneNumber";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        locationsArrayList = new ArrayList<Location>();
        testsArrayList = new ArrayList<Test>();
        checkInternetConnection();
    }

    private void checkInternetConnection() {

        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());

        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
        if (isInternetPresent) {

            new GetLocations().execute();
            new GetTests().execute();

        } else {

            showAlertDialog(SplashActivity.this, "No Internet Connection",
                    "You need an internet connection to use this application.\n\nPlease try again.\n", false);
        }
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetLocations extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                        String address = c.getString(TAG_ADDRESS);
                        String phone = c.getString(TAG_PHONE);

                        Location location = new Location();
                        location.setName(name);
                        location.setAddress(address);
                        location.setPhone(phone);

                        locationsArrayList.add(location);
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
            gotLocations = true;
            changeActivities();
        }
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetTests extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                        test.time = LocalTime.parse(new StringBuilder(c.getString(TAG_TIME)).insert(c.getString(TAG_TIME).length() - 2, ":").toString());
                        test.deadlineDate = LocalDate.parse(c.getString(TAG_CLOSING_DATE));
                        test.deadlineTime = LocalTime.parse(new StringBuilder(c.getString(TAG_CLOSING_TIME)).insert(c.getString(TAG_CLOSING_TIME).length() - 2, ":").toString());
                        test.setGender(c.getString(TAG_GENDER));

                        testsArrayList.add(test);
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
            gotTests = true;
            changeActivities();
        }
    }

    private void changeActivities() {
        if (gotLocations && gotTests) {
            // TODO check if loggedIn == true then go to MainActivity
            Intent intent = new Intent(this, LoginActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("locationsArrayList", locationsArrayList);
            b.putSerializable("testsArrayList", testsArrayList);
            intent.putExtras(b);
            startActivity(intent);
        }
    }

    /**
     * Function to display simple Alert Dialog
     *
     * @param context - application context
     * @param title   - alert dialog title
     * @param message - alert message
     * @param status  - success/failure (used to set icon)
     */
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.ic_check_grey600_24dp : R.drawable.ic_exclamation_grey600_24dp);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


}
