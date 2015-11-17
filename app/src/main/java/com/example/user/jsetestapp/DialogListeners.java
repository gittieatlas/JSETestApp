package com.example.user.jsetestapp;


import android.app.Activity;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DialogListeners extends Activity {

    MainActivity mainActivity;
    LoginActivity loginActivity;

    public void positiveButtonOnClickListener(String TAG_LISTENER) {
        switch (TAG_LISTENER) {

            case "schedule_test": {
                mainActivity.intentMethods.callIntent(mainActivity.getStringFromResources(R.string.schedule_test_phone_number));
                break;
            }

            case "become_jse_member": {
                mainActivity.intentMethods.callIntent(mainActivity.getStringFromResources(R.string.jse_phone_number));
                break;
            }

            case "call_jse_during_non_office_hours": {

                LocalDate localDate = LocalDate.now();
                int dayOfWeek = localDate.getDayOfWeek();

                // checking if dayOfWeek is friday, Shabbos, thursday or any other day
                String hours = mainActivity.getResources().getString(R.string.jse_office_hours_mon_thurs_hours_start_time);
                if (dayOfWeek == 5) {
                    // friday
                    setReminderToCallJse(hours, 3);
                } else if (dayOfWeek == 6) {
                    setReminderToCallJse(hours, 2);
                } else if (dayOfWeek == 4) {
                    String fridayHours = mainActivity.getResources().getString(R.string.jse_office_hours_friday_hours_end_time);
                    setReminderToCallJse(fridayHours, 1);
                } else {
                    setReminderToCallJse(hours, 1);
                }
                break;
            }

            case "call_jse_during_office_hours": {
                mainActivity.intentMethods.callIntent(mainActivity.getStringFromResources(R.string.jse_phone_number));
                break;
            }

            case "results_no_tests": {
                mainActivity.helperMethods.replaceFragment(R.id.container, mainActivity.searchFragment, mainActivity.getResources().getString(R.string.toolbar_title_search));
                mainActivity.tabLayout.getTabAt(1).select();
              //  getFragmentManager().popBackStack(); // activity has been destroyed
            }
            case "login_activity": {
                break;
            }
            case "login_failed_email_not_exist":
                loginActivity.helperMethods.replaceFragment(R.id.container, loginActivity.register1Fragment, loginActivity.getResources().getString(R.string.toolbar_title_register1), loginActivity);
                break;
            case "forgot_password": {
                //ToDo validate email address and get password from LDB that matches to emailEditText and send email to emailEditText. Close dialog
            }
            case "registration_failed_missing_fields":
                break;
        }
    }

    public void positiveButtonOnClickListener(String TAG_LISTENER, String email) {
        switch (TAG_LISTENER) {

            case "forgot_password": {
                // ToDo send email with password
                break;
            }
        }
    }

    public void negativeButtonOnClickListener(String TAG_LISTENER) {
        switch (TAG_LISTENER) {

        }
    }

    public void neutralButtonOnClickListener(String TAG_LISTENER) {
        switch (TAG_LISTENER) {
            case "create_account_failed_email_duplicate": {
                loginActivity.getFragmentManager().popBackStack();
                break;
            }

        }
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }

    public void setReminderToCallJse(String hours, int days) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm a");
        LocalTime localTime;
        localTime = fmt.parseLocalTime(hours);
        mainActivity.intentMethods.calendarIntent("Call JSE", null, null, LocalDate.now().plusDays(days), localTime);
    }

    public void setReminderToCallJse(String hours) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm a");
        LocalTime localTime;
        localTime = fmt.parseLocalTime(hours);
        mainActivity.intentMethods.calendarIntent("Call JSE", null, null, LocalDate.now().plusDays(1), localTime);
    }
}

