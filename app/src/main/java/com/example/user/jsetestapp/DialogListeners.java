package com.example.user.jsetestapp;


import android.app.Activity;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DialogListeners extends Activity {

    MainActivity mainActivity;

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

                // TODO send JSE office hours and correct parameters
                // ToDO handle if friday, saturday
                LocalDate localDate = LocalDate.now();
                int dayOfWeek = localDate.getDayOfWeek();
                if (dayOfWeek!=5) {
                    String hours = mainActivity.getResources().getString(R.string.jse_office_hours_mon_thurs_hours_start_time);
                    setReminderToCallJse(hours);
                } else{
                    String hours = mainActivity.getResources().getString(R.string.jse_office_hours_friday_hours_end_time);
                    setReminderToCallJse(hours);
                }
                // ToDO handle if friday, saturday
                break;
            }

            case "call_jse_during_office_hours": {
                mainActivity.intentMethods.callIntent(mainActivity.getStringFromResources(R.string.jse_phone_number));
                break;
            }

            case "results_no_tests": {
                mainActivity.helperMethods.replaceFragment(R.id.container, mainActivity.searchFragment, mainActivity.getResources().getString(R.string.toolbar_title_search));
                mainActivity.tabLayout.getTabAt(1).select();
            }

        }
    }

    public void setReminderToCallJse(String hours){
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm a");
        LocalTime localTime;
        localTime = fmt.parseLocalTime(hours);
        mainActivity.intentMethods.calendarIntent("Call JSE", null, null, LocalDate.now().plusDays(1), localTime);
    }

    public void negativeButtonOnClickListener(String TAG_LISTENER) {

    }

    public void neutralButtonOnClickListener(String TAG_LISTENER) {

    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

}
