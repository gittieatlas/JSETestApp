package com.example.user.jsetestapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

    // declare variable
    private Context _context;

    // constructor
    public ConnectionDetector(Context context) {
        // assign current context to context
        this._context = context;
    }

    /**
     * Function to check if application can connect to internet
     * @return boolean
     */
    public boolean isConnectingToInternet() {
        // create a new ConnectivityManager and try to get connectivity system service
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // connectivity is not empty
        if (connectivity != null) {
            // create a new Network info from the connectivity
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

            // if networkInfo is not empty
            if (networkInfo != null) {
                // if state of network info equals connected
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }
        return false;
    }
}