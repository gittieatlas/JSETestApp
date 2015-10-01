package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchFragment extends Fragment {

    //Controls
    View rootView;
    Spinner locationsSpinner, daysOfWeekSpinner;

    //Activities
    MainActivity mainActivity;

    //Fragments


    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.search_fragment,
                container, false);

        initializeViews(rootView);

        return rootView;
    }

    private void initializeViews(View rootView) {
        mainActivity.setToolbarTitle(R.string.nav_tests);

        locationsSpinner = (Spinner) rootView.findViewById(R.id.locationSpinner);
        daysOfWeekSpinner = (Spinner) rootView.findViewById(R.id.dayOfWeekSpinner);
        bindSpinnerData();
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