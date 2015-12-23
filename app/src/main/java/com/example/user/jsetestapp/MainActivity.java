package com.example.user.jsetestapp;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declare Controls
    TabLayout tabLayout;
    Toolbar toolbar;
    LinearLayout toolbarLinearLayout;
    LinearLayout scrollViewLinearLayout;
    ScrollView scrollView;
    LinearLayout tabLayoutLinearLayout;
    FrameLayout container;
    CoordinatorLayout coordinatorLayout;

    // Declare Classes
    HelperMethods helperMethods;

    // Declare Fragments
    ContactFragment contactFragment;
    SearchFragment searchFragment;
    LibrariesFragment librariesFragment;
    DashboardFragment dashboardFragment;
    ResultsFragment resultsFragment;

    // Declare Variables
    ArrayList<Location> locationsArrayList;
    ArrayList<String> locationsNameArrayList;
    ArrayList<Branch> branchesArrayList;
    ArrayList<String> branchesNameArrayList;
    ArrayList<Test> testsArrayList;
    ArrayList<TestDataObject> testsFilteredArrayList;
    ArrayList<com.example.user.jsetestapp.Hour> hourArrayList;
    ArrayList<HoursDataObject> hoursFilteredArrayList;
    ArrayList<Alert> alertArrayList;
    User user;
    Location defaultLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // call the parent activities onCreate
        super.onCreate(savedInstanceState);

        // attach xml to activity
        setContentView(R.layout.activity_main);

        // send activity reference to Util class
        Util.setReference(this);

        instantiateClasses();
        instantiateFragments();
        initializeViews();
        initializeVariables();
        setupToolbar();
        setupTablayout();
        setScrollViewMinHeight();
        inflateScrollViewWithFragment();
        getInfoFromIntent();
    }

    @Override
    public void onBackPressed() {
        // if there are fragments in the back stack
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            // undo the last back stack transaction
            getFragmentManager().popBackStack();
            // if there are no fragments in the back stack
        } else {
            // finish this activity
            super.onBackPressed();
        }
    }

    /**
     * Function to instantiate classes
     */
    private void instantiateClasses() {
        helperMethods = new HelperMethods();
        helperMethods.setMainActivity(this);
    }

    /**
     * Function to instantiate fragments
     */
    private void instantiateFragments() {
        contactFragment = new ContactFragment();
        contactFragment.setMainActivity(this);
        searchFragment = new SearchFragment();
        searchFragment.setMainActivity(this);
        librariesFragment = new LibrariesFragment();
        librariesFragment.setMainActivity(this);
        dashboardFragment = new DashboardFragment();
        dashboardFragment.setMainActivity(this);
        resultsFragment = new ResultsFragment();
        resultsFragment.setMainActivity(this);
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews() {
        // initialize and reference controls
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarLinearLayout = (LinearLayout) findViewById(R.id.toolbarLinearLayout);
        scrollViewLinearLayout = (LinearLayout) findViewById(R.id.scrollViewLinearLayout);
        tabLayoutLinearLayout = (LinearLayout) findViewById(R.id.tabLayoutLinearLayout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        container = (FrameLayout) findViewById(R.id.container);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }

    /**
     * Function to initialize variables and assign its values
     */
    private void initializeVariables() {
        // assign array list values from intent
        user = (User) getIntent().getExtras().getSerializable("user");
        locationsArrayList = (ArrayList<Location>) getIntent().getExtras()
                .getSerializable("locationsArrayList");
        branchesArrayList = (ArrayList<Branch>) getIntent().getExtras()
                .getSerializable("branchesArrayList");
        testsArrayList = helperMethods.filterTestsArrayListByGender((ArrayList<Test>) getIntent()
                .getExtras().getSerializable("testsArrayList"));
        hourArrayList = (ArrayList<Hour>) getIntent().getExtras()
                .getSerializable("hourArrayList");
        alertArrayList = (ArrayList<Alert>) getIntent().getExtras()
                .getSerializable("alertArrayList");

        // set up default location
        defaultLocation = helperMethods.setUpDefaultLocation();

        // assign array list values from locationsArrayList
        locationsNameArrayList = helperMethods.setUpLocationsNameArrayList(locationsArrayList);

        // assign array list values from branchesArrayList
        branchesNameArrayList = helperMethods.setUpBranchesNameArrayList(branchesArrayList);

        // initialize arrayLists
        testsFilteredArrayList = new ArrayList<>();
        hoursFilteredArrayList = new ArrayList<>();

        // set up isJseMember
        helperMethods.setUpJseStudentId();
    }

    /**
     * Function to set up toolBar
     */
    private void setupToolbar() {
        assert getSupportActionBar() != null;
        // designate toolbar as the action bar for this activity
        setSupportActionBar(toolbar);
        // set title
        getSupportActionBar().setTitle(getString(R.string.tabLayout_dashboard));
        // Inflate menu
        toolbar.inflateMenu(R.menu.menu_main);
        // Set navigation icon
        toolbar.setNavigationIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        // Navigation onClickLister
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if resultsFragment is visible
                if (resultsFragment.isVisible())
                    // pop the backStack
                    getFragmentManager().popBackStack();
            }
        });
    }

    /**
     * Function to set up tabLayout
     */
    private void setupTablayout() {
        // createTab and send layout, textView and text
        createTab(R.layout.tab_layout_dashboard, R.id.tab_title_dashboard, getString(R.string.tabLayout_dashboard));
        createTab(R.layout.tab_layout_tests, R.id.tab_title_tests, getString(R.string.tabLayout_tests));
        createTab(R.layout.tab_layout_libraries, R.id.tab_title_libraries, getString(R.string.tabLayout_libraries));
        createTab(R.layout.tab_layout_contact, R.id.tab_title_contact, getString(R.string.tabLayout_contact));

        tabLayout.setOnTabSelectedListener(tabListener);
    }

    /**
     * Function to set up each tab
     *
     * @param view      - the view
     * @param titleView - textView
     * @param title     - text for tab
     */
    public void createTab(int view, int titleView, String title) {
        // initialize the tab
        TabLayout.Tab tab = tabLayout.newTab();
        // set the view
        tab.setCustomView(view);
        // add this tab to the tabLayout
        tabLayout.addTab(tab);
        // set customized text on the tab
        ((TextView) findViewById(titleView)).setText(title);
    }

    /**
     * initializing a tabListener
     */
    TabLayout.OnTabSelectedListener tabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            // check selected tab item position
            switch (tabLayout.getSelectedTabPosition()) {
                // first tab - Dashboard
                case 0:
                    // check if dashboardFragment is visible or not
                    if (!dashboardFragment.isVisible()) {
                        // if its not visible call a method that will return this fragment
                        HelperMethods.replaceFragment(dashboardFragment,
                                getResources().getString(R.string.toolbar_title_dashboard));
                    }
                    // exit the switch statement
                    break;
                // second tab - Search
                case 1:
                    // check if searchFragment is visible or not
                    if (!searchFragment.isVisible()) {
                        // if its not visible call a method that will return this fragment
                        HelperMethods.replaceFragment(searchFragment,
                                getResources().getString(R.string.toolbar_title_search));
                    }
                    // exit the switch statement
                    break;
                // third tab - Libraries
                case 2:
                    // check if librariesFragment is visible or not
                    if (!librariesFragment.isVisible()) {
                        // if its not visible call a method that will return this fragment
                        HelperMethods.replaceFragment(librariesFragment,
                                getResources().getString(R.string.toolbar_title_libraries));
                    }
                    // exit the switch statement
                    break;
                // fourth tab - Contact
                case 3:
                    // check if contactFragment is visible or not
                    if (!contactFragment.isVisible()) {
                        // if its not visible call a method that will return this fragment
                        HelperMethods.replaceFragment(contactFragment,
                                getResources().getString(R.string.toolbar_title_contact));
                    }
                    // exit the switch statement
                    break;
                // error handler - used if any of the other cases are not used.
                default:
                    // exit the switch statement
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };



    /**
     * Function to sets the minimum height of the scrollView
     */
    private void setScrollViewMinHeight() {
        // get screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        // sets the minimum height
        scrollView.setMinimumHeight(height);
    }

    /**
     * Function to inflate scroll view with fragment
     */
    private void inflateScrollViewWithFragment() {
        // inflate scrollView with dashboardFragment
        HelperMethods.replaceFragment(dashboardFragment,
                getString(R.string.toolbar_title_dashboard));
    }

    /**
     * Function to get info from intent extras
     */
    private void getInfoFromIntent() {
        // if intent outcome = "create_account"
        if (getIntent().getExtras().getString("outcome").equals("create_account")) {
            // call showSnackBar and send tag "Account created"
            helperMethods.showSnackBar("Account created");
            // if intent outcome = "login"
        } else if (getIntent().getExtras().getString("outcome").equals("login")) {
            // call showSnackBar and send tag "Logged in"
            helperMethods.showSnackBar("Logged in");
            // if intent outcome = "update_profile"
        } else if (getIntent().getExtras().getString("outcome").equals("update_profile")) {
            // call showSnackBar and send tag "Profile updated"
            helperMethods.showSnackBar("Profile updated");
            // if intent outcome = "update_profile_cancel"
        } else if (getIntent().getExtras().getString("outcome").equals("update_profile_cancel")) {
            // call showSnackBar and send tag "Profile not updated"
            helperMethods.showSnackBar("Profile not updated");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // if selected menu item is log out
        if (id == R.id.log_out) {
            // launch activity with login activity intent
            Util.launchActivity(getLaunchLoginActivityIntent("log_out"));
            return true;
        }

        // if selected menu item is update profile
        if (id == R.id.update_profile) {
            // launch activity with login activity intent
            Util.launchActivity(getLaunchLoginActivityIntent("update_profile"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Function to create an intent to launch MainActivity
     *
     * @param outcome - string to describe intent intention
     * @return Intent
     */
    public Intent getLaunchLoginActivityIntent(String outcome) {
        // create new intent for current activity to launch MainActivity
        Intent intent = new Intent(Util.getActivity(), LoginActivity.class);

        // attach bundle to intent
        intent.putExtras(getLaunchLoginActivityBundle(outcome));

        return intent;
    }

    /**
     * Function to create bundle for MainActivity
     *
     * @param outcome - string to describe intent intention
     * @return bundle
     */
    public Bundle getLaunchLoginActivityBundle(String outcome) {

        testsArrayList = (ArrayList<Test>) getIntent()
                .getExtras().getSerializable("testsArrayList");

        // create bundle
        Bundle bundle = new Bundle();

        // put array lists, default location, and outcome in to bundle
        bundle.putSerializable("locationsArrayList", locationsArrayList);
        bundle.putSerializable("testsArrayList", testsArrayList);
        bundle.putSerializable("hourArrayList", hourArrayList);
        bundle.putSerializable("branchesArrayList", branchesArrayList);
        bundle.putSerializable("alertArrayList", alertArrayList);
        // check if outcome is update_profile or not
        if (outcome.equals("update_profile"))
            // put user in to bundle
            bundle.putSerializable("user", user);
        bundle.putSerializable("defaultLocation", defaultLocation);
        bundle.putString("outcome", outcome);

        return bundle;
    }

    /**
     * Function to check which dialog the neutral button is from
     * @param listenerTag - tag of dialog created
     */
    public void dialogFragmentPositiveClick(String listenerTag) {

        // if listenerTag equals call_jse_during_non_office_hours
        if (listenerTag.equals( Util.getActivity().getResources()
                .getResourceEntryName(R.array.call_jse_during_non_office_hours))) {
            // set reminder to call JSE during office hours
            helperMethods.setReminderToCallJse();
        }

        // if listenerTag equals call_jse_during_office_hours
        if (listenerTag.equals(Util.getActivity().getResources()
                .getResourceEntryName(R.array.call_jse_during_office_hours))) {
            // call JSE Office
            Util.callIntent(Util.getStringValue(R.string.jse_phone_number));
        }

        // if listenerTag equals schedule_test
        if (listenerTag.equals(Util.getActivity().getResources()
                .getResourceEntryName(R.array.schedule_test))) {
            // Call schedule test phone number
            Util.callIntent(Util.getStringValue(R.string.schedule_test_phone_number));
        }

        // if listenerTag equals become_jse_member
        if (listenerTag.equals(Util.getActivity().getResources()
                .getResourceEntryName(R.array.become_jse_member))) {
            // call JSE office
            Util.callIntent(Util.getStringValue(R.string.jse_phone_number));
        }

    }
}