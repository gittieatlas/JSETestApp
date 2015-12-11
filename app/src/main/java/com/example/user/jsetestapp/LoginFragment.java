package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.AsyncTask;
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

    // Declare Controls
    View rootView;
    RelativeLayout rootLayout;
    Button buttonLeft, buttonRight;
    TextView forgotPasswordTextView;
    EditText emailEditText, passwordEditText;

    // Declare Activities
    LoginActivity loginActivity;

    // Declare Variables
    AsyncTask taskGetUser;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        initializeViews();
        registerListeners();

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_login, loginActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // if user does not exist
        if (loginActivity.user.getId() != 0) {
            // launch activity with main activity intent
            Util.launchActivity(loginActivity.getLaunchMainActivityIntent("login"));
        }
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews() {
        // initialize and reference RelativeLayouts
        rootLayout = (RelativeLayout) rootView.findViewById(R.id.rootLayout);

        // initialize and reference EditTexts
        rootLayout = (RelativeLayout) rootView.findViewById(R.id.rootLayout);
        emailEditText = (EditText) rootView.findViewById(R.id.emailEditText);
        passwordEditText = (EditText) rootView.findViewById(R.id.passwordEditText);

        // initialize and reference TextViews
        forgotPasswordTextView = (TextView) rootView.findViewById(R.id.forgotPasswordTextView);

        // initialize and reference Buttons
        buttonLeft = (Button) rootView.findViewById(R.id.buttonLeft);
        buttonRight = (Button) rootView.findViewById(R.id.buttonRight);
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        forgotPasswordTextView.setOnClickListener(forgotPasswordEditTextOnClickListener);
        buttonLeft.setOnClickListener(buttonLeftOnClickListener);
        buttonRight.setOnClickListener(buttonRightOnClickListener);

        // Set up touch listener for non-text box views to hide keyboard
        HelperMethods.registerTouchListenerForNonEditText(rootView.findViewById(R.id.rootLayout));
    }

    /**
     * OnClickListener for buttonRight
     */
    OnClickListener buttonRightOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // if form validates, login. otherwise a dialog will display with errors found
            if (formValidates()) login();
        }
    };


    /**
     * OnClickListener for buttonLeft
     */
    OnClickListener buttonLeftOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // inflate container with Register1Fragment
            HelperMethods.replaceFragment(loginActivity.register1Fragment,
                    loginActivity.getResources().getString(R.string.toolbar_title_register1));
        }
    };

    /**
     * OnClickListener for forgotPasswordEditText
     */
    OnClickListener forgotPasswordEditTextOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Show dialog: Forgot Password - missing required values
            Util.showDialogFragment(R.array.forgot_password);
        }
    };

    /**
     * Function to validate form
     * return boolean
     */
    private boolean formValidates() {
        // return true if requiredFieldsHaveValues, emailAddressIsValid, and passwordsMatch
        return requiredFieldsHaveValues() && emailAddressIsValid();
    }

    /**
     * Function to check if required fields have values entered
     * return boolean
     */
    private boolean requiredFieldsHaveValues() {
        // if any if the required field controls don't have a value
        if (Util.isEmpty(emailEditText) ||
                Util.isEmpty(passwordEditText)) {

            // Show dialog: Login Failed - missing required values
            Util.showDialogFragment(R.array.login_failed_values);
            return false;
        }
        return true;
    }

    /**
     * Function to check if email address entered is a valid email address
     * return boolean
     */
    private boolean emailAddressIsValid() {
        // if email entered is not a valid email address
        if (!Util.isEmailAddressValid(emailEditText)) {
            // Show dialog: Login Failed - email address invalid
            Util. showDialogFragment(R.array.login_failed_invalid_email);

            return false;
        }
        return true;
    }

    /**
     * Function to login the user
     */
    private void login() {
        // set users email and password
        loginActivity.user.setEmail(emailEditText.getText().toString());
        loginActivity.user.setPassword(passwordEditText.getText().toString());

        // if internet status connection is true
        if (Util.checkInternetConnection()) {

            // call AsyncTask to get user
            taskGetUser = loginActivity.databaseOperations.getUser(loginActivity.user);
        }
        // if internet status connection is false
        else {
            // Show Dialog: No Internet Connection
            Util.showDialogFragment(R.array.no_internet_connection);
        }
    }

    /**
     * Function to set reference of LoginActivity
     * @param loginActivity - reference to LoginActivity
     */
    public void setLoginActivity(LoginActivity loginActivity) {
        // set this loginActivity to loginActivity
        this.loginActivity = loginActivity;
    }
}
