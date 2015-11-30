package com.example.user.jsetestapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    // Declare Classes
    MainActivity mainActivity;
    HelperMethods helperMethods;
    DialogListeners dialogListeners;

    // Declare Variables
    ArrayList<Location> locationsArrayList;
    ArrayList<Test> testsArrayList;
    ArrayList<Hours> hoursArrayList;
    ArrayList<Branch> branchesArrayList;
    ArrayList<Alerts> alertsArrayList;

    // Declare Booleans
    Boolean gotLocations = false;
    Boolean gotTests = false;
    Boolean gotHours = false;
    Boolean gotBranches = false;
    Boolean gotAlerts = false;
    static boolean active = false;

    // Declare AsyncTasks
    AsyncTask taskGetLocations, taskGetTests, taskGetHours, taskGetBranches, taskGetAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call the parent activities onCreate
        super.onCreate(savedInstanceState);

        // Entry point to initialize Fabric and contained Kits
        Fabric.with(this, new Crashlytics());

        // attach xml to activity
        setContentView(R.layout.activity_splash);

        // send activity reference to Util class
        Util.setReference(this);

        instantiateFragments();
    }

    @Override
    public void onStart() {
        // call the parent activities onStart
        super.onStart();
        // set active to true
        active = true;
    }

    @Override
    public void onStop() {
        // call the parent activities onStop
        super.onStop();
        // set active to false
        active = false;
    }

    @Override
    public void onResume() {
        // call the parent activities onResume
        super.onResume();

        // check for Internet status and set true/false
        if (HelperMethods.checkInternetConnection(getApplicationContext())) {
            getDataFromDatabase();
        } else {
            displayDialog("no_internet_connection");
        }
    }

    @Override
    public void onPause() {
        // call the parent activities onPause
        super.onPause();

        cancelAsyncTasks();
    }

    /**
     * Function to instantiate fragments
     */
    private void instantiateFragments() {

        dialogListeners = new DialogListeners();
        //dialogListeners.setSplashActivity(this);
    }
    /**
     * AsyncTask class to get json by making HTTP call
     */
    private class GetLocations extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... arg0) {

            addLocationToLocationsArrayList();
            // if locationsArrayList is empty or if task is canceled
            if (locationsArrayList.size() == 0 || isCancelled()) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // if results is not null and result is true and isCancelled is false
            if (result != null && result && !isCancelled())  {
                gotLocations = true;
                changeActivities();
            } else {
                gotLocations = false;
                if (!isCancelled()) appInfoNotLoaded();
            }
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);
            gotLocations = false;
        }
    }

    /**
     * AsyncTask class to get json by making HTTP call
     */
    private class GetTests extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... arg0) {

            addTestToTestsArrayList();
            // if testsArrayList is empty or if task is canceled
            if (testsArrayList.size() == 0 || isCancelled()) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // if results is not null and result is true and isCancelled is false
            if (result != null && result && !isCancelled())  {
                gotTests = true;
                changeActivities();
            } else {
                gotTests = false;
                if (!isCancelled()) appInfoNotLoaded();

            }
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);
            gotTests = false;

        }
    }

    /**
     * AsyncTask class to get json by making HTTP call
     */
    private class GetHours extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... arg0) {

            addHoursToHoursArrayList();
            // if hoursArrayList is empty or if task is canceled
            if (hoursArrayList.size() == 0 || isCancelled()) {
                return false;
            }
            return true;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // if results is not null and result is true and isCancelled is false
            if (result != null && result && !isCancelled())  {
                gotHours = true;
                changeActivities();
            } else {
                gotHours = false;
                if (!isCancelled()) appInfoNotLoaded();
            }
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);
            gotTests = false;

        }
    }

    /**
     * AsyncTask class to get json by making HTTP call
     */
    private class GetBranches extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... arg0) {

            addBranchesToBranchesArrayList();
            // if branchesArrayList is empty or if task is canceled
            if (branchesArrayList.size() == 0 || isCancelled()) {
                return false;
            }
            return true;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // if results is not null and result is true and isCancelled is false
            if (result != null && result && !isCancelled())  {
                gotBranches = true;
                changeActivities();
            } else {
                gotBranches = false;
                if (!isCancelled()) appInfoNotLoaded();
            }
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);
            gotBranches = result;

        }
    }

    /**
     * AsyncTask class to get json by making HTTP call
     */
    private class getAlerts extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... arg0) {

            addAlertsToAlertsArrayList();
            // if alertsArrayList is empty or if task is canceled
            if (alertsArrayList.size() == 0 || isCancelled()) {
                return false;
            }
            return true;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // if results is not null and result is true and isCancelled is false
            if (result != null && result && !isCancelled())  {
                gotAlerts = true;
                changeActivities();
            } else {
                gotAlerts = false;
                if (!isCancelled()) appInfoNotLoaded();
            }
        }

         @Override
        protected void onCancelled(Boolean result) {
             super.onCancelled(result);
            gotBranches = false;

        }
    }


    private void displayDialog(String tag) {
        switch (tag) {
            case "no_internet_connection":
                showAlertDialog(SplashActivity.this, getString(R.string.d_no_connection),
                        getString(R.string.d_no_connection_msg), false);
                break;
        }
    }

    private void getDataFromDatabase() {
        setUpArrayLists();
        setUpAsyncTasks();
    }

    //instantiate arrayLists
    public void setUpArrayLists(){
        locationsArrayList = new ArrayList<Location>();
        testsArrayList = new ArrayList<Test>();
        hoursArrayList = new ArrayList<Hours>();
        branchesArrayList = new ArrayList<Branch>();
        alertsArrayList = new ArrayList<Alerts>();
    }

    //instantiate AsyncTasks and execute them
    public void setUpAsyncTasks() {
        taskGetLocations = new GetLocations().execute();
        taskGetTests = new GetTests().execute();
        taskGetHours = new GetHours().execute();
        taskGetBranches = new GetBranches().execute();
        taskGetAlerts = new getAlerts().execute();
    }

    //cancel AsyncTasks
    private void cancelAsyncTasks() {
        taskGetLocations.cancel(true);
        gotLocations = false;
        taskGetTests.cancel(true);
        gotTests = false;
        taskGetHours.cancel(true);
        gotHours = false;
        taskGetBranches.cancel(true);
        gotBranches = false;
        taskGetAlerts.cancel(true);
        gotAlerts = false;
    }

    private void appInfoNotLoaded() {
        if (active)
            showAlertDialog(this, "Load Information Fail", "Application information can not be loaded right now.", false);
        // Todo add ok = neutral and "try again" - reload async task - do getDataFromDatabase();
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
            intent.putExtra("outcome", "login");
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Function to add locations from locationJsonArray to locationsArrayList
     *
     */
    public void addLocationToLocationsArrayList() {

        JSONArray locationsJsonArray = HelperMethods.getJsonArray
                (getString(R.string.locations_url), (getString(R.string.TAG_LOCATIONS)));
        try {
            // looping through All Locations
            for (int i = 0; i < locationsJsonArray.length(); i++) {

                JSONObject location = locationsJsonArray.getJSONObject(i);
                //adding location to locationsArrayList
                locationsArrayList.add(HelperMethods.setLocation(location));

                if (taskGetLocations.isCancelled()) {
                    break;
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * Function to add tests from testsJsonArray to testsArrayList
     *
     */
    public void addTestToTestsArrayList() {

        JSONArray testsJsonArray = HelperMethods.getJsonArray
                (getString(R.string.tests_url), (getString(R.string.TAG_TESTS)));

        try {
            // looping through All Tests
            for (int i = 0; i < testsJsonArray.length(); i++) {

                JSONObject test = testsJsonArray.getJSONObject(i);

                testsArrayList.add(HelperMethods.setTest(test));

                if (taskGetTests.isCancelled()) {
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    /**
     * Function to add hours from hoursJsonArray to hoursArrayList
     *
     */
    public void addHoursToHoursArrayList() {

        JSONArray hoursJsonArray = HelperMethods.getJsonArray
                (getString(R.string.hours_url), (getString(R.string.TAG_HOURS)));

        try {

            // looping through All Tests
            for (int i = 0; i < hoursJsonArray.length(); i++) {

                JSONObject hours = hoursJsonArray.getJSONObject(i);

                hoursArrayList.add(HelperMethods.setHours(hours));
                if (taskGetHours.isCancelled()) {
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }


    /**
     * Function to add branches from branchesJsonArray to branchesArrayList
     *
     */
    public void addBranchesToBranchesArrayList() {

        JSONArray branchesJsonArray = HelperMethods.getJsonArray
                (getString(R.string.branches_url), (getString(R.string.TAG_BRANCHES)));

        try {

            // looping through All Tests
            for (int i = 0; i < branchesJsonArray.length(); i++) {

                JSONObject branch = branchesJsonArray.getJSONObject(i);

                branchesArrayList.add(HelperMethods.setBranch(branch));
                if (taskGetBranches.isCancelled()) {
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    /**
     * Function to add alerts from alertsJsonArray to alertsArrayList
     *
     */
    public void addAlertsToAlertsArrayList() {

        // Getting JSON Array node
        JSONArray alertsJsonArray = HelperMethods.getJsonArray
                (getString(R.string.alerts_url), (getString(R.string.TAG_ALERTS)));

        try {

            // looping through All Tests
            for (int i = 0; i < alertsJsonArray.length(); i++) {

                JSONObject alert = alertsJsonArray.getJSONObject(i);

                alertsArrayList.add(HelperMethods.setAlert(alert));
                if (taskGetAlerts.isCancelled()) {
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
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

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

}
