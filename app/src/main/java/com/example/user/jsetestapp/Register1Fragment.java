package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Register1Fragment extends Fragment {

    //Controls
    View rootView;

    //Activities
    MainActivity mainActivity;

    //Fragments


    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.register1_fragment,
                container, false);

        initializeViews(rootView);

        return rootView;
    }

    private void initializeViews(View rootView) {

        mainActivity.setToolbarTitle(R.string.nav_register);

        Button buttonLeft = (Button) rootView.findViewById(R.id.buttonLeft);

        Button buttonRight = (Button) rootView.findViewById(R.id.buttonRight);
        buttonRight.setEnabled(false);
    }


    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}