package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LocationInfoFragment extends Fragment {

    //Controls
    View rootView;

    //Activities
    MainActivity mainActivity;

    //Fragments


    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.location_info_fragment,
                container, false);

        initializeViews(rootView);

        return rootView;
    }

    private void initializeViews(View rootView) {

    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}