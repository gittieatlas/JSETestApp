package com.example.user.jsetestapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QueryMethods extends Activity {

    MainActivity mainActivity;
    LoginActivity loginActivity;
    public QueryMethods() {

    }

    public String getTag() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        return bundle.getString("tag");

    }

    public ArrayList<Location> setUpLocationsArrayList() {

        Bundle bundle = mainActivity.getIntent().getExtras();
        return (ArrayList<Location>) bundle.getSerializable("locationsArrayList");

    }


    public void setUpLocationsNameArrayList(MainActivity mainActivity) {
        mainActivity.locationsNameArrayList = new ArrayList<String>();
        mainActivity.locationsNameArrayList.add("Location");
        for (Location location : mainActivity.locationsArrayList) {
            mainActivity.locationsNameArrayList.add(location.getName());
        }
    }


    public ArrayList<String> setUpLocationsNameArrayList(ArrayList<Location> locationsArrayList) {

        ArrayList<String> locationsNameArrayList = new ArrayList<String>();
        locationsNameArrayList.add("Location");
        for (Location location : locationsArrayList) {
            locationsNameArrayList.add(location.getName());
        }
        return locationsNameArrayList;
    }

    public ArrayList<Branch> setUpBranchesArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        return  (ArrayList<Branch>) bundle.getSerializable("branchesArrayList");
    }

    public ArrayList<String> setUpBranchesNameArrayList(ArrayList<Branch> branchesArrayList) {
        ArrayList<String> branchesNameArrayList = new ArrayList<String>();
        branchesNameArrayList.add("Branch");

        for (Branch branch : branchesArrayList) {
            if (branch.getId() == (mainActivity.defaultLocation.brachId)) {
                branchesNameArrayList.add(branch.getName() + " (Default Branch)");
            } else {
                branchesNameArrayList.add(branch.getName());
            }
        }
        return branchesNameArrayList;
    }

    public void setUpTestsArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        mainActivity.testsArrayList = (ArrayList<Test>) bundle.getSerializable("testsArrayList");
        filterTestsArrayListByGender();
    }

    public void filterTestsArrayListByGender() {
        for (int i = 0; i < mainActivity.testsArrayList.size(); i++) {
            if (!mainActivity.testsArrayList.get(i).gender.name().equals(mainActivity.user.gender.name())) {
                mainActivity.testsArrayList.remove(i);
                i--;
            }
        }
    }


    public void setUpTestsFilteredArrayList() {

        mainActivity.testsFilteredArrayList = new ArrayList<DataObject>();
    }

    public ArrayList<Hours> setUpHoursArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        return  (ArrayList<Hours>) bundle.getSerializable("hoursArrayList");

    }


    public void updateHoursArrayListView(ListView listView, String name) {

        setUpHoursFilteredArrayList(name);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        mainActivity.helperMethods.setListViewHeightBasedOnItems(listView);
    }


    public void setupListView(ListAdapter adapter, ListView listView, String name) {
        adapter = new MyBaseAdapter(mainActivity.getContext(), mainActivity.hoursFilteredArrayList);
        listView.setAdapter(adapter);
        updateHoursArrayListView(listView, name);
    }

    public ArrayList<Alerts> setUpAlertsArrayList() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        return  (ArrayList<Alerts>) bundle.getSerializable("alertsArrayList");
    }

    public User setUpUser() {
        Bundle bundle = mainActivity.getIntent().getExtras();
        return (User) bundle.getSerializable("user");

    }

    public void setUpHoursFilteredArrayList(String location) {

        if (mainActivity.hoursFilteredArrayList != null)
            mainActivity.hoursFilteredArrayList.clear();
        for (Hours hours : mainActivity.hoursArrayList) {

            if (hours.getName().equals(location)) {

                HoursDataObject obj = new HoursDataObject(Util.firstLetterCaps(hours.getDayOfWeek().toString()),

                        hours.getStartTime().toString("hh:mm a"),
                        hours.getEndTime().toString("hh:mm a"));

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
            if (HelperMethods.checkInternetConnection(mainActivity.getApplicationContext())) {
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


