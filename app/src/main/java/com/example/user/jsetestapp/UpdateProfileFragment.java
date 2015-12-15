package com.example.user.jsetestapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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

    // Progress Dialog
    private ProgressDialog pDialog;
    private static String result = "";

    // Declare Controls
    View rootView;
    Spinner genderSpinner, locationsSpinner;
    Button rightButton, leftButton;
    EditText firstNameEditText, lastNameEditText, dobMonthEditText, dobDayEditText;
    EditText dobYearEditText, ssnEditText, newPasswordEditText, confirmNewPasswordEditText;

    // Declare Activities
    MainActivity mainActivity;
    LoginActivity loginActivity;

    // Declare Variables
    User user = new User();
    AsyncTask taskUpdateUser;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_update_profile,
                container, false);

        initializeViews();
        registerListeners();

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_update_profile, loginActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadUserInfoOnScreen();

        // clear focus, if any, from screen
        firstNameEditText.requestFocus();
        firstNameEditText.clearFocus();
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

        // initialize and reference Buttons
        rightButton = (Button) rootView.findViewById(R.id.rightButton);
        leftButton = (Button) rootView.findViewById(R.id.leftButton);

        bindDataToSpinners();
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        rightButton.setOnClickListener(rightButtonListener);
        leftButton.setOnClickListener(leftButtonListener);

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
     * @return int
     */
    public int setLocationSpinnerSelection() {
        // get position of defaultLocation's name in locationsNameArrayList
        try {
            // return position
            return loginActivity.locationsNameArrayList.indexOf(loginActivity.defaultLocation.name);
        } catch (Exception ex){
            // if no such name exists in return 0 for the first item
            return 0;
        }
    }

    /**
     * Function to format int before loading the screen
     * @param value - value to be formatted and / or returned as String
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
            //taskUpdateUser = loginActivity.databaseOperations.updateUser(saveUpdatedUser());
            taskUpdateUser = new UpdateUser(saveUpdatedUser()).execute();
        } else {
            // Show Dialog: No Internet Connection
            Util.showDialogFragment(R.array.no_internet_connection);
        }
    }

    /**
     * Function to set updated account info to User
     */
    private User saveUpdatedUser() {
        // instantiate a new user to hold updated user info
        User updatedUser = new User();

        // set id
        updatedUser.id = loginActivity.user.id;

        // set email and password
        updatedUser.email = loginActivity.user.email;
        updatedUser.setPassword(newPasswordEditText.getText().toString());

        // set first and last name
        updatedUser.setFirstName(firstNameEditText.getText().toString());
        updatedUser.setLastName(lastNameEditText.getText().toString());

        // set ssn to XXX-XX- plus 4 digits entered
        updatedUser.setSsn("XXX-XX-" + ssnEditText.getText().toString());

        // set gender and default location from the selected item of the spinners
        updatedUser.setGender(genderSpinner.getSelectedItem().toString());
        updatedUser.setDefaultLocation(locationsSpinner.getSelectedItem().toString());

        // set dob from year, month, and day
        updatedUser.setDob(dobYearEditText.getText().toString(),
                dobMonthEditText.getText().toString(), dobDayEditText.getText().toString());

        // set location id based on text of updatedUser's default location name
        updatedUser.setLocationId(loginActivity.locationsArrayList, updatedUser);

        return updatedUser;
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
        User user = new User();
        String id, firstName, lastName, gender, dob, ssn, email, password, locationId;

        UpdateUser(User user) {
            this.user = user;
            this.id = Integer.toString(user.getId());
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.dob = user.getDob().toString("yyyy-MM-dd");
            this.gender = Integer.toString(user.getGender(user) + 1);
            this.ssn = user.getSsn();
            this.locationId = Integer.toString(user.getLocationId());


        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("Updating account. Please wait...");
        }

        /**
         * Updating User
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("firstName", firstName));
            params.add(new BasicNameValuePair("lastName", lastName));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("dob", dob));
            params.add(new BasicNameValuePair("ssn", ssn));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("locationId", locationId));
            return UpdateUserInDatabase(params, user);
        }


        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String result) {
            // dismiss the dialog once done

            updateUser(result);
            pDialog.dismiss();
        }


        protected void onCancelled(String result){

            pDialog.dismiss();
        }

    }

    public String UpdateUserInDatabase(List<NameValuePair> params, User user) {


        // get JsonObject of user from Json string
        JSONObject json = HelperMethods.getJsonObject
                (Util.getActivity().getString(R.string.url_update_user), new ArrayList<NameValuePair>());
        if (json != null) {
            try {
                result = json.getString(Util.getActivity().getString(R.string.TAG_RESULT));
                //id = Integer.parseInt(json.getString(TAG_JSE_STUDENT_ID));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (json != null && !result.equals("false")) {
                loginActivity.user = user;
            }

            return result;
        }
        return "false";
    }

    public void showProgressDialog(String message){
        pDialog = new ProgressDialog(loginActivity);
        pDialog.setMessage(message);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
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
}