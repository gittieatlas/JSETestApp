package com.example.user.jsetestapp;

import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class LibrariesFragment extends Fragment {

    // Declare Controls
    TextView locationTextView, locationAddressTextView, locationPhoneNumberTextView;

    // Declare Activities
    MainActivity mainActivity;

    // Declare Variables
    ListView lvDetail;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_libraries,
                container, false);

        initializeViews(rootView);
        registerListeners();
        loadLibraryInformation();
        setUpHoursListView();

        // set navigation icon in toolbar
        mainActivity.toolbar.setNavigationIcon(getResources().
                getDrawable(R.drawable.ic_arrow_left_white_24dp));

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_libraries, mainActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // select libraries tab
        mainActivity.tabLayout.getTabAt(2).select();

        mainActivity.showToolbar(true);
    }

    @Override
    public void onStop() {
        super.onStop();

        // Set navigation icon to transparent
        mainActivity.toolbar.setNavigationIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews(View rootView) {
        // initialize and reference TextViews
        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        locationAddressTextView = (TextView) rootView.findViewById(R.id.locationAddressTextView);
        locationPhoneNumberTextView = (TextView) rootView.findViewById(R.id.locationPhoneNumberTextView);

        // initialize and reference ListView
        lvDetail = (ListView) rootView.findViewById(R.id.libraryHoursListView);
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        locationAddressTextView.setOnClickListener(locationAddressOnClickListener);
        locationPhoneNumberTextView.setOnClickListener(locationPhoneNumberOnClickListener);
    }

    /**
     * OnClickListener for locationAddressTextView
     */
    OnClickListener locationAddressOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // open Google Maps and populate it with defaultLocation
            Util.navigationIntent(mainActivity.defaultLocation.getAddress());
        }
    };

    /**
     * OnClickListener for locationPhoneNumberTextView
     */
    OnClickListener locationPhoneNumberOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // call selected location
            Util.callIntent(locationPhoneNumberTextView.getText().toString());
        }
    };

    /**
     * Function to load default location information
     */
    private void loadLibraryInformation() {
        // set location information in text views
        locationTextView.setText(mainActivity.searchedLibrary.getName());
        locationAddressTextView.setText(mainActivity.searchedLibrary.getAddress());
        locationPhoneNumberTextView.setText(mainActivity.searchedLibrary.getPhone());
    }

    /**
     * Function to set up hours list view
     */
    public void setUpHoursListView() {
        // set up hours list view based on selected location
        mainActivity.helperMethods.setupListView(lvDetail,
                mainActivity.searchedLibrary.getName());
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