package com.example.user.jsetestapp;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.Spinner;
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
    DatabaseOperations databaseOperations;
    HelperMethods helperMethods;
    QueryMethods queryMethods;

    //Fragments
    LoginFragment loginFragment;
    Register1Fragment register1Fragment;
    Register2Fragment register2Fragment;
    UpdateProfileFragment updateProfileFragment;
    ContactFragment contactFragment;
    SearchFragment searchFragment;
    LibrariesFragment librariesFragment;
    DashboardFragment dashboardFragment;
    ResultsFragment resultsFragment;
    RecyclerViewAdapter recyclerViewAdapter;

    //Variables
    ArrayList<String> locationsArrayList;
    ArrayList<DataObject> testsFitlteredArrayList;

    ArrayList<Test> testsArrayList;
    User user = new User();
    boolean isJseMember = false;// flag for Internet connection status


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        createFragmentsActivitiesClasses();
        setupToolbar();
        setupTablayout();
        setScrollViewMinHeight();
        helperMethods.addFragment(R.id.container, dashboardFragment);

        queryMethods.setUpLocationsArrayList();
        queryMethods.setUpTestsArrayList();


        loadSavedPreferences();

    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //To retrieve values from a shared preferences file, call methods such as getInt() and getString(),
        // providing the key for the value you want, and optionally a default value to return if the key isn't present.
        user.setFirstName(sharedPreferences.getString("first_name", null));
        user.setLastName(sharedPreferences.getString("last_name", null));
        user.setEmail(sharedPreferences.getString("email", null));
        user.setPassword(sharedPreferences.getString("password", null));
        user.setSsn(sharedPreferences.getString("ssn", null));
        user.setDefaultLocation(sharedPreferences.getString("default_location", "COPE"));
      //  user.setDob(sharedPreferences.getString("dob", "COPE")); covert back to DateTime
        user.setGender(sharedPreferences.getString("gender", "2"));
        user.setIsJseMember(sharedPreferences.getBoolean("is_jse_member", false));
        Toast.makeText(getApplicationContext(), user.getDefaultLocation() + " " + user.getGender().toString() + " isJseMember " + user.isJseMember, Toast.LENGTH_LONG).show();
    }



    private void initializeViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayoutLinearLayout = (LinearLayout) findViewById(R.id.tabLayoutLinearLayout);
        //tabLayoutLinearLayout.removeAllViews(); //for Login pages
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
//        recyclerViewAdapter = new RecyclerViewAdapter();
//        recyclerViewAdapter.setMainActivity(this);

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

                    Toast.makeText(MainActivity.this, "Toolbar icon pressed", Toast.LENGTH_LONG).show();
                } // else nothing
            }
        });
    }

    private void setupTablayout() {
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_account_box_white_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_clipboard_text_white_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_library_white_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_bank_white_24dp));

        tabLayout.setOnTabSelectedListener(tabListener);
    }


    TabLayout.OnTabSelectedListener tabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

            switch (tabLayout.getSelectedTabPosition()) {
                case 0:
                    helperMethods.replaceFragment(R.id.container, dashboardFragment);
                    //replaceFragment(R.id.container, dashboardFragment);
                    break;
                case 1:
                    helperMethods.replaceFragment(R.id.container, searchFragment);
                    //replaceFragment(R.id.container, searchFragment);
                    break;
                case 2:
                    helperMethods.replaceFragment(R.id.container, librariesFragment);
                    //replaceFragment(R.id.container, librariesFragment);
                    break;
                case 3:
                    helperMethods.replaceFragment(R.id.container, contactFragment);
                    //replaceFragment(R.id.container, contactFragment);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.log_out) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("fragment", "log_out");
            startActivity(intent);

            Toast.makeText(this, "You've been logged out!", Toast.LENGTH_LONG).show();
            return true;

        }

        if (id == R.id.update_profile) {
            Intent intent = new Intent(this, LoginActivity.class);
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

    //adding stuff

    public void addDataToSpinner(ArrayList<String> arrayList, Spinner spinner, String tag) {
        helperMethods.addDataToSpinner(arrayList, spinner, tag);
    }

    public ArrayList<String> getLocationsArrayList() {

        return locationsArrayList;
    }

    public ArrayList<DataObject> getTestsFitlteredArrayList() {

        return queryMethods.getTestsArrayList();
    }

    public void showDialog(String title, String message) {
        helperMethods.showMyDialog(title, message);
    }

    public void addFragment(int container, Fragment fragment) {
        helperMethods.addFragment(container, fragment);
    }

    public void replaceFragment(int container, Fragment fragment) {
        helperMethods.replaceFragment(container, fragment);
    }

    public static void callJse() {
        //MainActivity mainActivity = new MainActivity();
        //Toast.makeText(, "ITEM PRESSED FROM MAIN ACTIVITY", Toast.LENGTH_SHORT).show();

    }


}
