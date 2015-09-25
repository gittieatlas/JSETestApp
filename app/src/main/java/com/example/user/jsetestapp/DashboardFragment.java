package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashboardFragment extends Fragment {

    //Controls
    View rootView;

    //Activities
    MainActivity mainActivity;

    //Fragments
    LocationInfoFragment locationInfoFragment;

    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dashboard_fragment,
                container, false);



        mainActivity.setToolbarTitle("Dashboard");


        locationInfoFragment = new LocationInfoFragment();

        getFragmentManager().beginTransaction().add(R.id.dashboardContainer, locationInfoFragment).commit();

        return rootView;
    }


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}