package com.example.user.jsetestapp;

import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultsFragment extends Fragment implements RecyclerViewItemClickListener, RecyclerViewItemImageClickListener {

    //Controls
    FloatingActionButton fab;
    View rootView;
    private RecyclerView recyclerView;

    //Fragments

    //Activities
    MainActivity mainActivity;
    SearchFragment searchFragment;

    //Variables


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_results, container, false);

        initializeViews(rootView);

        mainActivity.setToolbarTitle(R.string.toolbar_title_results);
        //Set icon
        mainActivity.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left_white_24dp));

        setupFab();
        setupRecyclerView();

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Set navigation icon
        mainActivity.toolbar.setNavigationIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    private void initializeViews(View rootView) {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
    }

    private void setupFab() {

        fab.setOnClickListener(fabListener);
    }

    OnClickListener fabListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            mainActivity.helperMethods.scheduleTest();
        }
    };

    private void setupRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity.getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getDataSet());
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClickListener(this);
        recyclerViewAdapter.setOnItemImageClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {

        mainActivity.helperMethods.scheduleTest();
    }

    @Override
    public void onImageItemClick(View view, int position) {
        Toast.makeText(mainActivity.getApplicationContext(), "Image Click ", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<DataObject> getDataSet() {
        return mainActivity.getTestsFilteredArrayList();
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }
}