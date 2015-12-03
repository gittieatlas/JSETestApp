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
    LinearLayout officeNumberTextViews, scheduleTestNumberTextViews, emailAddressTextViews;
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
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

         mainActivity.tabLayout.getTabAt(3).select();

    }

    /**
     * Function to initialize controls
     */
    private void initializeViews(View rootView) {
        // initialize and reference controls
        officeNumberTextViews = (LinearLayout) rootView.findViewById(R.id.officeNumberTextViews);
        scheduleTestNumberTextViews = (LinearLayout) rootView.findViewById(R.id.scheduleTestNumberTextViews);
        emailAddressTextViews = (LinearLayout) rootView.findViewById(R.id.emailAddressTextViews);
        jseOfficeHoursMonThursTextView = (TextView) rootView.findViewById(R.id.jseOfficeHoursMonThursTextView);
        jseOfficeHoursFridayTextView = (TextView) rootView.findViewById(R.id.jseOfficeHoursFridayTextView);
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        officeNumberTextViews.setOnClickListener(officeNumberListener);
        scheduleTestNumberTextViews.setOnClickListener(scheduleTestNumberListener);
        emailAddressTextViews.setOnClickListener(emailAddressListener);
    }

    /**
     * Function to set office hours in the text views
     */
    private void setOfficeHours() {
        jseOfficeHoursMonThursTextView.setText(getResources().getString(R.string.jse_office_hours_mon_thurs_hours_start_time) + " - " + getResources().getString(R.string.jse_office_hours_mon_thurs_hours_end_time));
        jseOfficeHoursFridayTextView.setText(getResources().getString(R.string.jse_office_hours_friday_hours_start_time) + " - " + getResources().getString(R.string.jse_office_hours_friday_hours_end_time));
    }

    /**
     * OnClickListener for officeNumber
     */
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

    /**
     * OnClickListener for scheduleTestNumber
     */
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

    /**
     * OnClickListener for emailAddress
     */
    OnClickListener emailAddressListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            String[] addresses = {"info@jseoffice.com"};
            String subject = "Contact JSE - Android App";
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