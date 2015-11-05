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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    //Controls
    View rootView;
    RelativeLayout rootLayout;
    Button buttonLeft, buttonRight;
    TextView forgotPasswordTextView;
    EditText emailEditText, passwordEditText;

    //Activities
    MainActivity mainActivity;
    LoginActivity loginActivity;

    //Fragments


    //Variables
    boolean userEnteredEmailAndPassword = false;
    String sharedPrefEmail = "";
    boolean emailAndSPEmailMatch = false;
    boolean emailPasswordMatch = false;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_login,
                container, false);

        initializeViews(rootView);
        registerListeners();
        //buttonRight.setEnabled(false);
        loginActivity.setToolbarTitle(R.string.toolbar_title_login);

        return rootView;
    }

    private void initializeViews(View rootView) {

        rootLayout = (RelativeLayout) rootView.findViewById(R.id.rootLayout);
        emailEditText = (EditText) rootView.findViewById(R.id.emailEditText);
        passwordEditText = (EditText) rootView.findViewById(R.id.passwordEditText);
        buttonLeft = (Button) rootView.findViewById(R.id.buttonLeft);
        buttonRight = (Button) rootView.findViewById(R.id.buttonRight);
        forgotPasswordTextView = (TextView) rootView.findViewById(R.id.forgotPasswordTextView);
    }

    private void registerListeners() {

        emailEditText.addTextChangedListener(editTextTextWatcher);
        passwordEditText.addTextChangedListener(editTextTextWatcher);
        forgotPasswordTextView.setOnClickListener(forgotPasswordEditTextOnClickListener);
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
                    !loginActivity.helperMethods.isEmpty(passwordEditText)) {
                userEnteredEmailAndPassword = true;
                Toast.makeText(loginActivity.getApplicationContext(),
                        "userEnteredEmailAndPassword = true", Toast.LENGTH_LONG).show();
            }
        }
    };

    OnClickListener forgotPasswordEditTextOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            // ToDo show Dialog Fragment
            // Title: Send me my password
            // Content: 'Email' label and EditText inputType=textEmailAddress
            // Positive Action: 'SEND' will be disabled (gray) onTextChanged()
            //      when emailEditText has valid email address, button will be enabled (teal)
            //      onClick = get password from LDB that matches to emailEditText and send email to emailEditText. Close dialog
            // Negative Action: 'CANCEL' onClick: close dialog
        }
    };

    OnClickListener buttonLeftOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            loginActivity.helperMethods.replaceFragment(R.id.container, loginActivity.register1Fragment, loginActivity.getResources().getString(R.string.toolbar_title_register1), loginActivity);
        }
    };

    OnClickListener buttonRightOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!userEnteredEmailAndPassword) {
                // ToDo show Dialog Fragment
                // Title: ????
                // Content: '"All fields require values"
                // Neutral Action: 'OK' will cancel dialog
            } else {
                Toast.makeText(loginActivity.getApplicationContext(), "validating form", Toast.LENGTH_LONG).show();
                validateForm();
            }
        }
    };


    private void validateForm() {
        if (loginActivity.sharedPreferences.getString("email", null) == null) {
            // ToDo show Dialog Fragment
            // Title: Login Failed
            // Content: "Email Address / Username Doesn't Exits. Please create an account."
            // Neutral Action: 'OK' will close dialog
        } else {
            Toast.makeText(loginActivity.getApplicationContext(), "sharedPrefEmail is null", Toast.LENGTH_LONG).show();
            if (!emailAndSPEmailMatch) {
                // ToDo show Dialog Fragment
                // Title: Login Failed
                // Content: "You entered an email address that is not on file. Please try another email address."
                // Positive Action: 'Create Account' will go to Create Account Screen
                // Negative Action: 'CANCEL' will close dialog
            } else {
                Toast.makeText(loginActivity.getApplicationContext(), "emailAnsSPEmailMatch is true", Toast.LENGTH_LONG).show();
                if (!emailPasswordMatch) {
                    // ToDo show Dialog Fragment
                    // Title: Login Failed
                    // Content: "Email address and password don't match. Please try again."
                    // Neutral Action: 'OK' will close dialog
                } else {
                    Toast.makeText(loginActivity.getApplicationContext(), "emailPassworMatch is true", Toast.LENGTH_LONG).show();
                    login();
                }
            }
        }
    }

    private void login() {
        // ToDo saveSharedPreferences
        // ToDo go to MainActivity.Dashboard
        // ToDo Check if jseMember: if JSEMember x exist is SP or sp.JSEMember = false, checkIfJSEMember() from JSE database
        // checkIfJseMember() getDOB() and getSocial(), compare to JDB, if member = true -> update SP to JSEMember = true
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }
}
