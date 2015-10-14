package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        mainActivity.setToolbarTitle(R.string.toolbar_title_libraries);

        locationsSpinner = (Spinner) rootView.findViewById(R.id.locationSpinner);
        bindSpinnerData();
    }

    private void bindSpinnerData() {

        mainActivity.addDataToSpinner(mainActivity.locationsArrayList, locationsSpinner, "location");
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}