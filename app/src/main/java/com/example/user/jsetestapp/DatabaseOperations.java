package com.example.user.jsetestapp;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class DatabaseOperations {
    MainActivity mainActivity;

    public DatabaseOperations() {

    }

    public void main(String[] args) {


    };
    public void testDB(){
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String password = "";
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection(dbUrl, user, password);
            Toast.makeText(mainActivity.getApplicationContext(), "Database connected!", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            // handle the error
            Toast.makeText(mainActivity.getApplicationContext(), "Can not connect!" + ex.toString(), Toast.LENGTH_LONG).show();
        }
    }
//    String url = "jdbc:mysql://localhost:3306/test";
//    String username = "root";
//    String password = "";
//
//    try {
//        Connection connection = DriverManager.getConnection(url, username, password);
//        Toast.makeText(mainActivity.getApplicationContext(), "Database connected!", Toast.LENGTH_LONG).show();
//    } catch (SQLException e) {
//        Toast.makeText(mainActivity.getApplicationContext(), "Can not connect!", Toast.LENGTH_LONG).show();
//        throw new IllegalStateException("Cannot connect the database!", e);
//    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

}
