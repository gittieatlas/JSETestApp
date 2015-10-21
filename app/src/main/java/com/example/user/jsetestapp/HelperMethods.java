package com.example.user.jsetestapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Rochel on 10/8/2015.
 */
public class HelperMethods extends Activity {

    MainActivity mainActivity;
    String number;
    String title;
    String messgae;

    public HelperMethods() {

    }

    public void replaceFragment(int container, Fragment fragment) {
        mainActivity.scrollView.scrollTo(0, 0); // Scroll to top
        mainActivity.getFragmentManager().beginTransaction().replace(container, fragment).addToBackStack(null).commit();
    }

    public void addFragment(int container, Fragment fragment) {
        mainActivity.scrollView.scrollTo(0, 0); // Scroll to top
        mainActivity.getFragmentManager().beginTransaction().add(container, fragment).addToBackStack(null).commit();
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    //adding stuff here
    public void addDataToSpinner(ArrayList<String> arrayList, Spinner spinner, String tag) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainActivity.getApplicationContext(),
                R.layout.location_spinner_dropdown_item, arrayList);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        spinner.setAdapter(adapter);

        if (tag.equals("location")) spinner.setSelection(2);
        else spinner.setSelection(0);
    }

    public String getNumber() {
        if (mainActivity.isJseMember) return number = "7328148432";
        return "7328148430";
    }

    //    public String getTitle() {
//        if (mainActivity.isJseMember) return title = "CALL JSE";
//        return "CALL OFFICE";
//    }

//    public String getMessage() {
//        if (mainActivity.isJseMember) return message = "";
//        return "In order to schedule a test you must be a JSE member. call the ofiice to become one.";
//    }

    //    public void addToCalendar(){
//        Calendar beginTime = Calendar.getInstance();
//        beginTime.set(2012, 0, 19, 7, 30);
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(2012, 0, 19, 8, 30);
//        Intent intent = new Intent(Intent.ACTION_INSERT)
//                .setData(Events.CONTENT_URI)
//                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
//                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
//                .putExtra(Events.TITLE, "Yoga")
//                .putExtra(Events.DESCRIPTION, "Group class")
//                .putExtra(Events.EVENT_LOCATION, "The gym")
//                .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
//                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
//        startActivity(intent);
//    }

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
     * Function to display simple Alert Dialog
     *
     * @param title          - alert dialog title
     * @param message        - alert message
     * @param icon           - drawable for icon
     * @param positiveButton - text for positive button
     * @param negativeButton - text for negative button
     * @param TAG_LISTENER   - tag to pass through to listener method
     */
    public void showAlertDialog(String title, String message, int icon, String positiveButton, String negativeButton, final String TAG_LISTENER) {
        AlertDialog alertDialog = new AlertDialog.Builder(mainActivity.getContext()).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon(icon);

        // Setting Positive Button
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, positiveButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                positiveButtonOnClickListener(TAG_LISTENER);
            }
        });

        // Setting Negative Button
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, negativeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                negativeButtonOnClickListener(TAG_LISTENER);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void positiveButtonOnClickListener(String TAG_LISTENER) {
        switch (TAG_LISTENER) {

            case "schedule_test": {
                // TODO add method to remove dashes from string
                //callIntent(getResources().getString(R.string.schedule_test_phone_number));
                callIntent("732-121-1212");
                break;
            }

            case "call_jse_during_non_office_hours": {
                // TODO set calendar

                break;
            }

            case "call_jse_during_office_hours": {
                // TODO add method to remove dashes from string
                callIntent("732-898-9898");
                break;
            }
        }
    }

    public void negativeButtonOnClickListener(String TAG_LISTENER) {

    }

    private void callIntent(String number) {
        Uri call = Uri.parse("tel:" + number);
        Intent intent = new Intent(Intent.ACTION_CALL, call);
        mainActivity.doIntent(intent);
    }

}