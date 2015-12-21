package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

public class Register2Fragment extends Fragment {

    // testsJsonArray JSONArray
    private String insertResult;
    private int checkEmailResult;
    private int id;

    // Declare Controls
    View rootView;
    Spinner genderSpinner, locationsSpinner;
    EditText firstNameEditText, lastNameEditText, dobMonthEditText;
    EditText dobDayEditText, dobYearEditText, ssnEditText;
    Button rightButton, leftButton;

    // Declare Activities
    LoginActivity loginActivity;

    // Declare Variables
    AsyncTask taskNewUser;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_register2,
                container, false);

        initializeViews();
        registerListeners();

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_register2, loginActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // if user was created before the activity got paused and did not switch activities
        if (loginActivity.user.getId() != 0) {
            // launch MainActivity
            Util.launchActivity(loginActivity.getLaunchMainActivityIntent("create_account"));
        }
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

        // initialize and reference Buttons
        rightButton = (Button) rootView.findViewById(R.id.rightButton);
        leftButton = (Button) rootView.findViewById(R.id.leftButton);

        // initialize and reference Spinners
        genderSpinner = (Spinner) rootView.findViewById(R.id.spinnerGender);
        locationsSpinner = (Spinner) rootView.findViewById(R.id.spinnerDefaultLocation);

        bindDataToSpinners();

    }

    /**
     * Function to bind list of data to spinners
     */
    private void bindDataToSpinners() {
        // add data to genderSpinner
        HelperMethods.addDataToSpinner(
                getResources().getStringArray(R.array.gender_array), genderSpinner);

        // add data to locationSpinner
        HelperMethods.addDataToSpinner(editLocationsNameArrayList(), locationsSpinner);
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

        // set onTouchListener for all non ediText controls to hide the soft keyboard
        HelperMethods.registerTouchListenerForNonEditText(rootView.findViewById(R.id.rootLayout));
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

    /**
     * OnClickListener for buttonLeft
     */
    OnClickListener leftButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            loginActivity.getFragmentManager().popBackStack();
        }
    };

    /**
     * OnClickListener for buttonRight
     */
    OnClickListener rightButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // if form validates, go to create account step 2.
            // otherwise a dialog will display with errors found
            if (formValidates()) createAccount();
        }
    };

    /**
     * Function to validate form
     * return boolean
     */
    private boolean formValidates() {
        // return true if requiredFieldsHaveValues, emailAddressIsValid, and passwordsMatch
        return requiredFieldsHaveValues() && birthdayDateIsValid() && ssnIsValid();
    }

    /**
     * Function to check if required fields have values entered
     * return boolean
     */
    private boolean requiredFieldsHaveValues() {
        // if any if the required field controls don't have a value or a value selected
        if (Util.isEmpty(firstNameEditText) ||
                Util.isEmpty(lastNameEditText) ||
                Util.isEmpty(dobDayEditText) ||
                Util.isEmpty(dobMonthEditText) ||
                Util.isEmpty(dobYearEditText) ||
                Util.isEmpty(ssnEditText) ||
                genderSpinner.getSelectedItemPosition() == 0 ||
                locationsSpinner.getSelectedItemPosition() == 0) {

            // Show dialog: Create Account Failed - missing required values
            Util.showDialogFragment(R.array.registration_failed_missing_fields);
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

            // Show dialog: Create Account Failed - birthday is invalid date
            Util.showDialogFragment(R.array.registration_failed_birthday_incorrect);

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

            // Show dialog: Create Account Failed - ssn is not length of 4
            Util.showDialogFragment(R.array.registration_failed_ssn_incorrect);

            // clear value of ssnEditText
            ssnEditText.setText("");

            return false;
        }
        return true;
    }

    /**
     * Function to create account with new user info
     */
    private void createAccount() {
        // if application can connect to internet
        if (Util.checkInternetConnection()) {
            saveUser();

            // call AsyncTask to create new user
            taskNewUser = new CreateNewUser().execute();
            // taskNewUser = loginActivity.databaseOperations.newUser(loginActivity.user);
        } else {
            // Show Dialog: No Internet Connection
            Util.showDialogFragment(R.array.no_internet_connection);
        }
    }

    /**
     * Function to set new account info to User
     */
    private void saveUser() {
        // set first and last name
        loginActivity.user.setFirstName(firstNameEditText.getText().toString());
        loginActivity.user.setLastName(lastNameEditText.getText().toString());

        // set ssn to XXX-XX- plus 4 digits entered
        loginActivity.user.setSsn("XXX-XX-" + ssnEditText.getText().toString());

        // set gender and default location from the selected item of the spinners
        loginActivity.user.setGender(genderSpinner.getSelectedItem().toString());
        loginActivity.user.setDefaultLocation(locationsSpinner.getSelectedItem().toString());

        // set dob from year, month, and day
        loginActivity.user.setDob(dobYearEditText.getText().toString(),
                dobMonthEditText.getText().toString(), dobDayEditText.getText().toString());

        // set location id based on text of user's default location name
        loginActivity.user.setLocationId(loginActivity.locationsArrayList, loginActivity.user);
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
     * Function to set edit first value of locationsNameArrayList
     */
    public ArrayList<String> editLocationsNameArrayList() {
        // create array list from locationsNameArrayList
        ArrayList<String> arrayList = loginActivity.locationsNameArrayList;
        // set value of the first location "All" to ""
        arrayList.set(0, "");
        return arrayList;
    }

    /**
     * Background Async Task to Create new user
     */
    class CreateNewUser extends AsyncTask<String, String, Boolean> {

        /**
         * Before starting background thread
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // show progress dialog
            loginActivity.showProgressDialog("Creating account. Please wait...");
        }

        /**
         * Creating user
         * @param params - information passed
         * @return Boolean
         */
        protected Boolean doInBackground(String... params) {
            // get JsonObject of user from Json string
            JSONObject userJsonObject = HelperMethods.getJsonObject
                    (Util.getActivity().getString(R.string.url_create_user), getUserHttpParams());

            // if json is not null
            if (userJsonObject != null) {
                getEmailResult(userJsonObject);
                return checkIfUserInserted(userJsonObject);
            }
            return false;
        }

        /**
         * After completing background task
         * @param result - param that holds result
         **/
        protected void onPostExecute(Boolean result) {

                // create account was unsuccessful: result is false
                if (!result) {
                    if (loginActivity.register2Fragment.isVisible())
                        // if fragment is visible, show dialog: create account failed
                    Util.showDialogFragment(R.array.create_account_failed_insert_failed);
                }
                // create account was successful: result is true
                else {
                    // checkEmailResult is equal 1 - email already exists
                    if (checkEmailResult == 1) {
                        if (loginActivity.register2Fragment.isVisible())
                            // if fragment is visible, show dialog:
                            // create account failed duplicate email
                            Util.showDialogFragment(R.array.create_account_failed_email_duplicate);
                    }
                    // checkEmailResult is equal 0 - email doesn't exists
                    else {
                        // insertResult is equal to false - user did not get inserted
                        if (insertResult.equals("false")) {
                            if (loginActivity.register2Fragment.isVisible())
                                // if fragment is visible, show dialog: create account failed
                                Util.showDialogFragment
                                        (R.array.create_account_failed_insert_failed);
                        }
                        // insertResult is equal to true - user was inserted successfully
                        else {
                            // if fragment is visible
                            if (loginActivity.register2Fragment.isVisible()) {
                                // set id to user
                                loginActivity.user.setId(id);
                                // launch activity with main activity intent
                                Util.launchActivity(loginActivity
                                        .getLaunchMainActivityIntent("create_account"));
                            }
                            // if fragment is not visible
                            else {
                                // if id is not equal to 0
                                if (id != 0) {
                                    // set id to user
                                    loginActivity.user.setId(id);
                                }
                            }
                        }
                    }
                }

            // dismiss the dialog once done
            loginActivity.pDialog.dismiss();

        }

        /**
         * After completing background task
         **/
        protected void onCancelled(Boolean result) {
            // if id is not equal to 0
            if (id != 0)
                // set id of user
                loginActivity.user.setId(id);
            // dismiss the dialog
            loginActivity.pDialog.dismiss();
        }

    }

    /**
     * Function to get httpParams - user information
     *
     * @return List
     **/
    private List<NameValuePair> getUserHttpParams() {
        // build parameters for http request
        List<NameValuePair> httpParams = new ArrayList<>();
        httpParams.add(new BasicNameValuePair("firstName", loginActivity.user.getFirstName()));
        httpParams.add(new BasicNameValuePair("lastName", loginActivity.user.getLastName()));
        httpParams.add(new BasicNameValuePair("gender", Integer.toString
                (loginActivity.user.getGender(loginActivity.user) + 1)));
        httpParams.add(new BasicNameValuePair("dob", loginActivity.user.getDob()
                .toString("yyyy-MM-dd")));
        httpParams.add(new BasicNameValuePair("ssn", loginActivity.user.getSsn()));
        httpParams.add(new BasicNameValuePair("email", loginActivity.user.getEmail()));
        httpParams.add(new BasicNameValuePair("password", loginActivity.user.getPassword()));
        httpParams.add(new BasicNameValuePair("locationId",
                Integer.toString(loginActivity.user.getLocationId())));
        return httpParams;
    }

    /**
     * Function to get checkEmailResult from jsonObject
     *
     * @param userJsonObject - object that is holding http request info from database
     **/
    private void getEmailResult(JSONObject userJsonObject) {
        // reset checkEmailResult
        checkEmailResult = 1;

        // try to get int with tag "checkEmailResult" from json object
        // 0 if email does not exist, 1 if email exist
        try {
            checkEmailResult = Integer.parseInt(userJsonObject.getString(Util.getActivity()
                    .getString(R.string.TAG_CHECK_EMAIL_RESULT)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to check if user was inserted from jsonObject
     *
     * @param userJsonObject - object that is holding http request info from database
     * @return Boolean
     **/
    private Boolean checkIfUserInserted(JSONObject userJsonObject){
        // initialize variables
        insertResult = "false";
        id = 0;

        // if email does not exist, see if insert was completed successfully
        if (checkEmailResult==0) {
            try {
                // try to get String with tag "insertResult" from json object
                insertResult = userJsonObject.getString(Util.getActivity()
                        .getString(R.string.TAG_INSERT_RESULT));
                // try to get int with tag "id" from json object
                id = Integer.parseInt(userJsonObject.getString(Util.getActivity()
                        .getString(R.string.TAG_ID)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}