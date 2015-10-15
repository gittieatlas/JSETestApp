package com.example.user.jsetestapp;

import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
    boolean isJseMember = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragmentsActivitiesClasses();
        setupToolbar();
        setupTablayout();
        setScrollViewMinHeight();
        initializeViews();
        helperMethods.addFragment(R.id.container, dashboardFragment);

        queryMethods.setUpLocationsArrayList();
        queryMethods.setUpTestsArrayList();



        //setUpSpinner();
    }

    private void initializeViews() {
        tabLayoutLinearLayout = (LinearLayout) findViewById(R.id.tabLayoutLinearLayout);
        //tabLayoutLinearLayout.removeAllViews(); //for Login pages

        container = (FrameLayout) findViewById(R.id.container);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
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
        helperMethods = new HelperMethods();
        helperMethods.setMainActivity(this);
        queryMethods = new QueryMethods();
        queryMethods.setMainActivity(this);
//        recyclerViewAdapter = new RecyclerViewAdapter();
//        recyclerViewAdapter.setMainActivity(this);

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
        toolbar.setTitle("JSE");// Set title
        toolbar.setTitleTextColor(getResources().getColor(R.color.icons)); //Set title color
    }

    private void setupTablayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_account_box_white_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_clipboard_text_white_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_library_white_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_map_marker_white_24dp));

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
            helperMethods.replaceFragment(R.id.container, updateProfileFragment);
            return true;
        }

        if (id == R.id.register1) {
            helperMethods.replaceFragment(R.id.container, register1Fragment);
            return true;
        }

        if (id == R.id.register2) {
            helperMethods.replaceFragment(R.id.container, register2Fragment);
            return true;
        }

        if (id == R.id.login) {
            helperMethods.replaceFragment(R.id.container, loginFragment);
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
