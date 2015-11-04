package com.example.user.jsetestapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.Calendar;

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

    public void calendarIntent(String title, String eventLocation, String description, LocalDate testDate, LocalTime testTime) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        //TODO check if any params are null
        if (title != null){
            intent.putExtra("title", title);
        }
        if (eventLocation!=null){
            intent.putExtra("eventLocation", eventLocation);
        }
        if (description!=null){
            intent.putExtra("description", description);
        }
        if (testDate!=null && testTime!=null){
            Calendar startTime = Calendar.getInstance();
            startTime.set(testDate.getYear(), testDate.getMonthOfYear() - 1, testDate.getDayOfMonth(), testTime.getHourOfDay(),testTime.getMinuteOfHour());
            intent.putExtra("beginTime", startTime.getTimeInMillis());
        }
        intent.putExtra("allDay", false);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("hasAlarm", 1);

        if (intent.resolveActivity(mainActivity.getApplicationContext().getPackageManager()) != null) {
            mainActivity.doIntent(intent);
        } else {
            Toast.makeText(mainActivity.getApplicationContext(), "No calendar clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}