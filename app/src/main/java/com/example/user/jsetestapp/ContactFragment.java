package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.jsetestapp.DatabaseOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class ContactFragment extends Fragment {

    //Controls
    View rootView;

    //Activities
    MainActivity mainActivity;

    //Fragments


    //Variables
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String pass = "";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.contact_fragment, container, false);

        initializeViews(rootView);

        //testDB();

        return rootView;
    }

    private void initializeViews(View rootView) {

        mainActivity.setToolbarTitle(R.string.nav_contact);
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void testDB() {
        //TextView tv = (TextView)this.findViewById(R.id.text_view);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            /* System.out.println("Databaseection success"); */

            String result = "Database connection success\n";
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("select * from table_name");
//            ResultSetMetaData rsmd = rs.getMetaData();
//
//            while(rs.next()) {
//                result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
//                result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
//                result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
//            }
            Toast.makeText(mainActivity.getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
        catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(mainActivity.getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}