package com.example.user.jsetestapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Controls
    TabLayout tabLayout;
    Toolbar toolbar;
    ScrollView scrollView;
    LinearLayout tabLayoutLinearLayout;
    FrameLayout container;

    //Activities HelperClasses Classes;
    HelperMethods helperMethods;
    QueryMethods queryMethods;
    DialogListeners dialogListeners;
    IntentMethods intentMethods;
    SplashActivity splashActivity;
    DatabaseOperations databaseOperations;

    //Fragments
    LoginFragment loginFragment;
    ContactFragment contactFragment;
    SearchFragment searchFragment;
    LibrariesFragment librariesFragment;
    DashboardFragment dashboardFragment;
    ResultsFragment resultsFragment;
    MainActivityDialogFragment mainActivityDialogFragment;


    //Variables
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        createFragmentsActivitiesClasses();
        helperMethods.showSnackBar();
        setupToolbar();
        setupTablayout();
        setScrollViewMinHeight();
        helperMethods.addFragment(R.id.container, dashboardFragment, getResources().getString(R.string.toolbar_title_dashboard));
        queryMethods.setUpLocationsArrayList();
        queryMethods.setUpUser();
        queryMethods.setUpLocationsNameArrayList(this);
        queryMethods.setUpBranchesArrayList();
        queryMethods.setUpBranchesNameArrayList();
        queryMethods.setUpTestsArrayList();
        queryMethods.setUpTestsFilteredArrayList();
        queryMethods.setUpHoursArrayList();
        queryMethods.setUpAlertsArrayList();
        queryMethods.setUpDefaultLocation();
        hoursFilteredArrayList = new ArrayList<HoursDataObject>();
        queryMethods.setUpIsJseMember();
      //  Toast.makeText(this, user.firstName + defaultLocation.name, Toast.LENGTH_SHORT).show();
    }

    private void initializeViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayoutLinearLayout = (LinearLayout) findViewById(R.id.tabLayoutLinearLayout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        container = (FrameLayout) findViewById(R.id.container);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    private void createFragmentsActivitiesClasses() {
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
        helperMethods = new HelperMethods();
        helperMethods.setMainActivity(this);
        queryMethods = new QueryMethods();
        queryMethods.setMainActivity(this);
        mainActivityDialogFragment = new MainActivityDialogFragment();
        mainActivityDialogFragment.setMainActivity(this);
        dialogListeners = new DialogListeners();
        dialogListeners.setMainActivity(this);
        intentMethods = new IntentMethods();
        intentMethods.setMainActivity(this);
        splashActivity = new SplashActivity();
        splashActivity.setMainActivity(this);
        databaseOperations = new DatabaseOperations();
        databaseOperations.setMainActivity(this);

    }

    private void setupToolbar() {
        toolbar.inflateMenu(R.menu.menu_main); //Inflate menu
        toolbar.getMenu().clear(); // Clear toolbar icons
        toolbar.setTitle(R.string.app_name);// Set title
        toolbar.setTitleTextColor(getResources().getColor(R.color.icons)); //Set title color
        // Set navigation icon
        toolbar.setNavigationIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {// Navigation onClickLister
            @Override
            public void onClick(View v) {
                if (toolbar.getTitle().toString().equals(getResources().getString(R.string.toolbar_title_results))) {
                    getFragmentManager().popBackStack();

                } // else nothing
            }
        });
    }

    @Override
    public void onBackPressed() {

        ResultsFragment resultsFragment = new ResultsFragment();
        try {
            resultsFragment = (ResultsFragment) getFragmentManager().findFragmentById(R.id.container);
        } catch (Exception ex) {

        }

        if (resultsFragment != null && resultsFragment.isVisible()) {
            getFragmentManager().popBackStack();
        }
        // ToDo add else for if coming from dashboard- show dialog to exit
        else {
            tabLayout.getTabAt(0).select();
            getFragmentManager().popBackStack(getResources().getString(R.string.toolbar_title_dashboard), 0);
        }
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
                    helperMethods.replaceFragment(R.id.container, dashboardFragment, getResources().getString(R.string.toolbar_title_dashboard));
                    break;
                case 1:
                    helperMethods.replaceFragment(R.id.container, searchFragment, getResources().getString(R.string.toolbar_title_search));
                    break;
                case 2:
                    helperMethods.replaceFragment(R.id.container, librariesFragment, getResources().getString(R.string.toolbar_title_libraries));
                    break;
                case 3:
                    helperMethods.replaceFragment(R.id.container, contactFragment, getResources().getString(R.string.toolbar_title_contact));
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
            //ToDo fix onCreate of LoginAcitivyt to handle this intent
//            Intent intent = new Intent(this, LoginActivity.class);
//            intent.putExtra("fragment", "log_out");
//            startActivity(intent);

            Toast.makeText(this, "You've been logged out!- this feature does not work yet", Toast.LENGTH_LONG).show();
            return true;

        }

        if (id == R.id.update_profile) {

            Bundle bundle = getIntent().getExtras();
            testsArrayList = (ArrayList<Test>) bundle.getSerializable("testsArrayList");

            Intent intent = new Intent(this, LoginActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("locationsArrayList", locationsArrayList);
           b.putSerializable("testsArrayList", testsArrayList);


           // b.putSerializable("testsArrayList", (ArrayList<Test>) bundle.getSerializable("testsArrayList"));

            b.putSerializable("hoursArrayList", hoursArrayList);
            b.putSerializable("branchesArrayList", branchesArrayList);
            b.putSerializable("alertsArrayList", alertsArrayList);
            b.putSerializable("user", user);
            b.putSerializable("defaultLocation", defaultLocation);
            intent.putExtras(b);
            intent.putExtra("fragment", "update_profile");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setToolbarTitle(int toolbarTitle) {
        toolbar.setTitle(toolbarTitle);
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

    //    private void loadSavedPreferences() {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//
////To retrieve values from a shared preferences file, call methods such as getInt() and getString(),
//// providing the key for the value you want, and optionally a default value to return if the key isn't present.
//        //helperMethods.loadSavedPreferences(sharedPreferences);
//        user.setFirstName(sharedPreferences.getString("first_name", null));
//        user.setLastName(sharedPreferences.getString("last_name", null));
//        user.setEmail(sharedPreferences.getString("email", null));
//        user.setPassword(sharedPreferences.getString("password", null));
//        user.setSsn(sharedPreferences.getString("ssn", null));
//        user.setDob(convertDob());
//        user.setDefaultLocation(sharedPreferences.getString("default_location", null));
//        user.setGender(sharedPreferences.getString("gender", "3"));
//
//// ToDo Check if jseMember: if JSEMember x exist is SP or sp.JSEMember = false, checkIfJSEMember() from JSE database
//// checkIfJseMember() getDOB() and getSocial(), compare to JDB, if member = true -> update SP to JSEMember = true
//        user.setIsJseMember(sharedPreferences.getBoolean("is_jse_member", false));
//
//
//
//    }
//    private LocalDate convertDob(){
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String dobString = (sharedPreferences.getString("dob_month", null)+ "-" + sharedPreferences.getString("dob_day", null) + "-" + sharedPreferences.getString("dob_year", null));
//        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM-dd-yyyy");
////        LocalDate dob = dtf.parseLocalDate(dobString);
//      //  return dob; ToDo // FIXME: 11/9/2015
//        return LocalDate.now();
//    }

    public void test() {

        Toast.makeText(this, "JSE Student Id is " + user.getJseStudentId(), Toast.LENGTH_LONG).show();
    }

}