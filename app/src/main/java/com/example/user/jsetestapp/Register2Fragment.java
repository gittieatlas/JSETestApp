package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.joda.time.LocalDate;

public class Register2Fragment extends Fragment {

    //Controls
    View rootView;
    Spinner genderSpinner, locationsSpinner;
    EditText firstNameEditText, lastNameEditText, dobMonthEditText,
            dobDayEditText, dobYearEditText, ssnEditText;
    Button rightButton, leftButton;

    //Activities
    LoginActivity loginActivity;

    //Fragments


    //Variables
    Boolean isValuesEntered = false;
    Boolean genderSpinnersHasValue = false;
    Boolean locationsSpinnersHasValue = false;
    Boolean isBirthdayCorrect = false;
    Boolean isSsn = false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_register2,
                container, false);

        initializeViews(rootView);
        registerListeners();
        return rootView;
    }

    private void initializeViews(View rootView) {
        loginActivity.setToolbarTitle(R.string.toolbar_title_register2);
        firstNameEditText = (EditText) rootView.findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) rootView.findViewById(R.id.lastNameEditText);
        dobMonthEditText = (EditText) rootView.findViewById(R.id.dobMonthEditText);
        dobDayEditText = (EditText) rootView.findViewById(R.id.dobDayEditText);
        dobYearEditText = (EditText) rootView.findViewById(R.id.dobYearEditText);
        ssnEditText = (EditText) rootView.findViewById(R.id.ssnEditText);
        genderSpinner = (Spinner) rootView.findViewById(R.id.spinnerGender);
        locationsSpinner = (Spinner) rootView.findViewById(R.id.spinnerDefaultLocation);
        rightButton = (Button) rootView.findViewById(R.id.rightButton);
        leftButton = (Button) rootView.findViewById(R.id.leftButton);
        bindSpinnerData();
    }

    private void bindSpinnerData() {
        // Create an adapter from the string array resource and use android's inbuilt layout file
        // simple_spinner_item that represents the default spinner in the UI
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(
                getActivity().getApplicationContext(),
                R.array.gender_array,
                R.layout.spinner_dropdown_item);
        // Set the layout to use for each dropdown item
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        genderSpinner.setAdapter(genderAdapter);

        loginActivity.helperMethods.addDataToSpinnerFromLoginActivity(
                loginActivity.locationsNameArrayList, locationsSpinner);
    }

    private void registerListeners() {
        genderSpinner.setOnItemSelectedListener(genderSpinnerOnItemSelectedListener);
        locationsSpinner.setOnItemSelectedListener(locationsSpinnerOnItemSelectedListener);
        rightButton.setOnClickListener(rightButtonListener);
        leftButton.setOnClickListener(leftButtonListener);
        firstNameEditText.addTextChangedListener(textWatcher);
        lastNameEditText.addTextChangedListener(textWatcher);
        dobDayEditText.addTextChangedListener(textWatcher);
        dobMonthEditText.addTextChangedListener(textWatcher);
        dobYearEditText.addTextChangedListener(textWatcher);
        ssnEditText.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            controlsHaveValues();
        }

    };

    private Boolean controlsHaveValues() {
        if (!loginActivity.helperMethods.isEmpty(firstNameEditText) &&
                !loginActivity.helperMethods.isEmpty(lastNameEditText) &&
                !loginActivity.helperMethods.isEmpty(dobDayEditText) &&
                !loginActivity.helperMethods.isEmpty(dobMonthEditText) &&
                !loginActivity.helperMethods.isEmpty(dobYearEditText) &&
                !loginActivity.helperMethods.isEmpty(ssnEditText)) {
            isValuesEntered = true;
        } else {
            isValuesEntered = false;
        }
        return isValuesEntered;
    }

    OnClickListener rightButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!isValuesEntered || !locationsSpinnersHasValue || !genderSpinnersHasValue) {
                loginActivity.showDialog("Create Account Failed", "All fields require a value.",
                        "OK", "CANCEL", null, R.drawable.ic_alert_grey600_24dp, "registration_failed_missing_fields");
            } else {
                validateForm();
            }
        }
    };

    OnClickListener leftButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            loginActivity.helperMethods.replaceFragment(R.id.container, loginActivity.register1Fragment, loginActivity.getResources().getString(R.string.toolbar_title_register1), loginActivity);
        }
    };

    AdapterView.OnItemSelectedListener genderSpinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0)
                genderSpinnersHasValue = true;

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };

    AdapterView.OnItemSelectedListener locationsSpinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {

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
            loginActivity.showDialog(getString(R.string.d_create_account_failed),
                    "Enter Date of birth in MM/DD/YYYY format.",
                    "OK", "CANCEL", null, R.drawable.ic_alert_grey600_24dp,
                    "registration_failed_birthday_incorrect");

        } else if (!isSsn()) {
            loginActivity.showDialog(getString(R.string.d_create_account_failed),
                    "Enter Last 4 digits of SSN.",
                    "OK", "CANCEL", null, R.drawable.ic_alert_grey600_24dp,
                    "registration_failed_ssn_incorrect");
            ssnEditText.setText("");
        } else {
            saveUser();
            loginActivity.databaseOperations.newUser(loginActivity.user);

        }

    }

    private Boolean isBirthdayCorrect() {

        LocalDate currentDate = LocalDate.now();

        if (Integer.parseInt(dobDayEditText.getText().toString()) > 31 ||
                Integer.parseInt(dobMonthEditText.getText().toString()) > 12 ||
                Integer.parseInt(dobMonthEditText.getText().toString()) > 12 ||
                Integer.parseInt(dobYearEditText.getText().toString()) < (currentDate.getYear() - 100) ||
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

    private void saveUser() {
        loginActivity.user.setFirstName(firstNameEditText.getText().toString());
        loginActivity.user.setLastName(lastNameEditText.getText().toString());
        loginActivity.user.setSsn("XXX-XX-" + ssnEditText.getText().toString());
        loginActivity.user.setGender(genderSpinner.getSelectedItem().toString());
        loginActivity.user.setDefaultLocation(locationsSpinner.getSelectedItem().toString());
        loginActivity.user.setDob(dobYearEditText.getText().toString(),
                dobMonthEditText.getText().toString(), dobDayEditText.getText().toString());
        loginActivity.user.setLocationId(loginActivity.locationsArrayList, loginActivity.user);
    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }

}