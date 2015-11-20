package com.example.user.jsetestapp;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Rochel on 11/20/2015.
 */

public class Util extends Activity {

    private static Context context = null;

    public static String getStringValue(int i){
        return context.getString(i);

    }


    public static void setContext(Context context) {
        Util.context = context;
    }
}
