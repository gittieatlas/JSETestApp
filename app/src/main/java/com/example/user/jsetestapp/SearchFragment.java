package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    //Controls
    View rootView;
    Spinner locationsSpinner, daysOfWeekSpinner;
    TextView registrationDateTextView;
    CardView searchButton;

    //Activities
    MainActivity mainActivity;


    //Fragments


    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        mainActivity.setToolbarTitle(R.string.toolbar_title_search);

        initializeViews(rootView);
        registerListeners();
        bindSpinnerData();
        registrationDateTextView.setText(getOpenRegistrationDates());

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        locationsSpinner.setSelection(2); // ToDo set to default location
        daysOfWeekSpinner.setSelection(0);
    }

    private String getOpenRegistrationDates() {

        //TODO run sql query to get fromDate and toDate
        String fromDate = "first date";
        String toDate = "second date";
        String openRegistrationString = String.format(getResources().getString(R.string.registration_date_range),
                fromDate, toDate);
        return openRegistrationString;
    }

    private void initializeViews(View rootView) {

        registrationDateTextView = (TextView) rootView.findViewById(R.id.registrationDateTextView);
        locationsSpinner = (Spinner) rootView.findViewById(R.id.locationSpinner);
        daysOfWeekSpinner = (Spinner) rootView.findViewById(R.id.dayOfWeekSpinner);
        searchButton = (CardView) rootView.findViewById(R.id.searchButton);
    }

    private void registerListeners() {

        searchButton.setOnClickListener(searchButtonListener);
    }

    private void bindSpinnerData() {

        mainActivity.helperMethods.addDataToSpinner(mainActivity.branchesNameArrayList, locationsSpinner, "location");

        String[] daysOfWeek = getResources().getStringArray(R.array.days_of_week_array);
        ArrayList<String> daysOfWeekArrayList = new ArrayList<String>();
        for (String s : daysOfWeek) daysOfWeekArrayList.add(s);
        mainActivity.helperMethods.addDataToSpinner(daysOfWeekArrayList, daysOfWeekSpinner, "dayOfWeek");
    }

    OnClickListener searchButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            //TODO pass to method that will query and then update array and then notify data set changed
            Branch searchCriteriaBranch=new Branch();
            for (Branch branch: mainActivity.branchesArrayList){
                if (locationsSpinner.getSelectedItem().toString().equals(branch.getName()))
                     searchCriteriaBranch =branch;
            }

            int  searchCriteriaDayOfWeek= daysOfWeekSpinner.getSelectedItemPosition();
            mainActivity.helperMethods.findTests(searchCriteriaBranch,searchCriteriaDayOfWeek);
    }
    };

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}