package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ResultsFragment extends Fragment implements View.OnClickListener {

    //Controls
    FloatingActionButton fab;
    View rootView;

    //Activities
    MainActivity mainActivity;

    //Fragments

    //Variables
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.results_fragment, container, false);

        initializeViews(rootView);
        setupFab();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mainActivity.getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(mainActivity.getApplicationContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        // Code to Add an item with default animation
        //((RecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((RecyclerViewAdapter) mAdapter).deleteItem(index);

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((RecyclerViewAdapter) mAdapter).setOnItemClickListener(new
                                                                          RecyclerViewAdapter.MyClickListener() {
                                                                              @Override
                                                                              public void onItemClick(int position, View v) {
                                                                                  Log.i(LOG_TAG, " Clicked on Item " + position);
                                                                              }
                                                                          });
    }

    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < 10; index++) {
            DataObject obj = new DataObject("Brooklyn - HASC ",
                    "Thuesday " , " 10:30 AM " , " September 8 2015 ","Deadline to register " , "September 7 2015");
            results.add(index, obj);
        }
        return results;
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