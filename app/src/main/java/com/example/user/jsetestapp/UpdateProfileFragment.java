package com.example.user.jsetestapp;
// CLEANED
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UpdateProfileFragment extends Fragment {

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
        HelperMethods.addDataToSpinner(
                loginActivity.helperMethods.editUpdateProfileLocationsNameArrayList(), locationsSpinner);
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

        // set selection of genderSpinner and locationSpinner
        genderSpinner.setSelection(user.gender.ordinal());
        locationsSpinner.setSelection(loginActivity.helperMethods.setLocationSpinnerSelection());

        // set newPasswordEditText and confirmNewPasswordEditText
        newPasswordEditText.setText(user.password);
        confirmNewPasswordEditText.setText(user.password);
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
        if (loginActivity.helperMethods.isEmpty(firstNameEditText) ||
                loginActivity.helperMethods.isEmpty(lastNameEditText) ||
                loginActivity.helperMethods.isEmpty(dobDayEditText) ||
                loginActivity.helperMethods.isEmpty(dobMonthEditText) ||
                loginActivity.helperMethods.isEmpty(dobYearEditText) ||
                loginActivity.helperMethods.isEmpty(newPasswordEditText) ||
                loginActivity.helperMethods.isEmpty(confirmNewPasswordEditText) ||
                loginActivity.helperMethods.isEmpty(ssnEditText)) {

            // Show dialog: Update Account Failed - missing required values
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_update_account_failed_missing_fields)));

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
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_update_account_failed_birthday_incorrect)));

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
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_update_account_failed_ssn_incorrect)));

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
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_update_account_failed_values_not_match)));

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
        if (HelperMethods.checkInternetConnection()) {


            // call AsyncTask to create new user
            taskUpdateUser = loginActivity.databaseOperations.updateUser(saveUpdatedUser());
        } else {
            // Show Dialog: No Internet Connection
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_no_internet_connection)));
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
     * Function to set reference of LoginActivity
     *
     * @param loginActivity - reference to LoginActivity
     */
    public void setLoginActivity(LoginActivity loginActivity) {
        // set this loginActivity to loginActivity
        this.loginActivity = loginActivity;
    }
}