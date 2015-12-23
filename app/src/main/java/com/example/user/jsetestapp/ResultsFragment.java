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
import android.widget.TextView;

import java.util.ArrayList;

public class ResultsFragment extends Fragment
        implements RecyclerViewItemClickListener, RecyclerViewItemImageClickListener {

    // Declare Controls
    FloatingActionButton fab;
    private RecyclerView recyclerView;
    private TextView messageTextView;

    // Declare Activities
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);

        initializeViews(rootView);
        setupRecyclerView();
        setupFab();
        checkIfNoResults();

        // set navigation icon in toolbar
        mainActivity.toolbar.setNavigationIcon(getResources().
                getDrawable(R.drawable.ic_arrow_left_white_24dp));

        // set toolbar title
        Util.setToolbarTitle(R.string.toolbar_title_results, mainActivity.toolbar);

        // return the layout for this fragment
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Set navigation icon to transparent
        mainActivity.toolbar.setNavigationIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    /**
     * Function to initialize controls
     */
    private void initializeViews(View rootView) {
        // initialize and reference TextView
        messageTextView = (TextView) rootView.findViewById(R.id.messageTextView);

        // initialize and reference RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        // initialize and reference FloatingActionButton
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
    }

    /**
     * Function to set up Fab
     */
    private void setupFab() {
        // set onClickListener
        fab.setOnClickListener(fabListener);
    }

    /**
     * OnClickListener for fab
     */
    OnClickListener fabListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // if jse member: show dialog "call to schedule test"
            // if not jse member: show dialog "become a jse member"
            mainActivity.helperMethods.scheduleTest();
        }
    };

    /**
     * Function to set up RecyclerView
     */
    private void setupRecyclerView() {
        // initialize linearLayoutManager
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(mainActivity.getApplicationContext());
        // Set layout manager to position the items
        recyclerView.setLayoutManager(linearLayoutManager);
        // set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // setHasFixedSize to true
        recyclerView.setHasFixedSize(true);
        // add item decorator to recyclerView
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        // Create testsAdapter passing in testsFilteredArrayList
        TestsAdapter testsAdapter = new TestsAdapter(getDataSet());
        // attach the adapter to the recyclerView to populate items
        recyclerView.setAdapter(testsAdapter);
        // set recyclerView item click listener
        testsAdapter.setOnItemClickListener(this);
        // set recyclerView image click listener
        testsAdapter.setOnItemImageClickListener(this);
    }

    /**
     * Function to check if results were found
     */
    private void checkIfNoResults() {
        // if testsFilteredArrayList is empty
        if (mainActivity.testsFilteredArrayList.size() == 0)
            // display textView with message: "No results were found"
            messageTextView.setVisibility(View.VISIBLE);
        else
            // hide textView
            messageTextView.setVisibility(View.GONE);
    }

    /**
     * OnItemClickListener
     */
    @Override
    public void onItemClick(View view, int position) {
        // display schedule a test dialog
        mainActivity.helperMethods.scheduleTest();
    }

    /**
     * OnImageClickListener
     */
    @Override
    public void onImageItemClick(View view, int position) {
        // assign selected test to new test
        Test test = mainActivity.testsArrayList.get(position);
        // open ic_calendar_green_24dp and populate it with test information
        Util.calendarIntent("JSE Test at " + test.getLocation(), getTestAddress(test),
                null, test.getDate(), test.getTime());
    }

    /**
     * Function to get test address
     * @param test - of type Test
     * @return String address
     */
    private String getTestAddress(Test test) {
        // initialize address as an empty string
        String address = "";
        // loop through each location in locationsArrayList
        for (Location location : mainActivity.locationsArrayList) {
            // if test's location is equal to name of current location
            if (test.getLocation().equals(location.getName()))
                // assign location's address to address
                address = location.getAddress();
        }
        return address;
    }

    /**
     * Function to get dataSet
     * @return ArrayList of type TestDataObject
     */
    private ArrayList<TestDataObject> getDataSet() {
        // return testFilteredArrayList
        return mainActivity.testsFilteredArrayList;
    }

    /**
     * Function to set reference of MainActivity
     *
     * @param mainActivity - reference to MainActivity
     */
    public void setMainActivity(MainActivity mainActivity) {
        // set this mainActivity to mainActivity
        this.mainActivity = mainActivity;
    }
}