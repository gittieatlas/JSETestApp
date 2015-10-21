package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ContactFragment extends Fragment {

    // TODO get correct info for contact page; JSE Office, Schedule a Test, Email, Office Hours

    //Controls
    View rootView;
    LinearLayout officeNumberTextViews, scheduleTestNumberTextViews, emailAddressTextViews;


    //Activities
    MainActivity mainActivity;

    //Fragments


    //Variables

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        initializeViews(rootView);
        registerListeners();
        mainActivity.setToolbarTitle(R.string.toolbar_title_contact);

        return rootView;
    }

    private void initializeViews(View rootView) {

        officeNumberTextViews = (LinearLayout) rootView.findViewById(R.id.officeNumberTextViews);
        scheduleTestNumberTextViews = (LinearLayout) rootView.findViewById(R.id.scheduleTestNumberTextViews);
        emailAddressTextViews = (LinearLayout) rootView.findViewById(R.id.emailAddressTextViews);
    }

    private void registerListeners() {

        officeNumberTextViews.setOnClickListener(officeNumberListener);
        scheduleTestNumberTextViews.setOnClickListener(scheduleTestNumberListener);
        emailAddressTextViews.setOnClickListener(emailAddressListener);
    }

    OnClickListener officeNumberListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            boolean isDuringOfficeHours = false;
            try {
                // TODO change icon to phone grey 24; add string to strings.xml
                if (isDuringOfficeHours) {
                    mainActivity.helperMethods.showAlertDialog("JSE Office",
                            "732-888-8978",
                            R.drawable.ic_calendar_clock_grey600_24dp, "CALL",
                            "CANCEL",
                            "call_jse_during_office_hours");
                }
                else {
                    // TODO change icon?; add string to strings.xml
                    mainActivity.helperMethods.showAlertDialog("JSE Office",
                            "THe JSE office is currently closed. Would you like to set a reminder on your phone to call during office hours?",
                            R.drawable.ic_calendar_clock_grey600_24dp,
                            "YES",
                            "NO",
                            "call_jse_during_non_office_hours");
                }
            } catch (Exception e){
                Toast.makeText(mainActivity.getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };

    OnClickListener scheduleTestNumberListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                // TODO change icon to phone grey 24; add string to strings.xml

                    mainActivity.helperMethods.showAlertDialog("Schedule a Test",
                            "800-989-6985",
                            R.drawable.ic_calendar_clock_grey600_24dp, "Call",
                            "CANCEL",
                            "schedule_test");

            } catch (Exception e){
                Toast.makeText(mainActivity.getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };

    OnClickListener emailAddressListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            String[] addresses = {"info@jseoffice.com"};
            String subject = "Contact JSE - Android App";
            mainActivity.helperMethods.composeEmail(addresses, subject);
        }
    };


    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}
