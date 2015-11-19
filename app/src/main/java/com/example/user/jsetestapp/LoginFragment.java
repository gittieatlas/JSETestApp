package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
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
    //boolean isValuesEntered = false;
    boolean isEmailValid = false;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_login,
                container, false);

        initializeViews(rootView);
        loginActivity.helperMethods.setupUI(rootView.findViewById(R.id.rootLayout));
        registerListeners();
        //  ToDo fix this error
        // loginActivity.setToolbarTitle(R.string.toolbar_title_login);

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
        forgotPasswordTextView.setOnClickListener(forgotPasswordEditTextOnClickListener);
        buttonLeft.setOnClickListener(buttonLeftOnClickListener);
        buttonRight.setOnClickListener(buttonRightOnClickListener);
    }

    OnClickListener buttonRightOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isValuesEntered()) {
                loginActivity.showDialog(getString(R.string.d_login_failed),
                        getString(R.string.d_fields_require_values_msg),
                        null, null, getString(R.string.d_ok),
                        R.drawable.ic_alert_grey600_24dp,
                        "login_failed_values");
            } else {
                if (!isEmailValid()) {
                    loginActivity.showDialog(getString(R.string.d_login_failed),
                            getString(R.string.d_invalid_email_msg),
                            null, null, getString(R.string.d_ok),
                            R.drawable.ic_alert_grey600_24dp,
                            "login_failed_invalid_email");
                } else {
                    login();
                }
            }
        }
    };

    OnClickListener buttonLeftOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            loginActivity.helperMethods.replaceFragment(loginActivity.register1Fragment,
                    loginActivity.getResources().getString(R.string.toolbar_title_register1),
                    loginActivity, loginActivity.scrollView);
        }
    };

    OnClickListener forgotPasswordEditTextOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            loginActivity.showDialog("Send me my password", "Enter your email address",
                    "SEND", null, null, R.drawable.ic_settings_grey600_24dp, "forgot_password");

        }
    };


    private boolean isValuesEntered() {
        if (!loginActivity.helperMethods.isEmpty(emailEditText) &&
                !loginActivity.helperMethods.isEmpty(passwordEditText)) {
            return true;
        } else
            return false;
    }

    private boolean isEmailValid() {
        isEmailValid = loginActivity.helperMethods.isEmailAddressValid(emailEditText.getText().toString());
        return isEmailValid;
    }

    private void login() {
        loginActivity.user.setEmail(emailEditText.getText().toString());
        loginActivity.user.setPassword(passwordEditText.getText().toString());

        loginActivity.databaseOperations.getUser(loginActivity.user);


    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }
}
