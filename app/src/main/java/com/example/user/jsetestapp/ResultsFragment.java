package com.example.user.jsetestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ResultsFragment extends Fragment implements View.OnClickListener {

    //Controls
    FloatingActionButton fab;
    View rootView;

    //Fragments


    //Activities
    MainActivity mainActivity;

    //Variables
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;




    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.results_fragment, container, false);

        initializeViews(rootView);
        mainActivity.setToolbarTitle(R.string.toolbar_title_tests);
        setupFab();
        setUpRecyclerView();


        return rootView;
    }

    private void setUpRecyclerView() {
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
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private ArrayList<DataObject> getDataSet() {

        return mainActivity.getTestsFitlteredArrayList();
    }

    private void initializeViews(View rootView) {

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
    }

    private void setupFab() {
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        mainActivity.callJse();
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }




}