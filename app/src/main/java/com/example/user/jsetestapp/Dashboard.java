package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Dashboard extends Fragment {
LocationInfo locationInfo;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dashboard,
                container, false);
locationInfo = new LocationInfo();

        getFragmentManager().beginTransaction().add(R.id.dashboardContainer, locationInfo).commit();

        return rootView;
    }
}