package com.example.user.jsetestapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab;
    TabLayout tabLayout;
    Toolbar toolbar;

    Login login;
    Register1 register1;
    Register2 register2;
    UpdateProfile updateProfile;
    Contact contact;
    Search search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFragments();

        setupToolbar();
        setupTablayout();
        setupFab();
        initializeViews();

        getFragmentManager().beginTransaction().add(R.id.container, login).commit();
        toolbar.setTitle("Login");

        //setUpSpinner();
    }

    private void initializeViews() {


    }

    private void setupFragments() {
        login = new Login();
        register1 = new Register1();
        register2 = new Register2();
        updateProfile = new UpdateProfile();
        contact = new Contact();
        search = new Search();

    }

    private void setupFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Set navigation icon
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {// Navigation onClickLister
            @Override
            public void onClick(View v) {
                // finish(); // or your action here
                Snackbar
                        .make(findViewById(R.id.rootLayout),
                                "This is the snackbar with no action",
                                Snackbar.LENGTH_LONG)
                        .setAction(null, this)
                        .show(); // Do not forget to show!
            }
        });

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
                    getFragmentManager().beginTransaction().replace(R.id.container, login).commit();
                    toolbar.setTitle("Login");
                    break;
                case 1:
//                    getFragmentManager().beginTransaction().replace(R.id.container, register1).commit();
//                    toolbar.setTitle("Register");
//                    break;
                    getFragmentManager().beginTransaction().replace(R.id.container, search).commit();
                    toolbar.setTitle("Search");
                    break;

                case 2:
                   getFragmentManager().beginTransaction().replace(R.id.container, register2).commit();
                    toolbar.setTitle("Register");
                    break;
                case 3:
                    getFragmentManager().beginTransaction().replace(R.id.container, contact).commit();
                    toolbar.setTitle("Contact");
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

        if (view.getId() == R.id.fab) {
            Snackbar
                    .make(findViewById(R.id.rootLayout),
                            "This is Snackbar",
                            Snackbar.LENGTH_LONG)
                    .setAction("Action", this)
                    .show(); // Do not forget to show!
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.log_out) {
            getFragmentManager().beginTransaction().replace(R.id.container, login).commit();
            toolbar.setTitle("Login");

            Snackbar
                    .make(findViewById(R.id.rootLayout),
                            "You've been logged out",
                            Snackbar.LENGTH_LONG)
                    .setAction(null, this)
                    .show(); // Do not forget to show!
            return true;

        }
        if (id == R.id.update_profile) {
            toolbar.setTitle("Update Profile");
            getFragmentManager().beginTransaction().replace(R.id.container, updateProfile).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
