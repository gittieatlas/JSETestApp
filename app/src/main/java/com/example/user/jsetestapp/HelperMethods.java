package com.example.user.jsetestapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.ArrayList;
import java.util.Collections;

public class HelperMethods extends Activity {

    MainActivity mainActivity;
    LoginActivity loginActivity;

    public HelperMethods() {

    }

    public void replaceFragment(int container, Fragment fragment, String tag) {
        mainActivity.scrollView.scrollTo(0, 0); // Scroll to top
        mainActivity.getFragmentManager().beginTransaction().replace(container, fragment).addToBackStack(tag).commit();
    }

    public void addFragment(int container, Fragment fragment, String tag) {
        mainActivity.scrollView.scrollTo(0, 0); // Scroll to top
        mainActivity.getFragmentManager().beginTransaction().add(container, fragment).addToBackStack(tag).commit();
    }

    public void replaceFragment(int container, Fragment fragment, String tag, MainActivity mainActivity) {
        mainActivity.scrollView.scrollTo(0, 0); // Scroll to top
        mainActivity.getFragmentManager().beginTransaction().replace(container, fragment).addToBackStack(tag).commit();
    }

    public void addFragment(int container, Fragment fragment, String tag, MainActivity mainActivity) {
        mainActivity.scrollView.scrollTo(0, 0); // Scroll to top
        mainActivity.getFragmentManager().beginTransaction().add(container, fragment).addToBackStack(tag).commit();
    }

    public void replaceFragment(int container, Fragment fragment, String tag, LoginActivity loginActivity) {
        loginActivity.scrollView.scrollTo(0, 0); // Scroll to top
        loginActivity.getFragmentManager().beginTransaction().replace(container, fragment).addToBackStack(tag).commit();
    }

    public void addFragment(int container, Fragment fragment, String tag, LoginActivity loginActivity) {
        loginActivity.scrollView.scrollTo(0, 0); // Scroll to top
        loginActivity.getFragmentManager().beginTransaction().add(container, fragment).addToBackStack(tag).commit();
    }

    public void addDataToSpinner(ArrayList<String> arrayList, Spinner spinner, String tag) {
        // ToDO add context as parameter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainActivity.getApplicationContext(),
                R.layout.spinner_dropdown_item, arrayList);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        spinner.setAdapter(adapter);

        if (tag.equals("location")) spinner.setSelection(2); // ToDo set posotion to defaultLocation
        else spinner.setSelection(0);
    }

    //for register2 page locationSpinner
    //TODO add the activity as a param so you can use the smae method coming from both activities
    public void addDataToSpinnerFromLoginActivity(ArrayList<String> arrayList, Spinner spinner) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(loginActivity.getApplicationContext(),
                R.layout.spinner_dropdown_item, arrayList);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        spinner.setAdapter(adapter);

        //if (tag.equals("location")) spinner.setSelection(2); // ToDo set posotion to defaultLocation
        spinner.setSelection(0);
    }

    public void scheduleTest() {
        if (mainActivity.user.isJseMember) {
            mainActivity.showDialog("Schedule a Test", null, "CALL", "CANCEL", null, R.drawable.ic_clipboard_text_grey600_24dp, "schedule_test");
        } else {
            String message = "To schedule a test you need to be a JSE member. Please call the JSE office to register.";
            mainActivity.showDialog("Become a JSE Member", message, "CALL", "CANCEL", null, R.drawable.ic_clipboard_text_grey600_24dp, "become_jse_member");
        }

    }

    /**
     * Function to make the first letter caps and the rest lowercase.
     *
     * @param data          -   capitalize this
     * @return String       -   alert message?
     */
    static public String firstLetterCaps(String data) {
        String firstLetter = data.substring(0, 1).toUpperCase();
        String restLetters = data.substring(1).toLowerCase();
        return firstLetter + restLetters;
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView      -   to be resized
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

        replaceFragment(R.id.container, mainActivity.resultsFragment, mainActivity.getResources().getString(R.string.toolbar_title_results));
    }

    private void filterTests(Location location) {
        for (Test test : mainActivity.testsArrayList) {
            if (test.getLocation().equals(location.name)) {
                addTestToArrayList(test);
            }
        }
        replaceFragment(R.id.container, mainActivity.resultsFragment, mainActivity.getResources().getString(R.string.toolbar_title_results));
    }

    private void addTestToArrayList(Test test) {
        String day = mainActivity.helperMethods.firstLetterCaps(test.getDeadlineDayOfWeek().toString());
        DataObject obj = new DataObject(test.getLocation(),
                mainActivity.helperMethods.firstLetterCaps(test.getDayOfWeek().toString()),
                test.getTime().toString("hh:mm a"),
                test.getDate().toString("MMMM dd yyyy"),
                "Registration Deadline: ",
                day + " " + test.getDeadlineDate().toString("MMMM dd yyyy") + " " + test.getDeadlineTime().toString("hh:mm a"));
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
    }

    public void getUser(String result) {
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

    public void updateUser(String result) {
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

    public void showSnackBar() {
        String message = "";
        switch (mainActivity.queryMethods.getTag()) {

            case "create_account": {
                message = "Account created";
                break;
            }
            case "login": {
                message = "Logged in";
                break;
            }
            case "update_profile": {
                message = "Profile updated";
                break;
            }
        }

        Snackbar snackbar = Snackbar
                .make(mainActivity.container, message, Snackbar.LENGTH_LONG);

        snackbar.show();
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
     * Function to check if a string is a valid email address
     *
     * @param email -   string containing the email to be validated
     * @return boolean      -   if valid return true; if not valid return false
     */
    public boolean isEmailAddressValid(String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else
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
        LocalTime now = LocalTime.now();

        // check if now is between start and end
        return isWithinInterval(start, end, now);
    }

    public int setLocationSpinnerSelection() {

        for (Location l : loginActivity.locationsArrayList) {
            if (loginActivity.defaultLocation.name.equals(l.getName())) {
                return loginActivity.locationsArrayList.indexOf(l)+1;
            }
        }
        return 0;
    }

}