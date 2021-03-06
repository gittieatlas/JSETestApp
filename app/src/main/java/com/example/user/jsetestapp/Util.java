package com.example.user.jsetestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Calendar;

public class Util extends Activity {

    // initialize variables
    private static Context context = null;
    private static Activity activity = null;

    /**
     * Function to set reference of current activity
     *
     * @param activity - current activity
     */
    public static void setActivity(Activity activity) {
        // set this activity to current activity
        Util.activity = activity;
    }

    /**
     * Function to return reference of current activity
     *
     * @return activity
     */
    public static Activity getActivity() {
        return activity;
    }

    /**
     * Function to return reference of current context
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Function to set reference of current context
     *
     * @param context - current context
     */
    public static void setContext(Context context) {

        // set this context to current context
        Util.context = context;
    }

    /**
     * Function to call methods that set reference to current activity
     *
     * @param activity - current activity
     */
    public static void setReference(Activity activity) {
        // set context to activity
        setContext(activity);
        // set activity to activity
        setActivity(activity);
    }

    /**
     * Function to return string from resource
     *
     * @param resourceReference - reference to position in resource file
     * @return String
     */
    public static String getStringValue(int resourceReference) {
        // get string from resource and return
        return context.getString(resourceReference);
    }

    /**
     * Function to check length of string
     *
     * @param text   - string of text
     * @param length - length of text
     * @return boolean
     */
    public static Boolean isLength(String text, int length) {
        // return true if length of text equals to length
        return text.length() == length;
    }

    /**
     * Function to check if dob is valid
     *
     * @param dobYear  - dob year
     * @param dobMonth - dob month
     * @param dobDay   - dob day
     * @return boolean
     */
    public static Boolean isBirthdayCorrect(String dobYear, String dobMonth, String dobDay) {

        // if year isn't a length of 4
        if (dobYear.length() != 4)
            return false;

        // if month isn't a length of 2
        if (dobMonth.length() != 2)
            return false;

        // if day isn't a length of 2
        if (dobDay.length() != 2)
            return false;

        // check if actual values entered is a valid ic_calendar_green_24dp date
        try {
            // instantiate user
            User user = new User();

            // try to set dob to user. if it doesn't throw exception, date is valid
            user.setDob(dobYear, dobMonth, dobDay);

            return true;

        } catch (Exception ex) {
            // if trying to set dob caused an exception, date is invalid
            return false;
        }

    }

    /**
     * Function to check if two Strings are equal
     *
     * @param s1 - string 1
     * @param s2 - string 2
     * @return boolean
     */
    public static boolean compareTwoStrings(String s1, String s2) {
        // if s1 equals to s2, return true
        return s1.equals(s2);
    }

    /**
     * Function to make the first letter caps and the rest lowercase.
     *
     * @param data - capitalize this
     * @return String
     */
    public static String firstLetterCaps(String data) {
        String firstLetter = data.substring(0, 1).toUpperCase();
        String restLetters = data.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }

    /**
     * Function to check if editText contains a string with a valid email address
     *
     * @param email - editText containing the string to be validated
     * @return boolean
     */
    public static boolean isEmailAddressValid(EditText email) {
        // return true if email matches a valid email address pattern
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
    }

