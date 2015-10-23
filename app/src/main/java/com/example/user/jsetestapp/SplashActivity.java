package com.example.user.jsetestapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    //Controls


    //Activities HelperClasses Classes;


    //Fragments
    LoginFragment loginFragment;
    DashboardFragment dashboardFragment;
    //DataMethods dataMethods;
    MainActivity mainActivity;

    //Variables
    Boolean isInternetPresent = false;
    ConnectionDetector cd;  // Connection detector class
    boolean startUpActivityDone = true;

    ArrayList<String> locationsArrayList;
    ArrayList<Test> testsArrayList;
    ArrayList<Hours> hoursArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        setUpFragments();
    }

    private void setUpFragments() {

//        dataMethods = new DataMethods();
//        dataMethods.setSplashActivity(this);
        //mainActivity = new MainActivity();


    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.

        checkInternetConnection();

    }

    private void checkInternetConnection() {

        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());

        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
        if (isInternetPresent) {
            // Internet Connection is Present
            // make HTTP requests
                //showAlertDialog(SplashActivity.this, "Internet Connection",
                //        "You have internet connection", true);

//            dataMethods.setUpLocationsArrayList();
//            dataMethods.setUpTestsArrayList();
//            dataMethods.setUpHoursArrayList();

            changeActivities(startUpActivityDone);
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(SplashActivity.this, "No Internet Connection",
                    "You need an internet connection to use this application.\n\nPlease try again.\n", false);
        }
    }

    /**
     * Function to display simple Alert Dialog
     *
     * @param context - application context
     * @param title   - alert dialog title
     * @param message - alert message
     * @param status  - success/failure (used to set icon)
     */
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.ic_check_grey600_24dp : R.drawable.ic_exclamation_grey600_24dp);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void changeActivities(boolean startUpActivityDone) {
        if (startUpActivityDone){

            // TODO check if loggedIn == true then go to MainActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

}
