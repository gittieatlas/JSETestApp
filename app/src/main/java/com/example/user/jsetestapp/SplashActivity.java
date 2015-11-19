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
    MainActivity mainActivity;

    //Fragments

    //Variables
    ArrayList<Location> locationsArrayList;
    ArrayList<Test> testsArrayList;
    ArrayList<Hours> hoursArrayList;
    ArrayList<Branch> branchesArrayList;
    ArrayList<Alerts> alertsArrayList;

    Boolean gotLocations = false;
    Boolean gotTests = false;
    Boolean gotHours = false;
    Boolean gotBranches = false;
    Boolean gotAlerts = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Entry point to initialize Fabric and contained Kits
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash);

        // check for Internet status and set true/false
        if (HelperMethods.checkInternetConnection(getApplicationContext())) {
            getDataFromDatabase();
        } else {
            displayDialog("no_internet_connection");
        }
    }

    private void getDataFromDatabase() {

        setUpLocations();
        setUpTests();
        setUpHours();
        setUpBranches();
        setUpAlerts();

    }

    public void setUpLocations() {
        locationsArrayList = new ArrayList<Location>();
        new GetLocations().execute();
    }

    public void setUpTests() {
        testsArrayList = new ArrayList<Test>();
        new GetTests().execute();

    }

    public void setUpHours() {
        hoursArrayList = new ArrayList<Hours>();
        new GetHours().execute();

    }

    public void setUpBranches() {
        branchesArrayList = new ArrayList<Branch>();
        new GetBranches().execute();

    }

    public void setUpAlerts() {
        alertsArrayList = new ArrayList<Alerts>();
        new getAlerts().execute();

    }


    private void displayDialog(String tag) {
        switch (tag) {
            case "no_internet_connection":
                showAlertDialog(SplashActivity.this, getString(R.string.d_no_connection),
                        getString(R.string.d_no_connection_msg), false);
                break;
        }
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetLocations extends AsyncTask<Void, Void, Void> {

        // URL to get locationsJsonArray JSON
        String locations_url =
                "http://phpstack-1830-4794-62139.cloudwaysapps.com/locations.php";
        // JSON Node names - locations
        final String TAG_LOCATIONS = "locations";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(locations_url, ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray locationsJsonArray = jsonObj.getJSONArray(TAG_LOCATIONS);

                    // looping through All Locations
                    for (int i = 0; i < locationsJsonArray.length(); i++) {

                        JSONObject c = locationsJsonArray.getJSONObject(i);

                        locationsArrayList.add(setLocation(c));
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


    public Location setLocation(JSONObject c) {


        // JSON Node names - locations
        final String TAG__LOCATION_ID = "id";
        final String TAG__BRANCH_ID = "branchId";
        final String TAG_NAME = "name";
        final String TAG_PHONE = "phoneNumber";

        Location location = new Location();

        try {


            location.setId(Integer.parseInt(c.getString(TAG__LOCATION_ID)));
            location.setBrachId(Integer.parseInt(c.getString(TAG__BRANCH_ID)));
            location.setName(c.getString(TAG_NAME));
            location.setAddress(getAddress(c));
            location.setPhone(c.getString(TAG_PHONE));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return location;
    }

    private String getAddress(JSONObject c) {

        final String TAG_ADDRESS = "address";
        final String TAG_CITY = "city";
        final String TAG_STATE = "state";
        final String TAG_ZIP = "zip";
        final String TAG_COUNTRY = "country";

        StringBuilder fullAddress = new StringBuilder();

        try {

            String address = c.getString(TAG_ADDRESS);
            String city = c.getString(TAG_CITY);
            String state = c.getString(TAG_STATE);
            String zip = c.getString(TAG_ZIP);
            String country = c.getString(TAG_COUNTRY);

            if (!address.equals("null")) fullAddress.append(address + " ");
            if (!city.equals("null")) fullAddress.append(city + " ");
            if (!state.equals("null")) fullAddress.append(state + " ");
            if (!zip.equals("null")) fullAddress.append(zip + " ");
            if (!country.equals("null")) fullAddress.append(country);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fullAddress.toString();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetTests extends AsyncTask<Void, Void, Void> {

        // URL to get testsJsonArray JSON
        private String tests_url =
                "http://phpstack-1830-4794-62139.cloudwaysapps.com/tests.php";

        // JSON Node names - tests
        private static final String TAG_TESTS = "tests";
        private static final String TAG_BRANCH_ID = "branchId";
        private static final String TAG_LOCATION = "NAME";
        private static final String TAG_DATE = "date";
        private static final String TAG_TIME = "time";
        private static final String TAG_CLOSING_DATE = "closingDate";
        private static final String TAG_CLOSING_TIME = "closingTime";
        private static final String TAG_GENDER = "gender";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(tests_url, ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray testsJsonArray = jsonObj.getJSONArray(TAG_TESTS);

                    // looping through All Tests
                    for (int i = 0; i < testsJsonArray.length(); i++) {

                        JSONObject c = testsJsonArray.getJSONObject(i);

                        Test test = new Test();

                        test.branchId = Integer.parseInt(c.getString(TAG_BRANCH_ID));
                        test.location = c.getString(TAG_LOCATION);
                        test.date = LocalDate.parse(c.getString(TAG_DATE));
                        test.setDayOfWeek(Test.DayOfWeek.values()[
                                (test.getDate().getDayOfWeek() - 1)].toString());
                        test.time = LocalTime.parse(new StringBuilder(
                                c.getString(TAG_TIME)).insert(
                                c.getString(TAG_TIME).length() - 2, ":").toString());
                        test.deadlineDate = LocalDate.parse(c.getString(TAG_CLOSING_DATE));
                        test.deadlineTime = LocalTime.parse(new StringBuilder(
                                c.getString(TAG_CLOSING_TIME)).insert(
                                c.getString(TAG_CLOSING_TIME).length() - 2, ":").toString());
                        test.setDeadlineDayOfWeek(Test.DayOfWeek.values()[
                                (test.getDeadlineDate().getDayOfWeek() - 1)].toString());
                        test.setGender(Integer.parseInt(c.getString(TAG_GENDER)));
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

        // URL to get hoursJsonArray JSON
        private String hours_url =
                "http://phpstack-1830-4794-62139.cloudwaysapps.com/library_hours.php";

        // JSON Node names - hours
        private static final String TAG_HOURS = "hours";
        private static final String TAG_LIBRARY_LOCATION = "name";
        private static final String TAG_DAY_OF_WEEK = "dayOfWeek";
        private static final String TAG_OPENING_TIME = "openingTime";
        private static final String TAG_DURATION = "duration";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(hours_url, ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray hoursJsonArray = jsonObj.getJSONArray(TAG_HOURS);

                    // looping through All Tests
                    for (int i = 0; i < hoursJsonArray.length(); i++) {

                        JSONObject c = hoursJsonArray.getJSONObject(i);

                        Hours hours = new Hours();
                        hours.name = c.getString(TAG_LIBRARY_LOCATION);
                        String day = c.getString(TAG_DAY_OF_WEEK);
                        hours.setDayOfWeek(Hours.DayOfWeek.values()[(Integer.parseInt(day) - 1)]);
                        hours.startTime = LocalTime.parse(c.getString(TAG_OPENING_TIME));
                        LocalTime duration = LocalTime.parse(c.getString(TAG_DURATION));
                        hours.endTime = hours.getStartTime().plusHours(duration.getHourOfDay())
                                .plusMinutes(duration.getMinuteOfHour());

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

        // URL to get branchesJsonArray JSON
        private String branches_url =
                "http://phpstack-1830-4794-62139.cloudwaysapps.com/branches.php";

        // JSON Node names - branches
        private static final String TAG_BRANCHES = "branches";
        private static final String TAG_ID = "id";
        final String TAG_NAME = "name";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(branches_url, ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray branchesJsonArray = jsonObj.getJSONArray(TAG_BRANCHES);

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

        // URL to get alertsJsonArray JSON
        private static final String alerts_url =
                "http://phpstack-1830-4794-62139.cloudwaysapps.com/alerts.php";

        //JSON Nodes names - alerts
        private static final String TAG_ALERTS = "alerts";
        private static final String TAG_LOCATION_NAME = "NAME";
        private static final String TAG_ALERT_TEXT = "alertText";
        private static final String TAG_TIME_STAMP = "timeStamp";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(alerts_url, ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray alertsJsonArray = jsonObj.getJSONArray(TAG_ALERTS);

                    // looping through All Locations
                    for (int i = 0; i < alertsJsonArray.length(); i++) {

                        JSONObject c = alertsJsonArray.getJSONObject(i);
                        // ToDo handle if any values are null
                        Alerts alert = new Alerts();
                        alert.setLocationName(c.getString(TAG_LOCATION_NAME));
                        String timeStamp = (c.getString(TAG_TIME_STAMP));
                        String date = timeStamp.substring(0, 10);
                        alert.date = LocalDate.parse(date);
                        alert.setDayOfWeek(Alerts.DayOfWeek.values()[(alert.getDate()
                                .getDayOfWeek() - 1)]);
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
        if (gotLocations && gotTests && gotHours && gotBranches && gotAlerts) {
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
        if (status)
            alertDialog.setIcon(R.drawable.ic_check_grey600_24dp);
        else
            alertDialog.setIcon(R.drawable.ic_exclamation_grey600_24dp);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alerts Message
        alertDialog.show();
    }


    public int getGender(Test test) {
        if (test.getGender().name().equals("MALE"))
            return 1;
        else if (test.getGender().name().equals("FEMALE"))
            return 2;
        else return 3;
    }


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


}
