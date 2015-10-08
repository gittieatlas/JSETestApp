package com.example.user.jsetestapp;

import android.app.Fragment;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Controls
    TabLayout tabLayout;
    Toolbar toolbar;
    ScrollView scrollView;
    LinearLayout tabLayoutLinearLayout;

    //Activities;
    DatabaseOperations databaseOperations;

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
    HelperMethods helperMethods;
    //RecyclerViewFragment recyclerViewFragment;

    //Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragmentsActivitiesClasses();
        setupToolbar();
        setupTablayout();
        setScrollViewMinHeight();
        initializeViews();
        addFragment(R.id.container, loginFragment);
        databaseOperations.Connect();

        //getFragmentManager().beginTransaction().add(R.id.container, loginFragment).commit();

        //setUpSpinner();
    }

    public void addFragment(int container, Fragment fragment){
        getFragmentManager().beginTransaction().add(container, fragment).commit();
    }

    public void replaceFragment(int container, Fragment fragment){
        getFragmentManager().beginTransaction().replace(container, fragment).commit();
    }

    private void initializeViews() {
        tabLayoutLinearLayout = (LinearLayout) findViewById(R.id.tabLayoutLinearLayout);
        //tabLayoutLinearLayout.removeAllViews(); //for Login pages
    }

    private void createFragmentsActivitiesClasses() {
        loginFragment = new LoginFragment();
        loginFragment.setMainActivity(this);
        register1Fragment = new Register1Fragment();
        register1Fragment.setMainActivity(this);
        register2Fragment = new Register2Fragment();
        register2Fragment.setMainActivity(this);
        updateProfileFragment = new UpdateProfileFragment();
        updateProfileFragment.setMainActivity(this);
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
        databaseOperations = new DatabaseOperations();
        databaseOperations.setMainActivity(this);
        helperMethods = new HelperMethods();
        helperMethods.setMainActivity(this);
        //recyclerViewFragment = new RecyclerViewFragment();
        //recyclerViewFragment.setMainActivity(this);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Set navigation icon
        //toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left_white_24dp));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {// Navigation onClickLister
//            @Override
//            public void onClick(View v) {
//                // finish(); // or your action here
//                Snackbar
//                        .make(findViewById(R.id.rootLayout),
//                                "This is the snackbar with no action",
//                                Snackbar.LENGTH_LONG)
//                        .setAction(null, this)
//                        .show(); // Do not forget to show!
//            }
//        });

        toolbar.inflateMenu(R.menu.menu_main); //Inflate menu
        toolbar.inflateMenu(R.menu.menu_main); //Inflate menu
        toolbar.getMenu().clear(); // Clear toolbar icons
        toolbar.setTitle("Page Title");// Set title
        toolbar.setTitleTextColor(getResources().getColor(R.color.icons)); //Set title color
    }

    private void setupTablayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_account_box_grey600_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_clipboard_text_grey600_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_library_grey600_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_map_marker_grey600_24dp));

        tabLayout.setOnTabSelectedListener(tabListener);
    }


    TabLayout.OnTabSelectedListener tabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {


            switch (tabLayout.getSelectedTabPosition()) {
                case 0:
                    replaceFragment(R.id.container, dashboardFragment);
                    break;
                case 1:
                    replaceFragment(R.id.container, searchFragment);
                    break;
                case 2:
                    replaceFragment(R.id.container, librariesFragment);
                    break;
                case 3:
                    replaceFragment(R.id.container, contactFragment);
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


//    private void setUpSpinner() {
//        // you need to have a list of data that you want the spinner to display
//        List<String> spinnerArray =  new ArrayList<String>();
//        spinnerArray.add("item1");
//        spinnerArray.add("item2");
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Spinner sItems = (Spinner) findViewById(R.id.spinner);
//        sItems.setAdapter(adapter);
//    }

    @Override
    public void onClick(View view) {

//        if (view.getId() == R.id.fab) {
//            Snackbar
//                    .make(findViewById(R.id.rootLayout),
//                            "This is Snackbar",
//                            Snackbar.LENGTH_LONG)
//                    .setAction("Action", this)
//                    .show(); // Do not forget to show!
//        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.log_out) {
            getFragmentManager().beginTransaction().replace(R.id.container, loginFragment).commit();

            Snackbar
                    .make(findViewById(R.id.rootLayout),
                            "You've been logged out",
                            Snackbar.LENGTH_LONG)
                    .setAction(null, this)
                    .show(); // Do not forget to show!
            return true;

        }
        if (id == R.id.update_profile) {
            replaceFragment(R.id.container, updateProfileFragment);
            return true;
        }

        if (id == R.id.register1) {
            replaceFragment(R.id.container, register1Fragment);
            return true;
        }
        if (id == R.id.register2) {
            replaceFragment(R.id.container, register2Fragment);
            return true;
        }

        if (id == R.id.login) {
            replaceFragment(R.id.container, loginFragment);
            return true;
        }
        if (id == R.id.results) {
            replaceFragment(R.id.container, resultsFragment);
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
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setMinimumHeight(height);
    }
}
 