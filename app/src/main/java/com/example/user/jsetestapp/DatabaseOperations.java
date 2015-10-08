package com.example.user.jsetestapp;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class DatabaseOperations {

    //Controls

    //Activities
    MainActivity mainActivity;

    //Fragments

    //Variables
    String dbUrl;
    String user;
    String password;
    String result;

    public DatabaseOperations() {
        dbUrl = "jdbc:mysql://127.0.0.1:3306/test";
        user = "root";
        password = "";
    }

    public void Connect() {
        Connect task = new Connect();
        task.execute();
    }

    // The types specified here are the input data type, the progress type, and the result type
    private class Connect extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            // Runs on the UI thread before doInBackground
            // Good for toggling visibility of a progress indicator
            //progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        protected String doInBackground(String... strings) {
            // Some long-running task like downloading an image.
            String result = "";

            //declare DB url, username, and password
            String url = "jdbc:mysql://127.0.0.1:3306/test";
            String username = "testUser";
            String password = "testPassword";

            //declare connection, statement and resultset objects
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            //load jdbc driver for mysql database
            try {
                Class.forName("com.mysql.jdbc.Driver");

                publishProgress("Able to load driver");
            }catch(Exception e) {
                publishProgress("Unable to load driver " + e.toString());
            }

            //Establish connection using DriverManager
            try {
                //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
                connection = DriverManager.getConnection(url, username, password);
                publishProgress("Able to connect to database");
            } catch (SQLException e) {
                publishProgress("Unable to connect to database " + e.toString());
            }

            //if connection is successfully established, create statement
            if(connection != null) {
                try {
                    statement = connection.createStatement();
                    publishProgress("Able to create statement");
                } catch (SQLException e) {
                    publishProgress("Unable to create statement");
                }
            }

            //if statement is created successfully, execute query and get results
            if(statement != null) {
                try {
                    resultSet = statement.executeQuery("SELECT name FROM branches");
                    publishProgress("Able to execute query and get results");
                } catch (SQLException e) {
                    publishProgress("Unable to execute query and get results");
                }
            }

            //if resultset is received and is not empty,
            // iterate over resultset to get values
            if(resultSet != null) {
                try {
                    while(resultSet.next()) {
                        publishProgress("Value in 1st column " + resultSet.getString(1));
                    }
                } catch (SQLException e) {
                    publishProgress("Unable to iterate over resultset");
                }
            }

//            try {
//                resultSet.close();
//                statement.close();
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                publishProgress("Error closing connections");
//            }

            return result;
        }

        protected void onProgressUpdate(String... values) {
            // Executes whenever publishProgress is called from doInBackground
            // Used to update the progress indicator
            if (values != null) {
                for (String value : values) {
                    // shows a toast for every value we get
                    Toast.makeText(mainActivity.getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                }
            }
        }

        protected void onPostExecute(String result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
        }
    }




    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

}
