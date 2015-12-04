package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


public class LibrariesFragment extends Fragment {

    // Declare Controls
    Spinner locationsSpinner;
    CardView findTestButton;
    LinearLayout libraryInfoLinearLayout;
    TextView locationAddressTextView, locationPhoneNumberTextView;

    // Declare Activities
    MainActivity mainActivity;

    // Declare Variables
    ListView lvDetail;
    ListAdapter hoursAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_libraries,
                container, false);

        initializeViews(rootView);
        registerListeners();
        setLinearLayoutVisibility();
        setUpHoursListView();

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_libraries, mainActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // select libraries tab
        mainActivity.tabLayout.getTabAt(2).select();
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews(View rootView) {
        // initialize and reference TextViews
        locationAddressTextView = (TextView) rootView.findViewById(R.id.locationAddressTextView);
        locationPhoneNumberTextView = (TextView)
                rootView.findViewById(R.id.locationPhoneNumberTextView);

        // initialize and reference CardView
        findTestButton = (CardView) rootView.findViewById(R.id.findTestButton);

        // initialize and reference LinearLayout
        libraryInfoLinearLayout = (LinearLayout) rootView.findViewById(R.id.libraryInfoLinearLayout);

        // initialize and reference ListView
        lvDetail = (ListView) rootView.findViewById(R.id.libraryHoursListView);

        // initialize and reference Spinner
        locationsSpinner = (Spinner) rootView.findViewById(R.id.locationSpinner);
        bindSpinnerData();
    }

    /**
     * Function to bind list of data to spinners
     */
    private void bindSpinnerData() {
        // add data to locationsSpinner
        HelperMethods.addDataToSpinner(mainActivity.locationsNameArrayList,
                locationsSpinner);
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        locationAddressTextView.setOnClickListener(locationAddressOnClickListener);
        locationPhoneNumberTextView.setOnClickListener(locationPhoneNumberOnClickListener);
        findTestButton.setOnClickListener(findTestButtonListener);
        locationsSpinner.setOnItemSelectedListener(locationsSpinnerOnItemSelectedListener);
    }

    /**
     * OnClickListener for locationAddressTextView
     */
    OnClickListener locationAddressOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // open Google Maps and populate it with defaultLocation
            Util.navigationIntent(mainActivity.defaultLocation.getAddress());
        }
    };

    /**
     * OnClickListener for locationPhoneNumberTextView
     */
    OnClickListener locationPhoneNumberOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // call selected location
            IntentMethods.callIntent(locationPhoneNumberTextView.getText().toString());
        }
    };

    /**
     * OnClickListener for findTestButton
     */
    OnClickListener findTestButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // find available tests at selected location
            mainActivity.helperMethods.findTests(getSelectedLocation());
        }
    };

    /**
     * OnClickListener for locationsSpinner
     */
    OnItemSelectedListener locationsSpinnerOnItemSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            setLinearLayoutVisibility();
            setUpHoursListView();
            setUpScreen(getSelectedLocation());

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * Function to set LinearLayout visibility based on location selection
     */
    public void setLinearLayoutVisibility(){
        // if selected location is "location" (first option)
        if (locationsSpinner.getSelectedItemPosition()==0){
            // hide LinearLayout
            libraryInfoLinearLayout.setVisibility(View.GONE);
        }
        // if a location is selected
        else {
            // show LinearLayout
            libraryInfoLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Function to set up hours list view
     */
    public void setUpHoursListView(){
        // set up hours list view based on selected location
        mainActivity.queryMethods.setupListView(hoursAdapter, lvDetail,
                locationsSpinner.getSelectedItem().toString());
    }

    /**
     * Function to populate text views with the selected location's information
     *
     * @param location - selected location
     */
    public void setUpScreen(Location location) {
        // set location address and phone number in textViews
        locationAddressTextView.setText(location.getAddress());
        locationPhoneNumberTextView.setText(location.getPhone());
    }

    /**
     * Function to get selected location
     * @return Location
     */
    private Location getSelectedLocation() {
        // initialize locationToPass
        Location locationToPass = new Location();
        // loop through each location in locationsArrayList
        for (Location location : mainActivity.locationsArrayList) {
            // if location name is equal to selected location in spinner
            if (location.getName().equals(locationsSpinner.getSelectedItem().toString()))
                // assign location to locationToPass
                locationToPass = location;
        }

        // if locationToPass is null
        if (locationToPass == null)
            // assign defaultLocation to locationToPass
            locationToPass = mainActivity.defaultLocation;
        // return locationToPass
        return locationToPass;
    }

    /**
     * Function to set reference of MainActivity
     *
     * @param mainActivity - reference to MainActivity
     */
    public void setMainActivity(MainActivity mainActivity) {
        // set this mainActivity to mainActivity
        this.mainActivity = mainActivity;
    }
}