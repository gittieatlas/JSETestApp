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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    // Progress Dialog
    private ProgressDialog pDialog;

    // testsJsonArray JSONArray
    JSONArray usersJsonArray = null;
    private static String result = "";
    private static String insertResult = "";
    private static String checkEmailResult = "";
    private static String resultUpdate = "";
    private static int id = 0;
    private static String studentId = "";
    private static int loginResult = 0;

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
    class GetUser extends AsyncTask<String, String, String> {

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
        protected String doInBackground(String... args) {
             return getUserFromDataBase();

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String result) {

            loginActivity.helperMethods.getUser(result);

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

    public String getUserFromDataBase(){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", loginActivity.user.email));
        params.add(new BasicNameValuePair("password", loginActivity.user.password));

        // getting JSON Object
        // Note that create user url accepts POST method
        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(Util.getActivity().getString(R.string.url_get_user),
                "POST", params);

        // check log cat for response
        Log.d("Get User", json.toString());

        try {
            loginResult = json.getInt(Util.getActivity().getString(R.string.TAG_RESULT));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // if json is not equal to null and result is not equal to 0
        if (json != null && loginResult != 0) {
            try {
                //  JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                usersJsonArray = json.getJSONArray(Util.getActivity().getString(R.string.TAG_USERS));

                // looping through All Tests
                for (int i = 0; i < usersJsonArray.length(); i++) {

                    JSONObject c = usersJsonArray.getJSONObject(i);

                    User user = new User();
                    user.setFirstName(c.getString(Util.getActivity().getString(R.string.TAG_FIRST_NAME)));
                    user.setId(Integer.parseInt(c.getString(Util.getActivity().getString(R.string.TAG_ID))));
                    user.setLastName(c.getString(Util.getActivity().getString(R.string.TAG_LAST_NAME)));
                    user.setGender(c.getString(Util.getActivity().getString(R.string.TAG_GENDER)));
                    user.setDob(c.getString(Util.getActivity().getString(R.string.TAG_DOB)));
                    user.setSsn(c.getString(Util.getActivity().getString(R.string.TAG_SSN)));
                    user.setEmail(c.getString(Util.getActivity().getString(R.string.TAG_EMAIL)));
                    user.setPassword(c.getString(Util.getActivity().getString(R.string.TAG_PASSWORD)));
                    user.setLocationId(c.getString(Util.getActivity().getString(R.string.TAG_LOCATION_ID)));
                    user.setIsJseMember(c.getString(Util.getActivity().getString(R.string.TAG_JSE_STUDENT_ID)));
                    user.setJseStudentId(c.getString(Util.getActivity().getString(R.string.TAG_JSE_STUDENT_ID)));

                    loginActivity.user = user;

                    //DoTo fix isCancelled method
                    //if (isCancelled()) break;


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Get User", "Couldn't get any login users");
        }

        return Integer.toString(loginResult);
    }

}
