package com.example.user.jsetestapp;
// CLEANED

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.widget.EditText;

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
        // instantiate user
        User user = new User();

        try {
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
     * @param bundle - information to be passed to new dialog fragment
     */
    public static void showDialog(Bundle bundle) {
        // instantiate new dialog fragment
        CustomDialogFragment dialogFragment = new CustomDialogFragment();

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
}
