package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Register2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.register2,
                container, false);

        Button buttonLeft = (Button) rootView.findViewById(R.id.buttonLeft);
        Button buttonRight = (Button) rootView.findViewById(R.id.buttonRight);
        buttonRight.setEnabled(false);


        return rootView;
    }
}