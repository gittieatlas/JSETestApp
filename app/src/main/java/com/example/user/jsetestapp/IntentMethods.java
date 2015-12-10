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

    public IntentMethods() {

    }


    /**
     * Function to send email with an intent
     *
     * @param addresses - array of email addresses
     * @param subject   - subject line of email
     */
    public static void composeEmail(String[] addresses, String subject) {
        // Create an Intent. Set the action to ACTION_SENDTO
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        // setdata convert mailto to uri
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        // add into intent a String[] holding e-mail addresses that should be delivered to.
        // add address to intent
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        // add into intent a constant string holding the desired subject line of a message.
        // add subject to intent
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(Util.getContext().getPackageManager()) != null) {
            //mainActivity.doIntent(intent);
            Util.getActivity().startActivity(intent);
        } else {
            Toast.makeText(Util.getContext(), "No email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Function to make a call with an intent
     *
     * @param number - number to call
     */
    public static void callIntent(String number) {
        Uri call = Uri.parse("tel:" + number);
        Intent intent = new Intent(Intent.ACTION_CALL, call);

        if (intent.resolveActivity(Util.getContext().getPackageManager()) != null) {
            Util.getActivity().startActivity(intent);
        } else {
            Toast.makeText(Util.getContext(), "No phone clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void calendarIntent(String title, String eventLocation, String description, LocalDate testDate, LocalTime testTime) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");

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

        if (intent.resolveActivity(Util.getContext().getPackageManager()) != null) {
            //mainActivity.doIntent(intent);
            Util.getActivity().startActivity(intent);
        } else {
            Toast.makeText(Util.getContext(), "No calendar clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}