package com.example.user.jsetestapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    ListView lvDetail;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_dashboard,
                container, false);

        locationInfoFragment = new LocationInfoFragment();
        getFragmentManager().beginTransaction().add(R.id.dashboardContainer, locationInfoFragment).commit();

        initializeViews(rootView);
        mainActivity.setToolbarTitle(R.string.toolbar_title_dashboard);

        setupListView();
        locationTextView.setText(mainActivity.user.defaultLocation);
        findTestButton.setOnClickListener(findTestButtonListener);


        return rootView;
    }

    private void setupListView() {

        lvDetail = (ListView) rootView.findViewById(R.id.libraryHoursListView);
        Context context = getActivity().getApplicationContext();
        lvDetail.setAdapter(new MyBaseAdapter(context, getDataSet()));
    }

    private void initializeViews(View rootView) {
        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        findTestButton = (CardView) rootView.findViewById(R.id.findTestButton);
    }

    private ArrayList<HoursDataObject> getDataSet() {

        return mainActivity.getHoursFilteredArrayList();

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