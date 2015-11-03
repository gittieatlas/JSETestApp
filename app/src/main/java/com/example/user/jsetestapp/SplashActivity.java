package com.example.user.jsetestapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    Boolean gotHours = false;
    Boolean gotBranches = false;
    Boolean gotAlerts = false;
    ArrayList<Location> locationsArrayList;
    ArrayList<Test> testsArrayList;
    ArrayList<Hours> hoursArrayList;
    ArrayList<Branch> branchesArrayList;
    ArrayList<Alerts> alertsArrayList;
    SharedPreferences sharedPreferences;
    // URL to get locationsJsonArray JSON
    private static String branches_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/branches.php";

    // URL to get locationsJsonArray JSON
    private static String locations_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/locations.php";

    // URL to get testsJsonArray JSON
    private static String tests_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/tests.php";

    // URL to get hoursJsonArray JSON
    private static String hours_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/library_hours.php";

    // URL to get alertsJsonArray JSON
    private static String alerts_url = "http://phpstack-1830-4794-62139.cloudwaysapps.com/alerts.php";


    // JSON Node names - locations
    private static final String TAG_BRANCHES = "branches";
    private static final String TAG_ID = "id";

    // JSON Node names - locations
    private static final String TAG_LOCATIONS = "locations";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_CITY = "city";
    private static final String TAG_STATE = "state";
    private static final String TAG_ZIP = "zip";
    private static final String TAG_COUNTRY = "country";
    private static final String TAG_PHONE = "phoneNumber";

    // JSON Node names - tests
    private static final String TAG_TESTS = "tests";
    private static final String TAG_BRANCH_ID = "branchId";
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

    //JSON Nodes names - alerts
    private static final String TAG_ALERTS = "alerts";
    private static final String TAG_LOCATION_NAME = "NAME";
    private static final String TAG_ALERT_TEXT = "alertText";
    private static final String TAG_TIME_STAMP = "timeStamp";

    // locationsJsonArray JSONArray
    JSONArray locationsJsonArray = null;
    // testsJsonArray JSONArray
    JSONArray testsJsonArray = null;
    // hoursJsonArray JSONArray
    JSONArray hoursJsonArray = null;
    // branchesArrayListJsonArray JSONArray
    JSONArray branchesJsonArray = null;
    // alertsArrayList JSONArray
    JSONArray alertsJsonArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        locationsArrayList = new ArrayList<Location>();
        testsArrayList = new ArrayList<Test>();
        hoursArrayList = new ArrayList<Hours>();
        branchesArrayList = new ArrayList<Branch>();
        alertsArrayList = new ArrayList<Alerts>();
        checkInternetConnection();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
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
            new GetHours().execute();
            new GetBranches().execute();
            new getAlerts().execute();

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
                        String city = c.getString(TAG_CITY);
                        String state = c.getString(TAG_STATE);
                        String zip = c.getString(TAG_ZIP);
                        String country = c.getString(TAG_COUNTRY);
                        String phone = c.getString(TAG_PHONE);

                        StringBuilder fullAddress = new StringBuilder();
                        if (!address.equals("null")) fullAddress.append(address + " ");
                        if (!city.equals("null")) fullAddress.append(city + " ");
                        if (!state.equals("null")) fullAddress.append(state + " ");
                        if (!zip.equals("null")) fullAddress.append(zip + " ");
                        if (!country.equals("null")) fullAddress.append(country);

                        Location location = new Location();
                        location.setName(name);
                        location.setAddress(fullAddress.toString());
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

                        test.branchId = Integer.parseInt(c.getString(TAG_BRANCH_ID));
                        test.location = c.getString(TAG_LOCATION);
                        test.date = LocalDate.parse(c.getString(TAG_DATE));
                        test.setDayOfWeek(Test.DayOfWeek.values()[(test.getDate().getDayOfWeek() - 1)].toString());
                        test.time = LocalTime.parse(new StringBuilder(c.getString(TAG_TIME)).insert(c.getString(TAG_TIME).length() - 2, ":").toString());
                        test.deadlineDate = LocalDate.parse(c.getString(TAG_CLOSING_DATE));
                        test.deadlineTime = LocalTime.parse(new StringBuilder(c.getString(TAG_CLOSING_TIME)).insert(c.getString(TAG_CLOSING_TIME).length() - 2, ":").toString());
                        test.setDeadlineDayOfWeek(Test.DayOfWeek.values()[(test.getDeadlineDate().getDayOfWeek() - 1)].toString());
                        test.setGender(Integer.parseInt(c.getString(TAG_GENDER)));
                        //TODO comment out this code once login activity is done
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("gender", "1");
                        editor.commit();

                        if ((getGender(test)==(3)) ||
                                Integer.parseInt(sharedPreferences.getString("gender","3"))==(getGender(test)))
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
                        hours.setDayOfWeek(Hours.DayOfWeek.values()[(Integer.parseInt(day) - 1)]);
                        hours.startTime = LocalTime.parse(c.getString(TAG_OPENING_TIME));
                        LocalTime duration = LocalTime.parse(c.getString(TAG_DURATION));
                        hours.endTime = hours.getStartTime().plusHours(duration.getHourOfDay()).plusMinutes(duration.getMinuteOfHour());

                        hoursArrayList.add(hours);
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
            gotHours = true;
            changeActivities();
        }
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetBranches extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(branches_url, ServiceHandler.GET);

            Log.d("Response Branches: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    branchesJsonArray = jsonObj.getJSONArray(TAG_BRANCHES);

                    // looping through All Locations
                    for (int i = 0; i < branchesJsonArray.length(); i++) {

                        JSONObject c = branchesJsonArray.getJSONObject(i);

                        Branch branch = new Branch();
                        branch.id = Integer.parseInt(c.getString(TAG_ID));
                        branch.name = c.getString(TAG_NAME);
                        branchesArrayList.add(branch);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the branches_url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            gotBranches = true;
            changeActivities();
        }
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class getAlerts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(alerts_url, ServiceHandler.GET);

            Log.d("Response Alerts: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    alertsJsonArray = jsonObj.getJSONArray(TAG_ALERTS);

                    // looping through All Locations
                    for (int i = 0; i < alertsJsonArray.length(); i++) {

                        JSONObject c = alertsJsonArray.getJSONObject(i);
                        // ToDo handle if any values are null
                        Alerts alert = new Alerts();
                        alert.setLocationName(c.getString(TAG_LOCATION_NAME));
                        String timeStamp= (c.getString(TAG_TIME_STAMP));
                        String date=timeStamp.substring(0, 10);
                        alert.date = LocalDate.parse(date);
                        alert.setDayOfWeek(Alerts.DayOfWeek.values()[(alert.getDate().getDayOfWeek() - 1)]);
                        String time = timeStamp.substring(timeStamp.length() - 8);
                        alert.setTime(LocalTime.parse(time));
                        alert.setAlertText(c.getString(TAG_ALERT_TEXT));

                        alertsArrayList.add(alert);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the alerts_url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            gotAlerts = true;
            changeActivities();
        }
    }


    private void changeActivities() {
        if (gotLocations && gotTests && gotHours && gotBranches) {
            // TODO check if loggedIn == true then go to MainActivity
            Intent intent = new Intent(this, LoginActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("locationsArrayList", locationsArrayList);
            b.putSerializable("testsArrayList", testsArrayList);
            b.putSerializable("hoursArrayList", hoursArrayList);
            b.putSerializable("branchesArrayList", branchesArrayList);
            b.putSerializable("alertsArrayList", alertsArrayList);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Function to display simple Alerts Dialog
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

        // Showing Alerts Message
        alertDialog.show();
    }



    public int getGender(Test test){
        if (test.getGender().name().equals("MALE"))
            return 1;
        else if(test.getGender().name().equals("FEMALE"))
            return 2;
        else return 3;
    }


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


}
