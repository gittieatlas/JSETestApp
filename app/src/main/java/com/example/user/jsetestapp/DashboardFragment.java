package com.example.user.jsetestapp;

import android.app.Fragment;
import android.content.Context;
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
    View rootView;
    TextView locationTextView, locationAddress, locationPhoneNumber, alertsTitleTextView,
            alertsDayTextView, alertsDateTextView, alertsTimeTextView, alertsMessageTextView;
    CardView findTestButton;

    // Declare Classes;
    MainActivity mainActivity;
    LoginActivity loginActivity;

    // Declare Fragments
    LocationInfoFragment locationInfoFragment;

    // Declare Variables
    ListView lvDetail;
    Context context;
    ListAdapter hoursAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_dashboard,
                container, false);

        initializeViews(rootView);
        registerListeners();
        loadDefaultLocationInformation();
        setUpAlerts();
        setUpHoursListView();
        mainActivity.setToolbarTitle(R.string.toolbar_title_dashboard);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //locationInfoFragment.getArguments().putAll(mainActivity.helperMethods.passLocationToLocationInfoFragment(mainActivity.defaultLocation));

        mainActivity.tabLayout.getTabAt(0).select();

    }

    private void initializeViews(View rootView) {
        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        locationAddress = (TextView) rootView.findViewById(R.id.locationAddress);
        locationPhoneNumber = (TextView) rootView.findViewById(R.id.locationPhoneNumber);
        alertsTitleTextView = (TextView) rootView.findViewById(R.id.alertsTitleTextView);
        alertsDayTextView = (TextView) rootView.findViewById(R.id.alertsDayTextView);
        alertsDateTextView = (TextView) rootView.findViewById(R.id.alertsDateTextView);
        alertsTimeTextView = (TextView) rootView.findViewById(R.id.alertsTimeTextView);
        alertsMessageTextView = (TextView) rootView.findViewById(R.id.AlertsMessageTextView);
        lvDetail = (ListView) rootView.findViewById(R.id.libraryHoursListView);
        findTestButton = (CardView) rootView.findViewById(R.id.findTestButton);
    }

    private void loadDefaultLocationInformation() {
        locationTextView.setText(mainActivity.defaultLocation.getName());
        locationAddress.setText(mainActivity.defaultLocation.getAddress());
        locationPhoneNumber.setText(mainActivity.defaultLocation.getPhone());
    }

    private void registerListeners() {
        findTestButton.setOnClickListener(findTestButtonListener);
        locationAddress.setOnClickListener(locationAddressOnClickListener);
        locationPhoneNumber.setOnClickListener(locationPhoneNumberOnClickListener);
    }

    OnClickListener locationAddressOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Util.navigationIntent(mainActivity.defaultLocation.getAddress());
        }
    };

    OnClickListener locationPhoneNumberOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.intentMethods.callIntent(locationPhoneNumber.getText().toString());
        }
    };

    OnClickListener findTestButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            mainActivity.helperMethods.findTests(mainActivity.defaultLocation);

        }
    };

    public void setUpAlerts() {
        for (Alerts alerts : mainActivity.alertsArrayList) {
            if (alerts.locationName.equals(mainActivity.defaultLocation.getName())) {
                alertsMessageTextView.setText(alerts.alertText);
                alertsDayTextView.setText(Util.firstLetterCaps(alerts.getDayOfWeek().toString()));
                alertsDateTextView.setText(alerts.getDate().toString("MMMM dd yyyy"));
                alertsTimeTextView.setText(alerts.getTime().toString("hh:mm a"));
            }
        }
        if (alertsMessageTextView.getText().equals("")){
            alertsMessageTextView.setText("There are no alerts for this location");
        }
    }

    public void setUpHoursListView(){
        mainActivity.queryMethods.setupListView(hoursAdapter, lvDetail, mainActivity.defaultLocation.getName());
        mainActivity.queryMethods.updateHoursArrayListView(lvDetail, mainActivity.defaultLocation.getName());
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }
}