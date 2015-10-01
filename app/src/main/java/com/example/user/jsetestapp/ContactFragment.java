package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactFragment extends Fragment {

    //Controls
    View rootView;

    //Activities
    MainActivity mainActivity;

    //Fragments


    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.contact_fragment, container, false);

        initializeViews(rootView);
        
        return rootView;
    }

    private void initializeViews(View rootView) {

        mainActivity.setToolbarTitle(R.string.nav_contact);
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}