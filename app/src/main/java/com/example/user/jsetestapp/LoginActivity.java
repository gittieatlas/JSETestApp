package com.example.user.jsetestapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    // Declare Controls
    Toolbar toolbar;
    ScrollView scrollView;
    FrameLayout container;
    CoordinatorLayout coordinatorLayout;

    // Declare Classes;
    HelperMethods helperMethods;
    QueryMethods queryMethods;
    DatabaseOperations databaseOperations;
    DialogListeners dialogListeners;

    // Declare Fragments
    LoginFragment loginFragment;
    Register1Fragment register1Fragment;
    Register2Fragment register2Fragment;
    UpdateProfileFragment updateProfileFragment;
    DashboardFragment dashboardFragment;

    // Declare Variables
    ArrayList<Location> locationsArrayList;
    ArrayList<String> locationsNameArrayList;
    ArrayList<Test> testsArrayList;
    ArrayList<Hours> hoursArrayList;
    ArrayList<Branch> branchesArrayList;
    ArrayList<Alerts> alertsArrayList;
    Location defaultLocation;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // call the parent activities onCreate
        super.onCreate(savedInstanceState);

        // attach xml to activity
        setContentView(R.layout.activity_login);

        instantiateClasses();
        instantiateFragments();
        initializeViews();
        initializeVariables();
        inflateScrollViewWithFragment();

        // send activity reference to Util class
        Util.setReference(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        getInfoFromIntent();

    }

    @Override
    public void onPause() {
        super.onPause();

        cancelRunningAsyncTasks();
    }

    @Override
    public void onBackPressed() {

        // if update profile is visible
        if (updateProfileFragment != null && updateProfileFragment.isVisible()) {
            // go to main activity with "update_profile_cancel" tag
            switchToMainActivity("update_profile_cancel");
        }
        // if there are fragments in the back stack
        else if (getFragmentManager().getBackStackEntryCount() > 1) {
            // undo the last back stack transaction
            getFragmentManager().popBackStack();
        }
        // if there are no fragments in the back stack
        else {
            // finish this activity
            super.onBackPressed();
        }
    }

    /**
     * Function to instantiate classes
     */
    private void instantiateClasses() {
        helperMethods = new HelperMethods();
        helperMethods.setLoginActivity(this);
        queryMethods = new QueryMethods();
        queryMethods.setLoginActivity(this);
        dialogListeners = new DialogListeners();
        dialogListeners.setLoginActivity(this);
        databaseOperations = new DatabaseOperations();
        databaseOperations.setLoginActivity(this);
    }

    /**
     * Function to instantiate fragments
     */
    private void instantiateFragments() {
        loginFragment = new LoginFragment();
        loginFragment.setLoginActivity(this);
        register1Fragment = new Register1Fragment();
        register1Fragment.setLoginActivity(this);
        register2Fragment = new Register2Fragment();
        register2Fragment.setLoginActivity(this);
        updateProfileFragment = new UpdateProfileFragment();
        updateProfileFragment.setLoginActivity(this);
        dashboardFragment = new DashboardFragment();
        dashboardFragment.setLoginActivity(this);
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews() {

        // initialize and reference controls
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        container = (FrameLayout) findViewById(R.id.container);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }

    /**
     * Function to initialize variables and assign its values
     */
    private void initializeVariables() {

        // initialize user
        user = new User();

        // assign array list values from intent
        locationsArrayList = (ArrayList<Location>) getIntent().getExtras().getSerializable("locationsArrayList");
        branchesArrayList = (ArrayList<Branch>) getIntent().getExtras().getSerializable("branchesArrayList");
        testsArrayList = (ArrayList<Test>) getIntent().getExtras().getSerializable("testsArrayList");
        hoursArrayList = (ArrayList<Hours>) getIntent().getExtras().getSerializable("hoursArrayList");
        alertsArrayList = (ArrayList<Alerts>) getIntent().getExtras().getSerializable("alertsArrayList");

        // assign array list values from locationsArrayList
        locationsNameArrayList = queryMethods.setUpLocationsNameArrayList(locationsArrayList);
    }

    /**
     * Function to get source of intent and inflate scroll view with fragment
     */
    private void inflateScrollViewWithFragment() {

        // if getIntentOutcome = "update_profile", inflate update profile  fragment
        if (getIntentOutcome() != null && getIntentOutcome().equals("update_profile")) {

            // inflate scrollView with UpdateProfileFragment
            helperMethods.replaceFragment(updateProfileFragment,
                    getResources().getString(R.string.toolbar_title_update_profile),
                    LoginActivity.this, LoginActivity.this.scrollView);
        }
        // else inflate login fragment
        else {

            // inflate scrollView with LoginFragment
            helperMethods.replaceFragment(loginFragment,
                    getResources().getString(R.string.toolbar_title_login),
                    LoginActivity.this, scrollView);
        }
    }

    /**
     * Function to get "outcome" from intent
     *
     * @return String - try to return intent extra with tag "outcome", else return null
     */
    public String getIntentOutcome() {

        // if intent exists
        if (this.getIntent() != null) {
            // Obtain String with tag "fragment" from Intent
            return this.getIntent().getExtras().getString("outcome");
        } else
            return null;
    }

    /**
     * Function to get info from intent extras
     */
    private void getInfoFromIntent() {

        // if getIntentOutcome = "log_out", inflate login fragment
        if (getIntentOutcome() != null && getIntentOutcome().equals("log_out")) {

            //  show snack bar - "log out successful"
            helperMethods.showSnackBar(getString(R.string.msg_logged_out), coordinatorLayout);

        }
        // else if getIntentOutcome = "update_profile", get user and default location from intent
        else if (getIntentOutcome() != null && getIntentOutcome().equals("update_profile")) {

            // get user info from intent extras
            user = (User) this.getIntent().getExtras().getSerializable("user");

            // get default location info from intent extras
            defaultLocation =
                    (Location) this.getIntent().getExtras().getSerializable("defaultLocation");
        }
    }

    /**
     * Function to cancel async tasks that are running
     */
    private void cancelRunningAsyncTasks() {

        // if async task GetUser  is not null- cancel the task
        if (loginFragment.taskGetUser != null)
            loginFragment.taskGetUser.cancel(true);
        // if async task NewUser  is not null- cancel the task
        if (register2Fragment.taskNewUser != null)
            register2Fragment.taskNewUser.cancel(true);
        // if async task UpdateUser  is not null- cancel the task
        if (updateProfileFragment.taskUpdateUser != null)
            updateProfileFragment.taskUpdateUser.cancel(true);
    }

    /**
     * Function to create an Activity intent and go to Main Activity
     *
     * @param tag - tag for new activity to pick up
     */
    public void switchToMainActivity(String tag) {

        // create a new intent from this activity to MainActivity
        Intent intent = new Intent(this, MainActivity.class);

        // create bundle
        Bundle b = new Bundle();

        // put array lists, user, default location, and tag in to bundle
        b.putSerializable("locationsArrayList", locationsArrayList);
        b.putSerializable("testsArrayList", testsArrayList);
        b.putSerializable("hoursArrayList", hoursArrayList);
        b.putSerializable("branchesArrayList", branchesArrayList);
        b.putSerializable("alertsArrayList", alertsArrayList);
        b.putSerializable("user", user);
        b.putSerializable("defaultLocation", defaultLocation);
        b.putString("tag", tag);

        // attach bundle to intent
        intent.putExtras(b);

        // start new intent
        startActivity(intent);

        // close this activity
        finish();
    }


    /**
     * Function to return reference of current activities context
     *
     * @return context
     */
    public Context getContext() {
        return LoginActivity.this;
    }




}