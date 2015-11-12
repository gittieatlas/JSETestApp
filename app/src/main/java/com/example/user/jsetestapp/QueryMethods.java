package com.example.user.jsetestapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class QueryMethods extends Activity {

    MainActivity mainActivity;
    LoginActivity loginActivity;
    public QueryMethods() {

    }

    public void setUpLocationsArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.locationsArrayList = (ArrayList<Location>) bundle.getSerializable("locationsArrayList");

    }

    public void setUpUser() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.user = (User) bundle.getSerializable("user");

    }

    public void setUpLocationsNameArrayList(MainActivity mainActivity) {
        mainActivity.locationsNameArrayList = new ArrayList<String>();
        mainActivity.locationsNameArrayList.add("Location");
        for (Location location : mainActivity.locationsArrayList) {
            mainActivity.locationsNameArrayList.add(location.getName());
        }
    }


    //for createUser activity
    public void setUpLocationsNameArrayList(LoginActivity loginActivity) {
        loginActivity.locationsNameArrayList = new ArrayList<String>();
        loginActivity.locationsNameArrayList.add("Location");
        for (Location location : loginActivity.locationsArrayList) {
            loginActivity.locationsNameArrayList.add(location.getName());
        }
    }

    public void setUpBranchesArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.branchesArrayList = (ArrayList<Branch>) bundle.getSerializable("branchesArrayList");

    }

    public void setUpBranchesNameArrayList() {
        mainActivity.branchesNameArrayList = new ArrayList<String>();
        mainActivity.branchesNameArrayList.add("Branch");

        for (Branch branch : mainActivity.branchesArrayList) {
            mainActivity.branchesNameArrayList.add(branch.getName());
        }

    }

    public void setUpTestsArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.testsArrayList = (ArrayList<Test>) bundle.getSerializable("testsArrayList");

    }

    public void filterTestsArrayListByGender(){
        for (int i = 0; i < loginActivity.testsArrayList.size(); i++){
            if (!loginActivity.testsArrayList.get(i).gender.name().equals(loginActivity.user.gender.name())){
                loginActivity.testsArrayList.remove(i);
                i--;
            }
        }
    }


    public void setUpTestsFilteredArrayList() {

       mainActivity.testsFilteredArrayList = new ArrayList<DataObject>();
    }

    public void setUpHoursArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.hoursArrayList = (ArrayList<Hours>) bundle.getSerializable("hoursArrayList");
    }


    public void updateHoursArrayListView(ListView listView, String name){

        setUpHoursFilteredArrayList(name);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        mainActivity.helperMethods.setListViewHeightBasedOnItems(listView);
    }


    public void setupListView(ListAdapter adapter, ListView listView,String name) {
        adapter = new MyBaseAdapter(mainActivity.getContext(), mainActivity.hoursFilteredArrayList);
        listView.setAdapter(adapter);
        updateHoursArrayListView(listView, name);
    }

    public void setUpAlertsArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.alertsArrayList = (ArrayList<Alerts>) bundle.getSerializable("alertsArrayList");
    }

    public void setUpHoursFilteredArrayList(String location) {

        if (mainActivity.getHoursFilteredArrayList()!= null)
            mainActivity.getHoursFilteredArrayList().clear();
        for (Hours hours : mainActivity.hoursArrayList) {

            if (hours.getName().equals(location)) {

                HoursDataObject obj = new HoursDataObject(mainActivity.helperMethods.firstLetterCaps(hours.getDayOfWeek().toString()),

                        hours.getStartTime().toString("hh:mm a"),
                        hours.getEndTime().toString("hh:mm a"));

                mainActivity.hoursFilteredArrayList.add(obj);
            }
        }
    }

    public Location setUpDefaultLocation(){
        Location location = new Location();
        for (Location l : mainActivity.locationsArrayList) {
            if (l.getName().equals(mainActivity.user.getDefaultLocation())) {
                location = l;
            }
        }
        return location;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setLoginActivity(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }







}


