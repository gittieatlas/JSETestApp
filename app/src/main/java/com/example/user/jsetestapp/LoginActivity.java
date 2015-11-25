package com.example.user.jsetestapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    //Controls
    Toolbar toolbar;
    ScrollView scrollView;
    FrameLayout container;
    CoordinatorLayout coordinatorLayout;

    //Activities HelperClasses Classes;
    HelperMethods helperMethods;
    QueryMethods queryMethods;

    //Fragments
    LoginFragment loginFragment;
    Register1Fragment register1Fragment;
    Register2Fragment register2Fragment;
    UpdateProfileFragment updateProfileFragment;
    DashboardFragment dashboardFragment;
    LoginActivityDialogFragment loginActivityDialogFragment;
    DialogListeners dialogListeners;
    SendEmail sendEmail;
    User user;
    DatabaseOperations databaseOperations;

    //Variables
    ArrayList<Location> locationsArrayList;
    ArrayList<String> locationsNameArrayList;
    ArrayList<Test> testsArrayList;
    ArrayList<Hours> hoursArrayList;
    ArrayList<Branch> branchesArrayList;
    ArrayList<Alerts> alertsArrayList;
    Location defaultLocation;

    // private static Context sContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        Util.setContext(this);
        Util.setActivity(this);
        createFragmentsActivitiesClasses();

        setupToolbar();
        user = new User();

        helperMethods.replaceFragment(loginFragment,
                getResources().getString(R.string.toolbar_title_login),
                LoginActivity.this, scrollView);

        locationsArrayList = new ArrayList<Location>();

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        locationsArrayList = (ArrayList<Location>) bundle.getSerializable("locationsArrayList");
        queryMethods.setUpLocationsNameArrayList(this);
        branchesArrayList = (ArrayList<Branch>) bundle.getSerializable("branchesArrayList");
        testsArrayList = (ArrayList<Test>) bundle.getSerializable("testsArrayList");
        hoursArrayList = (ArrayList<Hours>) bundle.getSerializable("hoursArrayList");
        alertsArrayList = (ArrayList<Alerts>) bundle.getSerializable("alertsArrayList");


        try {
            Intent intent = getIntent();
            if (intent.getStringExtra("fragment").equals("update_profile")) {
                helperMethods.replaceFragment(
                        LoginActivity.this.updateProfileFragment,
                        "update_profile",
                        LoginActivity.this, LoginActivity.this.scrollView);

                user = (User) bundle.getSerializable("user");
                defaultLocation = (Location) bundle.getSerializable("defaultLocation");
            } else if (intent.getStringExtra("fragment").equals("log_out")) {
                helperMethods.showSnackBar("You've been logged out", coordinatorLayout);
            }
        } catch (Exception e) {

        }
    }

    public void onPause() {
        super.onPause();

        if (loginFragment.taskGetUser != null) loginFragment.taskGetUser.cancel(true);
        if (register2Fragment.taskNewUser != null) register2Fragment.taskNewUser.cancel(true);
        if (updateProfileFragment.taskUpdateUser != null) updateProfileFragment.taskUpdateUser.cancel(true);

        // Toast.makeText(this, loginFragment.taskGetUser.isCancelled() + "", Toast.LENGTH_LONG).show();


    }


    @Override
    public void onBackPressed() {

        UpdateProfileFragment updateProfileFragment = new UpdateProfileFragment();
        try {
            updateProfileFragment = (UpdateProfileFragment) getFragmentManager().findFragmentById(R.id.container);
        } catch (Exception ex) {

        }

        if (updateProfileFragment != null && updateProfileFragment.isVisible()) {
            switchToMainActivity("update_profile_cancel");
        } else if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        container = (FrameLayout) findViewById(R.id.container);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }

    private void createFragmentsActivitiesClasses() {
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
        helperMethods = new HelperMethods();
        helperMethods.setLoginActivity(this);
        queryMethods = new QueryMethods();
        queryMethods.setLoginActivity(this);
        loginActivityDialogFragment = new LoginActivityDialogFragment();
        loginActivityDialogFragment.setLoginActivity(this);
        dialogListeners = new DialogListeners();
        dialogListeners.setLoginActivity(this);
        sendEmail = new SendEmail();
        sendEmail.setLoginActivity(this);
        databaseOperations = new DatabaseOperations();
        databaseOperations.setLoginActivity(this);

    }

    private void setupToolbar() {
        toolbar.getMenu().clear(); // Clear toolbar icons
        //toolbar.setTitle(R.string.app_name);// Set title
        toolbar.setTitleTextColor(getResources().getColor(R.color.icons)); //Set title color
        // Set navigation icon
        toolbar.setNavigationIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    public void switchToMainActivity(String tag) {

        Intent intent = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("locationsArrayList", locationsArrayList);
        b.putSerializable("testsArrayList", testsArrayList);
        b.putSerializable("hoursArrayList", hoursArrayList);
        b.putSerializable("branchesArrayList", branchesArrayList);
        b.putSerializable("alertsArrayList", alertsArrayList);
        b.putSerializable("user", user);
        b.putSerializable("defaultLocation", defaultLocation);
        b.putString("tag", tag);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }


    public void setToolbarTitle(int toolbarTitle) {
        toolbar.setTitle(toolbarTitle);
    }

    public Context getContext() {
        return LoginActivity.this;
    }


    /**
     * Function to create an instance of MainActivityDialogFragment
     *
     * @param title          - alert dialog title
     * @param message        - alert message
     * @param positiveButton - text for positive button
     * @param negativeButton - text for negative button
     * @param neutralButton  - text for neutral button
     * @param icon           - drawable for icon
     * @param tagListener    - tag to pass through to listener method
     */
    public void showDialog(String title, String message, String positiveButton, String negativeButton, String neutralButton, int icon, String tagListener) {

        android.app.FragmentManager fm = this.getFragmentManager();

        LoginActivityDialogFragment dialogFragment = new LoginActivityDialogFragment();

        Bundle bundle = new Bundle();

        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putString("positiveButton", positiveButton);
        bundle.putString("negativeButton", negativeButton);
        bundle.putString("neutralButton", neutralButton);
        bundle.putInt("icon", icon);
        bundle.putString("tagListener", tagListener);

        dialogFragment.setArguments(bundle);

        dialogFragment.show(fm, tagListener);
    }

}