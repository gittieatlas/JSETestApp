package com.example.user.jsetestapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {

    LoginActivity loginActivity;

    // url to create new product
    private static String url_create_user = "http://phpstack-1830-4794-62139.cloudwaysapps.com/new_user_insert.php";

    // JSON Node names
    private static final String TAG_RESULT = "result";

    private static String result = "";

    public void newUser(User user) {

        new CreateNewUser(user).execute();
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
//            pDialog = new ProgressDialog(LoginActivity.this);
//            pDialog.setMessage("Creating Product..");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
//            String firstName = "Rivky";
//            String lastName = "Cohen";
//            String gender = "2";
//            String email = "rivkycohen27@gmail.com";
//            String password = "1234";
//            String ssn = "xxx-xx-9865";
//            String dob = "2016-05-26";
//            String locationId = "4346";

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


          loginActivity.helperMethods.createUser(result);
        }

    }


    public void setLoginActivity(LoginActivity loginActivity) {

        this.loginActivity = loginActivity;
    }

}