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

public class Register2Fragment extends Fragment {

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

    private TextWatcher textWatcher = new TextWatcher() {

        public void afterTextChanged(Editable editable) {
            // if editTexts have required length amount of text, move focus to next editText
            Util.removeFocusFromEditText(editable, dobMonthEditText, dobDayEditText, 2);
            Util.removeFocusFromEditText(editable, dobDayEditText, dobYearEditText, 2);
            Util.removeFocusFromEditText(editable, dobYearEditText, ssnEditText, 4);
            Util.removeFocusFromEditText(editable, ssnEditText, null, 4);
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
        } else if (!isSsn()) {

            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_registration_failed_ssn_incorrect)));

            ssnEditText.setText("");
        } else {
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

    public Boolean isBirthdayCorrect() {
        return Util.isBirthdayCorrect(dobYearEditText.getText().toString(),
                dobMonthEditText.getText().toString(), dobDayEditText.getText().toString());
    }

    public Boolean isSsn() {
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