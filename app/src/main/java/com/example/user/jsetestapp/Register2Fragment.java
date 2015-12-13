package com.example.user.jsetestapp;

import android.app.Fragment;
import android.app.ProgressDialog;
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

    // Progress Dialog
    private ProgressDialog pDialog;

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
        HelperMethods.addDataToSpinner(
                loginActivity.helperMethods.editLocationsNameArrayList(), locationsSpinner);
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
     * Background Async Task to Create new product
     */
    class CreateNewUser extends AsyncTask<String, String, Boolean> {

        /**
         * Before starting background thread Show Progress Dialog
         * Runs on the UI thread before doInBackground
         * Good for toggling visibility of a progress indicator
         */
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            showProgressDialog("Creating account. Please wait...");
        }

        /**
         * Creating user
         */
        protected Boolean doInBackground(String... params) {

            return addUserToDatabase();

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(Boolean result) {

            createUser(result);

            // dismiss the dialog once done
            pDialog.dismiss();

        }


        protected void onCancelled(Boolean result) {

            //Toast.makeText(loginActivity.getContext(), "task onCancelled", Toast.LENGTH_LONG).show();
            if (id != 0) loginActivity.user.setId(id);
            pDialog.dismiss();
        }

    }

    public boolean addUserToDatabase() {
        insertResult = "false";
        checkEmailResult = 1;
        id = 0;

        //User user = params[0];
        // Building Parameters
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

        JSONObject json = HelperMethods.getJsonObject
                (Util.getActivity().getString(R.string.url_create_user), httpParams);

        // if json is not null
        if (json != null) {

            try {
                checkEmailResult = Integer.parseInt(json.getString(Util.getActivity().getString(R.string.TAG_CHECK_EMAIL_RESULT)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (checkEmailResult==0) {
                // if email does not exist, see if insert was completed successfully
                try {
                    insertResult = json.getString(Util.getActivity().getString(R.string.TAG_INSERT_RESULT));
                    id = Integer.parseInt(json.getString(Util.getActivity().getString(R.string.TAG_ID)));
                } catch (JSONException e) {
                    e.printStackTrace();
                    // assign value to insertResult
                }
            }
            return true;
        }
        return false;
    }


    public void createUser(boolean result) {

        if (loginActivity.register2Fragment.isVisible()) {
            // if success = 0 show dialog user cant be created
            if (!result) {
                Util.showDialogFragment(R.array.create_account_failed_insert_failed);
                // if success = 1
            } else {
                // if email exists show dialog email exits
                if (checkEmailResult == 1) {
                    Util.showDialogFragment(R.array.create_account_failed_email_duplicate);
                }
                // if email doest exists
                else {
                    // if not inserted
                    if (insertResult.equals("false")) {
                        Util.showDialogFragment(R.array.create_account_failed_insert_failed);
                    }
                    // if inserted
                    else {
                        // set id to user
                        loginActivity.user.setId(id);
                        // launch activity with main activity intent
                        Util.launchActivity(loginActivity.getLaunchMainActivityIntent("create_account"));
                    }
                }
            }

        } else {
            if (id!=0){
                // set id to user
                loginActivity.user.setId(id);
            }
        }


    }

    public void showProgressDialog(String message) {
        pDialog = new ProgressDialog(loginActivity);
        pDialog.setMessage(message);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    public void onPause() {
    super.onPause();
        if (taskNewUser != null){
            taskNewUser.cancel(true);
        }
    }



}