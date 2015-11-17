package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.joda.time.LocalDate;

public class UpdateProfileFragment extends Fragment {
//TODO remove textwatches from password and confirm password, look into ssn, load genderSpinner, locationid doesnt set in db

    //Controls
    View rootView;
    Spinner genderSpinner, locationsSpinner;

    //Activities
    MainActivity mainActivity;
    LoginActivity loginActivity;
    //Fragments

    //Variables
    User user = new User();
    Location defaultLocation;
    EditText firstNameEditText, lastNameEditText, dobMonthEditText, dobDayEditText, dobYearEditText,
            ssnEditText, newPasswordEditText, confirmNewPasswordEditText;
    Button rightButton, leftButton;
    //Boolean valuesEntered = false;
    Boolean genderSpinnersHasValue = false;
    Boolean locationsSpinnersHasValue = false;
    Boolean isBirthdayCorrect = false;
    Boolean isSsn = false;
    Boolean passwordEqualsConfirmPassword = false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_update_profile,
                container, false);

        initializeViews(rootView);
        loadUserInformation();
        registerListeners();
        return rootView;
    }

    private void initializeViews(View rootView) {
        loginActivity.setToolbarTitle(R.string.toolbar_title_update_profile);

        firstNameEditText = (EditText) rootView.findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) rootView.findViewById(R.id.lastNameEditText);
        dobMonthEditText = (EditText) rootView.findViewById(R.id.dobMonthEditText);
        dobDayEditText = (EditText) rootView.findViewById(R.id.dobDayEditText);
        dobYearEditText = (EditText) rootView.findViewById(R.id.dobYearEditText);
        ssnEditText = (EditText) rootView.findViewById(R.id.ssnEditText);
        newPasswordEditText = (EditText) rootView.findViewById(R.id.newPasswordEditText);
        confirmNewPasswordEditText = (EditText) rootView.findViewById(R.id.confirmNewPasswordEditText);
        genderSpinner = (Spinner) rootView.findViewById(R.id.spinnerGender);
        locationsSpinner = (Spinner) rootView.findViewById(R.id.spinnerDefaultLocation);
        rightButton = (Button) rootView.findViewById(R.id.rightButton);
        leftButton = (Button) rootView.findViewById(R.id.leftButton);
        bindSpinnerData();

    }

    private void bindSpinnerData() {
        // Create an adapter from the string array resource and use android's
        // inbuilt layout file simple_spinner_item that represents the default spinner in the UI
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(getActivity()
                .getApplicationContext(), R.array.gender_array, R.layout.spinner_dropdown_item);
        // Set the layout to use for each dropdown item
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        genderSpinner.setAdapter(genderAdapter);

        loginActivity.helperMethods.addDataToSpinnerFromLoginActivity
                (loginActivity.locationsNameArrayList, locationsSpinner);
    }

    //set user information in views
    private void loadUserInformation() {
        firstNameEditText.setText(loginActivity.user.firstName);
        lastNameEditText.setText(loginActivity.user.lastName);
        int month = loginActivity.user.dob.getMonthOfYear();
        dobMonthEditText.setText(month + "");
        int day = loginActivity.user.dob.getDayOfMonth();
        dobDayEditText.setText(day + "");
        int year = loginActivity.user.dob.getYear();
        dobYearEditText.setText(year + "");
        String ssn = loginActivity.user.ssn;
        ssnEditText.setText(ssn.substring(ssn.length() - 4));
        int gender = loginActivity.user.gender.ordinal()+1;
        Toast.makeText(loginActivity.getApplicationContext(),gender+"",Toast.LENGTH_LONG).show();
        genderSpinner.setSelection(gender);
        locationsSpinner.setSelection(loginActivity.helperMethods
                .setLocationSpinnerSelection());
        newPasswordEditText.setText(loginActivity.user.password);
        confirmNewPasswordEditText.setText(loginActivity.user.password);
    }

    private void registerListeners() {
        genderSpinner.setOnItemSelectedListener(genderSpinnerOnItemSelectedListener);
        locationsSpinner.setOnItemSelectedListener(locationsSpinnerOnItemSelectedListener);
        rightButton.setOnClickListener(rightButtonListener);
        leftButton.setOnClickListener(leftButtonListener);
//        firstNameEditText.addTextChangedListener(textWatcher);
//        lastNameEditText.addTextChangedListener(textWatcher);
//        dobDayEditText.addTextChangedListener(textWatcher);
//        dobMonthEditText.addTextChangedListener(textWatcher);
//        dobYearEditText.addTextChangedListener(textWatcher);
//        ssnEditText.addTextChangedListener(textWatcher);
        //newPasswordEditText.addTextChangedListener(textWatcher);
        //confirmNewPasswordEditText.addTextChangedListener(textWatcher);
    }

