package com.example.user.jsetestapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {

    LoginActivity loginActivity;
    MainActivity mainActivity;

    // url to create new user
    private static String url_create_user = "http://phpstack-1830-4794-62139.cloudwaysapps.com/new_user_insert.php";
    // url to get user
    private static String url_get_user = "http://phpstack-1830-4794-62139.cloudwaysapps.com/login.php";
    // url to get id from student
    private static String url_get_jse_student_id = "http://phpstack-1830-4794-62139.cloudwaysapps.com/get_jse_student_id.php";
    // url to update jseStudentId
    private static String url_update_jse_student_id = "http://phpstack-1830-4794-62139.cloudwaysapps.com/update_jse_student_id.php";

    // JSON Node names
    private static final String TAG_RESULT = "result";
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
    private static String resultUpdate = "";
    private static int id = 0;
    private static String studentId = "";
    private static int loginResult = 0;
    //private static int jseStudentIdResult = 0;
    private static String jseStudentId = "";

    public void newUser(User user) {

        new CreateNewUser(user).execute();
    }

    public void getUser(User user) {

        new GetUser(user).execute();
    }

    public void getJseStudentId(User user) {

        new GetJseStudentId(user).execute();
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
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

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
                result = json.getString(TAG_RESULT);
                id = Integer.parseInt(json.getString(TAG_JSE_STUDENT_ID));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String result) {
            // dismiss the dialog once done
            // pDialog.dismiss();

            loginActivity.helperMethods.createUser(result, id);
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
                        //DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
                        // user.dob = dtf.parseLocalDate(c.getString(TAG_DOB));
                        // user.dob =LocalDate.parse(c.getString(TAG_DOB), DateTimeFormat.forPattern("yyyy-MM-dd"));
                        //Todo get dpb from tag
                        LocalDate date = new LocalDate("2010-05-05");
                        user.dob = date;
                        user.setSsn(c.getString(TAG_SSN));
                        user.setEmail(c.getString(TAG_EMAIL));
                        user.setPassword(c.getString(TAG_PASSWORD));
                        user.setLocationId(c.getString(TAG_LOCATION_ID));
                        user.setIsJseMember(c.getString(TAG_JSE_STUDENT_ID));
                        user.setJseStudentId(c.getString(TAG_JSE_STUDENT_ID));

                        loginActivity.user = user;


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
            // dismiss the dialog once done
            // pDialog.dismiss();


            loginActivity.helperMethods.getUser(result);
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
            this.dob = user.getDob().toString("yyyy-mm-dd");
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
            JSONObject json = jsonParser.makeHttpRequest(url_get_jse_student_id,
                    "POST", params);

            // check log cat for response
            Log.d("Get JSE Student ID", json.toString());

            try {
                result = json.getString(TAG_RESULT);
                studentId = json.getString(TAG_ID);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (json != null && !result.equals("0")){

                // Building Parameters
                List<NameValuePair> paramsUpdate = new ArrayList<NameValuePair>();
                paramsUpdate.add(new BasicNameValuePair("id", Integer.toString(id)));
                paramsUpdate.add(new BasicNameValuePair("studentId", studentId));

                JSONObject jsonUpdate = jsonParser.makeHttpRequest(url_update_jse_student_id,
                        "POST", paramsUpdate);

                // check log cat for response
                Log.d("Update JSE Student ID", jsonUpdate.toString());

                try {
                    resultUpdate = jsonUpdate.getString(TAG_RESULT);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (jsonUpdate != null && !resultUpdate.equals("0")){
                    mainActivity.user.setJseStudentId(studentId);
                }  else {
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
            // dismiss the dialog once done
            // pDialog.dismiss();
           // mainActivity.test();
        }

    }


    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }


    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

}