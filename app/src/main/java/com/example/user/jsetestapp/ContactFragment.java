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

    // Declare Controls
    LinearLayout officeNumber, scheduleTestNumber, emailAddress;
    TextView jseOfficeHoursMonThursTextView, jseOfficeHoursFridayTextView;

    // Declare Activities
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        initializeViews(rootView);
        registerListeners();
        setOfficeHours();

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_contact, mainActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // select contact tab
        mainActivity.tabLayout.getTabAt(3).select();
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews(View rootView) {
        // initialize and reference LinearLayouts
        officeNumber = (LinearLayout) rootView.findViewById(R.id.officeNumber);
        scheduleTestNumber = (LinearLayout) rootView.findViewById(R.id.scheduleTestNumber);
        emailAddress = (LinearLayout) rootView.findViewById(R.id.emailAddress);

        // initialize and reference TextViews
        jseOfficeHoursMonThursTextView = (TextView) rootView.findViewById(R.id.jseOfficeHoursMonThursTextView);
        jseOfficeHoursFridayTextView = (TextView) rootView.findViewById(R.id.jseOfficeHoursFridayTextView);
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        officeNumber.setOnClickListener(officeNumberListener);
        scheduleTestNumber.setOnClickListener(scheduleTestNumberListener);
        emailAddress.setOnClickListener(emailAddressListener);
    }

    /**
     * Function to set office hours
     */
    private void setOfficeHours() {
        // set office hours in text views
        jseOfficeHoursMonThursTextView.setText(getResources().getString(R.string.jse_office_hours_mon_thurs_hours_start_time) + " - " + getResources().getString(R.string.jse_office_hours_mon_thurs_hours_end_time));
        jseOfficeHoursFridayTextView.setText(getResources().getString(R.string.jse_office_hours_friday_hours_start_time) + " - " + getResources().getString(R.string.jse_office_hours_friday_hours_end_time));
    }

    /**
     * OnClickListener for officeNumber
     */
    OnClickListener officeNumberListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // if its during office hours
            if (mainActivity.helperMethods.isDuringOfficeHours()) {
                // show dialog "Call JSE"
                Util.showDialogFragment(R.array.call_jse_during_office_hours);
            // if its after office hours
            } else {
                // show dialog "Jse office is currently closed. Would you like to set a reminder?"
                Util.showDialogFragment(R.array.call_jse_during_non_office_hours);
            }
        }
    };

    /**
     * OnClickListener for scheduleTestNumber
     */
    OnClickListener scheduleTestNumberListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // if user is jse member
            if (mainActivity.user.isJseMember) {
                // call to schedule a test
                mainActivity.helperMethods.scheduleTest();
            } else {
                // set user to be a jse member
                mainActivity.user.isJseMember = true;
                // call to schedule a test
                mainActivity.helperMethods.scheduleTest();
                // set user to be a non jse member
                mainActivity.user.isJseMember = false;
            }
        }
    };

    /**
     * OnClickListener for emailAddress
     */
    OnClickListener emailAddressListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // get list of email addresses from xml array
            String[] addresses =
                    Util.getActivity().getResources().getStringArray(R.array.addresses);

            // subject line
            String subject = "Contact JSE - Android App";
            // compose email using email address and subject line
            IntentMethods.composeEmail(addresses, subject);
        }
    };

    /**
     * Function to set reference of MainActivity
     *
     * @param mainActivity - reference to MainActivity
     */
    public void setMainActivity(MainActivity mainActivity) {
        // set this mainActivity to mainActivity
        this.mainActivity = mainActivity;
    }
}