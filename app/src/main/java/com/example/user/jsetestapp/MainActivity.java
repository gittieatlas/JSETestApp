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
    IntentMethods intentMethods;
    SplashActivity splashActivity;
    DatabaseOperations databaseOperations;

    // Declare Fragments
    LoginFragment loginFragment;
    ContactFragment contactFragment;
    SearchFragment searchFragment;
    LibrariesFragment librariesFragment;
    DashboardFragment dashboardFragment;
    ResultsFragment resultsFragment;
    MainActivityDialogFragment mainActivityDialogFragment;

    // Declare Variables
    ArrayList<Location> locationsArrayList;
    ArrayList<String> locationsNameArrayList;
    ArrayList<Branch> branchesArrayList;
    ArrayList<String> branchesNameArrayList;
    ArrayList<Test> testsArrayList;
    ArrayList<DataObject> testsFilteredArrayList;
    ArrayList<Hours> hoursArrayList;
    ArrayList<HoursDataObject> hoursFilteredArrayList;
    ArrayList<Alerts> alertsArrayList;
    User user = new User();
    Location defaultLocation;
    // boolean isJseMember = false;

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

        helperMethods.checkTag();
        helperMethods.replaceFragment(dashboardFragment,
                getResources().getString(R.string.toolbar_title_dashboard),
                MainActivity.this, scrollView);

        // send activity reference to Util class
        Util.setReference(this);
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
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
        intentMethods = new IntentMethods();
        intentMethods.setMainActivity(this);
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
        mainActivityDialogFragment = new MainActivityDialogFragment();
        mainActivityDialogFragment.setMainActivity(this);
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

        queryMethods.setUpLocationsArrayList();
        queryMethods.setUpUser();
        // queryMethods.setUpLocationsNameArrayList(this);
        locationsNameArrayList = queryMethods.setUpLocationsNameArrayList(locationsArrayList);
        queryMethods.setUpDefaultLocation();
        queryMethods.setUpBranchesArrayList();
        queryMethods.setUpBranchesNameArrayList();
        queryMethods.setUpTestsArrayList();
        queryMethods.setUpTestsFilteredArrayList();
        queryMethods.setUpHoursArrayList();
        queryMethods.setUpAlertsArrayList();
        hoursFilteredArrayList = new ArrayList<HoursDataObject>();
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

                if (resultsFragment.isVisible())
                    getFragmentManager().popBackStack();
            }
        });
    }


    private void setupTablayout() {
        createTab(R.layout.tab_layout_dashboard, R.id.tab_title_dashboard, getResources().getString(R.string.tabLayout_dashboard));
        createTab(R.layout.tab_layout_tests, R.id.tab_title_tests, getResources().getString(R.string.tabLayout_tests));
        createTab(R.layout.tab_layout_libraries, R.id.tab_title_libraries, getResources().getString(R.string.tabLayout_libraries));
        createTab(R.layout.tab_layout_contact, R.id.tab_title_contact, getResources().getString(R.string.tabLayout_contact));

        tabLayout.setOnTabSelectedListener(tabListener);
    }

    public void createTab(int view, int titleView, String title) {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setCustomView(view);
        tabLayout.addTab(tab);

        ((TextView) findViewById(titleView)).setText(title);
    }


    TabLayout.OnTabSelectedListener tabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

            switch (tabLayout.getSelectedTabPosition()) {
                case 0:
                    if (!dashboardFragment.isVisible()) {
                        helperMethods.replaceFragment(dashboardFragment,
                                getResources().getString(R.string.toolbar_title_dashboard),
                                MainActivity.this, scrollView);
                    }
                    break;
                case 1:
                    if (!searchFragment.isVisible()) {
                        helperMethods.replaceFragment(searchFragment,
                                getResources().getString(R.string.toolbar_title_search),
                                MainActivity.this, scrollView);
                    }
                    break;
                case 2:
                    if (!librariesFragment.isVisible()) {
                        helperMethods.replaceFragment(librariesFragment,
                                getResources().getString(R.string.toolbar_title_libraries),
                                MainActivity.this, scrollView);
                    }
                    break;
                case 3:
                    if (!contactFragment.isVisible()) {
                        helperMethods.replaceFragment(contactFragment,
                                getResources().getString(R.string.toolbar_title_contact),
                                MainActivity.this, scrollView);
                    }
                    break;
                default:
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

        if (id == R.id.log_out) {
            Bundle bundle = getIntent().getExtras();
            testsArrayList = (ArrayList<Test>) bundle.getSerializable("testsArrayList");
            Intent intent = new Intent(this, LoginActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("locationsArrayList", locationsArrayList);
            b.putSerializable("testsArrayList", testsArrayList);
            b.putSerializable("hoursArrayList", hoursArrayList);
            b.putSerializable("branchesArrayList", branchesArrayList);
            b.putSerializable("alertsArrayList", alertsArrayList);
            b.putSerializable("defaultLocation", defaultLocation);
            intent.putExtras(b);
            intent.putExtra("outcome", "log_out");
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.update_profile) {

            Bundle bundle = getIntent().getExtras();
            testsArrayList = (ArrayList<Test>) bundle.getSerializable("testsArrayList");
            Intent intent = new Intent(this, LoginActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("locationsArrayList", locationsArrayList);
            b.putSerializable("testsArrayList", testsArrayList);
            b.putSerializable("hoursArrayList", hoursArrayList);
            b.putSerializable("branchesArrayList", branchesArrayList);
            b.putSerializable("alertsArrayList", alertsArrayList);
            b.putSerializable("user", user);
            b.putSerializable("defaultLocation", defaultLocation);
            intent.putExtras(b);
            intent.putExtra("outcome", "update_profile");
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setToolbarTitle(int toolbarTitle) {
        getSupportActionBar().setTitle(toolbarTitle);
    }

    private void setScrollViewMinHeight() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        scrollView.setMinimumHeight(height);
    }

    public ArrayList<String> getLocationsNameArrayList() {

        return locationsNameArrayList;
    }

    public ArrayList<DataObject> getTestsFilteredArrayList() {

        return testsFilteredArrayList;
    }

    public ArrayList<HoursDataObject> getHoursFilteredArrayList() {

        return hoursFilteredArrayList;
    }

    public void doIntent(Intent intent) {
        startActivity(intent);
    }

    public Context getContext() {
        return MainActivity.this;

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

        MainActivityDialogFragment dialogFragment = new MainActivityDialogFragment();

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

    public String getStringFromResources(int number) {
        return getResources().getString(number);
    }


    public Location getDefaultLocation() {
        return defaultLocation;
    }

}