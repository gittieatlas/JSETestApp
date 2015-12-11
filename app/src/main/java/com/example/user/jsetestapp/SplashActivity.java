package com.example.user.jsetestapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    // Declare Classes
    HelperMethods helperMethods;

    // Declare Variables
    private ArrayList<Location> locationsArrayList;
    private ArrayList<Test> testsArrayList;
    private ArrayList<Hour> hourArrayList;
    private ArrayList<Branch> branchesArrayList;
    private ArrayList<Alert> alertArrayList;

    // Declare Booleans
    private Boolean gotLocations = false;
    private Boolean gotTests = false;
    private Boolean gotHours = false;
    private Boolean gotBranches = false;
    private Boolean gotAlerts = false;
    private boolean activityIsInForeground = false;

    // Declare AsyncTasks
    private AsyncTask taskGetLocations, taskGetTests, taskGetHours, taskGetBranches, taskGetAlerts;

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

        instantiateClasses();
    }

    @Override
    public void onStart() {
        // call the parent activities onStart
        super.onStart();

        // set activityIsInForeground to true
        activityIsInForeground = true;
    }

    @Override
    public void onStop() {
        // call the parent activities onStop
        super.onStop();

        // set activityIsInForeground to false
        activityIsInForeground = false;
    }

    @Override
    public void onResume() {
        // call the parent activities onResume
        super.onResume();

        // check for Internet status and set true/false
        if (Util.checkInternetConnection()) {
            getDataFromDatabase();
        } else {
            // call method showDialog and send tag "d_no_internet_connection"
            Util.showDialogFragment(R.array.no_internet_connection);
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
    private void instantiateClasses() {
        helperMethods = new HelperMethods();
        helperMethods.setSplashActivity(this);
    }

    /**
     * class to get list of locations from the database
     */
    private class GetLocations extends AsyncTask<Void, Void, Boolean> {

        /**
         * method to get json by making HTTP call
         * @param params - params to use for the task
         * @return Boolean - return true/false if task was successful.
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            addLocationToLocationsArrayList();

            // return true/false if locationsArrayList is empty or if task is canceled
            return !(locationsArrayList.size() == 0 || isCancelled());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // if results is not null and result is true and task is not cancelled
            if (result != null && result && !isCancelled()) {
                // setting gotLocation to true
                gotLocations = true;

                changeActivities();
            } else {
                // setting gotLocation to false
                gotLocations = false;

                //if isCancelled is false
                if (!isCancelled()) appInfoNotLoaded();
            }
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);
            // set gotLocation to false
            gotLocations = false;
        }
    }

    /**
     * class to get list of tests from the database
     */
    private class GetTests extends AsyncTask<Void, Void, Boolean> {

        /**
         * method to get json by making HTTP call
         * @param params - params to use for the task
         * @return Boolean - return true/false if task was successful.
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            addTestToTestsArrayList();

            // return true/false if testsArrayList is empty or if task is canceled
            return !(testsArrayList.size() == 0 || isCancelled());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // if results is not null and result is true and task is not cancelled
            if (result != null && result && !isCancelled()) {
                // setting gotTests to true
                gotTests = true;

                changeActivities();
            } else {
                // setting gotTests to false
                gotTests = false;

                //if isCancelled is false
                if (!isCancelled()) appInfoNotLoaded();
            }
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);
            // set gotTests to false
            gotTests = false;
        }
    }

    /**
     * class to get list of hours from the database
     */
    private class GetHours extends AsyncTask<Void, Void, Boolean> {

        /**
         * method to get json by making HTTP call
         * @param params - params to use for the task
         * @return Boolean - return true/false if task was successful.
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            addHoursToHoursArrayList();

            // return true/false if hourArrayList is empty or if task is canceled
            return !(hourArrayList.size() == 0 || isCancelled());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // if results is not null and result is true and task is not cancelled
            if (result != null && result && !isCancelled()) {
                // setting gotHours to true
                gotHours = true;

                changeActivities();
            } else {
                // setting gotHours to false
                gotHours = false;

                //if isCancelled is false
                if (!isCancelled()) appInfoNotLoaded();
            }
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);

            // set gotHours to false
            gotHours = false;
        }
    }

    /**
     * class to get list of branches from the database
     */
    private class GetBranches extends AsyncTask<Void, Void, Boolean> {

        /**
         * method to get json by making HTTP call
         * @param params - params to use for the task
         * @return Boolean - return true/false if task was successful.
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            addBranchesToBranchesArrayList();

            // return true/false if branchesArrayList is empty or if task is canceled
            return !(branchesArrayList.size() == 0 || isCancelled());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // if results is not null and result is true and task is not cancelled
            if (result != null && result && !isCancelled()) {
                // setting gotBranches to true
                gotBranches = true;

                changeActivities();
            } else {
                // setting gotBranches to false
                gotBranches = false;

                //if isCancelled is false
                if (!isCancelled()) appInfoNotLoaded();
            }
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);
            // set gotBranches to false
            gotBranches = false;
        }
    }

    /**
     * class to get list of alerts from the database
     */
    private class getAlerts extends AsyncTask<Void, Void, Boolean> {

        /**
         * method to get json by making HTTP call
         * @param params - params to use for the task
         * @return Boolean - return true/false if task was successful.
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            addAlertsToAlertsArrayList();

            // return true/false if alertArrayList is empty or if task is canceled
            return !(alertArrayList.size() == 0 || isCancelled());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            // if results is not null and result is true and task is not cancelled
            if (result != null && result && !isCancelled()) {
                // set gotAlerts to true
                gotAlerts = true;

                changeActivities();
            } else {
                // set gotAlerts to false
                gotAlerts = false;

                //if isCancelled is false
                if (!isCancelled()) appInfoNotLoaded();
            }
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);
            // set gotAlerts to false
            gotAlerts = false;
        }
    }

    /**
     * Function to get data from the database
     */
    private void getDataFromDatabase() {
        setUpArrayLists();
        setUpAsyncTasks();
    }

    /**
     * Function to initialize new array list
     */
    public void setUpArrayLists() {
        // instantiate arrayLists
        locationsArrayList = new ArrayList<>();
        testsArrayList = new ArrayList<>();
        hourArrayList = new ArrayList<>();
        branchesArrayList = new ArrayList<>();
        alertArrayList = new ArrayList<>();
    }

    /**
     * Function initialize async tasks and execute them
     */
    public void setUpAsyncTasks() {
        // instantiate AsyncTasks and execute them
        taskGetLocations = new GetLocations().execute();
        taskGetTests = new GetTests().execute();
        taskGetHours = new GetHours().execute();
        taskGetBranches = new GetBranches().execute();
        taskGetAlerts = new getAlerts().execute();
    }

    /**
     * Function cancel AsyncTasks and set booleans false
     */
    private void cancelAsyncTasks() {
        // if asyncTask has already been called
        if (taskGetLocations != null) {
            // cancel the task
            taskGetLocations.cancel(true);
            // set boolean of got data to false
            gotLocations = false;
        }

        // if asyncTask has already been called
        if (taskGetTests != null) {
            // cancel the task
            taskGetTests.cancel(true);
            gotTests = false;
        }

        // if asyncTask has already been called
        if (taskGetHours != null) {
            taskGetHours.cancel(true);
            // cancel the task
            // set boolean of got data to false
            gotHours = false;
        }

        // if asyncTask has already been called
        if (taskGetBranches != null) {
            // cancel the task
            taskGetBranches.cancel(true);
            // set boolean of got data to false
            gotBranches = false;
        }

        // if asyncTask has already been called
        if (taskGetAlerts != null) {
            // cancel the task
            taskGetAlerts.cancel(true);
            // set boolean of got data to false
            gotAlerts = false;
        }
    }

    /**
     * Function is called when app info could not perform http requests or execute them properly
     */
    private void appInfoNotLoaded() {
        // if activity is showing
        if (activityIsInForeground)
            // Show Dialog: Load Info Fail
            Util.showDialogFragment(R.array.load_info_fail);
    }

    private void changeActivities() {
        // if all asyncTasks were completed successfully / all app data is loaded
        if (gotLocations && gotTests && gotHours && gotBranches && gotAlerts) {
            // launch activity with login activity intent
            Util.launchActivity(getLaunchLoginActivityIntent("login"));
        }
    }

    /**
     * Function to create an intent to launch LoginActivity
     * @param tag - string to describe intent intention
     * @return Intent
     */
    public Intent getLaunchLoginActivityIntent(String tag) {
        // create new intent for current activity to launch LoginActivity
        Intent intent = new Intent(Util.getActivity(), LoginActivity.class);

        // attach bundle to intent
        intent.putExtras(getLaunchLoginActivityBundle(tag));

        return intent;
    }

    /**
     * Function to create bundle for LoginActivity
     * @param outcome - string to describe intent intention
     * @return bundle
     */
    public Bundle getLaunchLoginActivityBundle(String outcome) {
        // create bundle
        Bundle bundle = new Bundle();

        // put array lists, user, default location, and tag in to bundle
        bundle.putSerializable("locationsArrayList", locationsArrayList);
        bundle.putSerializable("testsArrayList", testsArrayList);
        bundle.putSerializable("hourArrayList", hourArrayList);
        bundle.putSerializable("branchesArrayList", branchesArrayList);
        bundle.putSerializable("alertArrayList", alertArrayList);
        bundle.putString("outcome", outcome);

        return bundle;
    }

    /**
     * Function to add locations from locationJsonArray to locationsArrayList
     */
    public void addLocationToLocationsArrayList() {
        // get JsonArray of locations from Json string
        JSONArray locationsJsonArray = HelperMethods.getJsonArray(
                Util.getActivity().getString(R.string.locations_url),
                Util.getActivity().getString(R.string.TAG_LOCATIONS),
                new ArrayList<NameValuePair>());
        try {
            // try to loop through locationsJsonArray
            for (int i = 0; i < locationsJsonArray.length(); i++) {
                // get location from JsonObject
                JSONObject location = locationsJsonArray.getJSONObject(i);
                // convert JSONObject to location and add to locationsArrayList
                locationsArrayList.add(HelperMethods.setLocation(location));
                // if taskGetLocations is cancelled
                if (taskGetLocations.isCancelled()) {
                    // exit method
                    break;
                }
            }
        } catch (JSONException e) {
            // if Json string is empty print error in console
            e.printStackTrace();
        }
    }

    /**
     * Function to add tests from testsJsonArray to testsArrayList
     */
    public void addTestToTestsArrayList() {
        // get JsonArray of tests from Json string
        JSONArray testsJsonArray = HelperMethods.getJsonArray(
                Util.getActivity().getString(R.string.tests_url),
                Util.getActivity().getString(R.string.TAG_TESTS),
                new ArrayList<NameValuePair>());

        try {
            // try to loop through testsJsonArray
            for (int i = 0; i < testsJsonArray.length(); i++) {
                // get test from JsonObject
                JSONObject test = testsJsonArray.getJSONObject(i);
                // convert JSONObject to test and add to testsArrayList
                testsArrayList.add(HelperMethods.setTest(test));
                // if taskGetTests is cancelled
                if (taskGetTests.isCancelled()) {
                    // exit method
                    break;
                }
            }
        } catch (JSONException e) {
            // if Json string is empty print error in console
            e.printStackTrace();
        }

    }

    /**
     * Function to add hours from hoursJsonArray to hourArrayList
     */
    public void addHoursToHoursArrayList() {
        // get JsonArray of hours from Json string
        JSONArray hoursJsonArray = HelperMethods.getJsonArray(
                Util.getActivity().getString(R.string.hours_url),
                Util.getActivity().getString(R.string.TAG_HOURS),
                new ArrayList<NameValuePair>());
        try {
            // try to looping through hoursJsonArray
            for (int i = 0; i < hoursJsonArray.length(); i++) {
                // get hours from JsonObject
                JSONObject hours = hoursJsonArray.getJSONObject(i);
                // convert JSONObject to hours and add to hourArrayList
                hourArrayList.add(HelperMethods.setHours(hours));
                // if taskGetHours is cancelled
                if (taskGetHours.isCancelled()) {
                    // exit method
                    break;
                }
            }
        } catch (JSONException e) {
            // if Json string is empty print error in console
            e.printStackTrace();
        }
    }

    /**
     * Function to add branches from branchesJsonArray to branchesArrayList
     */
    public void addBranchesToBranchesArrayList() {
        // get JsonArray of branches from Json string
        JSONArray branchesJsonArray = HelperMethods.getJsonArray(
                Util.getActivity().getString(R.string.branches_url),
                Util.getActivity().getString(R.string.TAG_BRANCHES),
                new ArrayList<NameValuePair>());

        try {
            // try to loop through branchesJsonArray
            for (int i = 0; i < branchesJsonArray.length(); i++) {
                // get branch from JsonObject
                JSONObject branch = branchesJsonArray.getJSONObject(i);
                // convert JSONObject to branch and add to branchesArrayList
                branchesArrayList.add(HelperMethods.setBranch(branch));
                // if taskGetBranches is cancelled
                if (taskGetBranches.isCancelled()) {
                    // exit method
                    break;
                }
            }
        } catch (JSONException e) {
            // if Json string is empty print error in console
            e.printStackTrace();
        }
    }

    /**
     * Function to add alerts from alertsJsonArray to alertArrayList
     */
    public void addAlertsToAlertsArrayList() {
        // get JsonArray of alerts from Json string
        JSONArray alertsJsonArray = HelperMethods.getJsonArray(
                Util.getActivity().getString(R.string.alerts_url),
                Util.getActivity().getString(R.string.TAG_ALERTS),
                new ArrayList<NameValuePair>());

        try {
            // try to loop through alertsJsonArray
            for (int i = 0; i < alertsJsonArray.length(); i++) {
                // get alert from JsonObject
                JSONObject alert = alertsJsonArray.getJSONObject(i);
                // convert JSONObject to alert and add to alertArrayList
                alertArrayList.add(HelperMethods.setAlert(alert));
                // if taskGetAlerts is cancelled
                if (taskGetAlerts.isCancelled()) {
                    // exit method
                    break;
                }
            }
        } catch (JSONException e) {
            // if Json string is empty print error in console
            e.printStackTrace();
        }
    }

    /**
     * Function to check which dialog the positive button is from
     * @param listenerTag - tag of dialog created
     */
    public void dialogFragmentPositiveClick(String listenerTag) {
        if (listenerTag.equals(Util.getActivity().getResources()
                .getResourceEntryName(R.array.load_info_fail))) {
            // Todo reload async task - do getDataFromDatabase();
        }
    }
}
