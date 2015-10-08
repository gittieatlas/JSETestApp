package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LibrariesFragment extends Fragment {

    //Controls
    View rootView;
    Spinner locationsSpinner;

    //Activities
    MainActivity mainActivity;

    //Fragments
    LocationInfoFragment locationInfoFragment;
    HelperMethods helperMethods;
    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.libraries_fragment,
                container, false);

        initializeViews(rootView);

        return rootView;
    }

    private void initializeViews(View rootView) {
        locationInfoFragment = new LocationInfoFragment();
        //helperMethods.replaceFragment(R.id.librariesContainer, locationInfoFragment);
        getFragmentManager().beginTransaction().add(R.id.librariesContainer, locationInfoFragment).commit();
        mainActivity.setToolbarTitle(R.string.nav_libraries);

        locationsSpinner = (Spinner) rootView.findViewById(R.id.locationSpinner);
        bindSpinnerData();
    }

    private void bindSpinnerData() {
        // Create an adapter from the string array resource and use android's inbuilt layout file simple_spinner_item that represents the default spinner in the UI
        ArrayAdapter locationsAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.locations_array, R.layout.location_spinner_dropdown_item);
        // Set the layout to use for each dropdown item
        locationsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        locationsSpinner.setAdapter(locationsAdapter);
        locationsSpinner.setSelection(2);
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}