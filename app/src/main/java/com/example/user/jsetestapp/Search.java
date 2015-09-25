package com.example.user.jsetestapp;

/**
 * Created by Rochel on 9/21/2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Search extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.search,
                container, false);

        TextView registrationDateTextView = (TextView) rootView.findViewById(R.id.registrationDateTextView);
        Button searchButton = (Button) rootView.findViewById(R.id.searchButton);
        return rootView;
    }
}
