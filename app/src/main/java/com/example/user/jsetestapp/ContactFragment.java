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
    @Override
    public void onResume() {
        super.onResume();

         mainActivity.tabLayout.getTabAt(3).select();

    }

    private void initializeViews(View rootView) {

        officeNumberTextViews = (LinearLayout) rootView.findViewById(R.id.officeNumberTextViews);
        scheduleTestNumberTextViews = (LinearLayout) rootView.findViewById(R.id.scheduleTestNumberTextViews);
        emailAddressTextViews = (LinearLayout) rootView.findViewById(R.id.emailAddressTextViews);
        jseOfficeHoursMonThursTextView = (TextView) rootView.findViewById(R.id.jseOfficeHoursMonThursTextView);
        jseOfficeHoursFridayTextView = (TextView) rootView.findViewById(R.id.jseOfficeHoursFridayTextView);
    }

    private void setText() {
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
            if (mainActivity.helperMethods.isDuringOfficeHours()) {

                Util.showDialog(HelperMethods.getDialogFragmentBundle(
                        getString(R.string.d_call_jse_during_hours)
                ));

            } else {
                Util.showDialog(HelperMethods.getDialogFragmentBundle(
                        getString(R.string.d_call_jse_non_hours)
                ));
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
            IntentMethods.composeEmail(addresses, subject);
        }
    };


    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}