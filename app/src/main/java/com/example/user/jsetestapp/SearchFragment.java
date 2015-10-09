package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFragment extends Fragment {

    //Controls
    View rootView;
    Spinner locationsSpinner, daysOfWeekSpinner;
    TextView registrationDateTextView;
    CardView searchButton;

    //Activities
    MainActivity mainActivity;

    //Fragments


    //Variables
    OnClickListener searchButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            //TODO pass to method that will query and then update araay and then notify data set changed
            String searchCriteriaLocation = locationsSpinner.getSelectedItem().toString();
            int searchCriteriaDayOfWeek = daysOfWeekSpinner.getSelectedItemPosition();
            Toast.makeText(mainActivity.getApplicationContext(), searchCriteriaLocation + " " + searchCriteriaDayOfWeek, Toast.LENGTH_LONG).show();
            //Change fragment
            mainActivity.replaceFragment(R.id.container, mainActivity.resultsFragment);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.search_fragment, container, false);

        mainActivity.setToolbarTitle(R.string.toolbar_title_tests);
        initializeViews(rootView);
        registerListeners();
        bindSpinnerData();
        registrationDateTextView.setText(getOpenRegistrationDates());

        return rootView;
    }

    private String getOpenRegistrationDates() {

        //TODO run sql query to get fromDate and toDate
        String fromDate = "first date";
        String toDate = "second date";
        String openRegistrationString = String.format(getResources().getString(R.string.registration_date_range),
                fromDate, toDate);
        return openRegistrationString;
    }

    private void initializeViews(View rootView) {

        registrationDateTextView = (TextView) rootView.findViewById(R.id.registrationDateTextView);
        locationsSpinner = (Spinner) rootView.findViewById(R.id.locationSpinner);
        daysOfWeekSpinner = (Spinner) rootView.findViewById(R.id.dayOfWeekSpinner);
        searchButton = (CardView) rootView.findViewById(R.id.searchButton);
    }

    private void registerListeners() {

        searchButton.setOnClickListener(searchButtonListener);
    }

    private void bindSpinnerData() {

        // Create an adapter from the string array resource and use android's inbuilt layout file simple_spinner_item that represents the default spinner in the UI
        ArrayAdapter locationsAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.locations_array, R.layout.location_spinner_dropdown_item);
        // Set the layout to use for each dropdown item
        locationsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        locationsSpinner.setAdapter(locationsAdapter);
        locationsSpinner.setSelection(2);

        // Create an adapter from the string array resource and use android's inbuilt layout file simple_spinner_item that represents the default spinner in the UI
        ArrayAdapter daysOfWeekAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.days_of_week_array, R.layout.location_spinner_dropdown_item);
        // Set the layout to use for each dropdown item
        daysOfWeekAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        daysOfWeekSpinner.setAdapter(daysOfWeekAdapter);
        daysOfWeekSpinner.setSelection(0);
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}