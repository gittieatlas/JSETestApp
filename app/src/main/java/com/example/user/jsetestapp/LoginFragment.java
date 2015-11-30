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

    //Controls
    View rootView;
    RelativeLayout rootLayout;
    Button buttonLeft, buttonRight;
    TextView forgotPasswordTextView;
    EditText emailEditText, passwordEditText;

    //Activities
    MainActivity mainActivity;
    LoginActivity loginActivity;
    AsyncTask taskGetUser;

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

         Util.setToolbarTitle(R.string.toolbar_title_login, loginActivity.toolbar);

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (loginActivity.user.getId()  != 0){
            loginActivity.switchToMainActivity("login");
        }
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

                Util.showDialog(HelperMethods.getDialogFragmentBundle(
                        getString(R.string.d_login_failed_values)
                ));

            } else {
                if (!isEmailValid()) {
                    Util.showDialog(HelperMethods.getDialogFragmentBundle(
                            getString(R.string.d_login_failed_invalid_email)
                    ));
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

            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_forgot_password)));


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
        isEmailValid = Util.isEmailAddressValid(emailEditText.getText().toString());
        return isEmailValid;
    }

    private void login() {
        loginActivity.user.setEmail(emailEditText.getText().toString());
        loginActivity.user.setPassword(passwordEditText.getText().toString());

        // check for Internet status and set true/false
        if (HelperMethods.checkInternetConnection(loginActivity.getApplicationContext())) {
            taskGetUser =  loginActivity.databaseOperations.getUser(loginActivity.user);
        } else {
            Util.showDialog(HelperMethods.getDialogFragmentBundle(
                    getString(R.string.d_no_internet_connection)
            ));

        }

    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }
}
