package com.example.user.jsetestapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class LibrariesFragment extends Fragment {

    //Controls
    View rootView;
    Spinner locationsSpinner;
    CardView findTestButton;

    //Activities
    MainActivity mainActivity;

    //Fragments
    LocationInfoFragment locationInfoFragment;
    HelperMethods helperMethods;

    //Variables
    ListView lvDetail;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_libraries,
                container, false);

        locationInfoFragment = new LocationInfoFragment();
        getFragmentManager().beginTransaction().add(R.id.librariesContainer, locationInfoFragment).commit();

        initializeViews(rootView);
        mainActivity.setToolbarTitle(R.string.toolbar_title_libraries);


        setupListView();
        findTestButton.setOnClickListener(findTestButtonListener);

        return rootView;
    }

    private void initializeViews(View rootView) {
        findTestButton = (CardView) rootView.findViewById(R.id.findTestButton);
        locationsSpinner = (Spinner) rootView.findViewById(R.id.locationSpinner);
        bindSpinnerData();
    }

    private void setupListView() {

        lvDetail = (ListView) rootView.findViewById(R.id.libraryHoursListView);
        Context context = getActivity().getApplicationContext();
        lvDetail.setAdapter(new MyBaseAdapter(context, getDataSet()));
        mainActivity.helperMethods.setListViewHeightBasedOnItems(lvDetail);
    }

    private ArrayList<HoursDataObject> getDataSet() {

        return mainActivity.getHoursFilteredArrayList();

    }

    OnClickListener findTestButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            Toast.makeText(mainActivity.getApplicationContext(), locationsSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            mainActivity.replaceFragment(R.id.container, mainActivity.resultsFragment);
        }
    };


    private void bindSpinnerData() {

        mainActivity.addDataToSpinner(mainActivity.locationsArrayList, locationsSpinner, "libraries_location");
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}