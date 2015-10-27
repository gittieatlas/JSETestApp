package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LocationInfoFragment extends Fragment {

    //Controls
    View rootView;
    TextView locationAddress, locationPhoneNumber;

    //Activities
    MainActivity mainActivity;

    //Fragments


    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_location_info,
                container, false);
mainActivity = ((MainActivity) getActivity());
        initializeViews(rootView);
        setUpScreen();

        return rootView;
    }

    private void setUpScreen() {
        locationAddress.setText(mainActivity.defaultLocation.getAddress());
        locationPhoneNumber.setText(mainActivity.getDefaultLocation().getPhone());
    }

    private void initializeViews(View rootView) {
        locationAddress = (TextView) rootView.findViewById(R.id.locationAddress);
        locationPhoneNumber = (TextView) rootView.findViewById(R.id.locationPhoneNumber);
    }

    @Override
    public void onResume(){
        super.onResume();
        setUpScreen();
    }

}