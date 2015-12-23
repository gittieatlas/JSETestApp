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
        setOpenRegistrationDates();

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_search, mainActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        setSpinnerSelections();
        // select search tab
        mainActivity.tabLayout.getTabAt(1).select();

        Util.hideToolbar(mainActivity.toolbarLinearLayout, mainActivity.scrollViewLinearLayout);
    }

    @Override
    public void onPause() {
        super.onPause();

        Util.showToolbar(mainActivity.toolbarLinearLayout,  mainActivity.scrollViewLinearLayout );
    }

    /**
     * Function that sets spinners selection
     */
    public void setSpinnerSelections() {
        // set spinner selection to user's default location
        locationsSpinner.setSelection(setBranchSpinnerSelection());
        // set spinner selection to "day of week"
        daysOfWeekSpinner.setSelection(0);
    }

    /**
     * Function to return a position of default branch in branchesNameArrayList
     * @return int
     */
    public int setBranchSpinnerSelection() {
        // declare string that contains (Default Branch)
        String d = " (Default Branch)";

        // loop through branches to see which branch contains (Default Branch)
        for (String s : mainActivity.branchesNameArrayList) {
            // if branch contains (Default Branch)
            if (s.contains(d)) {
                // return position of branch in array
                return mainActivity.branchesNameArrayList.indexOf(s);
            }
        }
        return 0;
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews(View rootView) {
        // initialize and reference TextView
        registrationDateTextView = (TextView) rootView.findViewById(R.id.registrationDateTextView);

        // initialize and reference Spinners
        locationsSpinner = (Spinner) rootView.findViewById(R.id.branchSpinner);
        daysOfWeekSpinner = (Spinner) rootView.findViewById(R.id.dayOfWeekSpinner);

        // initialize and reference CardView
        searchButton = (CardView) rootView.findViewById(R.id.searchButton);

        bindSpinnerData();
    }

    /**
     * Function to bind list of data to spinners
     */
    private void bindSpinnerData() {
        // add data to locationsSpinner
        HelperMethods.addDataToSpinner(mainActivity.branchesNameArrayList,
                locationsSpinner);

        // add data to daysOfWeekSpinner
        HelperMethods.addDataToSpinner(getResources().getStringArray(R.array.days_of_week_array),
                daysOfWeekSpinner);
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
            // if location contains: " (Default Branch)"
            if (location.contains(" (Default Branch)")) {
                // remove the string " (Default Branch)" from location
                location = location.replace(" (Default Branch)", "").trim();
            }

            // initialize searchCriteriaBranch
            Branch searchCriteriaBranch = new Branch();

                // loop through each branch in branchesArrayList
                for (Branch branch : mainActivity.branchesArrayList) {
                    // if location is equal to branch name
                    if (location.equals(branch.getName()))
                        // assign branch to searchCriteriaBranch
                        searchCriteriaBranch = branch;
                }

            // assign selected day of week to searchCriteriaDayOfWeek
            int searchCriteriaDayOfWeek = daysOfWeekSpinner.getSelectedItemPosition();
            //  find tests based on both criteria
            mainActivity.helperMethods.findTests(searchCriteriaBranch, searchCriteriaDayOfWeek);
        }
    };

    /**
     * Function to set open registration dates in textView
     */
    public void setOpenRegistrationDates() {
        // set open registration date range in control
        registrationDateTextView.setText(getOpenRegistrationDates());
    }

    /**
     * Function to get openRegistrationDate
     *
     * @return String
     */
    private String getOpenRegistrationDates() {
        // sort testsArrayList
        Collections.sort(mainActivity.testsArrayList);
        // assign to fromDate date of first test in testsArrayList
        String fromDate = mainActivity.testsArrayList.get(0).getDate().toString("MMMM dd yyyy");
        // assign to toDate date of last test in testsArrayList
        String toDate = mainActivity.testsArrayList.get(
                mainActivity.testsArrayList.size() - 1).getDate().toString("MMMM dd yyyy");
        // return: Registration is currently open from fromDate to toDate
        return String.format(getResources().getString(R.string.registration_date_range), fromDate, toDate);

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