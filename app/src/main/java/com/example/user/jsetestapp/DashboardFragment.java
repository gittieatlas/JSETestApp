package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardFragment extends Fragment {

    //Controls
    View rootView;
    TextView locationTextView;
    CardView findTestButton;

    //Activities
    MainActivity mainActivity;
    LoginActivity loginActivity;

    //Fragments
    LocationInfoFragment locationInfoFragment;

    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dashboard,
                container, false);

        initializeViews(rootView);
        mainActivity.setToolbarTitle(R.string.toolbar_title_dashboard);
        locationTextView.setText(mainActivity.user.defaultLocation);
        findTestButton.setOnClickListener(findTestButtonListener);

        return rootView;
    }

    private void initializeViews(View rootView) {
        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        findTestButton = (CardView) rootView.findViewById(R.id.findTestButton);
    }

    OnClickListener findTestButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            Toast.makeText(mainActivity.getApplicationContext(), mainActivity.user.defaultLocation, Toast.LENGTH_LONG).show();
            mainActivity.replaceFragment(R.id.container, mainActivity.resultsFragment);
        }
    };

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }
}