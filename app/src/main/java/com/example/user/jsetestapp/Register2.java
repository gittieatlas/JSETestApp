package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Register2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.register2,
                container, false);

        Button buttonLeft = (Button) rootView.findViewById(R.id.buttonLeft);
        Button buttonRight = (Button) rootView.findViewById(R.id.buttonRight);
        buttonRight.setEnabled(false);


        Spinner genderSpinner = (Spinner) rootView.findViewById(R.id.spinnerGender);

// Create an adapter from the string array resource and use
// android's inbuilt layout file simple_spinner_item
// that represents the default spinner in the UI
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.gender_array, R.layout.spinner_dropdown_item);
// Set the layout to use for each dropdown item
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);

        genderSpinner.setAdapter(genderAdapter);


        Spinner locationsSpinner = (Spinner) rootView.findViewById(R.id.spinnerDefaultLocation);

// Create an adapter from the string array resource and use
// android's inbuilt layout file simple_spinner_item
// that represents the default spinner in the UI
        ArrayAdapter locationsAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.locations_array, R.layout.spinner_dropdown_item);
// Set the layout to use for each dropdown item
        locationsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);

        locationsSpinner.setAdapter(locationsAdapter);

        return rootView;
    }
}