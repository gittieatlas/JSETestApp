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
import android.widget.Toast;


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
    HelperMethods helperMethods;

    //Variables
    ListView lvDetail;
    ListAdapter hoursAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_libraries,
                container, false);

        locationInfoFragment = new LocationInfoFragment();
        getFragmentManager().beginTransaction().add(R.id.librariesContainer, locationInfoFragment).commit();

        initializeViews(rootView);

        registerListeners();
        mainActivity.setToolbarTitle(R.string.toolbar_title_libraries);
        libraryInfoLinearLayout.setVisibility(View.GONE);


        mainActivity.queryMethods.setupListView(hoursAdapter, lvDetail, locationsSpinner.getSelectedItem().toString());

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        mainActivity.queryMethods.updateHoursArrayListView(lvDetail, locationsSpinner.getSelectedItem().toString());

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
            //mainActivity.queryMethods.getFilteredHoursArrayList("BKLYN - BY 18th Ave");

            mainActivity.queryMethods.setupListView(hoursAdapter, lvDetail, locationsSpinner.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    OnClickListener findTestButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            Toast.makeText(mainActivity.getApplicationContext(), locationsSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            mainActivity.replaceFragment(R.id.container, mainActivity.resultsFragment);
        }
    };


    private void bindSpinnerData() {

        mainActivity.helperMethods.addDataToSpinner(mainActivity.locationsNameArrayList, locationsSpinner, "libraries_location");
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}