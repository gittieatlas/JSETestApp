package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ResultsFragment extends Fragment implements View.OnClickListener {

    //Controls
    FloatingActionButton fab;
    View rootView;

    //Activities
    MainActivity mainActivity;

    //Fragments
RecyclerViewActivity recyclerViewActivity;

    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.results_fragment, container, false);

        initializeViews(rootView);
        setupFab();

        return rootView;
    }

    private void initializeViews(View rootView) {

//        recyclerViewActivity = new RecyclerViewActivity();
//        getFragmentManager().beginTransaction().add(R.id.resultsContainer, recyclerViewActivity).commit();

        mainActivity.setToolbarTitle(R.string.nav_tests);

    }

    private void setupFab() {
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.fab) {
            Snackbar
                    .make(rootView.findViewById(R.id.rootLayout),
                            "You clicked the FAB",
                            Snackbar.LENGTH_LONG)
                    .setAction("Action", this)
                    .show(); // Do not forget to show!
        }
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

}