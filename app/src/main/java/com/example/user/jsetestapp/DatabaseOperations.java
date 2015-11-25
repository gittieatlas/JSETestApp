package com.example.user.jsetestapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {

    LoginActivity loginActivity;
    MainActivity mainActivity;


    // Progress Dialog
    private ProgressDialog pDialog;

    // url to create new user
    private static String url_create_user = "http://phpstack-1830-4794-62139.cloudwaysapps.com/new_user_check_email_insert.php";
    // url to get user
    private static String url_get_user = "http://phpstack-1830-4794-62139.cloudwaysapps.com/login.php";
    // url to get id from student
    private static String url_get_jse_student_id = "http://phpstack-1830-4794-62139.cloudwaysapps.com/get_jse_student_id.php";
    // url to update jseStudentId
    private static String url_update_jse_student_id = "http://phpstack-1830-4794-62139.cloudwaysapps.com/update_jse_student_id.php";
    // url to update user
    private static String url_update_user = "http://phpstack-1830-4794-62139.cloudwaysapps.com/update_user.php";

    // JSON Node names
    private static final String TAG_RESULT = "result";
    private static final String TAG_INSERT_RESULT = "insertResult";
    private static final String TAG_CHECK_EMAIL_RESULT = "checkEmailResult";
    private static final String TAG_USERS = "users";
    private static final String TAG_ID = "id";
    private static final String TAG_FIRST_NAME = "firstName";
    private static final String TAG_LAST_NAME = "lastName";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_SSN = "ssn";
    private static final String TAG_LOCATION_ID = "locationId";
    private static final String TAG_DOB = "dob";
    private static final String TAG_JSE_STUDENT_ID = "jseStudentId";

    // testsJsonArray JSONArray
    JSONArray usersJsonArray = null;
    private static String result = "";
    private static String insertResult = "";
    private static String checkEmailResult = "";
    private static String resultUpdate = "";
    private static int id = 0;
    private static String studentId = "";
    private static int loginResult = 0;

    public AsyncTask newUser(User user) {

        return new CreateNewUser(user).execute();
    }

    public AsyncTask getUser(User user) {
        return new GetUser(user).execute();
    }

    public void getJseStudentId(User user) {

        new GetJseStudentId(user).execute();
    }

    public AsyncTask updateUser(User user) {

        return new UpdateUser(user).execute();
    }

    /**
     * Background Async Task to Create new product
     */
    class CreateNewUser extends AsyncTask<String, String, String> {

        String firstName, lastName, gender, dob, ssn, email, password, locationId;

        CreateNewUser(User user) {
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.dob = user.getDob().toString("yyyy-MM-dd");
            this.gender = Integer.toString(user.getGender(user) + 1);
            this.ssn = user.getSsn();
            this.locationId = Integer.toString(user.getLocationId());
        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(loginActivity);
            pDialog.setMessage("Creating account. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            result = "";
            insertResult = "";
            id = 0;
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("firstName", firstName));
            params.add(new BasicNameValuePair("lastName", lastName));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("dob", dob));
            params.add(new BasicNameValuePair("ssn", ssn));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("locationId", locationId));

            // getting JSON Object
            // Note that create user url accepts POST method
            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(url_create_user,
                    "POST", params);

            // check log cat fro response
            Log.d("Create User", json.toString());


            try {
                checkEmailResult = json.getString(TAG_CHECK_EMAIL_RESULT);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (checkEmailResult.equals("0")) {
                // if email does not exist, see if insert was completed successfully
                try {
                    insertResult = json.getString(TAG_INSERT_RESULT);
                    id = Integer.parseInt(json.getString(TAG_ID));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return insertResult;
            }

            return result;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String result) {

            loginActivity.helperMethods.createUser(result, id);

            // dismiss the dialog once done
            pDialog.dismiss();

        }


        protected void onCancelled(String result){

            //Toast.makeText(loginActivity.getContext(), "task onCancelled", Toast.LENGTH_LONG).show();
            if (id != 0) loginActivity.user.setId(id);
            pDialog.dismiss();
        }

    }

    /**
     * Background Async Task to Get User
     */
    class GetUser extends AsyncTask<String, String, String> {

        String email, password;

        GetUser(User user) {
            this.email = user.getEmail();
            this.password = user.getPassword();
        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(loginActivity);
            pDialog.setMessage("Logging in. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));

            // getting JSON Object
            // Note that create user url accepts POST method
            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(url_get_user,
                    "POST", params);

            // check log cat fro response
            Log.d("Get User", json.toString());

            try {
                loginResult = json.getInt(TAG_RESULT);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (json != null && loginResult != 0) {
                try {
                    //  JSONObject jsonObj = new JSONObject(json);

                    // Getting JSON Array node
                    usersJsonArray = json.getJSONArray(TAG_USERS);

                    // looping through All Tests
                    for (int i = 0; i < usersJsonArray.length(); i++) {

                        JSONObject c = usersJsonArray.getJSONObject(i);

                        User user = new User();
                        user.setFirstName(c.getString(TAG_FIRST_NAME));
                        user.setId(Integer.parseInt(c.getString(TAG_ID)));
                        user.setLastName(c.getString(TAG_LAST_NAME));
                        user.setGender(c.getString(TAG_GENDER));
                        //  DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
                        // user.dob = dtf.parseLocalDate(c.getString(TAG_DOB));
                        user.setDob(c.getString(TAG_DOB));
                        user.setSsn(c.getString(TAG_SSN));
                        user.setEmail(c.getString(TAG_EMAIL));
                        user.setPassword(c.getString(TAG_PASSWORD));
                        user.setLocationId(c.getString(TAG_LOCATION_ID));
                        user.setIsJseMember(c.getString(TAG_JSE_STUDENT_ID));
                        user.setJseStudentId(c.getString(TAG_JSE_STUDENT_ID));

                        loginActivity.user = user;

                        if (isCancelled()) break;


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("Get User", "Couldn't get any login users");
            }

            return Integer.toString(loginResult);
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String result) {

            loginActivity.helperMethods.getUser(result);

            // dismiss the dialog once done
            pDialog.dismiss();


        }



    }


    /**
     * Background Async Task to Get User
     */
    class GetJseStudentId extends AsyncTask<String, String, String> {

        String ssn, dob;
        int id;

        GetJseStudentId(User user) {
            this.id = user.getId();
            this.ssn = user.getSsn();
            this.dob = user.getDob().toString("yyyy-MM-dd");
        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * Getting jseStudentId
         */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ssn", ssn));
            params.add(new BasicNameValuePair("dob", dob));

            // getting JSON Object
            // Note that get jse student id url accepts POST method
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonGet = jsonParser.makeHttpRequest(url_get_jse_student_id,
                    "POST", params);

            // check log cat for response
            Log.d("Get JSE Student ID", jsonGet.toString());

            try {
                result = jsonGet.getString(TAG_RESULT);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jsonGet != null && !result.equals("0")) {

                try {

                    studentId = jsonGet.getString(TAG_JSE_STUDENT_ID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Building Parameters
                List<NameValuePair> paramsUpdate = new ArrayList<NameValuePair>();
                paramsUpdate.add(new BasicNameValuePair("id", Integer.toString(id)));
                paramsUpdate.add(new BasicNameValuePair("jseStudentId", studentId));

                JSONObject jsonUpdate = jsonParser.makeHttpRequest(url_update_jse_student_id,
                        "POST", paramsUpdate);

                // check log cat for response
                Log.d("Update JSE Student ID", jsonUpdate.toString());

                try {
                    resultUpdate = jsonUpdate.getString(TAG_RESULT);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (jsonUpdate != null && !resultUpdate.equals("0")) {
                    mainActivity.user.setJseStudentId(studentId);
                } else {
                    Log.e("Update JSE Student Id", "Couldn't update JSE Student Id");
                }


            } else {
                Log.e("Get JSE Student Id", "Couldn't get JSE Student Id");
            }
            return result;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String result) {
            //  mainActivity.test();
        }

    }


    /**
     * Background Async Task to Update User
     */
    class UpdateUser extends AsyncTask<String, String, String> {
        User user = new User();
        String id, firstName, lastName, gender, dob, ssn, email, password, locationId;

        UpdateUser(User user) {
            this.user = user;
            this.id = Integer.toString(user.getId());
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.dob = user.getDob().toString("yyyy-MM-dd");
            this.gender = Integer.toString(user.getGender(user) + 1);
            this.ssn = user.getSsn();
            this.locationId = Integer.toString(user.getLocationId());


        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(loginActivity);
            pDialog.setMessage("Updating account. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Updating User
         */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("firstName", firstName));
            params.add(new BasicNameValuePair("lastName", lastName));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("dob", dob));
            params.add(new BasicNameValuePair("ssn", ssn));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("locationId", locationId));

            // getting JSON Object
            // Note that create user url accepts POST method
            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(url_update_user,
                    "POST", params);

            // check log cat fro response
            Log.d("Update User", json.toString());


            try {
                result = json.getString(TAG_RESULT);
                //id = Integer.parseInt(json.getString(TAG_JSE_STUDENT_ID));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (json !=null && !result.equals("false") && !isCancelled()){
                loginActivity.user = user;
            }
            return result;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String result) {
            // dismiss the dialog once done

            loginActivity.helperMethods.updateUser(result);
            pDialog.dismiss();
        }


        protected void onCancelled(String result){

            Toast.makeText(loginActivity.getContext(), "task onCancelled", Toast.LENGTH_LONG).show();

            //loginActivity.helperMethods.updateUser(result);
            pDialog.dismiss();
        }

    }



    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }


    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

}