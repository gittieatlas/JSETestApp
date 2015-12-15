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




    /**
     * Function to show a snack bar in Coordinator Layout
     *
     * @param message - snack bar message
     */
    public void showSnackBar(String message) {
        try {
            // get reference of CoordinatorLayout the snack bar will be hosted in
            CoordinatorLayout coordinatorLayout =
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

    /**
     * Function to set up touch listener for non-text box views
     */
    public static void registerTouchListenerForNonEditText(View view) {
        // if a view is not an instance of EditText
        if (!(view instanceof EditText)) {
            // set on touch listener
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }

        // if the view is a layout container
        if (view instanceof ViewGroup) {
            // iterate over children
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                // get child view
                View innerView = ((ViewGroup) view).getChildAt(i);
                // seed recursion
                registerTouchListenerForNonEditText(innerView);
            }
        }
    }

    /**
     * Function to hide keyboard
     */
    public static void hideSoftKeyboard() {
        // get instance of keyboard through InputMethodManager
        InputMethodManager inputMethodManager = (InputMethodManager)
                Util.getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

        // find the currently focused view, so we can get the correct window token from it.
        View view = Util.getActivity().getCurrentFocus();

        // if no view currently has focus, create a new one, so we can grab a window token from it
        if (view == null) {
            view = new View(Util.getActivity());
        }

        // hide keyboard
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Function to create a location object from a JSONObject
     *
     * @param jsonLocation - JSONObject with location values
     * @return location
     */
    public static Location setLocation(JSONObject jsonLocation) {
        // instantiate new location
        Location location = new Location();

        // set location values from corresponding JSONObject values
        try {
            // set id
            location.setId(Integer.parseInt(
                    jsonLocation.getString(Util.getStringValue(R.string.TAG__LOCATION_ID))));

            // set branchId
            location.setBranchId(Integer.parseInt(
                    jsonLocation.getString(Util.getStringValue(R.string.TAG__BRANCH_ID))));

            // set name
            location.setName(jsonLocation.getString(Util.getStringValue(R.string.TAG_NAME)));

            // set address
            location.setAddress(getAddress(jsonLocation));

            // set phone
            location.setPhone(jsonLocation.getString(Util.getStringValue(R.string.TAG_PHONE)));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Function to build address location
     *
     * @param jsonLocation - JSONObject with location values
     * @return fullAddress
     */
    public static String getAddress(JSONObject jsonLocation) {
        // instantiate StringBuilder to hold full address
        StringBuilder fullAddress = new StringBuilder();

        // create fullAddress from values
        try {
            // set address values from corresponding JSONObject values
            String address = jsonLocation.getString(Util.getStringValue(R.string.TAG_ADDRESS));
            String city = jsonLocation.getString(Util.getStringValue(R.string.TAG_CITY));
            String state = jsonLocation.getString(Util.getStringValue(R.string.TAG_STATE));
            String zip = jsonLocation.getString(Util.getStringValue(R.string.TAG_ZIP));
            String country = jsonLocation.getString(Util.getStringValue(R.string.TAG_COUNTRY));

            // if values don't equal to "null", add to StringBuilder along with a space
            if (!address.equals("null")) fullAddress.append(address + " ");
            if (!city.equals("null")) fullAddress.append(city + " ");
            if (!state.equals("null")) fullAddress.append(state + " ");
            if (!zip.equals("null")) fullAddress.append(zip + " ");
            if (!country.equals("null")) fullAddress.append(country);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // return fullAddress as a string
        return fullAddress.toString();
    }

    /**
     * Function to create a test object from a JSONObject
     *
     * @param testObject - JSONObject with test values
     * @return test
     */
    public static Test setTest(JSONObject testObject) {
        // instantiate new test
        Test test = new Test();

        // set test values from corresponding JSONObject values
        try {
            // set branchId
            test.branchId = Integer.parseInt(
                    testObject.getString(Util.getStringValue(R.string.TAG_BRANCH_ID)));

            // set location
            test.location = testObject.getString(
                    Util.getStringValue(R.string.TAG_LOCATION));

            // set date
            test.date = LocalDate.parse(
                    testObject.getString(Util.getStringValue(R.string.TAG_DATE)));

            // set dayOfWeek
            test.setDayOfWeek(Test.DayOfWeek.values()[
                    (test.getDate().getDayOfWeek() - 1)].toString());

            // set time
            test.time = LocalTime.parse(new StringBuilder(
                    testObject.getString(Util.getStringValue(R.string.TAG_TIME)))
                    .insert(testObject.getString(Util.getStringValue(R.string.TAG_TIME))
                            .length() - 2, ":").toString());

            // set deadline date
            test.deadlineDate = LocalDate.parse(
                    testObject.getString(Util.getStringValue(R.string.TAG_CLOSING_DATE)));

            // set deadline time
            test.deadlineTime = LocalTime.parse(new StringBuilder(
                    testObject.getString(Util.getStringValue(R.string.TAG_CLOSING_TIME)))
                    .insert(testObject.getString(Util.getStringValue(R.string.TAG_CLOSING_TIME))
                            .length() - 2, ":").toString());

            // set setDeadlineDayOfWeek
            test.setDeadlineDayOfWeek(Test.DayOfWeek
                    .values()[(test.getDeadlineDate().getDayOfWeek() - 1)].toString());

            // set gender
            test.setGender(Integer.parseInt(
                    testObject.getString(Util.getStringValue(R.string.TAG_GENDER))));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return test;
    }

    /**
     * Function to create a hours object from a JSONObject
     *
     * @param hoursObject - JSONObject with test values
     * @return hour
     */
    public static Hour setHours(JSONObject hoursObject) {
        // instantiate new hour
        Hour hour = new Hour();

        // set hour values from corresponding JSONObject values
        try {
            // set name
            hour.name =
                    hoursObject.getString(Util.getStringValue(R.string.TAG_LIBRARY_LOCATION));

            // set day
            String day =
                    hoursObject.getString(Util.getStringValue(R.string.TAG_DAY_OF_WEEK));

            // set dayOfWeek
            hour.setDayOfWeek(Hour.DayOfWeek.
                    values()[(Integer.parseInt(day) - 1)]);

            // set startTime
            hour.startTime = LocalTime.parse(
                    hoursObject.getString(Util.getStringValue(R.string.TAG_OPENING_TIME)));

            // set duration
            LocalTime duration = LocalTime.parse(
                    hoursObject.getString(Util.getStringValue(R.string.TAG_DURATION)));

            // set endTime
            hour.endTime = hour.getStartTime().plusHours(duration.getHourOfDay())
                    .plusMinutes(duration.getMinuteOfHour());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hour;

    }

    /**
     * Function to create a branch object from a JSONObject
     *
     * @param branchObject - JSONObject with test values
     * @return branch
     */
    public static Branch setBranch(JSONObject branchObject) {
        // instantiate new branch
        Branch branch = new Branch();

        // set branch values from corresponding JSONObject values
        try {
            // set id
            branch.id = Integer.parseInt(
                    branchObject.getString(Util.getStringValue(R.string.TAG_ID)));

            // set name
            branch.name =
                    branchObject.getString(Util.getStringValue(R.string.TAG_BRANCH_NAME));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return branch;
    }

    /**
     * Function to create a alert object from a JSONObject
     *
     * @param alertObject - JSONObject with test values
     * @return alert
     */
    public static Alert setAlert(JSONObject alertObject) {
        // ToDo handle if any values are null
        // instantiate new alert
        Alert alert = new Alert();

        // set alert values from corresponding JSONObject values
        try {
            // set location name
            alert.setLocationName(
                    alertObject.getString(Util.getStringValue(R.string.TAG_LOCATION_NAME)));

            // set time stamp
            String timeStamp = (
                    alertObject.getString(Util.getStringValue(R.string.TAG_TIME_STAMP)));

            // set date
            alert.date = LocalDate.parse(
                    timeStamp.substring(0, 10));

            // set dayOfWeek
            alert.setDayOfWeek(
                    Alert.DayOfWeek.values()[(alert.getDate().getDayOfWeek() - 1)]);

            // set time
            alert.setTime(LocalTime.parse(
                    timeStamp.substring(timeStamp.length() - 8)));

            // set alert text
            alert.setAlertText(
                    alertObject.getString(Util.getStringValue(R.string.TAG_ALERT_TEXT)));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return alert;
    }

    /**
     * Function to set value of SplashActivity in this class
     *
     * @param splashActivity - instance of SplashActivity
     */
    public void setSplashActivity(SplashActivity splashActivity) {
        // set this classes instance of SplashActivity to splashActivity
        this.splashActivity = splashActivity;
    }

    /**
     * Function to set value of LoginActivity in this class
     *
     * @param loginActivity - instance of LoginActivity
     */
    public void setLoginActivity(LoginActivity loginActivity) {
        // set this classes instance of LoginActivity to loginActivity
        this.loginActivity = loginActivity;
    }

    /**
     * Function to set value of MainActivity in this class
     *
     * @param mainActivity - instance of MainActivity
     */
    public void setMainActivity(MainActivity mainActivity) {
        // set this classes instance of MainActivity to mainActivity
        this.mainActivity = mainActivity;
    }

    /**
     * Function to call http request that return a JsonObject and then convert it to a JsonArray
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
            // get int with tag "success" from json object
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

    /**
     * Function to call http request that returns a JsonObject
     *
     * @param url    - url to get the JSON string from
     * @param params - list of key values to pass along to the http request
     * @return JsonObject
     */
    public static JSONObject getJsonObject(String url, List<NameValuePair> params) {
        // instantiate new JsonParser
        JSONParser jsonParser = new JSONParser();

        // create JSON Object from request made to url
        JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

        // initialize int to receive value of success (0 or 1)
        int success = 0;

        try {
            // get int with tag "success" from json object
            success = json.getInt(Util.getStringValue(R.string.TAG_SUCCESS));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // if success is 0 ; either because the query of the http request was not successful
        // or because the http request itself was unsuccessful and therefor the JsonObject is null
        if (success != 0) {
            return json;
        }

        return null;
    }

    /**
     * Function to create a bundle for a DialogFragment
     *
     * @param array - tag of dialog fragment
     * @return bundle
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
     * Function to set up locationsNameArrayList withe names of each location
     *
     * @param locationsArrayList - array list with all locations
     * @return ArrayList<>
     */
    public ArrayList<String> setUpLocationsNameArrayList(ArrayList<Location> locationsArrayList) {
        // instantiate a new ArrayList to hold location names
        ArrayList<String> locationsNameArrayList = new ArrayList<>();

        // add "Location" to the beginning of the array
        locationsNameArrayList.add("Location");

        // for each location in locationsArrayList
        for (Location location : locationsArrayList) {
            // add name of the location to locationsNameArrayList
            locationsNameArrayList.add(location.getName());
        }

        return locationsNameArrayList;
    }

    /**
     * Function to set up branchesNameArrayList withe names of each branch
     *
     * @param branchesArrayList - array list with all branches
     * @return ArrayList<>
     */
    public ArrayList<String> setUpBranchesNameArrayList(ArrayList<Branch> branchesArrayList) {
        // instantiate a new ArrayList to hold branch names
        ArrayList<String> branchesNameArrayList = new ArrayList<>();

        // add "All" to the beginning of the array
        branchesNameArrayList.add("All");

        // for each branch in branchesArrayList
        for (Branch branch : branchesArrayList) {
            // if branch id equals to id of the user's default location
            if (branch.getId() == (mainActivity.defaultLocation.branchId)) {
                // add name of the branch to branchesNameArrayList along with (Default Branch)
                branchesNameArrayList.add(branch.getName() + " (Default Branch)");
            }
            // if branch id does not equal to id of the user's default location
            else {
                // add name of the branch to branchesNameArrayList
                branchesNameArrayList.add(branch.getName());
            }
        }
        return branchesNameArrayList;
    }

    /**
     * Function to filter testsArrayList by gender
     *
     * @param testsArrayList - array list with all tests
     *                       return ArrayList<>
     */
    public ArrayList<Test> filterTestsArrayListByGender(ArrayList<Test> testsArrayList) {
        // for each test in testsArrayList
        for (int i = 0; i < testsArrayList.size(); i++) {
            // if gender of test does not equal to gender of user
            if (!testsArrayList.get(i).gender.name().equals(mainActivity.user.gender.name())) {
                // remove the test from the array list
                testsArrayList.remove(i);
                i--;
            }
        }
        return testsArrayList;
    }

    /**
     * Function to set up lit view that contains library hours
     *
     * @param listView     - list view that will hold hours
     * @param locationName - name of location to get hours for
     */
    public void setupListView(ListView listView, String locationName) {
        // create adapter from hoursFilteredArrayList
        ListAdapter adapter =
                new HoursAdapter(Util.getContext(), mainActivity.hoursFilteredArrayList);

        // set adapter to the list view
        listView.setAdapter(adapter);

        // update the list view with hours based on location given
        updateHoursArrayListView(listView, locationName);
    }

    /**
     * Function to set up update hours array list with hours from given location
     *
     * @param listView     - list view that will hold hours
     * @param locationName - name of location to get hours for
     */
    public void updateHoursArrayListView(ListView listView, String locationName) {
        // fill hoursFilteredArrayList with all hours from this location
        setUpHoursFilteredArrayList(locationName);

        // notify adapter that data was changed
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

        // set list view height based on total amount of items in list view
        setListViewHeightBasedOnItems(listView);
    }

    /**
     * Function to fill hoursFilteredArrayList with all hours from the given location name
     *
     * @param locationName - the location name of hours needed to be retrieved
     */
    public void setUpHoursFilteredArrayList(String locationName) {
        // if hoursFilteredArrayList has values
        if (mainActivity.hoursFilteredArrayList != null)
            // clear all values
            mainActivity.hoursFilteredArrayList.clear();

        // for each hour in hourArrayList
        for (Hour hour : mainActivity.hourArrayList) {
            // if name of the hours equals to locationName
            if (hour.getName().equals(locationName)) {
                // create new HoursDataObject from dayOfWeek, startTime, endTime
                HoursDataObject hoursDataObject = new HoursDataObject(
                        Util.firstLetterCaps(hour.getDayOfWeek().toString()),
                        hour.getStartTime().toString("hh:mm a"),
                        hour.getEndTime().toString("hh:mm a"));

                // add hours data object to hoursFilteredArrayList
                mainActivity.hoursFilteredArrayList.add(hoursDataObject);
            }
        }
    }

    /**
     * Function to set up default location
     *
     * @return Location
     */
    public Location setUpDefaultLocation() {
        // instantiate a new Location
        Location defaultLocation = new Location();

        // for each location in locationsArrayList
        for (Location location : mainActivity.locationsArrayList) {
            // if id of location equals to location id of user
            if (location.getId() == mainActivity.user.locationId) {
                // set defaultLocation to this location
                defaultLocation = location;
            }
        }

        return defaultLocation;
    }

    /**
     * Function to set isJseMember
     */
    public void setUpJseStudentId() {
        // if jseStudentId is null
        if (mainActivity.user.jseStudentId == null) {
            // if internet connection status is true
            if (Util.checkInternetConnection()) {
                // call AsyncTask to get jseStudentId
                mainActivity.databaseOperations.getJseStudentId(mainActivity.user);
            }
        }
    }

    /**
     * Function to set event time and date of for a calendar event
     */
    public void setReminderToCallJse() {
        // get current day of week
        int dayOfWeek = LocalDate.now().getDayOfWeek();
        // get JSE office hours from resource
        String timeOfEvent =
                getResources().getString(R.string.jse_office_hours_mon_thurs_hours_start_time);
        // days to add
        LocalDate dateOfEvent;

        // if current day of week is Thursday
        if (dayOfWeek == 4) {
            // get hours for Friday
            timeOfEvent = getResources().getString(R.string.jse_office_hours_friday_hours_start_time);
        }

        // if current day of week is Friday
        if (dayOfWeek == 5) {
            dateOfEvent = LocalDate.now().plusDays(3);
        }
        // if current day of week is Saturday
        else if (dayOfWeek == 6) {
            dateOfEvent = LocalDate.now().plusDays(2);
        }
        // if current day is Sunday - Wednesday
        else {
            dateOfEvent = LocalDate.now().plusDays(1);
        }

        createCalendarEventToCallJSE(timeOfEvent, dateOfEvent);
    }

    /**
     * Function to create calendar event that will remind user to call JSE
     *
     * @param timeOfEvent -
     * @param dateOfEvent -
     */
    public void createCalendarEventToCallJSE(String timeOfEvent, LocalDate dateOfEvent) {
        // create DateTimeFormatter for time of reminder
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm a");

        // set calendar intent to call JSE during office hours
        Util.calendarIntent("Call JSE", null, null, dateOfEvent, fmt.parseLocalTime(timeOfEvent));
    }
}