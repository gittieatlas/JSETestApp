package com.example.user.jsetestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;

import com.mysql.jdbc.util.ErrorMappingsDocGenerator;

/**
 * Created by Rochel on 11/20/2015.
 */

public class Util extends Activity {

    private static Context context = null;

    private static Activity activity = null;

    public static String getStringValue(int i) {
        return context.getString(i);

    }

    public static void setActivity(Activity activity) {
        Util.activity = activity;
    }

    /**
     * Function to check if ssnEditText contains 4 numbers
     *
     * @param ssn -   EditText containing the ssn
     * @return boolean      -   if valid return true; if not valid return false
     */
     public static Boolean isSsn(String ssn) {

        if (ssn.length() < 4) {
            return false;
        }
        return true;
    }

    /**
     * Function to check if DOB is valid
     *
     * @param dobYear, dobMonth, dobDay -   Strings containing the DOB
     * @return boolean      -   if valid date return true; if not valid return false
     */
    public static Boolean isBirthdayCorrect(String dobYear, String dobMonth, String dobDay) {

        User user = new User();

        try {
            user.setDob(dobYear, dobMonth, dobDay);
            return true;

        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * Function to check if passwords are equal
     *
     * @param newPassword, confirmNewPassword -   EditTexts containing the 2 passwords
     * @return boolean      -   if passwords are equal return true; if not return false
     */
    public static boolean passwordEqualsConfirmPassword(String newPassword, String confirmNewPassword) {
        if (newPassword.equals(confirmNewPassword)) {
            return true;
        }
        return false;
    }

    /**
     * Function to make the first letter caps and the rest lowercase.
     *
     * @param data -   capitalize this
     * @return String       -   alert message?
     */
    public static String firstLetterCaps(String data) {
        String firstLetter = data.substring(0, 1).toUpperCase();
        String restLetters = data.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }

    /**
     * Function to check if a string is a valid email address
     *
     * @param email -   string containing the email to be validated
     * @return boolean      -   if valid return true; if not valid return false
     */
    public static boolean isEmailAddressValid(String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else
            return false;
    }

    /**
     * Function to create open Google Maps with address populated
     *
     * @param address - address that is added to Uri for intent
     */
    public static void navigationIntent(String address){

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



    public static void setContext(Context context) {

        Util.context = context;
    }
}
