package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

    Location location;

    public LocationInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_location_info,
                container, false);
        mainActivity = ((MainActivity) getActivity());

        initializeViews(rootView);
        registerListeners();

        if (this.getArguments() != null)
            location = (Location) this.getArguments().getSerializable("location");

        setUpScreen(location);

        return rootView;
    }

    private void initializeViews(View rootView) {
        locationAddress = (TextView) rootView.findViewById(R.id.locationAddress);
        locationPhoneNumber = (TextView) rootView.findViewById(R.id.locationPhoneNumber);
    }

    private void registerListeners() {
        locationAddress.setOnClickListener(locationAddressOnClickListener);
        locationPhoneNumber.setOnClickListener(locationPhoneNumberOnClickListener);
    }

    public void setUpScreen(Location location) {
        locationAddress.setText(location.getAddress());
        locationPhoneNumber.setText(location.getPhone());
    }

    OnClickListener locationAddressOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Util.navigationIntent(mainActivity.defaultLocation.getAddress());
        }
    };

    OnClickListener locationPhoneNumberOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.intentMethods.callIntent(locationPhoneNumber.getText().toString());
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        setUpScreen(location);
    }

}