package com.example.user.jsetestapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HelperMethods extends Activity {

    // Declare classes
    MainActivity mainActivity;
    LoginActivity loginActivity;
    SplashActivity splashActivity;

    // constructor
    public HelperMethods() {
    }

    /**
     * Function to add / replace fragment
     *
     * @param fragment - new fragment
     * @param tag      - tag to add along with the fragment to the back stack
     */
    public static void replaceFragment(Fragment fragment, String tag) {
        // replace fragment in container
        Util.getActivity().getFragmentManager().beginTransaction().replace(R.id.container,
                fragment).addToBackStack(tag).commit();

        // scroll to top of scrollView
        try {
            // get instance of scrollView the fragment is hosted in
            ScrollView scrollView = (ScrollView) Util.getActivity().findViewById(R.id.scrollView);
            // scroll to top
            scrollView.scrollTo(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to create adapter with arrayList
     *
     * @param arrayList - list of data to be bound
     * @param spinner   - bind adapter to this view
     */
    public static void addDataToSpinner(ArrayList<String> arrayList, Spinner spinner) {
        // create new adapter with layout of how spinner items will look and arrayList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Util.getContext(),
                R.layout.spinner_dropdown_item, arrayList);
        // set layout of how selected item of spinner will look
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        // bind adapter to spinner
        spinner.setAdapter(adapter);
    }

    /**
     * Function to create adapter with arrayList from a resource file
     *
     * @param stringArray - list of data to be bound
     * @param spinner     - bind adapter to this view
     */
    public static void addDataToSpinner(String[] stringArray, Spinner spinner) {
        // convert String Array to ArrayList<String>; and call addDataToSpinner()
        addDataToSpinner(new ArrayList<>(Arrays.asList(stringArray)), spinner);
    }

    /**
     * Function to show a dialog to schedule a test
     */
    public void scheduleTest() {
        // if User is a JSE member
        if (mainActivity.user.isJseMember) {
            // Show Dialog: Schedule A Test
            Util.showDialogFragment(R.array.schedule_test);
        }
        // if User is not a JSE member
        else {
            // Show Dialog: Become a JSE Member
            Util.showDialogFragment(R.array.become_jse_member);
        }
    }

    /**
     * Sets a ListView height dynamically based on the height of the items
     *
     * @param listView - being resized
     * @return true
     */
    public static boolean setListViewHeightBasedOnItems(ListView listView) {
        // get ListAdapter from listView
        ListAdapter listAdapter = listView.getAdapter();

        // if listAdapter was obtained successfully
        if (listAdapter != null) {
            // get amount of listView items
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

    /**
     * Function to get tests filtered by a location
     *
     * @param location - add tests that have a location matching to this location
     */
    public void findTests(Location location) {
        // clear testsFilteredArrayList
        Util.clearArrayList(mainActivity.testsFilteredArrayList);

        // get tests that have a location matching to this location
        filterTestsBy(location);

        // inflate scrollView with ResultsFragment
        replaceFragment(mainActivity.resultsFragment,
                mainActivity.getResources().getString(R.string.toolbar_title_results));
    }

    /**
     * Function to get tests filtered by a branch and a day of week
     *
     * @param branch    - selected branch
     * @param dayOfWeek - selected location
     */
    public void findTests(Branch branch, int dayOfWeek) {
        // clear testsFilteredArrayList
        Util.clearArrayList(mainActivity.testsFilteredArrayList);

        // sort testsArrayList by Location and then by Date
        Collections.sort(mainActivity.testsArrayList, new LocationDateComparator());

        // if no specific branch was chosen and no specific dayOfWeek was chosen
        if (branch.getName() == null && dayOfWeek == 0)
            // filter test by nothing
            filterTestsBy();
            // if a specific branch was chosen and no specific dayOfWeek was chosen
        else if (branch.getName() != null && dayOfWeek == 0)
            // filter test by a branch
            filterTestsBy(branch);
            // if no specific branch was chosen and a specific dayOfWeek was chosen
        else if (branch.getName() == null && dayOfWeek > 0)
            // filter test by a dayOfWeek
            filterTestsBy(dayOfWeek);
            // if a specific branch was chosen and a specific dayOfWeek was chosen
        else if (branch.getName() != null && dayOfWeek > 0)
            // filter test by a branch and a dayOfWeek
            filterTestsBy(branch, dayOfWeek);

        // inflate scrollView with ResultsFragment
        replaceFragment(mainActivity.resultsFragment,
                mainActivity.getResources().getString(R.string.toolbar_title_results));
    }

    /**
     * Function to get tests that have a location matching to this location
     *
     * @param location - add tests that have a location matching to this location
     */
    private void filterTestsBy(Location location) {
        // for each test in testsArrayList
        for (Test test : mainActivity.testsArrayList) {
            // if the location of test is equal to name of selected 'location'
            if (test.getLocation().equals(location.name)) {
                addTestToTestsFilteredArrayList(test);
            }
        }
    }

    /**
     * Function to get all tests
     */
    private void filterTestsBy() {
        // for each test in testsArrayList
        for (Test test : mainActivity.testsArrayList) {
            addTestToTestsFilteredArrayList(test);
        }
    }

    /**
     * Function to get tests that have a branch matching to this branch
     *
     * @param branch - add tests that have a branch matching to this branch
     */
    private void filterTestsBy(Branch branch) {
        // for each test in testsArrayList
        for (Test test : mainActivity.testsArrayList) {
            // if the branch of test is equal to name of selected 'branch'
            if (test.getBranchId() == (branch.id)) {
                addTestToTestsFilteredArrayList(test);
            }
        }
    }

    /**
     * Function to get tests that have a dayOfWeek matching to this dayOfWeek
     *
     * @param dayOfWeek - add tests that have a dayOfWeek matching to this dayOfWeek
     */
    private void filterTestsBy(int dayOfWeek) {
        // for each test in testsArrayList
        for (Test test : mainActivity.testsArrayList) {
            // if the dayOfWeek of test is equal to name of selected 'dayOfWeek'
            if (test.getDayOfWeek().ordinal() + 1 == (dayOfWeek)) {
                addTestToTestsFilteredArrayList(test);
            }
        }
    }

    /**
     * Function to get tests that have a branch and dayOfWeek matching to this branch and dayOfWeek
     *
     * @param branch    - add tests that have a branch matching to this branch
     * @param dayOfWeek - add tests that have a dayOfWeek matching to this dayOfWeek
     */
    //for branch and dayOfWeek
    private void filterTestsBy(Branch branch, int dayOfWeek) {
        // for each test in testsArrayList
        for (Test test : mainActivity.testsArrayList) {
            // if the branch and dayOfWeek of test equals to name of selected branch and 'dayOfWeek'
            if (test.getBranchId() == (branch.id) &&
                    (test.getDayOfWeek().ordinal() + 1 == (dayOfWeek))) {
                addTestToTestsFilteredArrayList(test);
            }
        }
    }

    /**
     * Function to convert a Test to TestDataObject and add it to testsFilteredArrayList
     *
     * @param test - test to convert and add
     */
    private void addTestToTestsFilteredArrayList(Test test) {
        // get values from test for TestDataObject
        String location =
                test.getLocation();
        String testDay =
                Util.firstLetterCaps(test.getDayOfWeek().toString());
        String testTime =
                test.getTime().toString("hh:mm a");
        String testDate =
                test.getDate().toString("MMMM dd yyyy");
        String testDeadlineTitle =
                "Registration Deadline: ";
        String testDeadlineDetails =
                Util.firstLetterCaps(test.getDeadlineDayOfWeek().toString()) + " "
                        + test.getDeadlineDate().toString("MMMM dd yyyy") + " "
                        + test.getDeadlineTime().toString("hh:mm a");

        // create new TestDataObject from strings
        TestDataObject obj = new TestDataObject(location, testDay, testTime,
                testDate, testDeadlineTitle, testDeadlineDetails);

        // add obj to testsFilteredArrayList
        mainActivity.testsFilteredArrayList.add(obj);
    }


    // ToDo come back to this
    public void getUser(String result) {

        if (loginActivity.loginFragment.isVisible()) {
            if (result.equals("0")) {
                // Not logged in
                Util.showDialogFragment(R.array.login_failed_not_match);
            } else {
                //login successful

                // launch activity with main activity intent
                Util.launchActivity(loginActivity.getLaunchMainActivityIntent("login"));
            }
        }
    }

    // ToDo come back to this
    public void updateUser(String result) {

        if (loginActivity.updateProfileFragment.isVisible()) {
            if (result.equals("true")) {
                // user updated

                // launch activity with main activity intent
                Util.launchActivity(loginActivity.getLaunchMainActivityIntent("update_profile"));

            } else {
                //user not updated
                Util.showDialogFragment(R.array.update_account_failed_msg);
            }
        }
    }

    /**
     * Function to show a snack bar in Coordinator Layout
     * @param message - snack bar message
     */
    public void showSnackBar(String message) {
        try {
            // create new CoordinatorLayout to hold reference of parent view for snackBar
            CoordinatorLayout coordinatorLayout = new CoordinatorLayout(Util.getContext());

            // get reference of CoordinatorLayout the layout is hosted in
            coordinatorLayout =
                    (CoordinatorLayout) Util.getActivity().findViewById(R.id.coordinatorLayout);

            // show snack bar
            Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
        } catch (Exception e) {
            // show toast
            Toast.makeText(Util.getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    // ToDo clean this function once hours are confirmed
    /**
     * Function to check if now is during  office hours
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
                // Friday - closed ?
                startTime = mainActivity.getString(R.string.jse_office_hours_friday_hours_start_time);
                endTime = mainActivity.getString(R.string.jse_office_hours_friday_hours_end_time);
                break;
            }
            case "6": {
                // Saturday - closed ?
                return false;
            }
            case "7": {
                // Sunday - closed ?
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
        return Util.isWithinInterval(start, end, now);
    }

    // Todo up to here
    /**
     * Function to return a position of default branch in branchesNameArrayList
     * @return int
     */
    public int setBranchSpinnerSelection() {
        for (String s : mainActivity.branchesNameArrayList) {
            String d = " (Default Branch)";
            if (s.contains(d)) {
                return mainActivity.branchesNameArrayList.indexOf(s);
            }
        }
        return 0;
    }

    public static void registerTouchListenerForNonEditText(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                registerTouchListenerForNonEditText(innerView);
            }
        }
    }

    public static void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) Util.getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (Util.getActivity().getCurrentFocus().getWindowToken() != null) {

            inputMethodManager.hideSoftInputFromWindow(Util.getActivity().getCurrentFocus().getWindowToken(), 0); // Todo this has a crash 12:08
        }
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
            location.setBranchId(Integer.parseInt(locationObject.getString(Util.getStringValue(R.string.TAG__BRANCH_ID))));
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
    public static Hour setHours(JSONObject hoursObject) {
        // instantiating hour
        Hour hour = new Hour();

        try {
            // Storing each json item in hour
            hour.name = hoursObject.getString(Util.getStringValue(R.string.TAG_LIBRARY_LOCATION));
            String day = hoursObject.getString(Util.getStringValue(R.string.TAG_DAY_OF_WEEK));
            hour.setDayOfWeek(Hour.DayOfWeek.values()[(Integer.parseInt(day) - 1)]);
            hour.startTime = LocalTime.parse(hoursObject.getString(Util.getStringValue(R.string.TAG_OPENING_TIME)));
            LocalTime duration = LocalTime.parse(hoursObject.getString(Util.getStringValue(R.string.TAG_DURATION)));
            hour.endTime = hour.getStartTime().plusHours(duration.getHourOfDay())
                    .plusMinutes(duration.getMinuteOfHour());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hour;

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
    public static Alert setAlert(JSONObject alertObject) {
// ToDo handle if any values are null
        // instantiating alert
        com.example.user.jsetestapp.Alert alert = new Alert();

        try {
            // Storing each json item in alert
            alert.setLocationName(alertObject.getString(Util.getStringValue(R.string.TAG_LOCATION_NAME)));
            String timeStamp = (alertObject.getString(Util.getStringValue(R.string.TAG_TIME_STAMP)));
            String date = timeStamp.substring(0, 10);
            alert.date = LocalDate.parse(date);
            alert.setDayOfWeek(Alert.DayOfWeek.values()[(alert.getDate()
                    .getDayOfWeek() - 1)]);
            String time = timeStamp.substring(timeStamp.length() - 8);
            alert.setTime(LocalTime.parse(time));
            alert.setAlertText(alertObject.getString(Util.getStringValue(R.string.TAG_ALERT_TEXT)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return alert;
    }

    public void setReminderToCallJse() {
        LocalDate localDate = LocalDate.now();
        int dayOfWeek = localDate.getDayOfWeek();
        String hours = getResources().getString(R.string.jse_office_hours_mon_thurs_hours_start_time);
        switch (dayOfWeek) {
            case 4: {
                // Thursday
                String fridayHours = getResources().getString(R.string.jse_office_hours_friday_hours_start_time);
                setUpIntentToCallJse(fridayHours, 1);
            }
            case 5: {
                // Friday
                setUpIntentToCallJse(hours, 3);
            }
            case 6: {
                // Saturday
                // closed ?
                setUpIntentToCallJse(hours, 2);
            }
            default: {
                // Sunday - Thursday
                setUpIntentToCallJse(hours, 1);
            }
        }
    }

    public void setUpIntentToCallJse(String hours, int days) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm a");
        LocalTime localTime;
        localTime = fmt.parseLocalTime(hours);
        Util.calendarIntent("Call JSE", null, null, LocalDate.now().plusDays(days), localTime);
    }

    /**
     * Function to make http request that return a JsonObject and then convert it to a JsonArray
     *
     * @param url    - url to get the JSON string from
     * @param tag    - tag of node to get objects from
     * @param params - list of key values to pass along to the http request
     * @return JsonArray
     */
    public static JSONArray getJsonArray(String url, String tag, List<NameValuePair> params) {
        // instantiate new JsonArray
        JSONArray jsonArray = new JSONArray();

        // instantiate new JsonParser
        JSONParser jsonParser = new JSONParser();

        // create JSON Object from request made to url
        JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

        // initialize int to receive value of success (0 or 1)
        int success = 0;

        try {
            // try to get int with tag "success" from json object
            success = json.getInt(Util.getStringValue(R.string.TAG_SUCCESS));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // if success is 0 ; either because the query of the http request was not successful
        // or because the http request itself was unsuccessful and therefor the JsonObject is null
        if (success != 0) {
            try {
                // get JSON Array node
                jsonArray = json.getJSONArray(tag);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // could not get JSON Array node
            Log.e("Json Parser", "Couldn't get any data from the url");
        }

        return jsonArray;
    }

    public static JSONObject getJsonObject(String url, List<NameValuePair> params) {
        // getting JSON Object
        // Note that create user url accepts POST method
        JSONParser jsonParser = new JSONParser();

        // create JSON Object from request made to url
        JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

        int success = 0;

        try {
        // try to get int with tag "success" from json object
            success = json.getInt(Util.getStringValue(R.string.TAG_SUCCESS));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (success !=0) {

            return json;
        }
        return null;


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
        branchesNameArrayList.add("All");

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
        setListViewHeightBasedOnItems(listView);
    }

    public void setupListView(ListAdapter adapter, ListView listView, String name) {
        adapter = new HoursAdapter(Util.getContext(), mainActivity.hoursFilteredArrayList);
        listView.setAdapter(adapter);
        updateHoursArrayListView(listView, name);
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
            if (Util.checkInternetConnection()) {
                mainActivity.databaseOperations.getJseStudentId(mainActivity.user);
            }
        }
    }

    /**
     * Function to create a bundle for a DialogFragment
     * @param array - tag of dialog fragment
     * return bundle
     */
    public static Bundle getDialogFragmentBundle(int array) {
        // instantiate a typed array and get its values from xml array
        TypedArray dialogArray = Util.getActivity().getResources().obtainTypedArray(array);

        // instantiate a bundle
        Bundle bundle = new Bundle();

        // add key-values to bundle: get values from array and tagListener from array name
        bundle.putString("title", dialogArray.getString(0));
        bundle.putString("message", dialogArray.getString(1));
        bundle.putString("positiveButton", dialogArray.getString(2));
        bundle.putString("negativeButton", dialogArray.getString(3));
        bundle.putString("neutralButton", dialogArray.getString(4));
        bundle.putInt("icon", dialogArray.getResourceId(5, -1));
        bundle.putString("tagListener", Util.getActivity().getResources().getResourceEntryName(array));

        // recycle the TypedArray, to be re-used by a later caller.
        dialogArray.recycle();

        return bundle;
    }

    /**
     * Function to set value of SplashActivity in this class
     * @param splashActivity - instance of SplashActivity
     */
    public void setSplashActivity(SplashActivity splashActivity) {
        // set this classes instance of SplashActivity to splashActivity
        this.splashActivity = splashActivity;
    }

    /**
     * Function to set value of LoginActivity in this class
     * @param loginActivity - instance of LoginActivity
     */
    public void setLoginActivity(LoginActivity loginActivity) {
        // set this classes instance of LoginActivity to loginActivity
        this.loginActivity = loginActivity;
    }

    /**
     * Function to set value of MainActivity in this class
     * @param mainActivity - instance of MainActivity
     */
    public void setMainActivity(MainActivity mainActivity) {
        // set this classes instance of MainActivity to mainActivity
        this.mainActivity = mainActivity;
    }
}