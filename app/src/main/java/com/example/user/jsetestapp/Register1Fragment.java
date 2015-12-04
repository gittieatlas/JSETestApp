package com.example.user.jsetestapp;
// CLEANED
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Register1Fragment extends Fragment {

    // Declare Controls
    View rootView;
    Button buttonLeft, buttonRight;
    EditText emailEditText, passwordEditText, confirmPasswordEditText;

    // Declare Activities
    LoginActivity loginActivity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_register1,
                container, false);

        initializeViews();
        registerListeners();

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_register1, loginActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // give focus to emailEditText
        emailEditText.requestFocus();
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews() {
        // initialize and reference EditTexts
        emailEditText = (EditText) rootView.findViewById(R.id.emailEditText);
        passwordEditText = (EditText) rootView.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) rootView.findViewById(R.id.confirmPasswordEditText);

        // initialize and reference Buttons
        buttonLeft = (Button) rootView.findViewById(R.id.buttonLeft);
        buttonRight = (Button) rootView.findViewById(R.id.buttonRight);
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        buttonLeft.setOnClickListener(buttonLeftOnClickListener);
        buttonRight.setOnClickListener(buttonRightOnClickListener);

        // set onTouchListener for all non ediText controls to hide the soft keyboard
        HelperMethods.registerTouchListenerForNonEditText(rootView.findViewById(R.id.rootLayout));
    }

    /**
     * OnClickListener for buttonLeft
     */
    OnClickListener buttonLeftOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // this will be the "already have an account? Sign in" button

            // undo the last back stack transaction
            loginActivity.getFragmentManager().popBackStack();
        }
    };

    /**
     * OnClickListener for buttonRight
     */
    OnClickListener buttonRightOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // if form validates, go to create account step 2.
            // otherwise a dialog will display with errors found
            if (formValidates()) goToCreateAccountStep2();
        }
    };

    /**
     * Function to validate form
     * return boolean
     */
    private boolean formValidates() {
        // return true if requiredFieldsHaveValues, emailAddressIsValid, and passwordsMatch
        return requiredFieldsHaveValues() && emailAddressIsValid() && passwordsMatch();
    }

    /**
     * Function to check if required fields have values entered
     * return boolean
     */
    private boolean requiredFieldsHaveValues() {
        // if any if the required field controls don't have a value
        if (loginActivity.helperMethods.isEmpty(emailEditText) ||
                loginActivity.helperMethods.isEmpty(passwordEditText) ||
                loginActivity.helperMethods.isEmpty(confirmPasswordEditText)) {

            // Show dialog: Create Account Failed - missing required values
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_create_account_failed_values)));
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
            // Show dialog: Create Account Failed - email address invalid
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_create_account_failed_email)));

            return false;
        }
        return true;
    }

    /**
     * Function to check if passwords entered match
     * return boolean
     */
    private boolean passwordsMatch() {
        // if passwordEditText and confirmPasswordEditText don't match
        if (!Util.compareTwoStrings(
                passwordEditText.getText().toString(),
                confirmPasswordEditText.getText().toString())) {

            // Show dialog: Create Account Failed - passwords don't match
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_create_account_failed_values_not_match)));

            // clear passwordEditText and confirmPasswordEditText values
            passwordEditText.setText("");
            confirmPasswordEditText.setText("");

            return false;
        }
        return true;
    }

    /**
     * Function to move on to step 2 of create account
     */
    private void goToCreateAccountStep2() {
        // save entered email and password to User
        loginActivity.user.setEmail(emailEditText.getText().toString());
        loginActivity.user.setPassword(passwordEditText.getText().toString());

        // inflate container with Register2Fragment
        loginActivity.helperMethods.replaceFragment(loginActivity.register2Fragment,
                loginActivity.getResources().getString(R.string.toolbar_title_register2),
                loginActivity, loginActivity.scrollView);
    }

    /**
     * Function to set reference of LoginActivity
     *
     * @param loginActivity - reference to LoginActivity
     */
    public void setLoginActivity(LoginActivity loginActivity) {
        // set this loginActivity to loginActivity
        this.loginActivity = loginActivity;
    }
}