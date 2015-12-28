package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Register1Fragment extends Fragment {

    // Declare Controls
    View rootView;
    LinearLayout logoTitleLinearLayout;
    CardView continueButton, formCardView;
    Button signInButton;
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

        loginActivity.showToolbar(false);
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
        continueButton = (CardView) rootView.findViewById(R.id.continueButton);
        signInButton = (Button) rootView.findViewById(R.id.signInButton);

        // initialize layouts
        formCardView = (CardView) rootView.findViewById(R.id.formCardView);
        logoTitleLinearLayout = (LinearLayout) rootView.findViewById(R.id.logoTitleLinearLayout);
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        continueButton.setOnClickListener(continueButtonOnClickListener);
        signInButton.setOnClickListener(signInButtonOnClickListener);

        // set onTouchListener for all non ediText controls to hide the soft keyboard
        HelperMethods.registerTouchListenerForNonEditText(rootView.findViewById(R.id.rootLayout));
    }

    /**
     * OnClickListener for continueButton
     */
    OnClickListener continueButtonOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // if form validates, go to create account step 2.
            // otherwise a dialog will display with errors found
            if (formValidates()) goToCreateAccountStep2();
        }
    };

    /**
     * OnClickListener for signInButton
     */
    OnClickListener signInButtonOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // undo the last back stack transaction
            loginActivity.getFragmentManager().popBackStack();
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
        if (Util.isEmpty(emailEditText) ||
                Util.isEmpty(passwordEditText) ||
                Util.isEmpty(confirmPasswordEditText)) {

            // Show dialog: Create Account Failed - missing required values
            Util.showDialogFragment(R.array.create_account_failed_values);
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
            Util.showDialogFragment(R.array.create_account_failed_email_not_valid);

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
            Util.showDialogFragment(R.array.create_account_failed_passwords_not_match);

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
        HelperMethods.replaceFragment(loginActivity.register2Fragment,
                loginActivity.getResources().getString(R.string.toolbar_title_register2));
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