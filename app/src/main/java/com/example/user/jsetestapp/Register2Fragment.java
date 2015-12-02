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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Register2Fragment extends Fragment {

    // Declare Controls
    Spinner genderSpinner, locationsSpinner;
    EditText firstNameEditText, lastNameEditText, dobMonthEditText,
            dobDayEditText, dobYearEditText, ssnEditText;
    Button rightButton, leftButton;
    AsyncTask taskNewUser;

    // Declare Activities
    LoginActivity loginActivity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register2,
                container, false);

        initializeViews(rootView);
        registerListeners(rootView);

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (loginActivity.user.getId()  != 0){

            // launch activity with main activity intent
            Util.launchActivity(loginActivity.getLaunchMainActivityIntent("create_account"));
        }
    }

    private void initializeViews(View rootView) {
        Util.setToolbarTitle(R.string.toolbar_title_register2, loginActivity.toolbar);
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

        loginActivity.helperMethods.addDataToSpinner(
                loginActivity.helperMethods.editLocationsNameArrayList(), locationsSpinner,
                "locationNameArrayList", Util.getContext());

    }

    private void registerListeners(View rootView) {
        rightButton.setOnClickListener(rightButtonListener);
        leftButton.setOnClickListener(leftButtonListener);
        dobDayEditText.addTextChangedListener(textWatcher);
        dobMonthEditText.addTextChangedListener(textWatcher);
        dobYearEditText.addTextChangedListener(textWatcher);
        ssnEditText.addTextChangedListener(textWatcher);

        // set onTouchListener for all non ediText controls to hide the soft keyboard
        HelperMethods.registerTouchListenerForNonEditText(rootView.findViewById(R.id.rootLayout));
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
                    HelperMethods.hideSoftKeyboard();
            }

        }


        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    };

    private Boolean controlsHaveValues() {
        return !loginActivity.helperMethods.isEmpty(firstNameEditText) &&
                !loginActivity.helperMethods.isEmpty(lastNameEditText) &&
                !loginActivity.helperMethods.isEmpty(dobDayEditText) &&
                !loginActivity.helperMethods.isEmpty(dobMonthEditText) &&
                !loginActivity.helperMethods.isEmpty(dobYearEditText) &&
                !loginActivity.helperMethods.isEmpty(ssnEditText) &&
                genderSpinner.getSelectedItemPosition() != 0 &&
                locationsSpinner.getSelectedItemPosition() != 0;
    }

    OnClickListener rightButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!controlsHaveValues()) {

                Util.showDialog(HelperMethods.getDialogFragmentBundle(
                        getString(R.string.d_registration_failed_missing_fields)));

            } else {
                validateForm();
            }
        }
    };

    OnClickListener leftButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            loginActivity.getFragmentManager().popBackStack();
        }
    };

    private void validateForm() {
        if (!isBirthdayCorrect()) {

            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_registration_failed_birthday_incorrect)));
        }
        else if (!isSsn()) {

            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_registration_failed_ssn_incorrect)));

            ssnEditText.setText("");
        }

        else {
            saveUser();

            // check for Internet status and set true/false
            if (HelperMethods.checkInternetConnection(loginActivity.getApplicationContext())) {
                taskNewUser = loginActivity.databaseOperations.newUser(loginActivity.user);
            } else {

                Util.showDialog(HelperMethods.getDialogFragmentBundle(
                        getString(R.string.d_no_internet_connection)));
            }

        }

    }

    public Boolean isBirthdayCorrect(){
        return Util.isBirthdayCorrect(dobYearEditText.getText().toString(),
                dobMonthEditText.getText().toString(), dobDayEditText.getText().toString());
    }

    public Boolean isSsn(){
        return Util.isLength(ssnEditText.getText().toString(), 4);
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