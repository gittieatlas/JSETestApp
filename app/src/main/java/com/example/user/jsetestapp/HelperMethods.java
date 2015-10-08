package com.example.user.jsetestapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by Rochel on 10/8/2015.
 */
public class HelperMethods extends Activity{

public HelperMethods(){

}
    LoginFragment loginFragment;
    MainActivity mainActivity;
    LibrariesFragment librariesFragment;

    public void replaceFragment(int container, Fragment fragment){
    getFragmentManager().beginTransaction().replace(container, fragment).commit();
    }

    public void addFragment(int container, Fragment fragment){
        getFragmentManager().beginTransaction().add(container, fragment).commit();
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}