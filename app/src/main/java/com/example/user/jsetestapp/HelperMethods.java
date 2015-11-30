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
    static Activity activity;

    public HelperMethods() {
        activity = Util.getActivity();
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

            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    activity.getString(R.string.d_schedule_test)
            ));

        } else {

            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    activity.getString(R.string.d_become_jse_member)
            ));
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
        if (mainActivity.testsFilteredArrayList != null)
            mainActivity.testsFilteredArrayList.clear();
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
                Util.showDialog(getDialogFragmentBundle(
                                activity.getString(
                                        R.string.d_create_account_failed_duplicate_email)));
            } else if (result.equals("true") && id != 0) {
                loginActivity.user.setId(id);
                loginActivity.switchToMainActivity("create_account");
            } else {

                Util.showDialog(getDialogFragmentBundle(
                        activity.getString(R.string.d_create_account_insert_failed)
                ));

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
                Util.showDialog(getDialogFragmentBundle(
                        activity.getString(R.string.d_login_failed_not_match)));
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
                Util.showDialog(getDialogFragmentBundle(
                        activity.getString(R.string.d_create_account_failed_email_duplicate)));

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

    /**
     * Function to get jsonArray
     *
     * @param url           - url to get the JSON string from
     * @param TAG_LOCATIONS - tag to get locations from
     * @return jsonArray
     */
    public static JSONArray getJsonArray(String url, String TAG_LOCATIONS) {
        // instantiating jsonArray
        JSONArray jsonArray = new JSONArray();
        // instantiating ServiceHandler
        ServiceHandler sh = new ServiceHandler();

        // Making a request to locations_url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
        // if jsonStr is not null
        if (jsonStr != null) {
            try {
                // instantiating JSONObject
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                jsonArray = jsonObj.getJSONArray(TAG_LOCATIONS);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // if jsonStr is null
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the locations_url");
        }
        return jsonArray;
    }

    /**
     * Function to set location
     *
     * @param locationObject
     * @return location
     */
    public static Location setLocation(JSONObject locationObject) {
        // instantiating location
        Location location = new Location();

        try {
            // Storing each json item in location
            location.setId(Integer.parseInt(locationObject.getString(Util.getStringValue(R.string.TAG__LOCATION_ID))));
            location.setBrachId(Integer.parseInt(locationObject.getString(Util.getStringValue(R.string.TAG__BRANCH_ID))));
            location.setName(locationObject.getString(Util.getStringValue(R.string.TAG_NAME)));
            location.setAddress(getAddress(locationObject));
            location.setPhone(locationObject.getString(Util.getStringValue(R.string.TAG_PHONE)));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Function to build address location
     *
     * @param locationObject
     * @return fullAddress
     */
    public static String getAddress(JSONObject locationObject) {
        // instantiating fullAddress
        StringBuilder fullAddress = new StringBuilder();

        try {

            String address = locationObject.getString(Util.getStringValue(R.string.TAG_ADDRESS));
            String city = locationObject.getString(Util.getStringValue(R.string.TAG_CITY));
            String state = locationObject.getString(Util.getStringValue(R.string.TAG_STATE));
            String zip = locationObject.getString(Util.getStringValue(R.string.TAG_ZIP));
            String country = locationObject.getString(Util.getStringValue(R.string.TAG_COUNTRY));

            if (!address.equals("null")) fullAddress.append(address + " ");
            if (!city.equals("null")) fullAddress.append(city + " ");
            if (!state.equals("null")) fullAddress.append(state + " ");
            if (!zip.equals("null")) fullAddress.append(zip + " ");
            if (!country.equals("null")) fullAddress.append(country);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fullAddress.toString();
    }

    /**
     * Function to set test
     *
     * @param testObject
     * @return test
     */
    public static Test setTest(JSONObject testObject) {
        // instantiating test
        Test test = new Test();

        try {
            // Storing each json item in test
            test.branchId = Integer.parseInt(testObject.getString(Util.getStringValue(R.string.TAG_BRANCH_ID)));
            test.location = testObject.getString(Util.getStringValue(R.string.TAG_LOCATION));
            test.date = LocalDate.parse(testObject.getString(Util.getStringValue(R.string.TAG_DATE)));
            test.setDayOfWeek(Test.DayOfWeek.values()[
                    (test.getDate().getDayOfWeek() - 1)].toString());
            test.time = LocalTime.parse(new StringBuilder(
                    testObject.getString(Util.getStringValue(R.string.TAG_TIME))).insert(
                    testObject.getString(Util.getStringValue(R.string.TAG_TIME)).length() - 2, ":").toString());
            test.deadlineDate = LocalDate.parse(testObject.getString(Util.getStringValue(R.string.TAG_CLOSING_DATE)));
            test.deadlineTime = LocalTime.parse(new StringBuilder(
                    testObject.getString(Util.getStringValue(R.string.TAG_CLOSING_TIME))).insert(
                    testObject.getString(Util.getStringValue(R.string.TAG_CLOSING_TIME)).length() - 2, ":").toString());
            test.setDeadlineDayOfWeek(Test.DayOfWeek.values()[
                    (test.getDeadlineDate().getDayOfWeek() - 1)].toString());
            test.setGender(Integer.parseInt(testObject.getString(Util.getStringValue(R.string.TAG_GENDER))));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return test;
    }

    /**
     * Function to set hours
     *
     * @param hoursObject
     * @return hours
     */
    public static Hours setHours(JSONObject hoursObject) {
        // instantiating hours
        Hours hours = new Hours();

        try {
            // Storing each json item in hours
            hours.name = hoursObject.getString(Util.getStringValue(R.string.TAG_LIBRARY_LOCATION));
            String day = hoursObject.getString(Util.getStringValue(R.string.TAG_DAY_OF_WEEK));
            hours.setDayOfWeek(Hours.DayOfWeek.values()[(Integer.parseInt(day) - 1)]);
            hours.startTime = LocalTime.parse(hoursObject.getString(Util.getStringValue(R.string.TAG_OPENING_TIME)));
            LocalTime duration = LocalTime.parse(hoursObject.getString(Util.getStringValue(R.string.TAG_DURATION)));
            hours.endTime = hours.getStartTime().plusHours(duration.getHourOfDay())
                    .plusMinutes(duration.getMinuteOfHour());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hours;

    }

    /**
     * Function to set branch
     *
     * @param branchObject
     * @return branch
     */
    public static Branch setBranch(JSONObject branchObject) {
        // instantiating branches
        Branch branch = new Branch();
        try {
            // Storing each json item in branch
            branch.id = Integer.parseInt(branchObject.getString(Util.getStringValue(R.string.TAG_ID)));
            branch.name = branchObject.getString(Util.getStringValue(R.string.TAG_BRANCH_NAME));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return branch;
    }

    /**
     * Function to set alert
     *
     * @param alertObject
     * @return alert
     */
    public static Alerts setAlert(JSONObject alertObject) {
// ToDo handle if any values are null
        // instantiating alert
        Alerts alert = new Alerts();

        try {
            // Storing each json item in alert
            alert.setLocationName(alertObject.getString(Util.getStringValue(R.string.TAG_LOCATION_NAME)));
            String timeStamp = (alertObject.getString(Util.getStringValue(R.string.TAG_TIME_STAMP)));
            String date = timeStamp.substring(0, 10);
            alert.date = LocalDate.parse(date);
            alert.setDayOfWeek(Alerts.DayOfWeek.values()[(alert.getDate()
                    .getDayOfWeek() - 1)]);
            String time = timeStamp.substring(timeStamp.length() - 8);
            alert.setTime(LocalTime.parse(time));
            alert.setAlertText(alertObject.getString(Util.getStringValue(R.string.TAG_ALERT_TEXT)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return alert;
    }


    /**
     * Function to create a bundle for a DialogFragment
     *
     * @param tagListener - tag to get data for the bundle
     */
    public static Bundle getDialogFragmentBundle(String tagListener) {

        String title, message, positiveButton, negativeButton, neutralButton;
        int icon;


        if (tagListener.equals(
                activity.getString(R.string.d_create_account_failed_duplicate_email))) {

            title = activity.getString(R.string.d_create_account_failed);
            message = activity.getString(R.string.d_account_create_fail_duplicate_email_msg);
            positiveButton = null;
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_create_account_insert_failed))) {

            title = activity.getString(R.string.d_create_account_failed);
            message = activity.getString(R.string.d_account_create_fail_msg);
            positiveButton = null;
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_login_failed_not_match))) {

            title = activity.getString(R.string.d_login_failed);
            message = activity.getString(R.string.d_username_password_not_match_msg);
            positiveButton = null;
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_create_account_failed_email_duplicate))) {

            title = activity.getString(R.string.d_create_account_failed);
            message = activity.getString(R.string.d_user_not_updated_msg);
            positiveButton = null;
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_no_internet_connection))) {

            title = activity.getString(R.string.d_no_connection);
            message = activity.getString(R.string.d_no_connection_msg);
            positiveButton = null;
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_exclamation_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_login_failed_values))) {

            title = activity.getString(R.string.d_login_failed);
            message = activity.getString(R.string.d_fields_require_values_msg);
            positiveButton = null;
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_login_failed_invalid_email))) {

            title = activity.getString(R.string.d_login_failed);
            message = activity.getString(R.string.d_invalid_email_msg);
            positiveButton = null;
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_forgot_password))) {

            title = activity.getString(R.string.d_send_password);
            message = activity.getString(R.string.d_enter_email_msg);
            positiveButton = activity.getString(R.string.d_send);
            negativeButton = null;
            neutralButton = null;
            icon = R.drawable.ic_settings_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_create_account_failed_email))) {

            title = activity.getString(R.string.d_create_account_failed);
            message = activity.getString(R.string.d_enter_valid_email_msg);
            positiveButton = null;
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_create_account_failed_values_not_match))) {

            title = activity.getString(R.string.d_create_account_failed);
            message = activity.getString(R.string.d_passwords_not_match_msg);
            positiveButton = null;
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_create_account_failed_values))) {

            title = activity.getString(R.string.d_create_account_failed);
            message = activity.getString(R.string.d_fields_require_values_msg);
            positiveButton = null;
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_registration_failed_birthday_incorrect))) {

            title = activity.getString(R.string.d_create_account_failed);
            message = activity.getString(R.string.d_enter_valid_dob_msg);
            positiveButton = activity.getString(R.string.d_ok);
            negativeButton = activity.getString(R.string.d_cancel);
            neutralButton = null;
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_registration_failed_ssn_incorrect))) {

            title = activity.getString(R.string.d_create_account_failed);
            message = activity.getString(R.string.d_enter_last_4_ssn);
            positiveButton = activity.getString(R.string.d_ok);
            negativeButton = activity.getString(R.string.d_cancel);
            neutralButton = null;
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_registration_failed_missing_fields))) {

            title = activity.getString(R.string.d_create_account_failed);
            message = activity.getString(R.string.d_fields_require_values_msg);
            positiveButton = activity.getString(R.string.d_ok);
            negativeButton = activity.getString(R.string.d_cancel);
            neutralButton = null;
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_update_account_failed_values_not_match))) {

            title = activity.getString(R.string.d_update_account_failed);
            message = activity.getString(R.string.d_passwords_not_match_msg);
            positiveButton = null;
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_update_account_failed_missing_fields))) {

            title = activity.getString(R.string.d_update_account_failed);
            message = activity.getString(R.string.d_fields_require_values_msg);
            positiveButton = activity.getString(R.string.d_ok);
            negativeButton = null;
            neutralButton = null;
            icon = R.drawable.ic_alert_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_call_jse_during_hours))) {

            title = activity.getString(R.string.d_jse_office);
            message = null;
            positiveButton = activity.getString(R.string.d_call);
            negativeButton = activity.getString(R.string.d_cancel);
            neutralButton = null;
            icon = R.drawable.ic_phone_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_call_jse_non_hours))) {

            title = activity.getString(R.string.d_jse_office);
            message = activity.getString(R.string.d_office_closed_msg);
            positiveButton = activity.getString(R.string.d_yes);
            negativeButton = activity.getString(R.string.d_no);
            neutralButton = null;
            icon = R.drawable.ic_calendar_clock_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_schedule_test))) {

            title = activity.getString(R.string.d_schedule_test_title);
            message = null;
            positiveButton = activity.getString(R.string.d_call);
            negativeButton = activity.getString(R.string.d_cancel);
            neutralButton = null;
            icon = R.drawable.ic_clipboard_text_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_become_jse_member))) {

            title = activity.getString(R.string.d_become_jse_member_title);
            message = activity.getString(R.string.d_schedule_test_become_member);
            positiveButton = activity.getString(R.string.d_call);
            negativeButton = activity.getString(R.string.d_cancel);
            neutralButton = null;
            icon = R.drawable.ic_clipboard_text_grey600_24dp;

        } else if (tagListener.equals(
                activity.getString(R.string.d_load_info_fail))) {

            title = activity.getString(R.string.d_load_info_fail_title);
            message = activity.getString(R.string.d_load_info_fail_msg);
            positiveButton = activity.getString(R.string.d_try_again);
            negativeButton = null;
            neutralButton = activity.getString(R.string.d_ok);
            icon = R.drawable.ic_clipboard_text_grey600_24dp;

        }

        else {
            title = "";
            message = "";
            positiveButton = null;
            negativeButton = null;
            neutralButton = null;
            icon = 0;
        }


        Bundle bundle = new Bundle();

        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putString("positiveButton", positiveButton);
        bundle.putString("negativeButton", negativeButton);
        bundle.putString("neutralButton", neutralButton);
        bundle.putInt("icon", icon);
        bundle.putString("tagListener", tagListener);

        return bundle;
    }


    public void setSplashActivity(SplashActivity splashActivity) {

        this.splashActivity = splashActivity;
    }

}