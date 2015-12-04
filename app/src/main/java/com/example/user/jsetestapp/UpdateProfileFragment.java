package com.example.user.jsetestapp;

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
        HelperMethods.registerTouchListenerForNonEditText(rootView.findViewById(R.id.rootLayout));
        registerListeners();

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_update_profile, loginActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();

        //
        loadUserInformation(loginActivity.user);
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
     * Function to bind get User info and load in on the page
     */
    private void loadUserInformation(User user) {
        firstNameEditText.setText(user.firstName);
        lastNameEditText.setText(user.lastName);
        int month = user.dob.getMonthOfYear();
        setDayAndMonthEditTexts(month, dobMonthEditText);
        int day = user.dob.getDayOfMonth();
        setDayAndMonthEditTexts(day, dobDayEditText);
        int year = user.dob.getYear();
        dobYearEditText.setText(year + "");
        String ssn = user.ssn;
        ssnEditText.setText(ssn.substring(ssn.length() - 4));
        int gender = user.gender.ordinal() ;
        genderSpinner.setSelection(gender);
        locationsSpinner.setSelection(loginActivity.helperMethods
                .setLocationSpinnerSelection()-1);
        newPasswordEditText.setText(user.password);
        confirmNewPasswordEditText.setText(user.password);
    }

    public void setDayAndMonthEditTexts(int value, EditText editText) {
        if (value < 10)
            editText.setText("0" + value);
        else
            editText.setText(value + "");
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

    private Boolean controlsHaveValues() {
        return !loginActivity.helperMethods.isEmpty(firstNameEditText) &&
                !loginActivity.helperMethods.isEmpty(lastNameEditText) &&
                !loginActivity.helperMethods.isEmpty(dobDayEditText) &&
                !loginActivity.helperMethods.isEmpty(dobMonthEditText) &&
                !loginActivity.helperMethods.isEmpty(dobYearEditText) &&
                !loginActivity.helperMethods.isEmpty(newPasswordEditText) &&
                !loginActivity.helperMethods.isEmpty(confirmNewPasswordEditText) &&
                !loginActivity.helperMethods.isEmpty(ssnEditText);
    }



    View.OnClickListener rightButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!controlsHaveValues()) {
                Util.showDialog(HelperMethods.getDialogFragmentBundle(
                        getString(R.string.d_update_account_failed_missing_fields)));
            } else {
                validateForm();
            }
        }
    };

    View.OnClickListener leftButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //this listener will be gone
        }
    };

    private void validateForm() {
        if (!isBirthdayCorrect()) {

            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_update_account_failed_birthday_incorrect)));
        }
        else if (!isSsn()) {

            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_update_account_failed_ssn_incorrect)));

            ssnEditText.setText("");

        } else if (!passwordEqualsConfirmPassword()) {

            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_update_account_failed_values_not_match)));


            newPasswordEditText.setText("");
            confirmNewPasswordEditText.setText("");
        } else {
            saveTestUser();
        }
    }

    public Boolean isBirthdayCorrect(){
        return Util.isBirthdayCorrect(dobYearEditText.getText().toString(),
                dobMonthEditText.getText().toString(), dobDayEditText.getText().toString());
    }

    public Boolean isSsn(){
        return Util.isLength(ssnEditText.getText().toString(), 4);
    }

    public Boolean passwordEqualsConfirmPassword(){
        return Util.compareTwoStrings(newPasswordEditText.getText().toString(),
                confirmNewPasswordEditText.getText().toString());
    }

    public void saveTestUser() {
        User testUser = new User();
        testUser.id = loginActivity.user.id;
        testUser.setFirstName(firstNameEditText.getText().toString());
        testUser.setLastName(lastNameEditText.getText().toString());
        testUser.setSsn("XXX-XX-" + ssnEditText.getText().toString());
        testUser.setGender(genderSpinner.getSelectedItem().toString());
        testUser.setDob(dobYearEditText.getText().toString(), dobMonthEditText.getText().toString(),
                dobDayEditText.getText().toString());
        testUser.setDefaultLocation(locationsSpinner.getSelectedItem().toString());
        testUser.email = loginActivity.user.email;
        testUser.setPassword(newPasswordEditText.getText().toString());
        testUser.setLocationId(loginActivity.locationsArrayList, testUser);

        // calling async task and sending testUser

        // check for Internet status and set true/false
        if (HelperMethods.checkInternetConnection()) {
            taskUpdateUser = loginActivity.databaseOperations.updateUser(testUser);
        } else {

            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_no_internet_connection)));

        }

    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }

}