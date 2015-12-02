package com.example.user.jsetestapp;
//CLEANED
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
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        initializeViews(rootView);
        registerListeners();

        // Set up touch listener for non-text box views to hide keyboard
        HelperMethods.setupUI(rootView.findViewById(R.id.rootLayout));//  ToDo refactor / rename

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
    private void initializeViews(View rootView) {
        // initialize and reference controls
        rootLayout = (RelativeLayout) rootView.findViewById(R.id.rootLayout);
        emailEditText = (EditText) rootView.findViewById(R.id.emailEditText);
        passwordEditText = (EditText) rootView.findViewById(R.id.passwordEditText);
        buttonLeft = (Button) rootView.findViewById(R.id.buttonLeft);
        buttonRight = (Button) rootView.findViewById(R.id.buttonRight);
        forgotPasswordTextView = (TextView) rootView.findViewById(R.id.forgotPasswordTextView);
    }

    /**
     * Function to register listeners
     */
    private void registerListeners() {
        // set onClickListeners
        forgotPasswordTextView.setOnClickListener(forgotPasswordEditTextOnClickListener);
        buttonLeft.setOnClickListener(buttonLeftOnClickListener);
        buttonRight.setOnClickListener(buttonRightOnClickListener);
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
     * Function to validate form
     */
    private Boolean formValidates() {
        // if all required field controls on page don't have values
        if (!isValuesEntered()) {
            // Show dialog: Login Failed - missing required values
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_login_failed_values)));
        }
        // if all required field controls on page have values
        else {
            // if email address entered is not a valid email address
            if (!isEmailValid()) {
                // Show dialog: Login Failed - missing required values
                Util.showDialog(HelperMethods.getDialogFragmentBundle(
                        getString(R.string.d_login_failed_invalid_email)));
            } else {
                // if email address entered is a valid email address
                return true;
            }
        }
        // if isValuesEntered or isEmailValid return false
        return false;
    }

    /**
     * OnClickListener for buttonLeft
     */
    OnClickListener buttonLeftOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // inflate container with Register1Fragment
            loginActivity.helperMethods.replaceFragment(loginActivity.register1Fragment,
                    loginActivity.getResources().getString(R.string.toolbar_title_register1),
                    loginActivity, loginActivity.scrollView);
        }
    };

    /**
     * OnClickListener for forgotPasswordEditText
     */
    OnClickListener forgotPasswordEditTextOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            // Show dialog: Forgot Password - missing required values
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_forgot_password)));
        }
    };

    /**
     * Function to check if values for required fields were entered
     */
    private boolean isValuesEntered() {
        // return true if emailEditText and passwordEditText have values
        return !loginActivity.helperMethods.isEmpty(emailEditText) &&
                !loginActivity.helperMethods.isEmpty(passwordEditText);
    }

    /**
     * Function to check if value entered for email address contains a valid email address
     */
    private boolean isEmailValid() {
        // return true if emailEditText contains a valid email address
        return Util.isEmailAddressValid(emailEditText.getText().toString());
    }

    /**
     * Function to login the user
     */
    private void login() {
        // set users email and password
        loginActivity.user.setEmail(emailEditText.getText().toString());
        loginActivity.user.setPassword(passwordEditText.getText().toString());

        // if internet status connection is true
        if (HelperMethods.checkInternetConnection(loginActivity.getApplicationContext())) {

            // call AsyncTask taskGetUser
            taskGetUser = loginActivity.databaseOperations.getUser(loginActivity.user);
        }
        // if internet status connection is false
        else {
            // Show Dialog: No Internet Connection
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_no_internet_connection)));
        }
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
