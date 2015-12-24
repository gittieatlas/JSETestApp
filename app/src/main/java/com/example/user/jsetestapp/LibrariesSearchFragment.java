package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class LibrariesSearchFragment extends Fragment {

    // Declare Controls
    Spinner locationsSpinner;
    CardView searchButton;

    // Declare Activities
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_libraries, container, false);

        initializeViews(rootView);
        registerListeners();

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
        mainActivity.tabLayout.getTabAt(2).select();

        mainActivity.showToolbar(false);
    }

    /**
     * Function that sets spinners selection
     */
    public void setSpinnerSelections() {
        // set spinner selection to user's default location
        locationsSpinner.setSelection(setLocationSpinnerSelection());
    }

    /**
     * Function to return a position of default location in locationsNameArrayList
     * @return int
     */
    public int setLocationSpinnerSelection() {

        // loop through all locations
        for (String location : mainActivity.locationsNameArrayList) {
            // if location is equal to defaultLocation name
            if (location.equals(mainActivity.defaultLocation.getName())) {
                // return position of location in array
                return mainActivity.locationsNameArrayList.indexOf(location);
            }
        }
        return 0;
    }


    /**
     * Function to initialize controls
     */
    private void initializeViews(View rootView) {

        // initialize and reference Spinners
        locationsSpinner = (Spinner) rootView.findViewById(R.id.branchSpinner);

        // initialize and reference CardView
        searchButton = (CardView) rootView.findViewById(R.id.searchButton);

        bindSpinnerData();
    }

    /**
     * Function to bind list of data to spinners
     */
    private void bindSpinnerData() {
        // add data to locationsSpinner
        HelperMethods.addDataToSpinner(editLocationsNameArrayList(),
                locationsSpinner);
    }

    /**
     * Function to set remove first value of locationsNameArrayList
     */
    public ArrayList<String> editLocationsNameArrayList() {
        // create array list from locationsNameArrayList
        ArrayList<String> arrayList = mainActivity.locationsNameArrayList;
        // remove value of the first location "Location"
        arrayList.remove(0);
        return arrayList;
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
            getSelectedLocation();
            // inflate scrollView with ResultsFragment
            HelperMethods.replaceFragment(mainActivity.librariesFragment,
                    mainActivity.getResources().getString(R.string.toolbar_title_libraries));
            }
               };

    /**
     * Function to get selected location and assign it to searchedLibrary in main activity
     *
     */
    private void getSelectedLocation() {
        // assign selected location to location
        String selectedLocation = locationsSpinner.getSelectedItem().toString();
        for (Location location:mainActivity.locationsArrayList){
            if (location.getName().equals(selectedLocation))
                mainActivity.searchedLibrary = location;
        }
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