package com.example.user.jsetestapp;


import android.app.Activity;

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

                // TODO send JSE office hours
                mainActivity.intentMethods.calendarIntent("Call JSE", "", "");
                break;
            }

            case "call_jse_during_office_hours": {
                mainActivity.intentMethods.callIntent(mainActivity.getStringFromResources(R.string.jse_phone_number));
                break;
            }

            case "results_no_tests": {
                mainActivity.helperMethods.replaceFragment(R.id.container, mainActivity.searchFragment);
            }

        }
    }

    public void negativeButtonOnClickListener(String TAG_LISTENER) {

    }

    public void neutralButtonOnClickListener(String TAG_LISTENER) {

    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

}
