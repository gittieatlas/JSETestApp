package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UpdateProfileFragment extends Fragment {

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
    Boolean isSsn = false;
    Boolean passwordEqualsConfirmPassword = false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_update_profile,
                container, false);

        initializeViews(rootView);
        loginActivity.helperMethods.setupUI(rootView.findViewById(R.id.rootLayout));
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

        // Todo remove index 0 from both spinners
        // Create an adapter from the string array resource and use android's
        // inbuilt layout file simple_spinner_item that represents the default spinner in the UI
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(getActivity()
                .getApplicationContext(), R.array.gender_array, R.layout.spinner_dropdown_item);
        // Set the layout to use for each dropdown item
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        genderSpinner.setAdapter(genderAdapter);

        loginActivity.helperMethods.addDataToSpinner
                (loginActivity.helperMethods.editLocationsNameArrayList(),
                        locationsSpinner, "locationsNameArray", loginActivity.getContext());
    }

    //set user information in views
    private void loadUserInformation() {
        firstNameEditText.setText(loginActivity.user.firstName);
        lastNameEditText.setText(loginActivity.user.lastName);
        int month = loginActivity.user.dob.getMonthOfYear();
        setDayAndMonthEditTexts(month, dobMonthEditText);
        int day = loginActivity.user.dob.getDayOfMonth();
        setDayAndMonthEditTexts(day, dobDayEditText);
        int year = loginActivity.user.dob.getYear();
        dobYearEditText.setText(year + "");
        String ssn = loginActivity.user.ssn;
        ssnEditText.setText(ssn.substring(ssn.length() - 4));
        int gender = loginActivity.user.gender.ordinal() + 1;
        genderSpinner.setSelection(gender);
        locationsSpinner.setSelection(loginActivity.helperMethods
                .setLocationSpinnerSelection());
        newPasswordEditText.setText(loginActivity.user.password);
        confirmNewPasswordEditText.setText(loginActivity.user.password);
    }

    public void setDayAndMonthEditTexts(int value, EditText editText) {
        if (value < 10)
            editText.setText("0" + value);
        else
            editText.setText(value + "");
    }

    private void registerListeners() {
        rightButton.setOnClickListener(rightButtonListener);
        leftButton.setOnClickListener(leftButtonListener);
        dobDayEditText.addTextChangedListener(textWatcher);
        dobMonthEditText.addTextChangedListener(textWatcher);
        dobYearEditText.addTextChangedListener(textWatcher);
        ssnEditText.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            if (s == dobMonthEditText.getEditableText()) {
                if (dobMonthEditText.getText().toString().length() == 2)
                    dobDayEditText.requestFocus();
            }
            if (s == dobDayEditText.getEditableText()) {
                if (dobDayEditText.getText().toString().length() == 2)
                    dobYearEditText.requestFocus();
            }
            if (s == dobYearEditText.getEditableText()) {
                if (dobYearEditText.getText().toString().length() == 4)
                    ssnEditText.requestFocus();
            }
            if (s == ssnEditText.getEditableText()) {
                if (ssnEditText.getText().toString().length() == 4)
                    loginActivity.helperMethods.hideSoftKeyboard(loginActivity);
            }


        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            //controlsHaveValues();

        }

    };

    private Boolean controlsHaveValues() {
        if (!loginActivity.helperMethods.isEmpty(firstNameEditText) &&
                !loginActivity.helperMethods.isEmpty(lastNameEditText) &&
                !loginActivity.helperMethods.isEmpty(dobDayEditText) &&
                !loginActivity.helperMethods.isEmpty(dobMonthEditText) &&
                !loginActivity.helperMethods.isEmpty(dobYearEditText) &&
                !loginActivity.helperMethods.isEmpty(newPasswordEditText) &&
                !loginActivity.helperMethods.isEmpty(confirmNewPasswordEditText) &&
                !loginActivity.helperMethods.isEmpty(ssnEditText) &&
                genderSpinner.getSelectedItemPosition() != 0 &&
                locationsSpinner.getSelectedItemPosition() != 0) {
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
            if (!controlsHaveValues()) {
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

            //this listener will be gone
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
//        Toast.makeText(loginActivity.getApplicationContext(), "first name: " + testUser.firstName +
//                        " last name: " + testUser.lastName + " id: " + testUser.id + " email: " +
//                        testUser.email + "password: " + testUser.password + " dob: " + testUser.dob +
//                        " gender: " + testUser.gender + " location id: " + testUser.locationId,
//                Toast.LENGTH_LONG).show();
        //calling async task and sending testUser

        loginActivity.databaseOperations.updateUser(testUser);
    }


    //checking if DOB is valid
    //test comment
    private Boolean isBirthdayCorrect() {

        User user = new User();
        try {
            user.setDob(dobYearEditText.getText().toString(),
                    dobMonthEditText.getText().toString(), dobDayEditText.getText().toString());
            return true;

        } catch (Exception ex) {
            return false;
        }

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