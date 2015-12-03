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
    TextView locationAddress, locationPhoneNumber;

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
        mainActivity.queryMethods.setupListView(hoursAdapter, lvDetail, locationsSpinner.getSelectedItem().toString());

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_libraries, mainActivity.toolbar);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mainActivity.queryMethods.updateHoursArrayListView(lvDetail, locationsSpinner.getSelectedItem().toString());
        setUpScreen(getSelectedLocation());

        mainActivity.tabLayout.getTabAt(2).select();
    }

    private Location getSelectedLocation() {
        Location locationToPass = new Location();
        for (Location location : mainActivity.locationsArrayList) {
            if (location.getName().equals(locationsSpinner.getSelectedItem().toString()))
                locationToPass = location;
        }
        if (locationToPass == null) locationToPass = mainActivity.defaultLocation;
        return locationToPass;
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews(View rootView) {
        // initialize and reference controls
        locationAddress = (TextView) rootView.findViewById(R.id.locationAddress);
        locationPhoneNumber = (TextView) rootView.findViewById(R.id.locationPhoneNumber);
        findTestButton = (CardView) rootView.findViewById(R.id.findTestButton);
        locationsSpinner = (Spinner) rootView.findViewById(R.id.locationSpinner);
        bindSpinnerData();
        libraryInfoLinearLayout = (LinearLayout) rootView.findViewById(R.id.libraryInfoLinearLayout);
        lvDetail = (ListView) rootView.findViewById(R.id.libraryHoursListView);
        // set layout to be invisible
        libraryInfoLinearLayout.setVisibility(View.GONE);
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        findTestButton.setOnClickListener(findTestButtonListener);
        locationsSpinner.setOnItemSelectedListener(locationsSpinnerOnItemSelectedListener);
        locationAddress.setOnClickListener(locationAddressOnClickListener);
        locationPhoneNumber.setOnClickListener(locationPhoneNumberOnClickListener);
    }

    /**
     * OnClickListener for locationAddress
     */
    OnClickListener locationAddressOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Util.navigationIntent(mainActivity.defaultLocation.getAddress());
        }
    };

    /**
     * OnClickListener for locationPhoneNumber
     */
    OnClickListener locationPhoneNumberOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
           IntentMethods.callIntent(locationPhoneNumber.getText().toString());
        }
    };

    /**
     * OnClickListener for locationsSpinner
     */
    OnItemSelectedListener locationsSpinnerOnItemSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0)
                libraryInfoLinearLayout.setVisibility(View.GONE);
            else
                libraryInfoLinearLayout.setVisibility(View.VISIBLE);

            mainActivity.queryMethods.setupListView(hoursAdapter, lvDetail, locationsSpinner.getSelectedItem().toString());

            //locationInfoFragment.setUpScreen(getSelectedLocation());

            setUpScreen(getSelectedLocation());

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * OnClickListener for findTestButton
     */
    OnClickListener findTestButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            mainActivity.helperMethods.findTests(getSelectedLocation());
        }
    };


    public void setUpScreen(Location location) {
        locationAddress.setText(mainActivity.defaultLocation.getAddress());
        locationPhoneNumber.setText(mainActivity.defaultLocation.getPhone());
    }

    /**
     * Function to bind spinner to data
     */
    private void bindSpinnerData() {

        mainActivity.helperMethods.addDataToSpinner(mainActivity.locationsNameArrayList,
                locationsSpinner);
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