package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactFragment extends Fragment {

// TODO get correct info for contact page; JSE Office, Schedule a Test, Email, Office Hours

    //Controls
    View rootView;
    LinearLayout officeNumberTextViews, scheduleTestNumberTextViews, emailAddressTextViews;
    TextView jseOfficeHoursMonThursTextView, jseOfficeHoursFridayTextView;

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
        setText();
        return rootView;
    }

    private void initializeViews(View rootView) {

        officeNumberTextViews = (LinearLayout) rootView.findViewById(R.id.officeNumberTextViews);
        scheduleTestNumberTextViews = (LinearLayout) rootView.findViewById(R.id.scheduleTestNumberTextViews);
        emailAddressTextViews = (LinearLayout) rootView.findViewById(R.id.emailAddressTextViews);
        jseOfficeHoursMonThursTextView = (TextView)rootView.findViewById(R.id.jseOfficeHoursMonThursTextView);
        jseOfficeHoursFridayTextView = (TextView)rootView.findViewById(R.id.jseOfficeHoursFridayTextView);
    }

    private void setText(){
        jseOfficeHoursMonThursTextView.setText(getResources().getString(R.string.jse_office_hours_mon_thurs_hours_start_time) + " - " + getResources().getString(R.string.jse_office_hours_mon_thurs_hours_end_time));
        jseOfficeHoursFridayTextView.setText(getResources().getString(R.string.jse_office_hours_friday_hours_start_time) + " - " + getResources().getString(R.string.jse_office_hours_friday_hours_end_time));
    }

    private void registerListeners() {

        officeNumberTextViews.setOnClickListener(officeNumberListener);
        scheduleTestNumberTextViews.setOnClickListener(scheduleTestNumberListener);
        emailAddressTextViews.setOnClickListener(emailAddressListener);
    }

    OnClickListener officeNumberListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

// ToDo get value of isDuringOfficeHours
            boolean isDuringOfficeHours = false;

            if (isDuringOfficeHours) {

// TODO change icon to phone grey 24; add string to strings.xml
// TODO show number in message?
                mainActivity.showDialog("JSE Office", null, "CALL", "CANCEL", null, R.drawable.ic_calendar_clock_grey600_24dp, "call_jse_during_office_hours");
            } else {

// TODO change icon?; add string to strings.xml
                String message = "The JSE office is currently closed. Would you like to set a reminder on your phone to call during office hours?";
                mainActivity.showDialog("JSE Office", message, "YES", "NO", null, R.drawable.ic_calendar_clock_grey600_24dp, "call_jse_during_non_office_hours");
            }
        }
    };

    OnClickListener scheduleTestNumberListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mainActivity.user.isJseMember) {
                mainActivity.helperMethods.scheduleTest();
            } else {
                mainActivity.user.isJseMember = true;
                mainActivity.helperMethods.scheduleTest();
                mainActivity.user.isJseMember = false;
            }
        }
    };

    OnClickListener emailAddressListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            String[] addresses = {"info@jseoffice.com"};
            String subject = "Contact JSE - Android App";
            mainActivity.intentMethods.composeEmail(addresses, subject);
        }
    };


    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}