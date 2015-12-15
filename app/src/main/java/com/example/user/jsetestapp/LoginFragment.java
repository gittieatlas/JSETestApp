package com.example.user.jsetestapp;

import android.app.Fragment;
import android.app.ProgressDialog;
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

    // testsJsonArray JSONArray
    private static int loginResult;

    // Progress Dialog
    private ProgressDialog pDialog;

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
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("Logging in. Please wait...");
        }

        /**
         * Creating product
         */
        protected Integer doInBackground(String... args) {
             return getUserFromDataBase();

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(Integer id) {
            // login result was equal to 0
            if (loginResult == 0){
                // show dialog: username and password didn't match
                Util.showDialogFragment(R.array.login_failed_not_match);
                }else{
                getUser(id);
                }

            // dismiss the dialog once done
            pDialog.dismiss();

        }

        public void showProgressDialog(String message){
            pDialog = new ProgressDialog(loginActivity);
            pDialog.setMessage(message);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }

    public int getUserFromDataBase() {
        // testsJsonArray JSONArray
        loginResult = 1;

        // Building Parameters
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("email", loginActivity.user.email));
        params.add(new BasicNameValuePair("password", loginActivity.user.password));

        // get JsonObject of user from Json string
        JSONObject userJsonObject = HelperMethods.getJsonObject
                (Util.getActivity().getString(R.string.url_get_user), params);


        // if json is not equal to null
        if (userJsonObject != null) {

            try {
                // try to get int with tag "loginResult" from json object
                loginResult = userJsonObject.getInt(Util.getStringValue(R.string.TAG_CHECK_LOGIN_RESULT));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // if username and password don't match
            if (loginResult == 0) {
                return 0;
            // if username and password match
            } else {
                User user = new User();
                try {
                    // Storing each json node in user
                    user.setId(userJsonObject.getInt(Util.getActivity().getString(R.string.TAG_ID)));
                    user.setFirstName(userJsonObject.getString(Util.getActivity().getString(R.string.TAG_FIRST_NAME)));
                    user.setLastName(userJsonObject.getString(Util.getActivity().getString(R.string.TAG_LAST_NAME)));
                    user.setEmail(userJsonObject.getString(Util.getActivity().getString(R.string.TAG_EMAIL)));
                    user.setPassword(userJsonObject.getString(Util.getActivity().getString(R.string.TAG_PASSWORD)));
                    user.setSsn(userJsonObject.getString(Util.getActivity().getString(R.string.TAG_SSN)));
                    user.setLocationId(userJsonObject.getInt(Util.getActivity().getString(R.string.TAG_LOCATION_ID)));
                    user.setJseStudentId(userJsonObject.getString(Util.getActivity().getString(R.string.TAG_JSE_STUDENT_ID)));
                    user.setDob(userJsonObject.getString(Util.getActivity().getString(R.string.TAG_DOB)));
                    user.setGender(userJsonObject.getString(Util.getActivity().getString(R.string.TAG_GENDER)));
                    user.setIsJseMember(userJsonObject.getString(Util.getActivity().getString(R.string.TAG_JSE_STUDENT_ID)));
                    // set user
                    loginActivity.user = user;

                    // return int with tag "id" from json object
                    return userJsonObject.getInt(Util.getActivity().getString(R.string.TAG_ID));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        } else {
            Log.e("Get User", "Couldn't get any login users");
            return 0;
        }
    }

    // ToDo come back to this
    public void getUser(int id) {

        if (loginActivity.loginFragment.isVisible()) {
            if (id == 0 || loginActivity.user.getId() == 0) {
                // show dialog: Unable to login at this time
                Util.showDialogFragment(R.array.login_failed_msg);
            } else {
                 //login successful
                // launch activity with main activity intent
                Util.launchActivity(loginActivity.getLaunchMainActivityIntent("login"));
            }
        }
    }

}
