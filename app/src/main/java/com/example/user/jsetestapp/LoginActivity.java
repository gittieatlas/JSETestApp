package com.example.user.jsetestapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    //Controls
    TabLayout tabLayout;
    Toolbar toolbar;
    ScrollView scrollView;
    LinearLayout tabLayoutLinearLayout;
    FrameLayout container;

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

    //Variables
    String firstName, lastName, email, password, ssn, defaultLocation, gender;
    DateTime dob;
    boolean isJseMember;
    SharedPreferences sharedPreferences;

    ArrayList<Location> locationsArrayList;
    ArrayList<String> locationsNameArrayList;
    ArrayList<Test> testsArrayList;
    ArrayList<Hours> hoursArrayList;
    ArrayList<Branch> branchesArrayList;
    ArrayList<Alerts> alertsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        createFragmentsActivitiesClasses();
        setupToolbar();

        getFragmentManager().beginTransaction().add(R.id.container, loginFragment).commit();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

      //  loginFragment.locationsArrayList = (ArrayList<Location>) this.getIntent().getSerializableExtra("locationsArrayList");

        locationsArrayList = new ArrayList<Location>();

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        locationsArrayList = (ArrayList<Location>)bundle.getSerializable("locationsArrayList");
        branchesArrayList = (ArrayList<Branch>)bundle.getSerializable("branchesArrayList");
        testsArrayList = (ArrayList<Test>)bundle.getSerializable("testsArrayList");
        hoursArrayList = (ArrayList<Hours>)bundle.getSerializable("hoursArrayList");
        alertsArrayList = (ArrayList<Alerts>)bundle.getSerializable("alertsArrayList");
        try {
            Intent intent = getIntent();

            if (intent.getStringExtra("fragment").equals("log_out")) {
                getFragmentManager().beginTransaction().replace(R.id.container, loginFragment).commit();
            } else if (intent.getStringExtra("fragment").equals("update_profile")) {
                getFragmentManager().beginTransaction().replace(R.id.container, updateProfileFragment).commit();
            }
        } catch (Exception e) {

        }

        firstName = "Chani";
        lastName = "Cohen";
        email = "chanicohen@gmail.com";
        password = "1234";
        ssn = "XXX-XX-1234";
        defaultLocation = "BKLYN - BY 18th Ave. (M)";
        dob = DateTime.now();
        // TODO filter testArrayList according to gender
        gender = "1";
        isJseMember = false;

        helperMethods.savePreferences("first_name", firstName, sharedPreferences);
        helperMethods.savePreferences("last_name", lastName, sharedPreferences);
        helperMethods.savePreferences("email", email, sharedPreferences);
        helperMethods.savePreferences("password", password, sharedPreferences);
        helperMethods.savePreferences("ssn", ssn, sharedPreferences);
        helperMethods.savePreferences("default_location", defaultLocation, sharedPreferences);
        helperMethods.savePreferences("dob", dob.toString(), sharedPreferences);
        helperMethods.savePreferences("gender", gender, sharedPreferences);
        helperMethods.savePreferences("is_jse_member", isJseMember, sharedPreferences);

        queryMethods.setUpLocationsNameArrayList2();
    }

    private void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        container = (FrameLayout) findViewById(R.id.container);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
//        tabLayoutLinearLayout = (LinearLayout) findViewById(R.id.tabLayoutLinearLayout);
//        tabLayoutLinearLayout.removeAllViews(); //for Login pages
//        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
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
        queryMethods= new QueryMethods();
        queryMethods.setLoginActivity(this);
        loginActivityDialogFragment = new LoginActivityDialogFragment();
        loginActivityDialogFragment.setLoginActivity(this);
        dialogListeners = new DialogListeners();
        dialogListeners.setLoginActivity(this);
    }

    private void setupToolbar() {
        toolbar.inflateMenu(R.menu.menu_login); //Inflate menu
        toolbar.getMenu().clear(); // Clear toolbar icons
        toolbar.setTitle(R.string.app_name);// Set title
        toolbar.setTitleTextColor(getResources().getColor(R.color.icons)); //Set title color
// Set navigation icon
        toolbar.setNavigationIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.register1) {
            getFragmentManager().beginTransaction().replace(R.id.container, register1Fragment).commit();
            return true;
        }
        if (id == R.id.register2) {
            getFragmentManager().beginTransaction().replace(R.id.container, register2Fragment).commit();
            return true;
        }


        if (id == R.id.dashboard) {
            switchToMainActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("locationsArrayList", locationsArrayList);
        b.putSerializable("testsArrayList", testsArrayList);
        b.putSerializable("hoursArrayList", hoursArrayList);
        b.putSerializable("branchesArrayList", branchesArrayList);
        b.putSerializable("alertsArrayList", alertsArrayList);
        intent.putExtras(b);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
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
