package com.example.user.jsetestapp;

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
    DatabaseOperations databaseOperations;

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
    ArrayList<Hour> hourArrayList;
    ArrayList<Branch> branchesArrayList;
    ArrayList<Alert> alertArrayList;
    Location defaultLocation;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call the parent activities onCreate
        super.onCreate(savedInstanceState);

        // attach xml to activity
        setContentView(R.layout.activity_login);

        // send activity reference to Util class
        Util.setReference(this);

        instantiateClasses();
        instantiateFragments();
        initializeViews();
        initializeVariables();
        inflateScrollViewWithFragment();
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
            // launch activity with main activity intent
            Util.launchActivity(getLaunchMainActivityIntent("update_profile_cancel"));
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
        hourArrayList = (ArrayList<Hour>) getIntent().getExtras().getSerializable("hourArrayList");
        alertArrayList = (ArrayList<Alert>) getIntent().getExtras().getSerializable("alertArrayList");

        // assign array list values from locationsArrayList
        locationsNameArrayList = helperMethods.setUpLocationsNameArrayList(locationsArrayList);
    }

    /**
     * Function to get source of intent and inflate scroll view with fragment
     */
    private void inflateScrollViewWithFragment() {
        // if getIntentOutcome = "update_profile", inflate update profile  fragment
        if (getIntent().getExtras().getString("outcome").equals("update_profile")) {
            // inflate scrollView with UpdateProfileFragment
            HelperMethods.replaceFragment(updateProfileFragment,
                    getResources().getString(R.string.toolbar_title_update_profile));
        }
        // else inflate login fragment
        else {
            // inflate scrollView with LoginFragment
            HelperMethods.replaceFragment(loginFragment,
                    getResources().getString(R.string.toolbar_title_login));
        }
    }

    /**
     * Function to get info from intent extras
     */
    private void getInfoFromIntent() {
        // if getIntentOutcome = "log_out", inflate login fragment
        if (getIntent().getExtras().getString("outcome").equals("log_out")) {

            //  show snack bar - "log out successful"
            helperMethods.showSnackBar(getString(R.string.msg_logged_out));

        }
        // else if getIntentOutcome = "update_profile", get user and default location from intent
        else if (getIntent().getExtras().getString("outcome").equals("update_profile")) {

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
     * Function to create an intent to launch MainActivity
     * @param tag - string to describe intent intention
     * @return Intent
     */
    public Intent getLaunchMainActivityIntent(String tag) {
        // create new intent for current activity to launch MainActivity
        Intent intent = new Intent(Util.getActivity(), MainActivity.class);

        // attach bundle to intent
        intent.putExtras(getLaunchMainActivityBundle(tag));

        return intent;
    }

    /**
     * Function to create bundle for MainActivity
     * @param outcome - string to describe intent intention
     * @return bundle
     */
    public Bundle getLaunchMainActivityBundle(String outcome) {
        // create bundle
        Bundle bundle = new Bundle();

        // put array lists, user, default location, and tag in to bundle
        bundle.putSerializable("locationsArrayList", locationsArrayList);
        bundle.putSerializable("testsArrayList", testsArrayList);
        bundle.putSerializable("hourArrayList", hourArrayList);
        bundle.putSerializable("branchesArrayList", branchesArrayList);
        bundle.putSerializable("alertArrayList", alertArrayList);
        bundle.putSerializable("user", user);
        bundle.putSerializable("defaultLocation", defaultLocation);
        bundle.putString("outcome", outcome);

        return bundle;
    }

    /**
     * Function to check which dialog the positive button is from
     * @param listenerTag - tag of dialog created
     */
    public void dialogFragmentPositiveClick(String listenerTag) {

        if (listenerTag.equals(Util.getActivity().getResources()
                .getResourceEntryName(R.array.forgot_password))){
            // ToDo send email with password
        }
    }

    /**
     * Function to check which dialog the neutral button is from
     * @param listenerTag - tag of dialog created
     */
    public void dialogFragmentNeutralClick(String listenerTag) {
        if (listenerTag.equals(Util.getActivity().getResources()
                        .getResourceEntryName(R.array.create_account_failed_email_duplicate))){
            getFragmentManager().popBackStack();
        }
    }
}