//    private TextWatcher textWatcher = new TextWatcher() {
//
//        public void afterTextChanged(Editable s) {
//        }
//
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            controlsHaveValues();
//        }
//
//    };

    private Boolean controlsHaveValues() {
        if (!loginActivity.helperMethods.isEmpty(firstNameEditText) &&
                !loginActivity.helperMethods.isEmpty(lastNameEditText) &&
                !loginActivity.helperMethods.isEmpty(dobDayEditText) &&
                !loginActivity.helperMethods.isEmpty(dobMonthEditText) &&
                !loginActivity.helperMethods.isEmpty(dobYearEditText) &&
                !loginActivity.helperMethods.isEmpty(newPasswordEditText) &&
                !loginActivity.helperMethods.isEmpty(confirmNewPasswordEditText) &&
                !loginActivity.helperMethods.isEmpty(ssnEditText)) {
            return true;
        } else
            return false;
    }

    private boolean passwordEqualsConfirmPassword() {
        if (newPasswordEditText.getText().toString()
                .equals(confirmNewPasswordEditText.getText().toString())) {
            passwordEqualsConfirmPassword = true;
        } else
            passwordEqualsConfirmPassword = false;
        return passwordEqualsConfirmPassword;
    }

    View.OnClickListener rightButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!controlsHaveValues() || !locationsSpinnersHasValue || !genderSpinnersHasValue) {
                //|| passwordEqualsConfirmPassword() ) {
                loginActivity.showDialog("Account Update Failed", "All fields require a value.",
                        "OK", null, null, R.drawable.ic_alert_grey600_24dp,
                        "registration_failed_missing_fields");
            } else {
                validateForm();

            }
        }
    };

    View.OnClickListener leftButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            loginActivity.helperMethods.replaceFragment(R.id.container,
                    loginActivity.register1Fragment, loginActivity.getResources()
                            .getString(R.string.toolbar_title_register1), loginActivity);
        }
    };

    AdapterView.OnItemSelectedListener genderSpinnerOnItemSelectedListener =
            new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0)
                        genderSpinnersHasValue = true;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            };

    AdapterView.OnItemSelectedListener locationsSpinnerOnItemSelectedListener =
            new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0)
                        locationsSpinnersHasValue = true;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            };

    private void validateForm() {
        if (!isBirthdayCorrect()) {
            loginActivity.showDialog("Create Account Failed",
                    "Enter Date of birth in MM/DD/YYYY format.",
                    "OK", "CANCEL", null, R.drawable.ic_alert_grey600_24dp,
                    "registration_failed_birthday_incorrect");

        } else if (!isSsn()) {
            loginActivity.showDialog("Create Account Failed", "Enter Last 4 digits of SSN.",
                    "OK", "CANCEL", null, R.drawable.ic_alert_grey600_24dp,
                    "registration_failed_ssn_incorrect");
            ssnEditText.setText("");
        } else if (!passwordEqualsConfirmPassword()) {
            loginActivity.showDialog("Create Account Failed",
                    "Password and Confirm Password values don't match. Please try again.", null,
                    null, "OK", R.drawable.ic_alert_grey600_24dp, "create_account_failed_values_match");
            newPasswordEditText.setText("");
            confirmNewPasswordEditText.setText("");
        } else {
            saveTestUser();
        }
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
        //testUser.setLocationId(loginActivity.locationsArrayList, testUser);
        testUser.email = loginActivity.user.email;
        testUser.setPassword(newPasswordEditText.getText().toString());
        testUser.setLocationId(loginActivity.locationsArrayList, testUser);
        Toast.makeText(loginActivity.getApplicationContext(), "first name: " + testUser.firstName +
                        " last name: " + testUser.lastName + " id: " + testUser.id + " email: " +
                        testUser.email + "password: " + testUser.password + " dob: " + testUser.dob +
                        " gender: " + testUser.gender + " location id: " + testUser.locationId,
                Toast.LENGTH_LONG).show();
        //calling async task and sending testUser

        loginActivity.databaseOperations.updateUser(testUser);
    }

    private Boolean isBirthdayCorrect() {

        LocalDate currentDate = LocalDate.now();

        if (Integer.parseInt(dobDayEditText.getText().toString()) > 31 ||
                Integer.parseInt(dobMonthEditText.getText().toString()) > 12 ||
                Integer.parseInt(dobMonthEditText.getText().toString()) > 12 ||
                Integer.parseInt(dobYearEditText.getText().toString()) <
                        (currentDate.getYear() - 100) ||
                Integer.parseInt(dobYearEditText.getText().toString()) > (currentDate.getYear())) {

            isBirthdayCorrect = false;
        } else {
            isBirthdayCorrect = true;
        }
        return isBirthdayCorrect;
    }

    private Boolean isSsn() {
        if (ssnEditText.getText().toString().length() < 4) {
            isSsn = false;
        } else {
            isSsn = true;
        }
        return isSsn;
    }


    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }
}