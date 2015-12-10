package com.example.user.jsetestapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

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

        return new CreateNewUser().execute(user);
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
    class CreateNewUser extends AsyncTask<User, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * Runs on the UI thread before doInBackground
         * Good for toggling visibility of a progress indicator
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("Creating account. Please wait...");

        }

        /**
         * Creating user
         */
        protected String doInBackground(User... params) {
            result = "";
            insertResult = "";
            id = 0;

            addUserToDatabase();

            User user = params[0];
            // Building Parameters
            List<NameValuePair> httpParams = new ArrayList<>();
            httpParams.add(new BasicNameValuePair("firstName", user.getFirstName()));
            httpParams.add(new BasicNameValuePair("lastName", user.getLastName()));
            httpParams.add(new BasicNameValuePair("gender", Integer.toString(user.getGender(user) + 1)));
            httpParams.add(new BasicNameValuePair("dob", user.getDob().toString("yyyy-MM-dd")));
            httpParams.add(new BasicNameValuePair("ssn", user.getSsn()));
            httpParams.add(new BasicNameValuePair("email", user.getEmail()));
            httpParams.add(new BasicNameValuePair("password", user.getPassword()));
            httpParams.add(new BasicNameValuePair("locationId", Integer.toString(user.getLocationId())));
            // getting JSON Object
            // Note that create user url accepts POST method
            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(Util.getActivity().getString(R.string.url_create_user),
                    "POST", httpParams);

            // check log cat for response
            Log.d("Create User", json.toString());


            try {
                checkEmailResult = json.getString(Util.getActivity().getString(R.string.TAG_CHECK_EMAIL_RESULT));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (checkEmailResult.equals("0")) {
                // if email does not exist, see if insert was completed successfully
                try {
                    insertResult = json.getString(Util.getActivity().getString(R.string.TAG_INSERT_RESULT));
                    id = Integer.parseInt(json.getString(Util.getActivity().getString(R.string.TAG_ID)));
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

    public void addUserToDatabase(){

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
            showProgressDialog("Logging in. Please wait...");
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
            JSONObject json = jsonParser.makeHttpRequest(Util.getActivity().getString(R.string.url_get_user),
                    "POST", params);

            // check log cat fro response
            Log.d("Get User", json.toString());

            try {
                loginResult = json.getInt(Util.getActivity().getString(R.string.TAG_RESULT));
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
            JSONObject jsonGet = jsonParser.makeHttpRequest(Util.getActivity().getString(R.string.url_get_jse_student_id),
                    "POST", params);

            // check log cat for response
            Log.d("Get JSE Student ID", jsonGet.toString());

            try {
                result = jsonGet.getString(Util.getActivity().getString(R.string.TAG_RESULT));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jsonGet != null && !result.equals("0")) {

                try {

                    studentId = jsonGet.getString(Util.getActivity().getString(R.string.TAG_JSE_STUDENT_ID));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Building Parameters
                List<NameValuePair> paramsUpdate = new ArrayList<NameValuePair>();
                paramsUpdate.add(new BasicNameValuePair("id", Integer.toString(id)));
                paramsUpdate.add(new BasicNameValuePair("jseStudentId", studentId));

                JSONObject jsonUpdate = jsonParser.makeHttpRequest(Util.getActivity().getString(R.string.url_update_jse_student_id),
                        "POST", paramsUpdate);

                // check log cat for response
                Log.d("Update JSE Student ID", jsonUpdate.toString());

                try {
                    resultUpdate = jsonUpdate.getString(Util.getActivity().getString(R.string.TAG_RESULT));
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
            showProgressDialog("Updating account. Please wait...");
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
            JSONObject json = jsonParser.makeHttpRequest(Util.getActivity().getString(R.string.url_update_user),
                    "POST", params);

            // check log cat fro response
            Log.d("Update User", json.toString());


            try {
                result = json.getString(Util.getActivity().getString(R.string.TAG_RESULT));
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

            pDialog.dismiss();
        }

    }

    public void showProgressDialog(String message){
        pDialog = new ProgressDialog(loginActivity);
        pDialog.setMessage(message);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }


    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

}