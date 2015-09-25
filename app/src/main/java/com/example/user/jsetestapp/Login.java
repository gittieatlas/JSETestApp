package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Login extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.login,
                container, false);

        final RelativeLayout rootLayout = (RelativeLayout) rootView.findViewById(R.id.rootLayout);
        Button buttonLeft = (Button) rootView.findViewById(R.id.buttonLeft);
        Button buttonRight = (Button) rootView.findViewById(R.id.buttonRight);
        buttonRight.setEnabled(false);
        TextView textViewForgotPassword = (TextView) rootView.findViewById(R.id.textViewForgotPassword);
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar
                        .make(rootLayout,
                                "This is the onClick for Forgot Password TextView",
                                Snackbar.LENGTH_LONG)
                        .setAction(null, this)
                        .show(); // Do not forget to show!
            }
        });

        return rootView;
    }
}