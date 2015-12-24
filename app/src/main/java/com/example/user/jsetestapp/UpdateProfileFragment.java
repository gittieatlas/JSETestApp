package com.example.user.jsetestapp;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateProfileFragment extends Fragment {

    // Declare Controls
    View rootView;
    Spinner genderSpinner, locationsSpinner;
    CardView updateProfileButton;
    EditText firstNameEditText, lastNameEditText, dobMonthEditText, dobDayEditText;
    EditText dobYearEditText, ssnEditText, newPasswordEditText, confirmNewPasswordEditText;

    // Declare Activities
    LoginActivity loginActivity;

    // Declare Variables
    User user = new User();
    AsyncTask taskUpdateUser;
    private static String result = "";

    Boolean userSaved = false;
    Boolean pageLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_update_profile,
                container, false);

        initializeViews();
        registerListeners();

        // set navigation icon in toolbar
        loginActivity.toolbar.setNavigationIcon(getResources().
                getDrawable(R.drawable.ic_arrow_left_white_24dp));

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_update_profile, loginActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        loginActivity.showToolbar(true);

        loadUserInfoOnScreen();

        // clear focus, if any, from screen
        firstNameEditText.requestFocus();
        firstNameEditText.clearFocus();



        //my code
        userSaved = false;

        if (pageLoaded){
            // check if there are values in sp
            if (loginActivity.getSharedPrefs().firstName != null){
                // if there are compare to db
                if (!loginActivity.getSharedPrefs().firstName.equals(loginActivity.user.firstName))
                    firstNameEditText.setText(loginActivity.getSharedPrefs().firstName);
            }

            if (loginActivity.getSharedPrefs().lastName != null) {
                if (!loginActivity.getSharedPrefs().lastName.equals(loginActivity.user.lastName))
                    lastNameEditText.setText(loginActivity.getSharedPrefs().lastName);
            }

            if (loginActivity.getSharedPrefs().ssn != null) {
                if (!loginActivity.getSharedPrefs().ssn.equals(loginActivity.user.ssn))
                    ssnEditText.setText(loginActivity.getSharedPrefs().ssn);
            }

//            if (loginActivity.getSharedPrefs().gender != null) {
//                if (!loginActivity.getSharedPrefs().gender.equals(loginActivity.user.gender))
//                    genderSpinner.setSelection(loginActivity.getSharedPrefs().gender.ordinal());
//            }



            String date = loginActivity.getSharedPrefs().dob.toString();
            String userDate = loginActivity.user.dob.toString();
            if (!date.equals(userDate)){
                dobMonthEditText.setText(formatInt(loginActivity.getSharedPrefs().dob.getMonthOfYear(), 2));
                dobDayEditText.setText(formatInt(loginActivity.getSharedPrefs().dob.getDayOfMonth(), 2));
                dobYearEditText.setText(formatInt(loginActivity.getSharedPrefs().dob.getYear(), 4));
            }

//        Handle dob when empty
//        String month = formatInt(loginActivity.getSharedPrefs().dob.getMonthOfYear(), 2);
//        String userMonths = formatInt(loginActivity.user.dob.getMonthOfYear(), 2);
//        if (formatInt(loginActivity.getSharedPrefs().dob.getMonthOfYear(), 2) != null){
//            if (!month.equals(userMonths))
//                dobMonthEditText.setText(formatInt(loginActivity.getSharedPrefs().dob.getMonthOfYear(), 2));
//        }


//        User sharedPrefsUser = loginActivity.getSharedPrefs();
//        if (!sharedPrefsUser.firstName.equals(loginActivity.user.firstName))
//            firstNameEditText.setText(sharedPrefsUser.firstName);
//        else if (!sharedPrefsUser.lastName.equals(loginActivity.user.lastName))
//            lastNameEditText.setText(sharedPrefsUser.lastName);
//        else if (!sharedPrefsUser.ssn.equals(loginActivity.user.ssn))
//            ssnEditText.setText(sharedPrefsUser.ssn);
//        else if (!sharedPrefsUser.dob.equals(loginActivity.user.dob)) {
//            dobMonthEditText.setText(formatInt(sharedPrefsUser.dob.getMonthOfYear(), 2));
//            dobDayEditText.setText(formatInt(sharedPrefsUser.dob.getDayOfMonth(), 2));
//            dobYearEditText.setText(formatInt(sharedPrefsUser.dob.getYear(), 4));
//        }
//        else if (!sharedPrefsUser.gender.equals(loginActivity.user.gender)){
//            // set selection of genderSpinner to User's gender
//            genderSpinner.setSelection(user.gender.ordinal());
//        }
            //Toast.makeText(Util.getActivity(), value, Toast.LENGTH_LONG).show();    }
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        // if user not saved
        if (!userSaved) {
            // if user's first name is not equals to firstNameEditText
            if (!loginActivity.user.firstName.equals(firstNameEditText.getText().toString()) ||
                    !loginActivity.user.lastName.equals(lastNameEditText.getText().toString()) ||
                    !loginActivity.user.ssn.equals(ssnEditText.getText().toString()) ||
                    !Integer.toString(loginActivity.user.dob.getMonthOfYear()).equals(dobMonthEditText.getText().toString())){
                        loginActivity.setSharedPrefs(firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(), ssnEditText.getText().toString(),
                        dobMonthEditText.getText().toString(), dobDayEditText.getText().toString(),
                        dobYearEditText.getText().toString());
                //,genderSpinner.getSelectedItem().toString()
                // set gender and default location from the selected item of the spinners
            }
        pageLoaded = true;

        }

    }

    @Override
    public void onStop() {
        super.onStop();

        // Set navigation icon to transparent
        loginActivity.toolbar.setNavigationIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews() {
        // initialize and reference EditTexts
        firstNameEditText = (EditText) rootView.findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) rootView.findViewById(R.id.lastNameEditText);
        dobMonthEditText = (EditText) rootView.findViewById(R.id.dobMonthEditText);
        dobDayEditText = (EditText) rootView.findViewById(R.id.dobDayEditText);
        dobYearEditText = (EditText) rootView.findViewById(R.id.dobYearEditText);
        ssnEditText = (EditText) rootView.findViewById(R.id.ssnEditText);
        newPasswordEditText = (EditText) rootView.findViewById(R.id.newPasswordEditText);
        confirmNewPasswordEditText =
                (EditText) rootView.findViewById(R.id.confirmNewPasswordEditText);

        // initialize and reference Spinners
        genderSpinner = (Spinner) rootView.findViewById(R.id.spinnerGender);
        locationsSpinner = (Spinner) rootView.findViewById(R.id.spinnerDefaultLocation);


        // initialize and reference CardView
        updateProfileButton = (CardView) rootView.findViewById(R.id.updateProfileButton);
        bindDataToSpinners();
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        updateProfileButton.setOnClickListener(rightButtonListener);
       // leftButton.setOnClickListener(leftButtonListener);

        // set textWatchers
        dobDayEditText.addTextChangedListener(textWatcher);
        dobMonthEditText.addTextChangedListener(textWatcher);
        dobYearEditText.addTextChangedListener(textWatcher);
        ssnEditText.addTextChangedListener(textWatcher);

        // Set up touch listener for non-text box views to hide keyboard
        HelperMethods.registerTouchListenerForNonEditText(rootView.findViewById(R.id.rootLayout));
    }

    /**
     * Function to bind list of data to spinners
     */
    private void bindDataToSpinners() {
        // add data to genderSpinner
        HelperMethods.addDataToSpinner(
                getResources().getStringArray(R.array.update_profile_gender_array), genderSpinner);

        // add data to locationSpinner
        HelperMethods.addDataToSpinner(editLocationsNameArrayList(), locationsSpinner);
    }

    /**
     * Function to get User info and load in on the page
     */
    private void loadUserInfoOnScreen() {
        // instantiate a user from User
        User user = loginActivity.user;

        // set firstNameEditText and lastNameEditText
        firstNameEditText.setText(user.firstName);
        lastNameEditText.setText(user.lastName);

        // set dobMonthEditText, dobDayEditText, and dobYearEditText
        dobMonthEditText.setText(formatInt(user.dob.getMonthOfYear(), 2));
        dobDayEditText.setText(formatInt(user.dob.getDayOfMonth(), 2));
        dobYearEditText.setText(formatInt(user.dob.getYear(), 4));

        // set ssnEditText
        ssnEditText.setText(user.ssn.substring(user.ssn.length() - 4));

        // set selection of genderSpinner to User's gender
        genderSpinner.setSelection(user.gender.ordinal());
        // set selection of locationsSpinner to User's default location
        locationsSpinner.setSelection(setLocationSpinnerSelection());

        // set newPasswordEditText and confirmNewPasswordEditText
        newPasswordEditText.setText(user.password);
        confirmNewPasswordEditText.setText(user.password);
    }

    /**
     * Function to return position of default location in locationsArrayList
     *
     * @return int
     */
    public int setLocationSpinnerSelection() {
        // get position of defaultLocation's name in locationsNameArrayList
        try {
            // return position
            return loginActivity.locationsNameArrayList.indexOf(loginActivity.defaultLocation.name);
        } catch (Exception ex) {
            // if no such name exists in return 0 for the first item
            return 0;
        }
    }

    /**
     * Function to format int before loading the screen
     *
     * @param value  - value to be formatted and / or returned as String
     * @param length - desired length of text/int
     */
    private String formatInt(int value, int length) {
        // if value's length of characters is not equal to desired length
        if (String.valueOf(value).toCharArray().length != length) {
            // add a "0" to the beginning of the value and return as a String
            return "0" + Integer.toString(value);
        }
        // return value as a String
        return Integer.toString(value);
    }

    /**
     * TextWatcher - for all EditText controls
     */
    private TextWatcher textWatcher = new TextWatcher() {

        /**
         * This method is called when text is about to be replaced by new text
         * */
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        /**
         * This method is called when text have just replaced old text.
         * */
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        /**
         * This method is called when text has been changed.
         */
        public void afterTextChanged(Editable s) {
            // if editTexts have required length amount of text, move focus to next editText
            Util.removeFocusFromEditText(s, dobMonthEditText, dobDayEditText, 2);
            Util.removeFocusFromEditText(s, dobDayEditText, dobYearEditText, 2);
            Util.removeFocusFromEditText(s, dobYearEditText, ssnEditText, 4);
            Util.removeFocusFromEditText(s, ssnEditText, null, 4);
        }
    };


    View.OnClickListener leftButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //this listener will be gone
        }
    };

    View.OnClickListener rightButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // if form validates, update account. otherwise a dialog will display with errors found
            if (formValidates()) updateAccount();
        }
    };

    /**
     * Function to validate form
     * return boolean
     */
    private boolean formValidates() {
        // return true if requiredFieldsHaveValues, birthdayDateIsValid, ssnIsValid, passwordsMatch
        return requiredFieldsHaveValues() && birthdayDateIsValid() &&
                ssnIsValid() && passwordsMatch();
    }

    /**
     * Function to check if required fields have values entered
     * return boolean
     */
    private boolean requiredFieldsHaveValues() {
        // if any if the required field controls don't have a value
        if (Util.isEmpty(firstNameEditText) ||
                Util.isEmpty(lastNameEditText) ||
                Util.isEmpty(dobDayEditText) ||
                Util.isEmpty(dobMonthEditText) ||
                Util.isEmpty(dobYearEditText) ||
                Util.isEmpty(newPasswordEditText) ||
                Util.isEmpty(confirmNewPasswordEditText) ||
                Util.isEmpty(ssnEditText)) {

            // Show dialog: Update Account Failed - missing required values
            Util.showDialogFragment(R.array.update_account_failed_missing_fields);

            return false;
        }
        return true;
    }

    /**
     * Function to check if birthday entered is a valid date
     * return boolean
     */
    public Boolean birthdayDateIsValid() {
        // if birthday entered is not a valid date
        if (!Util.isBirthdayCorrect(
                dobYearEditText.getText().toString(),
                dobMonthEditText.getText().toString(),
                dobDayEditText.getText().toString())) {

            // Show dialog: Update Account Failed - birthday is invalid date
            Util.showDialogFragment(R.array.update_account_failed_birthday_incorrect);

            return false;
        }
        return true;
    }

    /**
     * Function to check if ssn entered is a length of 4
     * return boolean
     */
    public Boolean ssnIsValid() {
        // if ssn entered is not a length of 4
        if (!Util.isLength(ssnEditText.getText().toString(), 4)) {

            // Show dialog: Update Account Failed - ssn is not length of 4
            Util.showDialogFragment(R.array.update_account_failed_ssn_incorrect);

            // clear value of ssnEditText
            ssnEditText.setText("");

            return false;
        }
        return true;
    }

    /**
     * Function to check if passwords entered match
     * return boolean
     */
    private boolean passwordsMatch() {
        // if newPasswordEditText and confirmNewPasswordEditText don't match
        if (!Util.compareTwoStrings(
                newPasswordEditText.getText().toString(),
                confirmNewPasswordEditText.getText().toString())) {

            // Show dialog: Update Account Failed - passwords don't match
            Util.showDialogFragment(R.array.update_account_failed_values_not_match);

            // clear newPasswordEditText and confirmNewPasswordEditText values
            newPasswordEditText.setText("");
            confirmNewPasswordEditText.setText("");

            return false;
        }
        return true;
    }

    /**
     * Function to create account with new user info
     */
    private void updateAccount() {
        // if application can connect to internet
        if (Util.checkInternetConnection()) {
            // call AsyncTask to create new user
            taskUpdateUser = new UpdateUser().execute();
        } else {
            // Show Dialog: No Internet Connection
            Util.showDialogFragment(R.array.no_internet_connection);
        }
    }

    /**
     * Function to set updated account info to User
     */
    private User saveUpdatedUser() {
        userSaved = true;
        // set id
        user.id = loginActivity.user.id;

        // set email and password
        user.email = loginActivity.user.email;
        user.setPassword(newPasswordEditText.getText().toString());

        // set first and last name
        user.setFirstName(firstNameEditText.getText().toString());
        user.setLastName(lastNameEditText.getText().toString());

        // set ssn to XXX-XX- plus 4 digits entered
        user.setSsn("XXX-XX-" + ssnEditText.getText().toString());

        // set gender and default location from the selected item of the spinners
        user.setGender(genderSpinner.getSelectedItem().toString());
        user.setDefaultLocation(locationsSpinner.getSelectedItem().toString());

        // set dob from year, month, and day
        user.setDob(dobYearEditText.getText().toString(),
                dobMonthEditText.getText().toString(), dobDayEditText.getText().toString());

        // set location id based on text of updatedUser's default location name
        user.setLocationId(loginActivity.locationsArrayList, user);

        return user;
    }

    /**
     * Function to set remove first value of locationsNameArrayList
     */
    public ArrayList<String> editLocationsNameArrayList() {
        // create array list from locationsNameArrayList
        ArrayList<String> arrayList = loginActivity.locationsNameArrayList;
        // remove value of the first location "All"
        arrayList.remove(0);
        return arrayList;
    }

    /**
     * Function to set reference of LoginActivity
     *
     * @param loginActivity - reference to LoginActivity
     */
    public void setLoginActivity(LoginActivity loginActivity) {
        // set this loginActivity to loginActivity
        this.loginActivity = loginActivity;
    }


    /**
     * Background Async Task to Update User
     */
    class UpdateUser extends AsyncTask<String, String, String> {

        // constructor
        UpdateUser (){
            saveUpdatedUser();
        }

        /**
         * Before starting background thread
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // show progress dialog
            loginActivity.showProgressDialog("Updating account. Please wait...");
        }

        /**
         * Updating user
         *
         * @param params - information passed
         */
        protected String doInBackground(String... params) {
            // get JsonObject of user from Json string
            JSONObject userJsonObject = HelperMethods.getJsonObject
                    (Util.getActivity().getString(R.string.url_update_user),
                            getUpdateUserHttpParams());

            // if json is not equal to null
            if (userJsonObject != null) {
                getUpdateResult(userJsonObject);
                return updateLocalUser();
            } else {
                Log.e("Update User", "Couldn't update user");
            }

            return "false";

        }


        /**
         * After completing background task
         **/
        protected void onPostExecute(String result) {
            // if account was updated
            if (result.equals("true")) {
                // if fragment is visible, launch activity with main activity intent
                if (loginActivity.updateProfileFragment.isVisible())
                    Util.launchActivity(
                            loginActivity.getLaunchMainActivityIntent("update_profile"));
            }
            // if account was not updated
            else {
                // if fragment is visible, show dialog: update account failed
                if (loginActivity.updateProfileFragment.isVisible())
                    Util.showDialogFragment(R.array.update_account_failed_msg);
            }

            // dismiss the dialog
            loginActivity.pDialog.dismiss();
        }

        /**
         * After completing background task
         **/
        protected void onCancelled(String result) {
            super.onCancelled(result);
            // dismiss the dialog
            loginActivity.pDialog.dismiss();
        }

    }

    public String updateLocalUser() {
        if (result.equals("true")) {
            loginActivity.user = user;
        }
        return result;
    }

    /**
     * Function to get updateResult from jsonObject
     *
     * @param userJsonObject - object that is holding http request info from database
     **/
    private void getUpdateResult(JSONObject userJsonObject) {
        // reset result
        result = "false";

        // get "result" from json object
        // false if update was not done, true if update was done
        try {
            result = userJsonObject.getString(Util.getActivity().getString(R.string.TAG_RESULT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<NameValuePair> getUpdateUserHttpParams() {
        // build parameters for http request
        List<NameValuePair> httpParams = new ArrayList<>();

        httpParams.add(new BasicNameValuePair("id",
                Integer.toString(user.getId())));
        httpParams.add(new BasicNameValuePair("firstName",
                user.getFirstName()));
        httpParams.add(new BasicNameValuePair("lastName",
                user.getLastName()));
        httpParams.add(new BasicNameValuePair("gender",
                Integer.toString(user.getGender(user) + 1)));
        httpParams.add(new BasicNameValuePair("dob",
                user.getDob().toString("yyyy-MM-dd")));
        httpParams.add(new BasicNameValuePair("ssn",
                user.getSsn()));
        httpParams.add(new BasicNameValuePair("email",
                user.getEmail()));
        httpParams.add(new BasicNameValuePair("password",
                user.getPassword()));
        httpParams.add(new BasicNameValuePair("locationId",
                Integer.toString(user.getLocationId())));

        return httpParams;
    }


}