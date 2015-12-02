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
    TextView locationTextView, locationAddressTextView, locationPhoneNumberTextView, alertsTitleTextView,
            alertsDayTextView, alertsDateTextView, alertsTimeTextView, alertsMessageTextView;
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
        setUpAlerts();
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
        if (mainActivity.tabLayout != null) {
            mainActivity.tabLayout.getTabAt(0).select();
        }

    }

    /**
     * Function to initialize controls
     */
    private void initializeViews(View rootView) {
        // initialize and reference controls
        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        locationAddressTextView = (TextView) rootView.findViewById(R.id.locationAddressTextView);
        locationPhoneNumberTextView = (TextView) rootView.findViewById(R.id.locationPhoneNumberTextView);
        alertsTitleTextView = (TextView) rootView.findViewById(R.id.alertsTitleTextView);
        alertsDayTextView = (TextView) rootView.findViewById(R.id.alertsDayTextView);
        alertsDateTextView = (TextView) rootView.findViewById(R.id.alertsDateTextView);
        alertsTimeTextView = (TextView) rootView.findViewById(R.id.alertsTimeTextView);
        alertsMessageTextView = (TextView) rootView.findViewById(R.id.AlertsMessageTextView);
        lvDetail = (ListView) rootView.findViewById(R.id.libraryHoursListView);
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
            Util.navigationIntent(mainActivity.defaultLocation.getAddress());
        }
    };

    /**
     * OnClickListener for locationPhoneNumber
     */
    OnClickListener locationPhoneNumberOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            IntentMethods.callIntent(locationPhoneNumberTextView.getText().toString());
        }
    };

    /**
     * OnClickListener for findTestButton
     */
    OnClickListener findTestButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            mainActivity.helperMethods.findTests(mainActivity.defaultLocation);

        }
    };

    /**
     * Function to load default location information into controls
     */
    private void loadDefaultLocationInformation() {
        locationTextView.setText(mainActivity.defaultLocation.getName());
        locationAddressTextView.setText(mainActivity.defaultLocation.getAddress());
        locationPhoneNumberTextView.setText(mainActivity.defaultLocation.getPhone());
    }

    /**
     * Function to set up alerts
     */
    public void setUpAlerts() {
        // loop through each alert in alertsArrayList
        for (Alerts alerts : mainActivity.alertsArrayList) {
            // if location name in alert is equal to default location
            if (alerts.locationName.equals(mainActivity.defaultLocation.getName())) {
                // load alert information into controls
                alertsMessageTextView.setText(alerts.alertText);
                alertsDayTextView.setText(Util.firstLetterCaps(alerts.getDayOfWeek().toString()));
                alertsDateTextView.setText(alerts.getDate().toString("MMMM dd yyyy"));
                alertsTimeTextView.setText(alerts.getTime().toString("hh:mm a"));
            }
        }
        // if there is no alert for this location
        if (alertsMessageTextView.getText().equals("")){
            // display "There are no alerts for this location"
            alertsMessageTextView.setText("There are no alerts for this location");
        }
    }

    /**
     * Function to set up hours list view
     */
    public void setUpHoursListView(){
        // set up list view
        mainActivity.queryMethods.setupListView(hoursAdapter, lvDetail, mainActivity.defaultLocation.getName());
        // get hoursArrayList of default location
        mainActivity.queryMethods.updateHoursArrayListView(lvDetail, mainActivity.defaultLocation.getName());
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