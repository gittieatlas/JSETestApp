package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DashboardFragment extends Fragment {


    // Declare Controls
    TextView locationTextView, locationAddressTextView, locationPhoneNumberTextView,
            alertsTitleTextView, alertsDayTextView, alertsDateTextView, alertsTimeTextView,
            alertsMessageTextView;
    CardView findTestButton;

    // Declare Classes;
    MainActivity mainActivity;

    // Declare Variables
    ListView lvDetail;
    ListAdapter hoursAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard,
                container, false);

        initializeViews(rootView);
        registerListeners();
        loadDefaultLocationInformation();
        findAlerts();
        setUpHoursListView();

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_dashboard, mainActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // select dashboard tab
        mainActivity.tabLayout.getTabAt(0).select();
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews(View rootView) {
        // initialize and reference TextViews
        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        locationAddressTextView = (TextView) rootView.findViewById(R.id.locationAddressTextView);
        locationPhoneNumberTextView = (TextView) rootView.findViewById(R.id.locationPhoneNumberTextView);
        alertsTitleTextView = (TextView) rootView.findViewById(R.id.alertsTitleTextView);
        alertsDayTextView = (TextView) rootView.findViewById(R.id.alertsDayTextView);
        alertsDateTextView = (TextView) rootView.findViewById(R.id.alertsDateTextView);
        alertsTimeTextView = (TextView) rootView.findViewById(R.id.alertsTimeTextView);
        alertsMessageTextView = (TextView) rootView.findViewById(R.id.AlertsMessageTextView);

        // initialize and reference ListView
        lvDetail = (ListView) rootView.findViewById(R.id.libraryHoursListView);

        // initialize and reference CardView
        findTestButton = (CardView) rootView.findViewById(R.id.findTestButton);
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        findTestButton.setOnClickListener(findTestButtonListener);
        locationAddressTextView.setOnClickListener(locationAddressOnClickListener);
        locationPhoneNumberTextView.setOnClickListener(locationPhoneNumberOnClickListener);
    }

    /**
     * OnClickListener for locationAddress
     */
    OnClickListener locationAddressOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // open Google Maps and populate it with location
            Util.navigationIntent(mainActivity.defaultLocation.getAddress());
        }
    };

    /**
     * OnClickListener for locationPhoneNumber
     */
    OnClickListener locationPhoneNumberOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // call default location
            Util.callIntent(locationPhoneNumberTextView.getText().toString());
        }
    };

    /**
     * OnClickListener for findTestButton
     */
    OnClickListener findTestButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // find available tests in default location
            mainActivity.helperMethods.findTests(mainActivity.defaultLocation);
        }
    };

    /**
     * Function to load default location information
     */
    private void loadDefaultLocationInformation() {
        // set location information in text views
        locationTextView.setText(mainActivity.defaultLocation.getName());
        locationAddressTextView.setText(mainActivity.defaultLocation.getAddress());
        locationPhoneNumberTextView.setText(mainActivity.defaultLocation.getPhone());
    }

    /**
     * Function to check for alerts in user's default location
     */
    public void findAlerts() {
        // loop through each alert in alertArrayList
        for (Alert alert : mainActivity.alertArrayList) {
            // if location of alert is equal to default location
            if (alert.locationName.equals(mainActivity.defaultLocation.getName())) {
                setAlertInformation(alert);
            }
        }

        // if there are no alerts at this location
        if (alertsMessageTextView.getText().equals("")) {
            // display message: "There are no alerts for this location" in text view
            alertsMessageTextView.setText("There are no alerts for this location");
        }
    }

    /**
     * Function to set text views with alert information
     */
    public void setAlertInformation(Alert alert){
        alertsMessageTextView.setText(alert.alertText);
        alertsDayTextView.setText(Util.firstLetterCaps(alert.getDayOfWeek().toString()));
        alertsDateTextView.setText(alert.getDate().toString("MMMM dd yyyy"));
        alertsTimeTextView.setText(alert.getTime().toString("hh:mm a"));
    }

    /**
     * Function to set up hours list view
     */
    public void setUpHoursListView() {
        // set up list view based on user's default location
        mainActivity.queryMethods.setupListView(hoursAdapter, lvDetail, mainActivity.defaultLocation.getName());
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