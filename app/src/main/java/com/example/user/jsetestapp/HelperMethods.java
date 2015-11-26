package com.example.user.jsetestapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class HelperMethods extends Activity {

    MainActivity mainActivity;
    LoginActivity loginActivity;
    SplashActivity splashActivity;

    public HelperMethods() {

    }

    public void replaceFragment(Fragment fragment, String tag,
                                Activity activity, ScrollView scrollView) {

        // Scroll to top
        scrollView.scrollTo(0, 0);

        // replace fragment in container
        activity.getFragmentManager().beginTransaction().replace(R.id.container,
                fragment).addToBackStack(tag).commit();
    }


    public void addDataToSpinner(ArrayList<String> arrayList, Spinner spinner,
                                 String tag, Context context) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.spinner_dropdown_item, arrayList);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        spinner.setAdapter(adapter);

        //if (tag.equals("location"))
    }

    public void scheduleTest() {
        if (mainActivity.user.isJseMember) {
            mainActivity.showDialog("Schedule a Test", null, "CALL", "CANCEL",
                    null, R.drawable.ic_clipboard_text_grey600_24dp, "schedule_test");
        } else {
            String message = "To schedule a test you need to be a JSE member. " +
                    "Please call the JSE office to register.";
            mainActivity.showDialog("Become a JSE Member", message, "CALL", "CANCEL",
                    null, R.drawable.ic_clipboard_text_grey600_24dp, "become_jse_member");
        }

    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView -   to be resized
     * @return true         -   if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    public Bundle passLocationToLocationInfoFragment(Location location) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("location", location);
        return bundle;
    }

    //for dashboard and library search
    public void findTests(Location location) {
        clearTestsFilteredArrayList();
        filterTests(location);
    }

    //for search page
    public void findTests(Branch branch, int dayOfWeek) {
        clearTestsFilteredArrayList();
        Collections.sort(mainActivity.testsArrayList, new LocationDateComparator());
        //Collections.sort(mainActivity.testsArrayList);
        if (branch.equals("branch") && dayOfWeek == 0)
            filterTests();
        else if (!branch.equals("branch") && dayOfWeek == 0)
            filterTests(branch);
        else if (branch.equals("branch") && dayOfWeek != 0)
            filterTests(dayOfWeek);
        else if (!branch.equals("branch") && dayOfWeek != 0)
            filterTests(branch, dayOfWeek);

        replaceFragment(mainActivity.resultsFragment,
                mainActivity.getResources().getString(R.string.toolbar_title_results),
                mainActivity, mainActivity.scrollView);
    }

    private void filterTests(Location location) {
        for (Test test : mainActivity.testsArrayList) {
            if (test.getLocation().equals(location.name)) {
                addTestToArrayList(test);
            }
        }
        replaceFragment(mainActivity.resultsFragment,
                mainActivity.getResources().getString(R.string.toolbar_title_results),
                mainActivity, mainActivity.scrollView);
    }

    private void addTestToArrayList(Test test) {
        String day = Util.firstLetterCaps(test.getDeadlineDayOfWeek()
                .toString());
        DataObject obj = new DataObject(test.getLocation(),
                Util.firstLetterCaps(test.getDayOfWeek().toString()),
                test.getTime().toString("hh:mm a"),
                test.getDate().toString("MMMM dd yyyy"),
                "Registration Deadline: ",
                day + " " + test.getDeadlineDate().toString("MMMM dd yyyy") + " " +
                        test.getDeadlineTime().toString("hh:mm a"));
        mainActivity.testsFilteredArrayList.add(obj);
    }

    // none
    private void filterTests() {
        for (Test test : mainActivity.testsArrayList) {
            addTestToArrayList(test);
        }
    }

    //for branches only
    private void filterTests(Branch branch) {
        for (Test test : mainActivity.testsArrayList) {
            if (test.getBranchId() == (branch.id)) {
                addTestToArrayList(test);
            }
        }
    }

    //for dayOfWeek only
    private void filterTests(int dayOfWeek) {
        for (Test test : mainActivity.testsArrayList) {
            if (test.getDayOfWeek().ordinal() + 1 == (dayOfWeek)) {
                addTestToArrayList(test);
            }
        }
    }

    //for branch and dayOfWeek
    private void filterTests(Branch branch, int dayOfWeek) {
        for (Test test : mainActivity.testsArrayList) {
            if (test.getBranchId() == (branch.id) && (test.getDayOfWeek().ordinal() + 1 == (dayOfWeek))) {
                addTestToArrayList(test);
            }
        }
    }


    private void clearTestsFilteredArrayList() {
        if (mainActivity.getTestsFilteredArrayList() != null)
            mainActivity.getTestsFilteredArrayList().clear();
    }

    public void setLoginActivity(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public void createUser(String result, int id) {
        if (loginActivity.register2Fragment.isVisible()) {
            if (result.equals("") && id == 0) {
                loginActivity.showDialog(loginActivity.getString(R.string.d_create_account_failed),
                        loginActivity.getString(R.string.d_account_create_fail_duplicate_email_msg),
                        null, null, loginActivity.getString(R.string.d_ok),
                        R.drawable.ic_alert_grey600_24dp,
                        loginActivity.getString(R.string.d_create_account_failed_duplicate_email));
            } else if (result.equals("true") && id != 0) {
                loginActivity.user.setId(id);
                loginActivity.switchToMainActivity("create_account");
            } else {
                loginActivity.showDialog(loginActivity.getString(R.string.d_create_account_failed),
                        loginActivity.getString(R.string.d_account_create_fail_msg),
                        null, null, loginActivity.getString(R.string.d_ok),
                        R.drawable.ic_alert_grey600_24dp,
                        loginActivity.getString(R.string.d_create_account_insert_failed));
            }
        } else {
            if (result.equals("") && id == 0) {

            } else if (result.equals("true") && id != 0) {
                loginActivity.user.setId(id);
                //loginActivity.switchToMainActivity("create_account");
            } else {

            }
        }

    }

    public void getUser(String result) {

        if (loginActivity.loginFragment.isVisible()) {
            if (result.equals("0")) {
                // Not logged in
                loginActivity.showDialog(loginActivity.getString(R.string.d_login_failed),
                        "This username and password did not match. Please try again.",
                        null, null, "OK", R.drawable.ic_alert_grey600_24dp,
                        "login_failed_not_match");
            } else {
                //login successful
                loginActivity.switchToMainActivity("login");

            }
        }
    }

    public void updateUser(String result) {

        if (loginActivity.updateProfileFragment.isVisible()) {
            if (result.equals("true")) {
                // user updated
                loginActivity.switchToMainActivity("update_profile");
            } else {
                //user not updated
                loginActivity.showDialog("Create Account Failed",
                        "User not updated.",
                        null, null, "OK", R.drawable.ic_alert_grey600_24dp,
                        "create_account_failed_email_duplicate");
            }
        }
    }

    public void checkTag() {
        switch (mainActivity.queryMethods.getTag()) {

            case "create_account": {
                showSnackBar("Account created", mainActivity.coordinatorLayout);
                break;
            }
            case "login": {
                showSnackBar("Logged in", mainActivity.coordinatorLayout);
                break;
            }
            case "update_profile": {
                showSnackBar("Profile updated", mainActivity.coordinatorLayout);
                break;
            }
            case "update_profile_cancel": {
                showSnackBar("Profile not updated", mainActivity.coordinatorLayout);
                break;
            }
        }
    }

    /**
     * Function to show a snack bar in Coordinator Layout
     *
     * @param message           -   snack bar message
     * @param coordinatorLayout -   parent view for snackBar
     */
    public void showSnackBar(String message, CoordinatorLayout coordinatorLayout) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Function to check if the current time is a start and end time
     *
     * @param start -   beginning time
     * @param end   -   finishing time
     * @param time  -   now
     * @return boolean      -   if is between, return true; if is not between, return false
     */
    public static boolean isWithinInterval(LocalTime start, LocalTime end, LocalTime time) {
        if (time.isAfter(start) && time.isBefore(end)) {
            return true;
        }
        return false;
    }


    /**
     * Function to check internet status
     *
     * @param context -   current context
     * @return boolean      -   true if present/false if not present
     */
    public static boolean checkInternetConnection(Context context) {

        // creating connection detector class instance
        ConnectionDetector cd = new ConnectionDetector(context);

        // get Internet status
        return cd.isConnectingToInternet();
    }

    /**
     * Function to check if now is during office hours
     *
     * @return boolean
     */
    public boolean isDuringOfficeHours() {

        String startTime, endTime;
        DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();

        // get current day of week
        String dayOfWeek = Integer.toString(LocalDate.now().getDayOfWeek());

        //get start time and end time of today's office hours
        switch (dayOfWeek) {
            case "5": {
                // Friday
                startTime = mainActivity.getString(R.string.jse_office_hours_friday_hours_start_time);
                endTime = mainActivity.getString(R.string.jse_office_hours_friday_hours_end_time);
                break;
            }
            case "6": {
                // Saturday
                // closed ?
                return false;
            }
            case "7": {
                // Sunday
                // closed ?
                return false;
            }
            default: {
                // Monday - Thursday
                startTime = mainActivity.getString(R.string.jse_office_hours_mon_thurs_hours_start_time);
                endTime = mainActivity.getString(R.string.jse_office_hours_mon_thurs_hours_end_time);
                break;
            }
        }

        LocalTime start = LocalTime.parse(startTime, parseFormat);
        LocalTime end = LocalTime.parse(endTime, parseFormat);

        // get current moment in default time zone
        DateTime nowDefault = new org.joda.time.DateTime();
        // translate to New York local time
        DateTime nowNY = nowDefault.toDateTime(DateTimeZone.forID("America/New_York"));
        // parse to LocalTime
        LocalTime now = nowNY.toLocalTime();

        // check if now is between start and end
        return isWithinInterval(start, end, now);
    }

    public int setLocationSpinnerSelection() {

        for (Location l : loginActivity.locationsArrayList) {
            if (loginActivity.defaultLocation.name.equals(l.getName())) {
                return loginActivity.locationsArrayList.indexOf(l) + 1;
            }
        }
        return 0;
    }

    public int setBranchSpinnerSelection() {

        for (String s : mainActivity.branchesNameArrayList) {
//            if (mainActivity.defaultLocation.name.equals(s)) {
//                return mainActivity.branchesNameArrayList.indexOf(s)+1;
//            }
            String d = " (Default Branch)";
            if (s.contains(d)) {
                return mainActivity.branchesNameArrayList.indexOf(s);
            }
        }
        return 0;
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(loginActivity);
                    return false;
                }

            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }


    public static void hideSoftKeyboard(Activity loginActivity) {
        InputMethodManager inputMethodManager = (InputMethodManager) loginActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(loginActivity.getCurrentFocus().getWindowToken(), 0); // Todo this has a crash 12:08
    }

    public ArrayList<String> editLocationsNameArrayList() {
        ArrayList<String> arrayList = loginActivity.locationsNameArrayList;
        arrayList.set(0, "");
        return arrayList;
    }


    public ArrayList<String> editUpdateProfileLocationsNameArrayList() {
        ArrayList<String> arrayList = loginActivity.locationsNameArrayList;
        arrayList.remove(0);
        return arrayList;
    }

    public static JSONArray getJsonArray(String url, String TAG_LOCATIONS) {
        //instantiating jsonArray
        JSONArray jsonArray= new JSONArray();

        ServiceHandler sh = new ServiceHandler();

        // Making a request to locations_url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                jsonArray = jsonObj.getJSONArray(TAG_LOCATIONS);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
            Log.e("ServiceHandler", "Couldn't get any data from the locations_url");
        }
        return jsonArray;

    }


}