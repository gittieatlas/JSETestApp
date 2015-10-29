package com.example.user.jsetestapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by gatlas on 10/22/2015.
 */
public class IntentMethods extends Activity {

    MainActivity mainActivity;

    public IntentMethods() {

    }


    /**
     * Function to send email with an intent
     *
     * @param addresses - array of email addresses
     * @param subject   - subject line of email
     */
    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(mainActivity.getApplicationContext().getPackageManager()) != null) {
            mainActivity.doIntent(intent);
        } else {
            Toast.makeText(mainActivity.getApplicationContext(), "No email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Function to make a call with an intent
     *
     * @param number - number to call
     */
    public void callIntent(String number) {
        Uri call = Uri.parse("tel:" + number);
        Intent intent = new Intent(Intent.ACTION_CALL, call);
        mainActivity.doIntent(intent);
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void calendarIntent(String title, String eventLocation, String description, String testDate, String testTime) {

        //TODO work on startTime and endTime and date
        //TODO check if any params are null
        //TODO set Alarm
        //TODO for location - show address?

        // long startTime = System.currentTimeMillis() + 1000 * 60 * 60;
        // long endTime = System.currentTimeMillis() + 1000 * 60 * 60 * 2;

        //String startDate = testDate;

        Date date = null;
        long starttime, endtime;

        //try {
        //Date testdate = new SimpleDateFormat("MMMMMMMMM dd yyyy").parse("10 28 2015");
        //startTime=date.getTime();
        // date = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:Ss z").parse(tit1);
        //}
        //catch(Exception e){ }

        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", "12 28 2016");
        intent.putExtra("allDay", false);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", "12 28 2016");
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("eventLocation", eventLocation);
        intent.putExtra("hasAlarm", 1);

        if (intent.resolveActivity(mainActivity.getApplicationContext().getPackageManager()) != null) {
            mainActivity.doIntent(intent);
        } else {
            Toast.makeText(mainActivity.getApplicationContext(), "No calendar clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
