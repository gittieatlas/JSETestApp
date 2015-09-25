package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Search extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.search,
                container, false);
        Spinner locationsSpinner = (Spinner) rootView.findViewById(R.id.locationSpinner);

// Create an adapter from the string array resource and use
// android's inbuilt layout file simple_spinner_item
// that represents the default spinner in the UI
        ArrayAdapter locationsAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.locations_array, R.layout.location_spinner_dropdown_item);
// Set the layout to use for each dropdown item
        locationsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);

        locationsSpinner.setAdapter(locationsAdapter);

        locationsSpinner.setSelection(2);


        Spinner daysOfWeekSpinner = (Spinner) rootView.findViewById(R.id.dayOfWeekSpinner);

// Create an adapter from the string array resource and use
// android's inbuilt layout file simple_spinner_item
// that represents the default spinner in the UI
        ArrayAdapter daysOfWeekAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.days_of_week_array, R.layout.location_spinner_dropdown_item);
// Set the layout to use for each dropdown item
        daysOfWeekAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);

        daysOfWeekSpinner.setAdapter(daysOfWeekAdapter);

        daysOfWeekSpinner.setSelection(0);

        return rootView;
    }
}