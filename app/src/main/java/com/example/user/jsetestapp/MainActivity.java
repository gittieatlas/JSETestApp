package com.example.user.jsetestapp;

import android.content.Context;
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
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declare Controls
    TabLayout tabLayout;
    Toolbar toolbar;
    ScrollView scrollView;
    LinearLayout tabLayoutLinearLayout;
    FrameLayout container;
    CoordinatorLayout coordinatorLayout;

    // Declare Classes
    HelperMethods helperMethods;
    QueryMethods queryMethods;
    DialogListeners dialogListeners;
    SplashActivity splashActivity;
    DatabaseOperations databaseOperations;

    // Declare Fragments
    LoginFragment loginFragment;
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
    ArrayList<DataObject> testsFilteredArrayList = new ArrayList<DataObject>();
    ArrayList<Hours> hoursArrayList;
    ArrayList<HoursDataObject> hoursFilteredArrayList = new ArrayList<HoursDataObject>();
    ArrayList<Alerts> alertsArrayList;
    User user = new User();
    Location defaultLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // call the parent activities onCreate
        super.onCreate(savedInstanceState);

        // attach xml to activity
        setContentView(R.layout.activity_main);

        instantiateClasses();
        instantiateFragments();
        initializeViews();
        initializeVariables();
        setupToolbar();
        setupTablayout();
        setScrollViewMinHeight();
        inflateScrollViewWithFragment();
        helperMethods.checkTag();

        // send activity reference to Util class
        Util.setReference(this);
    }

    @Override
    public void onBackPressed() {
        // if backStack contains more than fragment/activity
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            // then pop the most recent one.
            getFragmentManager().popBackStack();
        // if it has one or less
        } else {
            // close the app
            super.onBackPressed();
        }
    }

    /**
     * Function to instantiate classes
     */
    private void instantiateClasses() {
        helperMethods = new HelperMethods();
        helperMethods.setMainActivity(this);
        queryMethods = new QueryMethods();
        queryMethods.setMainActivity(this);
        dialogListeners = new DialogListeners();
        dialogListeners.setMainActivity(this);
        splashActivity = new SplashActivity();
        splashActivity.setMainActivity(this);
        databaseOperations = new DatabaseOperations();
        databaseOperations.setMainActivity(this);
    }

    /**
     * Function to instantiate fragments
     */
    private void instantiateFragments() {
        loginFragment = new LoginFragment();
        loginFragment.setMainActivity(this);
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
        testsArrayList = queryMethods.setUpTestsArrayList((ArrayList<Test>) getIntent()
                .getExtras().getSerializable("testsArrayList"));
        hoursArrayList = (ArrayList<Hours>) getIntent().getExtras()
                .getSerializable("hoursArrayList");
        alertsArrayList = (ArrayList<Alerts>) getIntent().getExtras()
                .getSerializable("alertsArrayList");

        // set up default location
        defaultLocation = queryMethods.setUpDefaultLocation();

        // assign array list values from locationsArrayList
        locationsNameArrayList = queryMethods.setUpLocationsNameArrayList(locationsArrayList);

        // assign array list values from branchesArrayList
        branchesNameArrayList = queryMethods.setUpBranchesNameArrayList(branchesArrayList);

        // initialize arrayLists
        //testsFilteredArrayList = new ArrayList<>();
        //hoursFilteredArrayList = new ArrayList<>();

        // set up isJseMember
        queryMethods.setUpIsJseMember();
    }

    /**
     * Function to set up toolBar
     */
    private void setupToolbar() {
        // designate toolbar as the action bar for this activity
        setSupportActionBar(toolbar);
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
        createTab(R.layout.tab_layout_dashboard, R.id.tab_title_dashboard, getString(R.string.tabLayout_dashboard));
        createTab(R.layout.tab_layout_tests, R.id.tab_title_tests, getString(R.string.tabLayout_tests));
        createTab(R.layout.tab_layout_libraries, R.id.tab_title_libraries, getString(R.string.tabLayout_libraries));
        createTab(R.layout.tab_layout_contact, R.id.tab_title_contact, getString(R.string.tabLayout_contact));

        tabLayout.setOnTabSelectedListener(tabListener);
    }


    /**
     * Function to set the toolbar title
     *
     * @param toolbarTitle - title for toolbar
     *
     */
    public void setToolbarTitle(int toolbarTitle) {
        assert getSupportActionBar() != null;
        // set title of action bar
        getSupportActionBar().setTitle(toolbarTitle);
    }

    /**
     *
     * Function to set the scroll views min height
     */
    private void setScrollViewMinHeight() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        scrollView.setMinimumHeight(height);
    }

    /**
     *
     * Function inflate scroll view with fragment
     */
    private void inflateScrollViewWithFragment() {
        helperMethods.replaceFragment(dashboardFragment,
                getResources().getString(R.string.toolbar_title_dashboard),
                MainActivity.this, scrollView);

    }

    /**
     * Function to set up each tab
     * @param view - view for tab
     * @param titleView - style to use for text on the tab
     * @param title - text for tab
     */
    public void createTab(int view, int titleView, String title) {
        // initializing the tab
        TabLayout.Tab tab = tabLayout.newTab();
        // setting the view
        tab.setCustomView(view);
        // adding this tab to the tabLayout
        tabLayout.addTab(tab);
        // setting customized text on the tab
        ((TextView) findViewById(titleView)).setText(title);
    }

    /**
     * initializing a tabListener
     *
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
                        helperMethods.replaceFragment(dashboardFragment,
                                getResources().getString(R.string.toolbar_title_dashboard),
                                MainActivity.this, scrollView);
                    }
                    // exit the switch statement
                    break;
                // second tab - Search
                case 1:
                    // check if searchFragment is visible or not
                    if (!searchFragment.isVisible()) {
                        // if its not visible call a method that will return this fragment
                        helperMethods.replaceFragment(searchFragment,
                                getResources().getString(R.string.toolbar_title_search),
                                MainActivity.this, scrollView);
                    }
                    // exit the switch statement
                    break;
                // third tab - Libraries
                case 2:
                    // check if librariesFragment is visible or not
                    if (!librariesFragment.isVisible()) {
                        // if its not visible call a method that will return this fragment
                        helperMethods.replaceFragment(librariesFragment,
                                getResources().getString(R.string.toolbar_title_libraries),
                                MainActivity.this, scrollView);
                    }
                    // exit the switch statement
                    break;
                // fourth tab - Contact
                case 3:
                    // check if contactFragment is visible or not
                    if (!contactFragment.isVisible()) {
                        // if its not visible call a method that will return this fragment
                        helperMethods.replaceFragment(contactFragment,
                                getResources().getString(R.string.toolbar_title_contact),
                                MainActivity.this, scrollView);
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

        // check if selected menu item is log out
        if (id == R.id.log_out) {
            // launch activity with login activity intent
            Util.launchActivity(getLaunchLoginActivityIntent("log_out"));
            return true;
        }

        // check if selected menu item is update profile
        if (id == R.id.update_profile) {
            // launch activity with login activity intent
            Util.launchActivity(getLaunchLoginActivityIntent("update_profile"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Function to create an intent to launch MainActivity
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
     * @param outcome - string to describe intent intention
     * @return bundle
     */
    public Bundle getLaunchLoginActivityBundle(String outcome) {
        // create bundle
        Bundle bundle = new Bundle();

        // put array lists, default location, and outcome in to bundle
        bundle.putSerializable("locationsArrayList", locationsArrayList);
        bundle.putSerializable("testsArrayList", testsArrayList);
        bundle.putSerializable("hoursArrayList", hoursArrayList);
        bundle.putSerializable("branchesArrayList", branchesArrayList);
        bundle.putSerializable("alertsArrayList", alertsArrayList);
        // check if outcome is update_profile or not
        if (outcome.equals("update_profile"))
            // put user in to bundle
            bundle.putSerializable("user", user);
        bundle.putSerializable("defaultLocation", defaultLocation);
        bundle.putString("outcome", outcome);

        return bundle;
    }

}