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

    public ArrayList<String> setUpLocationsNameArrayList(ArrayList<Location> locationsArrayList) {

        ArrayList<String> locationsNameArrayList = new ArrayList<String>();
        locationsNameArrayList.add("Location");
        for (Location location : locationsArrayList) {
            locationsNameArrayList.add(location.getName());
        }
        return locationsNameArrayList;
    }

    public ArrayList<String> setUpBranchesNameArrayList(ArrayList<Branch> branchesArrayList) {
        ArrayList<String> branchesNameArrayList = new ArrayList<String>();
        branchesNameArrayList.add("Branch");

        for (Branch branch : branchesArrayList) {
            if (branch.getId() == (mainActivity.defaultLocation.branchId)) {
                branchesNameArrayList.add(branch.getName() + " (Default Branch)");
            } else {
                branchesNameArrayList.add(branch.getName());
            }
        }
        return branchesNameArrayList;
    }

    public ArrayList<Test> setUpTestsArrayList(ArrayList<Test> testsArrayList) {

        // filtering by gender
        for (int i = 0; i < testsArrayList.size(); i++) {
            if (!testsArrayList.get(i).gender.name().equals(mainActivity.user.gender.name())) {
                testsArrayList.remove(i);
                i--;
            }
        }
       return testsArrayList;
    }

    public void updateHoursArrayListView(ListView listView, String name) {

        setUpHoursFilteredArrayList(name);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        mainActivity.helperMethods.setListViewHeightBasedOnItems(listView);
    }


    public void setupListView(ListAdapter adapter, ListView listView, String name) {
        adapter = new HoursAdapter(Util.getContext(), mainActivity.hoursFilteredArrayList);
        listView.setAdapter(adapter);
        updateHoursArrayListView(listView, name);
    }

    public User setUpUser() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        return (User) bundle.getSerializable("user");

    }

    public void setUpHoursFilteredArrayList(String location) {

        if (mainActivity.hoursFilteredArrayList != null)
            mainActivity.hoursFilteredArrayList.clear();
        for (Hour hour : mainActivity.hourArrayList) {

            if (hour.getName().equals(location)) {

                HoursDataObject obj = new HoursDataObject(Util.firstLetterCaps(hour.getDayOfWeek().toString()),

                        hour.getStartTime().toString("hh:mm a"),
                        hour.getEndTime().toString("hh:mm a"));

                mainActivity.hoursFilteredArrayList.add(obj);
            }
        }
    }


    public Location setUpDefaultLocation() {
        Location location = new Location();
        for (Location l : mainActivity.locationsArrayList) {
            if (l.getId() == mainActivity.user.locationId) {
                location = l;
            }
        }
        return location;
    }

    public void setUpIsJseMember() {

        if (mainActivity.user.jseStudentId == null) {

            // if internet connection status is true getJseStudentId
            if (HelperMethods.checkInternetConnection()) {
                mainActivity.databaseOperations.getJseStudentId(mainActivity.user);
            }
        }
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setLoginActivity(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }


}


