package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Register2Fragment extends Fragment {

    //Controls
    View rootView;
    Spinner genderSpinner, locationsSpinner;

    //Activities
    MainActivity mainActivity;

    //Fragments


    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.register2_fragment,
                container, false);

        initializeViews(rootView);

        return rootView;
    }

    private void initializeViews(View rootView) {
        mainActivity.setToolbarTitle(R.string.nav_register);

        Button buttonLeft = (Button) rootView.findViewById(R.id.buttonLeft);

        Button buttonRight = (Button) rootView.findViewById(R.id.buttonRight);
        buttonRight.setEnabled(false);

        genderSpinner = (Spinner) rootView.findViewById(R.id.spinnerGender);
        locationsSpinner = (Spinner) rootView.findViewById(R.id.spinnerDefaultLocation);
        bindSpinnerData();

    }

    private void bindSpinnerData() {
        // Create an adapter from the string array resource and use android's inbuilt layout file simple_spinner_item that represents the default spinner in the UI
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.gender_array, R.layout.spinner_dropdown_item);
        // Set the layout to use for each dropdown item
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        genderSpinner.setAdapter(genderAdapter);

        // Create an adapter from the string array resource and use android's inbuilt layout file simple_spinner_item that represents the default spinner in the UI
        ArrayAdapter locationsAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.locations_array, R.layout.spinner_dropdown_item);
        // Set the layout to use for each dropdown item
        locationsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        locationsSpinner.setAdapter(locationsAdapter);

    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

}