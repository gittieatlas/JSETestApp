package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;


public class LibrariesFragment extends Fragment {

    //Controls
    View rootView;
    Spinner locationsSpinner;
    CardView findTestButton;
    LinearLayout libraryInfoLinearLayout;


    //Activities
    MainActivity mainActivity;

    //Fragments
    LocationInfoFragment locationInfoFragment;
    //  HelperMethods helperMethods;

    //Variables
    ListView lvDetail;
    ListAdapter hoursAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_libraries,
                container, false);

        initializeViews(rootView);

        locationInfoFragment = new LocationInfoFragment();
        locationInfoFragment.setArguments(mainActivity.helperMethods.passLocationToLocationInfoFragment(getSelectedLocation()));
        getFragmentManager().beginTransaction().add(R.id.librariesContainer, locationInfoFragment).commit();

        registerListeners();
        mainActivity.setToolbarTitle(R.string.toolbar_title_libraries);
        libraryInfoLinearLayout.setVisibility(View.GONE);

        mainActivity.queryMethods.setupListView(hoursAdapter, lvDetail, locationsSpinner.getSelectedItem().toString());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivity.queryMethods.updateHoursArrayListView(lvDetail, locationsSpinner.getSelectedItem().toString());

        locationInfoFragment.getArguments().putAll(mainActivity.helperMethods.passLocationToLocationInfoFragment(getSelectedLocation()));

        mainActivity.tabLayout.getTabAt(2).select();
    }

    private Location getSelectedLocation() {
        Location locationToPass = new Location();
        for (Location location : mainActivity.locationsArrayList){
            if (location.getName().equals(locationsSpinner.getSelectedItem().toString()))
                locationToPass = location;
        }
        if (locationToPass == null)locationToPass = mainActivity.defaultLocation;
        return locationToPass;
    }

    private void initializeViews(View rootView) {
        findTestButton = (CardView) rootView.findViewById(R.id.findTestButton);
        locationsSpinner = (Spinner) rootView.findViewById(R.id.locationSpinner);
        bindSpinnerData();
        libraryInfoLinearLayout = (LinearLayout) rootView.findViewById(R.id.libraryInfoLinearLayout);
        lvDetail = (ListView) rootView.findViewById(R.id.libraryHoursListView);
    }

    private void registerListeners() {
        findTestButton.setOnClickListener(findTestButtonListener);
        locationsSpinner.setOnItemSelectedListener(locationsSpinnerOnItemSelectedListener);
    }

    OnItemSelectedListener locationsSpinnerOnItemSelectedListener = new OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0)
                libraryInfoLinearLayout.setVisibility(View.GONE);
            else
                libraryInfoLinearLayout.setVisibility(View.VISIBLE);

            mainActivity.queryMethods.setupListView(hoursAdapter, lvDetail, locationsSpinner.getSelectedItem().toString());

            locationInfoFragment.setUpScreen(getSelectedLocation());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    OnClickListener findTestButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            mainActivity.helperMethods.findTests(getSelectedLocation());
//            Toast.makeText(mainActivity.getApplicationContext(), locationsSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//            mainActivity.replaceFragment(R.id.container, mainActivity.resultsFragment);
        }
    };


    private void bindSpinnerData() {

        mainActivity.helperMethods.addDataToSpinner(mainActivity.locationsNameArrayList,
                locationsSpinner, "libraries_location", mainActivity.getContext());
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}