package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class SearchFragment extends Fragment {

    // Declare Controls
    Spinner locationsSpinner, daysOfWeekSpinner;
    TextView registrationDateTextView;
    CardView searchButton;

    // Declare Activities
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        initializeViews(rootView);
        registerListeners();
        bindSpinnerData();
        setOpenRegistrationDateRange();

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_search, mainActivity.toolbar);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // set selection to user's default location
        locationsSpinner.setSelection(mainActivity.helperMethods.setBranchSpinnerSelection());
        // set selection to "day of week"
        daysOfWeekSpinner.setSelection(0);

        mainActivity.tabLayout.getTabAt(1).select();
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews(View rootView) {
        // initialize and reference controls
        registrationDateTextView = (TextView) rootView.findViewById(R.id.registrationDateTextView);
        locationsSpinner = (Spinner) rootView.findViewById(R.id.locationSpinner);
        daysOfWeekSpinner = (Spinner) rootView.findViewById(R.id.dayOfWeekSpinner);
        searchButton = (CardView) rootView.findViewById(R.id.searchButton);
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListener
        searchButton.setOnClickListener(searchButtonListener);
    }

    /**
     * OnClickListener for searchButton
     */
    OnClickListener searchButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // assign selected location to location
            String location = locationsSpinner.getSelectedItem().toString();
            // if location contains " (Default Branch)"
            if (location.contains(" (Default Branch)")){
                // remove " (Default Branch)" from location
                location = location.replace(" (Default Branch)", "").trim();
            }
            // initialize searchCriteriaBranch
            Branch searchCriteriaBranch = new Branch();
            // loop through each branch in branchesArrayList
            for (Branch branch : mainActivity.branchesArrayList) {
                // if location is equal to branch.name
                if (location.equals(branch.getName()))
                    // assign branch to searchCriteriaBranch
                    searchCriteriaBranch = branch;
            }

            // assign selected day of week to searchCriteriaDayOfWeek
            int searchCriteriaDayOfWeek = daysOfWeekSpinner.getSelectedItemPosition();
            //  find tests based on both criterias
            mainActivity.helperMethods.findTests(searchCriteriaBranch, searchCriteriaDayOfWeek);
        }
    };

    /**
     * Function to bind spinner to data
     */
    private void bindSpinnerData() {

        mainActivity.helperMethods.addDataToSpinner(mainActivity.branchesNameArrayList,
                locationsSpinner);


        mainActivity.helperMethods.addDataToSpinner(getResources().getStringArray(R.array.days_of_week_array),
                daysOfWeekSpinner);
    }

    private String getOpenRegistrationDates() {

        Collections.sort(mainActivity.testsArrayList);

        String fromDate = mainActivity.testsArrayList.get(0).getDate().toString("MMMM dd yyyy");
        String toDate = mainActivity.testsArrayList.get(
                mainActivity.testsArrayList.size() - 1).getDate().toString("MMMM dd yyyy");
        String openRegistrationString =
                String.format(getResources().getString(R.string.registration_date_range),
                fromDate, toDate);
        return openRegistrationString;
    }

    /**
     * Function that sets registration dates information
     */
    public void setOpenRegistrationDateRange(){
        // set open registration date range in control
        registrationDateTextView.setText(getOpenRegistrationDates());
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