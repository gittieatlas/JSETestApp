package com.example.user.jsetestapp;

import android.app.Activity;
import android.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Rochel on 10/8/2015.
 */
public class HelperMethods extends Activity {

    MainActivity mainActivity;

    public HelperMethods() {

    }

    public void replaceFragment(int container, Fragment fragment) {
        getFragmentManager().beginTransaction().replace(container, fragment).commit();
    }

    public void addFragment(int container, Fragment fragment) {
        getFragmentManager().beginTransaction().add(container, fragment).commit();
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
}