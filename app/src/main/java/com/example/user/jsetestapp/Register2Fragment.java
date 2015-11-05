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
import android.widget.Toast;

public class Register2Fragment extends Fragment {

    //Controls
    View rootView;
    Spinner genderSpinner, locationsSpinner;
    EditText firstNameEditText, lastNameEditText, dobMonthEditText, dobDayEditText, dobYearEditText, ssnEditText;
    Button rightButton, leftButton;
    //Activities
    LoginActivity loginActivity;

    //Fragments


    //Variables
    Boolean textViewsNotEmpty = false;
    Boolean genderSpinnersNotEmpty = false;
    Boolean locationsSpinnersNotEmpty = false;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_register2,
                container, false);

        initializeViews(rootView);
        registerListeners();
        checkIfHasValue();
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
        // Create an adapter from the string array resource and use android's inbuilt layout file simple_spinner_item that represents the default spinner in the UI
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.gender_array, R.layout.spinner_dropdown_item);
        // Set the layout to use for each dropdown item
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_single);
        genderSpinner.setAdapter(genderAdapter);

        loginActivity.helperMethods.addDataToSpinner2(loginActivity.locationsNameArrayList, locationsSpinner);
    }

    private void registerListeners() {
        genderSpinner.setOnItemSelectedListener(genderSpinnerOnItemSelectedListener);
        locationsSpinner.setOnItemSelectedListener(locationsSpinnerOnItemSelectedListener);
        rightButton.setOnClickListener(rightButtonListener);
        leftButton.setOnClickListener(leftButtonListener);
    }

    private void checkIfHasValue(){
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
            if (!loginActivity.helperMethods.isEmpty(firstNameEditText) &&
                    !loginActivity.helperMethods.isEmpty(lastNameEditText) &&
                    !loginActivity.helperMethods.isEmpty(dobDayEditText) &&
                    !loginActivity.helperMethods.isEmpty(dobMonthEditText) &&
                    !loginActivity.helperMethods.isEmpty(dobYearEditText) &&
                    !loginActivity.helperMethods.isEmpty(ssnEditText) &&
                    genderSpinnersNotEmpty && locationsSpinnersNotEmpty) {
                textViewsNotEmpty = true;
            }

            Toast.makeText(loginActivity.getApplicationContext(), textViewsNotEmpty.toString(), Toast.LENGTH_LONG).show();
        }

    };

    OnClickListener rightButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //call validateForm();
            loginActivity.showDialog("JSE Office", null, "CALL", "CANCEL", null, R.drawable.ic_calendar_clock_grey600_24dp, "from_login_activity");
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
               // Toast.makeText(loginActivity.getApplicationContext(), "Selected", Toast.LENGTH_LONG).show();
                genderSpinnersNotEmpty=true;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };

    AdapterView.OnItemSelectedListener locationsSpinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0)

                locationsSpinnersNotEmpty=true;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }

}