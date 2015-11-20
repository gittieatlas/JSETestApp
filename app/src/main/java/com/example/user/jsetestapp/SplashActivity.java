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

        Util.setContext(this);
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(getString(R.string.locations_url), ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray locationsJsonArray = jsonObj.getJSONArray(getString(R.string.TAG_LOCATIONS));

                    // looping through All Locations
                    for (int i = 0; i < locationsJsonArray.length(); i++) {

                        JSONObject location = locationsJsonArray.getJSONObject(i);

                        locationsArrayList.add(setLocation(location));
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


    public Location setLocation(JSONObject locationObject) {

      Location location = new Location();

        try {

            location.setId(Integer.parseInt(locationObject.getString(getString(R.string.TAG__LOCATION_ID))));
            location.setBrachId(Integer.parseInt(locationObject.getString(getString(R.string.TAG__BRANCH_ID))));
            location.setName(locationObject.getString(getString(R.string.TAG_NAME)));
            location.setAddress(getAddress(locationObject));
            location.setPhone(locationObject.getString(getString(R.string.TAG_PHONE)));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return location;
    }

    private String getAddress(JSONObject locationObject) {

        StringBuilder fullAddress = new StringBuilder();

        try {

            String address = locationObject.getString(getString(R.string.TAG_ADDRESS));
            String city = locationObject.getString(getString(R.string.TAG_CITY));
            String state = locationObject.getString(getString(R.string.TAG_STATE));
            String zip = locationObject.getString(getString(R.string.TAG_ZIP));
            String country = locationObject.getString(getString(R.string.TAG_COUNTRY));

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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(getString(R.string.tests_url), ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray testsJsonArray = jsonObj.getJSONArray(getString(R.string.TAG_TESTS));

                    // looping through All Tests
                    for (int i = 0; i < testsJsonArray.length(); i++) {

                        JSONObject test = testsJsonArray.getJSONObject(i);

                        testsArrayList.add(setTest(test));
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

    public Test setTest(JSONObject testObject) {

        Test test = new Test();

        try {

            test.branchId = Integer.parseInt(testObject.getString(getString(R.string.TAG_BRANCH_ID)));
            test.location = testObject.getString(getString(R.string.TAG_LOCATION));
            test.date = LocalDate.parse(testObject.getString(getString(R.string.TAG_DATE)));
            test.setDayOfWeek(Test.DayOfWeek.values()[
                    (test.getDate().getDayOfWeek() - 1)].toString());
            test.time = LocalTime.parse(new StringBuilder(
                    testObject.getString(getString(R.string.TAG_TIME))).insert(
                    testObject.getString(getString(R.string.TAG_TIME)).length() - 2, ":").toString());
            test.deadlineDate = LocalDate.parse(testObject.getString(getString(R.string.TAG_CLOSING_DATE)));
            test.deadlineTime = LocalTime.parse(new StringBuilder(
                    testObject.getString(getString(R.string.TAG_CLOSING_TIME))).insert(
                    testObject.getString(getString(R.string.TAG_CLOSING_TIME)).length() - 2, ":").toString());
            test.setDeadlineDayOfWeek(Test.DayOfWeek.values()[
                    (test.getDeadlineDate().getDayOfWeek() - 1)].toString());
            test.setGender(Integer.parseInt(testObject.getString(getString(R.string.TAG_GENDER))));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return test;
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
            ServiceHandler sh = new ServiceHandler();
            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(getString(R.string.hours_url), ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray hoursJsonArray = jsonObj.getJSONArray(getString(R.string.TAG_HOURS));

                    // looping through All Tests
                    for (int i = 0; i < hoursJsonArray.length(); i++) {

                        JSONObject hours = hoursJsonArray.getJSONObject(i);

                        hoursArrayList.add(setHours(hours));
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

    public Hours setHours(JSONObject hoursObject) {



        Hours hours = new Hours();

        try {

            hours.name = hoursObject.getString(getString(R.string.TAG_LIBRARY_LOCATION));
            String day = hoursObject.getString(getString(R.string.TAG_DAY_OF_WEEK));
            hours.setDayOfWeek(Hours.DayOfWeek.values()[(Integer.parseInt(day) - 1)]);
            hours.startTime = LocalTime.parse(hoursObject.getString(getString(R.string.TAG_OPENING_TIME)));
            LocalTime duration = LocalTime.parse(hoursObject.getString(getString(R.string.TAG_DURATION)));
            hours.endTime = hours.getStartTime().plusHours(duration.getHourOfDay())
                    .plusMinutes(duration.getMinuteOfHour());

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return hours;

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
            ServiceHandler sh = new ServiceHandler();
            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(getString(R.string.branches_url), ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray branchesJsonArray = jsonObj.getJSONArray(getString(R.string.TAG_BRANCHES));

                    // looping through All Locations
                    for (int i = 0; i < branchesJsonArray.length(); i++) {

                        JSONObject branch = branchesJsonArray.getJSONObject(i);


                        branchesArrayList.add(setBranch(branch));
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

    public Branch setBranch(JSONObject branchObject){



        Branch branch = new Branch();
        try {

            branch.id = Integer.parseInt(branchObject.getString(getString(R.string.TAG_ID)));
            branch.name = branchObject.getString(getString(R.string.TAG_BRANCH_NAME));

        }catch (JSONException e) {
                e.printStackTrace();
            }

        return branch;
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
            ServiceHandler sh = new ServiceHandler();
            // Making a request to locations_url and getting response
            String jsonStr = sh.makeServiceCall(getString(R.string.alerts_url), ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray alertsJsonArray = jsonObj.getJSONArray(getString(R.string.TAG_ALERTS));

                    // looping through All Locations
                    for (int i = 0; i < alertsJsonArray.length(); i++) {

                        JSONObject alert = alertsJsonArray.getJSONObject(i);

                        alertsArrayList.add(setAlert(alert));

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

    public Alerts setAlert(JSONObject alertObject) {
// ToDo handle if any values are null

        Alerts alert = new Alerts();

        try {
            alert.setLocationName(alertObject.getString(getString(R.string.TAG_LOCATION_NAME)));
            String timeStamp = (alertObject.getString(getString(R.string.TAG_TIME_STAMP)));
            String date = timeStamp.substring(0, 10);
            alert.date = LocalDate.parse(date);
            alert.setDayOfWeek(Alerts.DayOfWeek.values()[(alert.getDate()
                    .getDayOfWeek() - 1)]);
            String time = timeStamp.substring(timeStamp.length() - 8);
            alert.setTime(LocalTime.parse(time));
            alert.setAlertText(alertObject.getString(getString(R.string.TAG_ALERT_TEXT)));
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return alert;
    }

    private void changeActivities() {
        if (gotLocations && gotTests && gotHours && gotBranches && gotAlerts) {

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
