package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Register1Fragment extends Fragment {

    //Controls
    View rootView;
    Button buttonLeft, buttonRight;
    EditText emailEditText, passwordEditText, confirmPasswordEditText;

    //Activities
    LoginActivity loginActivity;

    //Fragments


    //Variables
    boolean isValuesEntered = false;
    boolean isEmailValid = false;
    boolean passwordEqualsConfirmPassword = false;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_register1,
                container, false);

        initializeViews(rootView);
        registerListeners();
        loginActivity.setToolbarTitle(R.string.toolbar_title_register1);

        return rootView;
    }

    private void initializeViews(View rootView) {

        emailEditText = (EditText) rootView.findViewById(R.id.emailEditText);
        passwordEditText = (EditText) rootView.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) rootView.findViewById(R.id.confirmPasswordEditText);
        buttonLeft = (Button) rootView.findViewById(R.id.buttonLeft);
        buttonRight = (Button) rootView.findViewById(R.id.buttonRight);
    }

    private void registerListeners() {


        emailEditText.addTextChangedListener(editTextTextWatcher);
        passwordEditText.addTextChangedListener(editTextTextWatcher);
        confirmPasswordEditText.addTextChangedListener(editTextTextWatcher);
        buttonLeft.setOnClickListener(buttonLeftOnClickListener);
        buttonRight.setOnClickListener(buttonRightOnClickListener);
    }

    TextWatcher editTextTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isValuesEntered();
        }
    };

    OnClickListener buttonLeftOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // this will be the "already have an account? Sign in" button
            loginActivity.helperMethods.replaceFragment(R.id.container, loginActivity.loginFragment, loginActivity.getResources().getString(R.string.toolbar_title_login), loginActivity);
        }
    };

    OnClickListener buttonRightOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isValuesEntered) {
                loginActivity.showDialog("Create Account Failed", "All fields require a value.", null, null, "OK", R.drawable.ic_check_grey600_24dp, "create_account_failed_values");
            } else {
                validateForm();
            }
        }
    };

    private void validateForm() {
        // check if email exists- cant do now as not doing db on server
        if (!isEmailValid()){
            loginActivity.showDialog("Create Account Failed", "You need to enter a valid email address.", null, null, "OK", R.drawable.ic_check_grey600_24dp, "create_account_failed_email");
        } else {
            if (!passwordEqualsConfirmPassword()) {
                loginActivity.showDialog("Create Account Failed", "Password and Confirm Password values don't match. Please try again.", null, null, "OK", R.drawable.ic_check_grey600_24dp, "create_account_failed_values_match");
                passwordEditText.setText("");
                confirmPasswordEditText.setText("");
            } else {
                loginActivity.register2Fragment.setEmailPasswordValues(emailEditText.getText().toString(), passwordEditText.getText().toString());
                loginActivity.helperMethods.replaceFragment(R.id.container, loginActivity.register2Fragment, loginActivity.getResources().getString(R.string.toolbar_title_register2), loginActivity);
            }
        }

    }

    private boolean isValuesEntered() {
        if (!loginActivity.helperMethods.isEmpty(emailEditText) &&
                !loginActivity.helperMethods.isEmpty(passwordEditText) &&
                !loginActivity.helperMethods.isEmpty(confirmPasswordEditText)) {
            isValuesEntered = true;
        } else
            isValuesEntered = false;
        return isValuesEntered;
    }

    private boolean isEmailValid() {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText()).matches()) {
            isEmailValid = true;
        } else
            isEmailValid = false;
        return isEmailValid;
    }

    private boolean passwordEqualsConfirmPassword() {
        if (passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())) {
            passwordEqualsConfirmPassword = true;
        } else
            passwordEqualsConfirmPassword = false;
        return passwordEqualsConfirmPassword;
    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }
}