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


    //Controls
    View rootView;
    TextView locationTextView, alertsTitleTextView, alertsDayTextView,
            alertsDateTextView, alertsTimeTextView, alertsMessageTextView;
    CardView findTestButton;

    //Activities
    MainActivity mainActivity;
    LoginActivity loginActivity;

    //Fragments
    LocationInfoFragment locationInfoFragment;

    //Variables
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
        setUpText();
        mainActivity.queryMethods.setupListView(hoursAdapter, lvDetail, mainActivity.defaultLocation.getName());
        setUpAlerts();
        mainActivity.setToolbarTitle(R.string.toolbar_title_dashboard);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        locationInfoFragment = new LocationInfoFragment();
        locationInfoFragment.setArguments(mainActivity.helperMethods.passLocationToLocationInfoFragment(mainActivity.defaultLocation));
        getFragmentManager().beginTransaction().add(R.id.dashboardContainer, locationInfoFragment).commit();

        mainActivity.queryMethods.updateHoursArrayListView(lvDetail, mainActivity.defaultLocation.getName());

        locationInfoFragment.getArguments().putAll(mainActivity.helperMethods.passLocationToLocationInfoFragment(mainActivity.defaultLocation));

        mainActivity.tabLayout.getTabAt(0).select();

    }

    private void setUpText() {
        locationTextView.setText(mainActivity.defaultLocation.getName());
    }

    private void initializeViews(View rootView) {
        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        lvDetail = (ListView) rootView.findViewById(R.id.libraryHoursListView);
        findTestButton = (CardView) rootView.findViewById(R.id.findTestButton);

        alertsTitleTextView = (TextView) rootView.findViewById(R.id.alertsTitleTextView);

        alertsDayTextView = (TextView) rootView.findViewById(R.id.alertsDayTextView);
        alertsDateTextView = (TextView) rootView.findViewById(R.id.alertsDateTextView);
        alertsTimeTextView = (TextView) rootView.findViewById(R.id.alertsTimeTextView);
        alertsMessageTextView = (TextView) rootView.findViewById(R.id.AlertsMessageTextView);
    }

    private void registerListeners() {
        findTestButton.setOnClickListener(findTestButtonListener);
    }


    public void setUpAlerts() {
        for (Alerts alerts : mainActivity.alertsArrayList) {
            if (alerts.locationName.equals(mainActivity.defaultLocation.getName())) {
                alertsMessageTextView.setText(alerts.alertText);
                alertsDayTextView.setText(Util.firstLetterCaps(alerts.getDayOfWeek().toString()));
                alertsDateTextView.setText(alerts.getDate().toString("MMMM dd yyyy"));
                alertsTimeTextView.setText(alerts.getTime().toString("hh:mm a"));
            }
        }
        if (alertsMessageTextView.getText()== null){
            alertsMessageTextView.setText("There are no alerts for this location");
        }
    }

    OnClickListener findTestButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            mainActivity.helperMethods.findTests(mainActivity.defaultLocation);

        }
    };

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }
}