    /**
     * Function to open Google Maps with address populated
     *
     * @param address - address that is added to Uri for intent
     */
    public static void navigationIntent(String address) {
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            activity.startActivity(mapIntent);
        }
    }

    /**
     * Function to set title of toolbar
     *
     * @param toolbarTitle - int of string reference to add as toolbar title
     * @param toolbar      - set title to this toolbar
     */
    public static void setToolbarTitle(int toolbarTitle, Toolbar toolbar) {
        // set toolbar title
        toolbar.setTitle(toolbarTitle);
    }

    /**
     * Function to create and show dialog fragment
     *
     * @param array - that holds info about the dialog fragment
     */
    public static void showDialogFragment(int array) {
        // instantiate new dialog fragment
        CustomDialogFragment dialogFragment = new CustomDialogFragment();

        // create a bundle with dialog information from dialog array
        Bundle bundle = HelperMethods.getDialogFragmentBundle(array);

        // add bundle as an argument to dialogFragment
        dialogFragment.setArguments(bundle);

        // show dialogFragment; add to this fragment FragmentManager, add tag to fragment
        dialogFragment.show(activity.getFragmentManager(), bundle.getString("tagListener"));
    }

    /**
     * Function to launch new activity and finish current activity
     *
     * @param intent - intent to switch activities
     */
    public static void launchActivity(Intent intent) {
        // start new intent
        context.startActivity(intent);

        // close this activity
        activity.finish();
    }

    /**
     * Function to move focus to the next control if text length is a given value
     *
     * @param editable  - text that the textWatcher picked up
     * @param fromFocus - move focus from this editText
     * @param toFocus   - move focus to this editText
     * @param length    - required length of text
     */
    public static void removeFocusFromEditText(Editable editable, EditText fromFocus,
                                               EditText toFocus, int length) {
        // if text the textWatcher picked up equals to text of fromFocus editText
        if (editable == fromFocus.getEditableText()) {
            // if length of text from fromFocus editText equals to 'length'
            if (fromFocus.getText().toString().length() == length)
                // if a editText exists after the fromFocus editText
                if (toFocus != null) {
                    // move focus to toFocus editText
                    toFocus.requestFocus();
                }
                // if the next control is not an editText
                else {
                    // hide soft keyboard
                    HelperMethods.hideSoftKeyboard();
                }
        }
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
        // Only email apps should handle this
        // Create a Uri from an intent string.
        intent.setData(Uri.parse("mailto:"));
        // Add addresses to intent
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        // Add subject to intent
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        // Attempt to start an activity that can handle the Intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            activity.startActivity(intent);
        } else {
            // Show message: "No email clients installed."
            Toast.makeText(context, "No email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Function to make a call with an intent
     *
     * @param number - number to call
     */
    public static void callIntent(String number) {
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri call = Uri.parse("tel:" + number);
        // Create an Intent. Set the action to ACTION_CALL and send uri
        Intent intent = new Intent(Intent.ACTION_CALL, call);

        // Attempt to start an activity that can handle the Intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            activity.startActivity(intent);
        } else {
            // Show message: "No phone clients installed."
            Toast.makeText(context, "No phone clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Function to make a call with an intent
     *
     * @param title - title of event
     * @param eventLocation - location of event
     * @param description - description of event
     * @param testDate - date of event
     * @param testTime - time of event
     */
    public static void calendarIntent(String title, String eventLocation, String description,
                                      LocalDate testDate, LocalTime testTime) {
        // Create an Intent. Set the action to ACTION_INSERT
        Intent intent = new Intent(Intent.ACTION_INSERT);
        // Set type as ic_calendar_green_24dp event
        intent.setType("vnd.android.cursor.item/event");
        // If title is not null
        if (title != null){
            // Add title to intent
            intent.putExtra("title", title);
        }
        // If eventLocation is not null
        if (eventLocation!=null){
            // Add eventLocation to intent
            intent.putExtra("eventLocation", eventLocation);
        }
        // If description is not null
        if (description!=null){
            // Add description to intent
            intent.putExtra("description", description);
        }
        // If testDat is not null and testTime is not null
        if (testDate!=null && testTime!=null){
            // startTime equals to a calendar object whose fields are set to current date and time:
            Calendar startTime = Calendar.getInstance();
            // Set date and time of startTime
            startTime.set(testDate.getYear(), testDate.getMonthOfYear() - 1,
                    testDate.getDayOfMonth(), testTime.getHourOfDay(), testTime.getMinuteOfHour());
            // Add startTime to intent
            intent.putExtra("beginTime", startTime.getTimeInMillis());
        }
        // Add allDay(as false) to intent
        intent.putExtra("allDay", false);
        // Add alarm to intent
        intent.putExtra("hasAlarm", 1);

        // Attempt to start an activity that can handle the Intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            activity.startActivity(intent);
        } else {
            // Show message: "No calendar clients installed."
            Toast.makeText(context, "No calendar clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Function to clear all values of an ArrayList
     * @param arrayList - arrayList to clear
     */
    public static void clearArrayList(ArrayList arrayList) {
        //  if arrayList is not already cleared
        if (arrayList != null)
            // clear arrayList
            arrayList.clear();
    }

    /**
     * Function to check if an EditText does not contain text
     * @param editText - editText to check
     */
    public static boolean isEmpty(EditText editText) {
        // return true if editText values length is 0
        return editText.getText().toString().trim().length() == 0;
    }

    /**
     * Function to check if the current time is a start and end time
     * @param start - beginning time
     * @param end - finishing time
     * @param time - now
     * @return boolean
     */
    public static boolean isWithinInterval(LocalTime start, LocalTime end, LocalTime time) {
        // return true if time is between start time and end time
        return time.isAfter(start) && time.isBefore(end);
    }

    /**
     * Function to check internet status
     * @return boolean
     */
    public static boolean checkInternetConnection() {
        // creating connection detector class instance
        ConnectionDetector cd = new ConnectionDetector(Util.getContext());

        // return true if ConnectionDetector can connect to internet
        return cd.isConnectingToInternet();
    }

    /**
     * Method to show the toolbar and remove margin from scrollView
     *
     * @param toolbarLinearLayout - container holding the toolbar
     * @param scrollViewLinearLayout - container holding the scrollView
     */
    public static void showToolbar(LinearLayout toolbarLinearLayout,
                                   LinearLayout scrollViewLinearLayout) {
        // if toolbar is not null, show toolbar layout
        if (toolbarLinearLayout != null) {
            toolbarLinearLayout.setVisibility(View.VISIBLE);
        }

        // if scrollView is not null, add margin to the container
        if (scrollViewLinearLayout != null) {
            // set size in ??
            int sizeInDP = 56;
            // convert size to dp
            int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDP,
                    getActivity().getResources().getDisplayMetrics());

            // create new LayoutParams
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            // set margins
            params.setMargins(0, marginInDp, 0, 0);
            // add params to scrollView container
            scrollViewLinearLayout.setLayoutParams(params);
        }
    }

    /**
     * Method to hide the toolbar and remove margin from scrollView
     *
     * @param toolbarLinearLayout - container holding the toolbar
     * @param scrollViewLinearLayout - container holding the scrollView
     */
    public static void hideToolbar(LinearLayout toolbarLinearLayout,
                                   LinearLayout scrollViewLinearLayout) {
        // if toolbar is not null, hide toolbar layout
        if (toolbarLinearLayout != null) {
            toolbarLinearLayout.setVisibility(View.GONE);
        }

        // if scrollView is not null, add margin to the container
        if (scrollViewLinearLayout != null) {
            // create new LayoutParams
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            // set margins
            params.setMargins(0, 0, 0, 0);
            // add params to scrollView container
            scrollViewLinearLayout.setLayoutParams(params);
        }
    }
}
