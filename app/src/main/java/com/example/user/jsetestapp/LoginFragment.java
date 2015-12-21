package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private static int loginResult;


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
            Util.showDialogFragment(R.array.login_failed_invalid_email);

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
            //taskGetUser = loginActivity.databaseOperations.getUser(loginActivity.user);
            taskGetUser = new GetUser().execute();
        }
        // if internet status connection is false
        else {
            // Show Dialog: No Internet Connection
            Util.showDialogFragment(R.array.no_internet_connection);
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


    /**
     * Background Async Task to Get User
     */
    class GetUser extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // show progress dialog
            loginActivity.showProgressDialog("Logging in. Please wait...");
        }

        /**
         * Getting user from database
         *
         * @param params - information passed
         */
        protected Integer doInBackground(String... params) {
            // build parameters for http request
            List<NameValuePair> httpParams = new ArrayList<>();
            httpParams.add(new BasicNameValuePair("email", loginActivity.user.email));
            httpParams.add(new BasicNameValuePair("password", loginActivity.user.password));

            // get JsonObject of user from Json string
            JSONObject userJsonObject = HelperMethods.getJsonObject
                    (Util.getActivity().getString(R.string.url_get_user), httpParams);

            // if json is not equal to null
            if (userJsonObject != null) {
                getLoginResult(userJsonObject);
                return getUserFromDataBase(userJsonObject);
            } else {
                Log.e("Get User", "Couldn't get any login users");
            }
            return 0;
        }

        /**
         * After completing background task
         *
         * @param id - of user received from database
         **/
        protected void onPostExecute(Integer id) {
            // login was unsuccessful: result was equal to 0
            if (loginResult == 0) {
                // if fragment is visible, show dialog: username and password didn't match
                if (loginActivity.loginFragment.isVisible())
                    Util.showDialogFragment(R.array.login_failed_not_match);
            }
            // login was successful: result was equal to 1
            if (loginResult == 1) {
                // login was successful but id of user wasn't assigned
                if (id == 0 || loginActivity.user.getId() == 0) {
                    // if fragment is visible, show dialog: Unable to login at this time
                    if (loginActivity.loginFragment.isVisible())
                        Util.showDialogFragment(R.array.login_failed_msg);
                }
                // login was successful and id of user was assigned
                else {
                    // if fragment is visible, launch activity with main activity intent
                    if (loginActivity.loginFragment.isVisible())
                        Util.launchActivity(loginActivity.getLaunchMainActivityIntent("login"));
                }
            }

            // dismiss the dialog once done
            loginActivity.pDialog.dismiss();
        }

        @Override
        protected void onCancelled(Integer id) {
            super.onCancelled(id);
            loginActivity.pDialog.dismiss();
        }


    }

    /**
     * Function to get user from jsonObject
     *
     * @param userJsonObject - object that is holding http request info from database
     **/
    public int getUserFromDataBase(JSONObject userJsonObject) {
        // if username and password don't match
        if (loginResult == 0) {
            return 0;
        }
        // if username and password do match
        else {
            // create new user to hold user information
            User user = new User();

            // get each json node and assign it to user
            try {
                // set id
                user.setId(userJsonObject.getInt(
                        Util.getActivity().getString(R.string.TAG_ID)));
                // set first name
                user.setFirstName(userJsonObject.getString(
                        Util.getActivity().getString(R.string.TAG_FIRST_NAME)));
                // set last name
                user.setLastName(userJsonObject.getString(
                        Util.getActivity().getString(R.string.TAG_LAST_NAME)));
                // set email
                user.setEmail(userJsonObject.getString(
                        Util.getActivity().getString(R.string.TAG_EMAIL)));
                // set password
                user.setPassword(userJsonObject.getString(
                        Util.getActivity().getString(R.string.TAG_PASSWORD)));
                // set ssn
                user.setSsn(userJsonObject.getString(
                        Util.getActivity().getString(R.string.TAG_SSN)));
                // set location id
                user.setLocationId(userJsonObject.getInt(
                        Util.getActivity().getString(R.string.TAG_LOCATION_ID)));
                // set JSE student id
                user.setJseStudentId(userJsonObject.getString(
                        Util.getActivity().getString(R.string.TAG_JSE_STUDENT_ID)));
                // set dob
                user.setDob(userJsonObject.getString(
                        Util.getActivity().getString(R.string.TAG_DOB)));
                // set gender
                user.setGender(userJsonObject.getInt(
                        Util.getActivity().getString(R.string.TAG_GENDER)));

                // set user mainActivity to user
                loginActivity.user = user;

                // if everything was successful, return "id" from json object
                return userJsonObject.getInt(Util.getActivity().getString(R.string.TAG_ID));
            } catch (JSONException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    /**
     * Function to get loginResult from jsonObject
     *
     * @param userJsonObject - object that is holding http request info from database
     **/
    private void getLoginResult(JSONObject userJsonObject) {
        // reset loginResult
        loginResult = 1;

        // get "loginResult" from json object
        // 0 for no matches of username and password, 1 for a match
        try {
            loginResult = userJsonObject.getInt(Util.getStringValue(R.string.TAG_CHECK_LOGIN_RESULT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
