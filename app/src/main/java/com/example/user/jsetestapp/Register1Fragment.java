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
    boolean userEnteredEmailAndPasswordAndConfirmPassword = false;
    boolean passwordConfirmPasswordMatch = false;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_register1,
                container, false);

        initializeViews(rootView);
        registerListeners();
        loginActivity.setToolbarTitle(R.string.toolbar_title_register1);
        //buttonRight.setEnabled(false);

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
            if (!loginActivity.helperMethods.isEmpty(emailEditText) &&
                    !loginActivity.helperMethods.isEmpty(passwordEditText) &&
                    !loginActivity.helperMethods.isEmpty(confirmPasswordEditText)) {
                userEnteredEmailAndPasswordAndConfirmPassword = true;
            }

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
            if (!userEnteredEmailAndPasswordAndConfirmPassword) {
                // ToDo show Dialog Fragment
                // Title: ????
                // Content: "All fields require values"
                // Neutral Action: 'OK' will cancel dialog
            } else {
                validateForm();
            }
        }
    };

    private void validateForm() {
        // check if email exists- cant do now as not doing db on server
        if (!passwordConfirmPasswordMatch) {
            // ToDo show Dialog Fragment
            // Title: Create Account Failed
            // Content: "Password and Confirm Password don't match. Please try again."
            // Neutral Action: 'OK' will clear passwordEditText and confirmPasswordEditText and close dialog
        } else {
            // ToDo save entered email and password to SP
            // ToDo go to 'SIGN UP 2' screen
        }

    }


    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }
}