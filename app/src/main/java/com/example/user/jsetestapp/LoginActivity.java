package com.example.user.jsetestapp;

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

public class LoginActivity extends AppCompatActivity {

    //Controls
    TabLayout tabLayout;
    Toolbar toolbar;
    ScrollView scrollView;
    LinearLayout tabLayoutLinearLayout;
    FrameLayout container;

    //Activities HelperClasses Classes;
    HelperMethods helperMethods;

    //Fragments
    LoginFragment loginFragment;
    Register1Fragment register1Fragment;
    Register2Fragment register2Fragment;
    UpdateProfileFragment updateProfileFragment;
    DashboardFragment dashboardFragment;

    //Variables
    String firstName, lastName, email, password, ssn, defaultLocation, gender;
    DateTime dob;
    boolean isJseMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        createFragmentsActivitiesClasses();
        setupToolbar();

        getFragmentManager().beginTransaction().add(R.id.container, loginFragment).commit();

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
        defaultLocation = "BKLYN - BY 18th Ave";
        dob = DateTime.now();
        gender = "2";
        isJseMember = false;

        savePreferences("first_name", firstName);
        savePreferences("last_name", lastName);
        savePreferences("email", email);
        savePreferences("password", password);
        savePreferences("ssn", ssn);
        savePreferences("default_location", defaultLocation);
        savePreferences("dob", dob.toString());
        savePreferences("gender", gender);
        savePreferences("is_jse_member", isJseMember);

    }

    private void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setToolbarTitle(int toolbarTitle) {
        toolbar.setTitle(toolbarTitle);
    }


}
