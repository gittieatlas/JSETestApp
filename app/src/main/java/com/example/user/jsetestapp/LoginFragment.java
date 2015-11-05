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
    boolean isEmailPasswordEntered = false;
    boolean isEmailSaved = false;
    boolean isEmailEqualSavedEmail = false;
    boolean isEmailPasswordEqualSavedEmailPassword = false;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_login,
                container, false);

        initializeViews(rootView);
        registerListeners();
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
            isEmailPasswordEntered();
        }
    };

    OnClickListener buttonRightOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isEmailPasswordEntered) {
                loginActivity.showDialog("Login Failed", "All fields require a value.", null, null, "OK", R.drawable.ic_check_grey600_24dp, "login_failed_values");
            } else {
                validateForm();
            }
        }
    };

    OnClickListener buttonLeftOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            loginActivity.helperMethods.replaceFragment(R.id.container, loginActivity.register1Fragment, loginActivity.getResources().getString(R.string.toolbar_title_register1), loginActivity);
        }
    };

    OnClickListener forgotPasswordEditTextOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            loginActivity.showDialog("Send me my password", "'Email' label and EditText inputType=textEmailAddress",
                    "SEND", "CANCEL", "OK", R.drawable.ic_check_grey600_24dp, "forgot_password");
            //ToDo custom xml layout
        }
    };

    private boolean isEmailPasswordEntered() {
        if (!loginActivity.helperMethods.isEmpty(emailEditText) &&
                !loginActivity.helperMethods.isEmpty(passwordEditText)) {
            isEmailPasswordEntered = true;
        }
        return isEmailPasswordEntered;
    }

    private boolean isEmailSaved() {
        if (loginActivity.sharedPreferences.getString("email", null) != null) {
            isEmailSaved = true;
        }
        return isEmailSaved;
    }

    private boolean isEmailEqualSavedEmail() {
        if (loginActivity.sharedPreferences.getString("email", null).equals(emailEditText.getText().toString())) {
            isEmailEqualSavedEmail = true;
        }
        return isEmailEqualSavedEmail;
    }


    private boolean isEmailPasswordEqualSavedEmailPassword() {
        if (loginActivity.sharedPreferences.getString("email", null).equals(emailEditText.getText().toString()) &&
                loginActivity.sharedPreferences.getString("password", null).equals(passwordEditText.getText().toString())) {
            isEmailPasswordEqualSavedEmailPassword = true;
        }
        return isEmailPasswordEqualSavedEmailPassword;
    }

    private void validateForm() {
        if (!isEmailSaved()) {
            loginActivity.showDialog("Login Failed", "An Email Address / Username Doesn't Exits. Please create an account.",
                    "CREATE ACCOUNT", "CANCEL", null, R.drawable.ic_check_grey600_24dp, "login_failed_email_not_exist");
        } else {
            if (!isEmailEqualSavedEmail()) {
                loginActivity.showDialog("Login Failed", "You entered an email address that is not on file. Please try another email address.",
                        null, null, "OK", R.drawable.ic_check_grey600_24dp, "login_failed_email_not_match");
            } else {
                if (!isEmailPasswordEqualSavedEmailPassword()) {
                    loginActivity.showDialog("Login Failed", "Email address and password don't match. Please try again.",
                            null, null, "OK", R.drawable.ic_check_grey600_24dp, "login_failed_email_password_not_match");
                } else {
                    login();
                }
            }
        }
    }

    private void login() {
        loginActivity.switchToMainActivity();
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }
}
